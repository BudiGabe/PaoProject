package com.example.mdsback.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AudioRecording {
    protected String name;
    protected Long length;
}
