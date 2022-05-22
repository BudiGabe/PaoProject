package com.example.mdsback.repositories;

import com.example.mdsback.models.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
