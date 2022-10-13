package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {
    private int id;
    private int questionId;
    private String description;
    private boolean correct;
}
