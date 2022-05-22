package com.example.mdsback.DTOs;

import com.example.mdsback.models.Sample;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class SampleDTO {
    private Long id;
    private String name;
    private float totalTime;
    private Long likes;
    private Collection<NoteDTO> notes = new ArrayList<>();

    public SampleDTO() {
    }

    // Add playlists if needed
    public SampleDTO(Sample sample) {
        this.id = sample.getId();
        this.name = sample.getName();
        this.totalTime = sample.getTotalTime();
        this.likes = sample.getLikes();
        this.notes = sample.getNotes()
                .stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
    }
}
