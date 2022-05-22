package com.example.mdsback.DTOs;

import com.example.mdsback.models.Harmony;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class HarmonyDTO {
    private Long id;
    private String name;
    private Integer totalQuantizedSteps;
    private Collection<NoteDTO> notes = new ArrayList<>();

    public HarmonyDTO(Harmony harmony) {
        this.id = harmony.getId();
        this.name = harmony.getName();
        this.totalQuantizedSteps = harmony.getTotalQuantizedSteps();
        this.notes = harmony.getNotes()
                .stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
    }
}
