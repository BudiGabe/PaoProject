package com.example.mdsback.DTOs;

import com.example.mdsback.models.Note;
import lombok.Data;

@Data
public class NoteDTO {
    private int pitch;
    private float startTime;
    private float endTime;

    public NoteDTO() {
    }

    public NoteDTO(Note note) {
        this.pitch = note.getPitch();
        this.startTime = note.getStartTime();
        this.endTime = note.getEndTime();
    }
}
