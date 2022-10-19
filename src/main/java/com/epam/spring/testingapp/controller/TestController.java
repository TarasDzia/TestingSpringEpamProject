package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping
    public List<TestDto> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) String sorting, @RequestParam(required = false) String subject) {
        return testService.findAll(search, sorting, subject);
    }

    @GetMapping("/{testId}")
    public TestDto find(@PathVariable int testId) {
        return testService.find(testId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestDto create(@RequestBody TestDto testDto) {
        return testService.create(testDto);
    }

    @PutMapping("/{testId}")
    public TestDto update(@RequestBody TestDto testDto, @PathVariable int testId) {
        return testService.update(testDto, testId);
    }

    @DeleteMapping("/{testId}")
    public void delete(@PathVariable int testId) {
        testService.delete(testId);
    }


}
