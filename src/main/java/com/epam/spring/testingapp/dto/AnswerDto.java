package com.epam.spring.testingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {
    @Null(message = "{null.answer.id}")
    private Integer id;

    @Null(message = "{null.answer.questionId}")
    private Integer questionId;

    @NotBlank(message = "{null.answer.description}")
    private String description;

    private boolean correct;
}
