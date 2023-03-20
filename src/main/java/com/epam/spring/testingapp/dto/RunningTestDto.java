package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.model.RunningTest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    private Timestamp startTime;
    private Timestamp endTime;
    public Timestamp getEndTime() {
        return Timestamp.valueOf(this.getStartTime().toLocalDateTime().plusMinutes(this.getTest().getDuration()));
    }
}
