package com.epam.spring.testingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunningTestDto {
    private Integer id;
    private TestDtoSubject test;
    private Integer accountId;
    private Set<AnswerDto> userAnswers;
    private Integer testResultId;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp startTime;
}
