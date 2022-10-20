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
    public List<TestDto> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) String sorting, @RequestParam(required = false) int subjectId) {
        log.info("findAll({}, {}, {})", search, sorting, subjectId);
        return testService.findAll(search, sorting, subjectId);
    }

    @GetMapping("/{testId}")
    public TestDto find(@PathVariable int testId) {
        log.info("find({})", testId);
        return testService.find(testId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestDto create(@RequestBody TestDto testDto) {
        log.info("create({})", testDto);
        return testService.create(testDto);
    }

    @PutMapping("/{testId}")
    public TestDto update(@RequestBody TestDto testDto, @PathVariable int testId) {
        log.info("update({}, {})", testDto, testId);
        return testService.update(testDto, testId);
    }

    @DeleteMapping("/{testId}")
    public void delete(@PathVariable int testId) {
        log.info("delete({})", testId);
        testService.delete(testId);
    }


}
