package com.example.mdsback.controllers;

import com.example.mdsback.DTOs.HarmonyDTO;
import com.example.mdsback.models.Harmony;
import com.example.mdsback.models.Note;
import com.example.mdsback.repositories.HarmonyRepository;
import com.example.mdsback.services.HarmonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/harmony")
public class HarmonyController {
    @Autowired
    private HarmonyRepository harmonyRepository;

    @Autowired
    private HarmonyService harmonyService;

    @CrossOrigin
    @GetMapping("/id/{id}")
    public HarmonyDTO findById(@PathVariable Long id) {
        Harmony harmony = harmonyService.findById(id);
        return new HarmonyDTO(harmony);
    }


    @CrossOrigin
    @GetMapping
    public List<HarmonyDTO> findAll() {
        return harmonyRepository.findAll()
                .stream()
                .map(HarmonyDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/name/{name}")
    public HarmonyDTO findByName(@PathVariable String name) {
        return new HarmonyDTO(harmonyService.findByName(name));
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Harmony create(@RequestBody Harmony harmony) {
        return harmonyService.create(harmony);
    }


    @CrossOrigin
    @DeleteMapping("{name}")
    public long delete(@PathVariable String name){
        return harmonyService.delete(name);
    }

    @CrossOrigin
    @PatchMapping("{harmonyId}")
    public HarmonyDTO extendHarmony(@PathVariable Long harmonyId, @RequestBody List<Note> notes) {
        return new HarmonyDTO(harmonyService.extendHarmony(harmonyId, notes));
    }
}
