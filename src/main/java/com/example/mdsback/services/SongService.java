package com.example.mdsback.services;

import com.example.mdsback.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Service
public class SongService {
    @Autowired
    private HarmonyService harmonyService = new HarmonyService();
    @Autowired
    private SampleService sampleService = new SampleService();

    private final LoggingService logger = new LoggingService();

    public Song generateRandomSong() {
        logger.log("Getting all samples");
        Collection<Sample> samples = sampleService.findAll();
        logger.log("Getting all harmonies");
        Collection<Harmony> harmonies = harmonyService.findAll();

        Random random = new Random();
        int randomSampleInd = random.nextInt(samples.size());
        int randomHarmonyInd = random.nextInt(harmonies.size());

        logger.log("Getting a random sample");
        Sample randomSample = samples.stream()
                .skip(randomSampleInd)
                .findFirst()
                .orElseThrow();

        logger.log("Getting a random harmony");
        Harmony randomHarmony = harmonies.stream()
                .skip(randomHarmonyInd)
                .findFirst()
                .orElseThrow();

        logger.log("Building song");
        return Song.builder()
                .name("Random sample")
                .length(100L)
                .melody(randomSample)
                .harmony(randomHarmony)
                .build();
    }

    public Podcast generateRandomPodcast(int i) {
        logger.log("Building podcast");
        return Podcast.builder()
                .name("Random Podcast")
                .length((long) i)
                .host("Host" + i)
                .guest("Guest" + i)
                .build();
    }

    public Collection<AudioRecording> getRecordings(int num) {
        logger.log("Generating new recordings");
        Collection<AudioRecording> recordings = new ArrayList<>();
        for(int i = 0; i < num; i++) {
            AudioRecording recording;
            if (i % 2 == 0) {
                logger.log("Generating song");
                recording = generateRandomSong();
            }
            else {
                logger.log("GEnerating podcast");
                recording = generateRandomPodcast(i);
            }

            recordings.add(recording);
        }

        return recordings;
    }
}
