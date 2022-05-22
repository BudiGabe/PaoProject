package com.example.mdsback.services;

import com.example.mdsback.models.Harmony;
import com.example.mdsback.models.Note;
import com.example.mdsback.repositories.HarmonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class HarmonyService {
    @Autowired
    private HarmonyRepository harmonyRepository;
    private final LoggingService logger = new LoggingService();

    public Harmony findById(Long id) {
        logger.log("Getting harmony with id " + id);
        return harmonyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Harmony not found"));
    }

    public Collection<Harmony> findAll() {
        logger.log("Getting all harmonies");
        return harmonyRepository.findAll();
    }

    public Harmony findByName(String name) {
        logger.log("Getting harmony with name " + name);
        return harmonyRepository.findByName(name);
    }


    public Harmony create(Harmony harmony) {
        logger.log("Creating harmony " + harmony.getName());
        return harmonyRepository.save(harmony);
    }

    public long delete(String name){
        logger.log("Deleting harmony with name " + name);
        return harmonyRepository.deleteByName(name);
    }


    public Harmony extendHarmony(Long harmonyId, List<Note> notes) {
        logger.log("Extending harmony " + harmonyId + " by " + notes.size());
        Harmony harmonyToChange = this.findById(harmonyId);
        harmonyToChange.getNotes().addAll(notes);
        return harmonyRepository.save(harmonyToChange);
    }


}
