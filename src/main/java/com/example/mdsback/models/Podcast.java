package com.example.mdsback.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Podcast extends AudioRecording {
    private String host;
    private String guest;
}
