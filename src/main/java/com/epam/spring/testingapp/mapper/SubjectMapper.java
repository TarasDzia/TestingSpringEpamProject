package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    List<SubjectDto> subjectsToSubjectsDtos(List<Subject> subjects);
    List<Subject> subjectsDtosToSubjects(List<SubjectDto> subjectDtos);

    SubjectDto subjectToSubjectDto(Subject subject);
    Subject subjectDtoToSubject(SubjectDto subjectDto);
}
