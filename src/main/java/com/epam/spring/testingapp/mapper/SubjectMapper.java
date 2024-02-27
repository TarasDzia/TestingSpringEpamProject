package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    List<SubjectDto> toSubjectsDtos(List<Subject> subjects);

    default Page<SubjectDto> toSubjectsDto(Page<Subject> page) {
        return page.map(this::toSubjectDto);
    }
    List<Subject> toSubjects(List<SubjectDto> subjectDtos);

    SubjectDto toSubjectDto(Subject subject);
    Subject toSubject(SubjectDto subjectDto);
}
