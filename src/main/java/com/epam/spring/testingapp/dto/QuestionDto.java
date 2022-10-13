package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDto {
    private int id;
    private int testId;
    private String description;
}
