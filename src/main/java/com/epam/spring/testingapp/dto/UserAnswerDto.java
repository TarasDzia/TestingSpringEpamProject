package com.epam.spring.testingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswerDto {
    @Null(message = "{null.userAnswer.id}")
    private Integer id;

    @NotNull
    @Min(value = 1, message = "{min.userAnswer.questionId}")
    private Integer questionId;
    @NotNull(message = "Answers ids shouldn`t be absent")
    private Set<Integer> answerIds;
}
