package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.SubjectMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.service.SubjectService;
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
@RequestMapping("/subject")
@Validated
@CrossOrigin
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> findAll() {
        log.info("findAll()");
        return SubjectMapper.INSTANCE.toSubjectsDtos(subjectService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody @Valid SubjectDto subjectDto) {
        log.info("create({})", subjectDto);
        Subject subject = SubjectMapper.INSTANCE.toSubject(subjectDto);
        return SubjectMapper.INSTANCE.toSubjectDto(subjectService.create(subject));
    }

    @PutMapping("/{subjectId}")
    public SubjectDto update(@RequestBody @Validated(OnUpdate.class) SubjectDto subjectDto, @PathVariable @Min(1) int subjectId) {
        log.info("update({}, {})", subjectDto, subjectId);
        Subject subject = SubjectMapper.INSTANCE.toSubject(subjectDto);
        return SubjectMapper.INSTANCE.toSubjectDto(subjectService.update(subject, subjectId));
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int subjectId) {
        log.info("delete({})", subjectId);
        subjectService.delete(subjectId);
    }
}
