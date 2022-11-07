package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class TestController {
    private final TestService testService;

    @GetMapping("/test")
    public List<TestDto> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) String sorting, @PathVariable(required = false) Integer subjectId) {
        log.info("findAll({}, {}, {})", search, sorting, subjectId);
        return testService.findAll(search, sorting, subjectId);
    }

    @GetMapping("/test/{testId}")
    public TestDto find(@PathVariable @Min(1) int testId) {
        log.info("find({})", testId);

        TestDto testDto = testService.find(testId);
        if(Objects.isNull(testDto)){
            throw new NotFoundException("Test with id %s not found".formatted(testId));
        }
        return testDto;
    }

    @PostMapping("/subject/{subjectId}/test")
    @ResponseStatus(HttpStatus.CREATED)
    public TestDto create(@RequestBody @Valid TestDto testDto, @PathVariable @Min(1) int subjectId) {
        log.info("create({})", testDto);
        return testService.create(testDto, subjectId);
    }

    @PutMapping("/test/{testId}")
    public TestDto update(@RequestBody @Validated(OnUpdate.class) TestDto testDto, @PathVariable @Min(1) int testId) {
        log.info("update({}, {})", testDto, testId);

        if(Objects.isNull(testService.find(testId))){
            throw new NotFoundException("Test with id %s not found".formatted(testId));
        }
        return testService.update(testDto, testId);
    }

    @DeleteMapping("/test/{testId}")
    public void delete(@PathVariable @Min(1) int testId) {
        log.info("delete({})", testId);

        if(Objects.isNull(testService.find(testId))){
            throw new NotFoundException("Test with id %s not found".formatted(testId));
        }
        testService.delete(testId);
    }


}
