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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {
    @Null(message = "Id of question must be absent")
    private Integer id;

    @Null(message = "Id of test in witch this question occurs must be absent")
    private Integer testId;

    @NotBlank(message = "Question description can`t be empty")
    private String description;

    private List<AnswerDto> answers;
}
