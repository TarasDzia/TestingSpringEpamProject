package com.epam.spring.testingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private int id;
    private Test test;
    private String description;
    private List<Answer> answers;
}
