package com.epam.spring.testingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {
    private int id;
    private int questionId;
    @NotBlank(message = "Answer description can`t be empty")
    private String description;
    private boolean correct;
}
