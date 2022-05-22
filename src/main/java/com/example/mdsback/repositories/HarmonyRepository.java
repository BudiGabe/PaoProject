package com.example.mdsback.repositories;

import com.example.mdsback.models.Harmony;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarmonyRepository extends JpaRepository<Harmony, Long> {
    Harmony findByName(String name);

    long deleteByName(String name);
}
