package com.example.mdsback.services;

import com.example.mdsback.models.Playlist;
import com.example.mdsback.models.Sample;
import com.example.mdsback.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SampleService sampleService;
    private final LoggingService loggingService = new LoggingService();

    public Playlist findById(Long id) {
        loggingService.log("Searching for playlist with id " + id);
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found"));
    }

    public Collection<Playlist> findAll() {
        loggingService.log("Getting all playlists");
        return playlistRepository.findAll();
    }

    public Playlist create(Playlist playlist) {
        loggingService.log("Saving playlist " + playlist.getName());
        return playlistRepository.save(playlist);
    }


    public void addSampleById(Long playlistId, Long sampleId) {
        loggingService.log("Adding sample " + sampleId + "to playlist " + playlistId);
        Playlist playlistToChange = findById(playlistId);
        Sample sampleToAdd = sampleService.findById(sampleId);

        playlistToChange.getSamples().add(sampleToAdd);
        playlistRepository.save(playlistToChange);
    }

    public long delete(String name){
        loggingService.log("Deleting playlist " + name);
        return playlistRepository.deleteByName(name);
    }

}
