package com.example.mdsback.DTOs;

import com.example.mdsback.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private List<SampleDTO> samples = new ArrayList<>();
    private List<PlaylistDTO> playlists = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.samples = user.getSamples()
                .stream()
                .map(SampleDTO::new)
                .collect(Collectors.toList());
        this.playlists = user.getPlaylists()
                .stream()
                .map(PlaylistDTO::new)
                .collect(Collectors.toList());
    }
}
