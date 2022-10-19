package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class TestResultDto {
    private int id;
    private int accountId;
    private TestDto test;
    private int score;
    private Timestamp completionDate;
}
