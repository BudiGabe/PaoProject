import * as mm from '@magenta/music'
import { NoteSequence } from '@magenta/music'
import * as sampleService from "./sample-service";
import * as harmonyService from "./harmony-service"

const SOUNDFONT_URL =
    'https://storage.googleapis.com/magentadata/js/soundfonts/sgm_plus'

const JUMP_SONG = {
    name: "Jump Song",
    notes: [
        {pitch: 62, startTime: 0.5, endTime: 0.75},
        {pitch: 64, startTime: 1.25, endTime: 1.5},
        {pitch: 60, startTime: 2.0, endTime: 2.25},
        {pitch: 60, startTime: 2.75, endTime: 3.0},
        {pitch: 62, startTime: 3.25, endTime: 3.5},
        {pitch: 62, startTime: 3.75, endTime: 4.5},
        {pitch: 64, startTime: 4.5, endTime: 4.75},
        {pitch: 60, startTime: 5.25, endTime: 5.5},
        {pitch: 57, startTime: 5.75, endTime: 6.25},
        {pitch: 55, startTime: 6.25, endTime: 6.75},
        {pitch: 55, startTime: 6.75, endTime: 8.0},
    ],
    totalTime: 8
};
const TWINKLE_TWINKLE = {
    name: "test",
    notes: [
        {pitch: 60, startTime: 0.0, endTime: 0.5},
        {pitch: 60, startTime: 0.5, endTime: 1.0},
        {pitch: 67, startTime: 1.0, endTime: 1.5},
        {pitch: 67, startTime: 1.5, endTime: 2.0},
        {pitch: 69, startTime: 2.0, endTime: 2.5},
        {pitch: 69, startTime: 2.5, endTime: 3.0},
        {pitch: 67, startTime: 3.0, endTime: 4.0},
        {pitch: 65, startTime: 4.0, endTime: 4.5},
        {pitch: 65, startTime: 4.5, endTime: 5.0},
        {pitch: 64, startTime: 5.0, endTime: 5.5},
        {pitch: 64, startTime: 5.5, endTime: 6.0},
        {pitch: 62, startTime: 6.0, endTime: 6.5},
        {pitch: 62, startTime: 6.5, endTime: 7.0},
        {pitch: 60, startTime: 7.0, endTime: 8.0},
    ],
    totalTime: 8
};


// Generates a harmony based on Bach's composition starting from a given sample
// The new harmony and sample are saved to be used in further combinations
export async function generate_harmony(seq) {
    console.log("Sequence: ")
    console.log(seq)

    let coconet = new mm.Coconet('https://storage.googleapis.com/magentadata/js/checkpoints/coconet/bach')
    await coconet.initialize()
    const start = performance.now();
    const output = await coconet.infill(seq)
    const fixedOutput =
        replaceInstruments(mergeConsecutiveNotes(output), seq);
    play_sample(fixedOutput)
    coconet.dispose();

    await harmonyService.saveHarmony(fixedOutput);
    await sampleService.saveSample(seq)
    // save song with both
}

// Method takes 2 simple samples from db and generates a new one from those
async function combine_sample() {
    let music_vae = new mm.MusicVAE('https://storage.googleapis.com/magentadata/js/checkpoints/music_vae/mel_4bar_small_q2');
    await music_vae.initialize();

    const samples = await sampleService.getSamples()

    const numInterpolations = 11;
    const track1 = mm.sequences.quantizeNoteSequence(
        samples[Math.floor(Math.random() * samples.length)], 4);
    const track2 = mm.sequences.quantizeNoteSequence(
        samples[Math.floor(Math.random() * samples.length)], 4);


    const new_sample = music_vae.interpolate([track1, track2], numInterpolations)
        .then((samples) => {
            return samples[5];
        });

    return new_sample
}

// Helper funtions used to play the generated sample/harmony
function play_sample(sample) {
    const Player = new mm.Player();
    if (Player.isPlaying()) {
        Player.stop()
    } else {
        Player.start(sample);
    }
}

function replaceInstruments(originalSequence, replaceSequence) {
    const instrumentsInOriginal =
        new Set(originalSequence.notes.map(n => n.instrument));
    const instrumentsInReplace =
        new Set(replaceSequence.notes.map(n => n.instrument));

    const newNotes = [];
    // Go through the original sequence, and only keep the notes for instruments
    // *not* in the second sequence.
    originalSequence.notes.forEach(n => {
        if (!instrumentsInReplace.has(n.instrument)) {
            newNotes.push(NoteSequence.Note.create(n));
        }
    });
    // Go through the second sequence and add all the notes for instruments in the
    // first sequence.
    replaceSequence.notes.forEach(n => {
        if (instrumentsInOriginal.has(n.instrument)) {
            newNotes.push(NoteSequence.Note.create(n));
        }
    });

    // Sort the notes by instrument, and then by time.
    const output = clone(originalSequence);
    output.notes = newNotes.sort((a, b) => {
        const voiceCompare = a.instrument - b.instrument;
        if (voiceCompare) {
            return voiceCompare;
        }
        return a.quantizedStartStep - b.quantizedStartStep;
    });
    return output;
}

function mergeConsecutiveNotes(sequence) {
    const output = clone(sequence);
    output.notes = [];

    // Sort the input notes.
    const newNotes = sequence.notes.sort((a, b) => {
        const voiceCompare = a.instrument - b.instrument;
        if (voiceCompare) {
            return voiceCompare;
        }
        return a.quantizedStartStep - b.quantizedStartStep;
    });

    // Start with the first note.
    const note = new NoteSequence.Note();
    note.pitch = newNotes[0].pitch;
    note.instrument = newNotes[0].instrument;
    note.quantizedStartStep = newNotes[0].quantizedStartStep;
    note.quantizedEndStep = newNotes[0].quantizedEndStep;
    output.notes.push(note);
    let o = 0;

    for (let i = 1; i < newNotes.length; i++) {
        const thisNote = newNotes[i];
        const previousNote = output.notes[o];
        // Compare next note's start time with previous note's end time.
        if (previousNote.instrument === thisNote.instrument &&
            previousNote.pitch === thisNote.pitch &&
            thisNote.quantizedStartStep === previousNote.quantizedEndStep &&
            // Doesn't start on the measure boundary.
            thisNote.quantizedStartStep % 16 !== 0) {
            // If the next note has the same pitch as this note and starts at the
            // same time as the previous note ends, absorb the next note into the
            // previous output note.
            output.notes[o].quantizedEndStep +=
                thisNote.quantizedEndStep - thisNote.quantizedStartStep;
        } else {
            // Otherwise, append the next note to the output notes.
            const note = new NoteSequence.Note();
            note.pitch = newNotes[i].pitch;
            note.instrument = newNotes[i].instrument;
            note.quantizedStartStep = newNotes[i].quantizedStartStep;
            note.quantizedEndStep = newNotes[i].quantizedEndStep;
            output.notes.push(note);
            o++;
        }
    }
    return output;
}

function clone(ns) {
    return NoteSequence.decode(NoteSequence.encode(ns).finish());
}

export { combine_sample, play_sample }
