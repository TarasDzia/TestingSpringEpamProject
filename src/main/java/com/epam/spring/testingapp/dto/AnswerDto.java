package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AnswerDto {
    private int id;
    private int questionId;
    @NotBlank(message = "Answer description can`t be empty")
    private String description;
    private boolean correct;
}
