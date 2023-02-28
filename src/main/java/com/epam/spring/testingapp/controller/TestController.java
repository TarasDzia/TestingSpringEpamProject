package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.TestDtoSubject;
import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.mapper.TestMapper;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class TestController {
    private final TestService testService;

    @GetMapping("/test")
    @CrossOrigin
    public List<TestDtoSubject> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) Integer subjectId,
                                        Pageable pageable) {
        log.info("findAll({}, {}, {})", search,  subjectId, pageable);
        return TestMapper.INSTANCE.toTestsDtos(testService.findAll(search, subjectId, pageable));
    }

    @GetMapping("/test/{testId}")
    public TestDto find(@PathVariable @Min(1) int testId) {
        log.info("find({})", testId);
        return TestMapper.INSTANCE.toTestDto(testService.find(testId));
    }

    @PostMapping("/subject/{subjectId}/test")
    @ResponseStatus(HttpStatus.CREATED)
    public TestDto create(@RequestBody @Validated(OnCreate.class) TestDtoSubject testDtoSubject, @PathVariable @Min(1) int subjectId) {
        log.info("create({})", testDtoSubject);
        Test test = TestMapper.INSTANCE.toTest(testDtoSubject);
        return TestMapper.INSTANCE.toTestDto(testService.create(test, subjectId));
    }

    @PutMapping("/test/{testId}")
    public TestDto update(@RequestBody @Validated TestDto testDto, @PathVariable @Min(1) int testId) {
        log.info("update({}, {})", testDto, testId);
        Test test = TestMapper.INSTANCE.toTest(testDto);
        return TestMapper.INSTANCE.toTestDto(testService.update(test, testId));
    }

    @DeleteMapping("/test/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int testId) {
        log.info("delete({})", testId);
        testService.delete(testId);
    }


}
