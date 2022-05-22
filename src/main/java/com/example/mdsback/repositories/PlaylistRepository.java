package com.example.mdsback.repositories;

import com.example.mdsback.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    long deleteByName(String name);
}
