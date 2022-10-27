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
    @Null(message = "Id of answer must be absent")
    private Integer id;

    @Null(message = "Id of question in witch this answer occurs must be absent")
    private Integer questionId;

    @NotBlank(message = "Answer description can`t be empty")
    private String description;

    private boolean correct;
}
