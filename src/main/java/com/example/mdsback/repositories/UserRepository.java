package com.example.mdsback.repositories;

import com.example.mdsback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    long deleteByName(String name);
}
