package com.example.mdsback.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class Song extends AudioRecording {
    // Sample used to generate the harmony
    private Sample melody;
    private Harmony harmony;
}
