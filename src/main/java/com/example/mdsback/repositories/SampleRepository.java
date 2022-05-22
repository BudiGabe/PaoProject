package com.example.mdsback.repositories;

import com.example.mdsback.models.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SampleRepository extends JpaRepository<Sample, Long> {

    List<Sample> findAllByOrderByIdDesc();

    List<Sample> findAllByOrderByLikesDesc();

    Sample findByName(String name);

    long deleteByName(String name);
}
