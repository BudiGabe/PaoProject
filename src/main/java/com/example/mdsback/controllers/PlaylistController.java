package com.example.mdsback.controllers;

import com.example.mdsback.DTOs.PlaylistDTO;
import com.example.mdsback.models.Playlist;
import com.example.mdsback.repositories.PlaylistRepository;
import com.example.mdsback.services.PlaylistService;
import com.example.mdsback.services.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private PlaylistService playlistService;

    @CrossOrigin
    @GetMapping("/{id}")
    public PlaylistDTO findById(@PathVariable Long id) {
        return new PlaylistDTO(playlistService.findById(id));
    }

    @CrossOrigin
    @GetMapping
    public List<PlaylistDTO> findAll() {
        return playlistService.findAll()
                .stream()
                .map(PlaylistDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Playlist create(@RequestBody Playlist playlist) {
        return playlistService.create(playlist);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{playlistId}/{sampleId}")
    public void addSampleById(@PathVariable Long playlistId, @PathVariable Long sampleId) {
        playlistService.addSampleById(playlistId, sampleId);
    }


    @CrossOrigin
    @DeleteMapping("{name}")
    public long delete(@PathVariable String name){
        return playlistService.delete(name);
    }
}
