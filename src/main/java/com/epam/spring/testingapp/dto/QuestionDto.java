package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {
    @Null(message = "{null.question.id}")
    private Integer id;

    @Null(message = "{null.question.testId}")
    private Integer testId;

    @NotBlank(message = "{notBlank.question.description}")
    private String description;

    private Set<AnswerDto> answers;
}
