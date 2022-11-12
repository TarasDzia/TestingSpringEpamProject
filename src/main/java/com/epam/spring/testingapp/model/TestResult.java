package com.epam.spring.testingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResult {
    private int id;
    private Account account;
    private Test test;
    private int score;
    private Timestamp completionDate;
}
