package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAnswerDto {
    private int questionId;
    private int answerId;
}
