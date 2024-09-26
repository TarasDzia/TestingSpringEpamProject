package com.epam.spring.testingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {
    private int id;
    private int testId;
    @NotBlank(message = "Question description can`t be empty")
    private String description;
    private List<AnswerDto> answers;
}
