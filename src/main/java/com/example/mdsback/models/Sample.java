package com.example.mdsback.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "Sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "totalTime")
    private float totalTime;

    @Column(name = "likes")
    private Long likes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "sample_notes",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sample_id", referencedColumnName = "id"))
    @Column(name = "notes")
    private Collection<Note> notes;

    @ManyToMany(mappedBy = "samples")
    @Column(name = "playlists")
    private Collection<Playlist> playlists;

    @ManyToOne
    private User user;
}
