package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
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
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> findAll() {
        log.info("findAll()");
        return subjectService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto create(@RequestBody @Valid SubjectDto subjectDto) {
        log.info("create({})", subjectDto);
        return subjectService.create(subjectDto);
    }

    @PutMapping("/{subjectId}")
    public SubjectDto update(@RequestBody @Validated(OnUpdate.class) SubjectDto subjectDto, @PathVariable @Min(1) int subjectId) {
        log.info("update({}, {})", subjectDto, subjectId);

        if(Objects.isNull(subjectService.find(subjectId))){
            throw new NotFoundException("Subject with id %s not found".formatted(subjectId));
        }

        return subjectService.update(subjectDto, subjectId);
    }

    @DeleteMapping("/{subjectId}")
    public void delete(@PathVariable @Min(1) int subjectId) {
        log.info("delete({})", subjectId);

        if(Objects.isNull(subjectService.find(subjectId))){
            throw new NotFoundException("Subject with id %s not found".formatted(subjectId));
        }
        subjectService.delete(subjectId);
    }
}
