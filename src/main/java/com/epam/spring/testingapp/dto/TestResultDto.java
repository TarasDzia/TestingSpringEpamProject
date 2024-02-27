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
    private TestDtoSubject test;
    private int score;

    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp completionDate;
}
