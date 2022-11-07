package com.epam.spring.testingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResultDto {
    private int id;
    private int accountId;
    private TestDto test;
    private int score;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp completionDate;
}
