package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    public List<TestDto> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) Integer subjectId,
                                 Pageable pageable) {
        log.info("findAll({}, {}, {})", search,  subjectId, pageable);
        return testService.findAll(search, subjectId, pageable);
    }

    @GetMapping("/test/{testId}")
    public TestDto find(@PathVariable @Min(1) int testId) {
        log.info("find({})", testId);
        return testService.find(testId);
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
        return testService.update(testDto, testId);
    }

    @DeleteMapping("/test/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int testId) {
        log.info("delete({})", testId);
        testService.delete(testId);
    }


}
