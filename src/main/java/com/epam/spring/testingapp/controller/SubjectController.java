package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> findAll() {
        return subjectService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody SubjectDto subjectDto) {
        return subjectService.create(subjectDto);
    }

    @PutMapping("/{subjectId}")
    public SubjectDto update(@RequestBody SubjectDto subjectDto, @PathVariable int subjectId) {
        return subjectService.update(subjectDto, subjectId);
    }

    @DeleteMapping("/{subjectId}")
    public void delete(@PathVariable int subjectId) {
        subjectService.delete(subjectId);
    }
}
