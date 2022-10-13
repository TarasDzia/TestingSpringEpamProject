package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class TestResultDto {
    private int id;
    private int accountId;
    private int testId;
    private int score;
    private Timestamp completionDate;
}
