package com.example.mdsback.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "harmony")
public class Harmony {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name ="totalQuantizedSteps")
    private Integer totalQuantizedSteps;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "sample_notes",
            joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sample_id", referencedColumnName = "id"))
    @Column(name = "notes")
    private Collection<Note> notes;


    @ManyToOne
    private User user;

}
