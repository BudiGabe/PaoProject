package com.example.mdsback.DTOs;

import com.example.mdsback.models.Playlist;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class PlaylistDTO {
    private String name;
    private Collection<SampleDTO> samples = new ArrayList<>();

    public PlaylistDTO() {}

    public PlaylistDTO(Playlist playlist) {
        this.name = playlist.getName();
        this.samples = playlist.getSamples()
                .stream()
                .map(SampleDTO::new)
                .collect(Collectors.toList());
    }
}
