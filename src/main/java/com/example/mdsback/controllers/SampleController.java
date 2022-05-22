package com.example.mdsback.controllers;

import com.example.mdsback.DTOs.SampleDTO;
import com.example.mdsback.models.Sample;
import com.example.mdsback.repositories.SampleRepository;
import com.example.mdsback.services.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/samples")
public class SampleController {
    @Autowired
    private SampleRepository sampleRepo;

    @Autowired
    private SampleService sampleService;

    @CrossOrigin
    @GetMapping("/id/{id}")
    public SampleDTO findById(@PathVariable Long id) {
        Sample sample = sampleService.findById(id);
        return new SampleDTO(sample);
    }

    @CrossOrigin
    @GetMapping("/name/{name}")
    public SampleDTO findByName(@PathVariable String name) {
        return new SampleDTO(sampleService.findByName(name));
    }

    @CrossOrigin
    @GetMapping
    public List<SampleDTO> findAll() {
        return sampleService.findAll()
                .stream()
                .map(SampleDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/new")
    public List<SampleDTO> findAllNew() {
        return sampleService.findAllNew()
                .stream()
                .map(SampleDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/top")
    public List<SampleDTO> findAllTop() {
        return sampleService.findAllTop()
                .stream()
                .map(SampleDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sample create(@RequestBody Sample sample) {
        return sampleService.create(sample);
    }

    @CrossOrigin
    @PutMapping("/like/{id}")
    public SampleDTO likeSample(@PathVariable Long id) {
        return new SampleDTO(sampleService.likeSample(id));
    }

    @CrossOrigin
    @DeleteMapping("{name}")
    public long delete(@PathVariable String name) {
        return sampleService.delete(name);
    }

    @CrossOrigin
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id) {
        sampleService.deleteById(id);
    }

}
