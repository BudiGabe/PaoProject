package com.example.mdsback.services;

import com.example.mdsback.models.Sample;
import com.example.mdsback.models.User;
import com.example.mdsback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SampleService sampleService;

    private final LoggingService logger = new LoggingService();

    public User findById(Long id) {
        logger.log("Getting user with id " + id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User addSampleToUser(Long userId, Long sampleId) {
        logger.log("Adding sample with id " + sampleId + " to user with id " + userId);
        User user = findById(userId);
        Sample sample = sampleService.findById(sampleId);

        user.getSamples().add(sample);
        return userRepository.save(user);
    }

    public User addHarmonyToUser(Long userId, Long harmonyId) {
        logger.log("Adding harmony with id " + harmonyId + " to user with id " + userId);
        User user = findById(userId);
        Sample sample = sampleService.findById(harmonyId);

        user.getHarmonies().add(sample);
        return userRepository.save(user);
    }

    public User create(User user) {
        logger.log("Creating user with name "  + user.getName());
        return userRepository.save(user);
    }

    public long delete(String name){
        logger.log("Deleting user with name " + name);
        return userRepository.deleteByName(name);
    }

}