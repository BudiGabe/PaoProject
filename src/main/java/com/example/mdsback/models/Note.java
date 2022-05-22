package com.example.mdsback.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pitch")
    private int pitch;

    @Column(name = "start_time")
    private float startTime;

    @Column(name = "end_time")
    private float endTime;

    @ManyToMany(mappedBy = "notes")
    @Column(name = "samples")
    private Collection<Sample> samples;

    @ManyToMany(mappedBy = "notes")
    @Column(name = "harmonies")
    private Collection<Harmony> harmonies;
}
