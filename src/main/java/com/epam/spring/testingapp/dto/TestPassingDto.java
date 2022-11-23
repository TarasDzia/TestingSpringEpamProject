package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.model.TestResult;
import com.epam.spring.testingapp.model.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestPassingDto {
    private int id;
    private TestDto test;
    private Integer accountId;
    private List<UserAnswerDto> userAnswers;
    private Timestamp startTime;
    private Integer testResultId;
}
