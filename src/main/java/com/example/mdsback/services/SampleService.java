package com.example.mdsback.services;

import com.example.mdsback.models.Sample;
import com.example.mdsback.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class SampleService {
    @Autowired
    private SampleRepository sampleRepository;
    private final LoggingService logger = new LoggingService();

    public Sample findById(Long id) {
        logger.log("Getting sample with id " + id);
        return sampleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sample not found"));
    }

    public Sample findByName(String name) {
        logger.log("Getting sample with name " + name);
        return sampleRepository.findByName(name);
    }

    public void incrementLikes(Sample sample) {
        Long likes = sample.getLikes();
        if (likes == null) {
            sample.setLikes(1L);
        } else {
            sample.setLikes(sample.getLikes() + 1);
        }
    }

    public Collection<Sample> findAll() {
        logger.log("Getting all samples");
        return sampleRepository.findAll();
    }

    public List<Sample> findAllNew() {
        logger.log("Getting newest samples");
        return sampleRepository.findAllByOrderByIdDesc();
    }

    public List<Sample> findAllTop() {
        logger.log("Getting most liked samples");
        return sampleRepository.findAllByOrderByLikesDesc();
    }

    public Sample create(Sample sample) {
        logger.log("Creating sample with name " + sample.getName());
        return sampleRepository.save(sample);
    }

    public Sample likeSample(Long id) {
        logger.log("Liking sample with id " + id);
        Sample sample = this.findById(id);
        this.incrementLikes(sample);
        return sampleRepository.save(sample);
    }

    public long delete(String name) {
        logger.log("Deleting sample with name " + name);
        return sampleRepository.deleteByName(name);
    }

    public void deleteById(Long id) {
        logger.log("Deleting sample with id " + id);
        sampleRepository.deleteById(id);
    }

}
