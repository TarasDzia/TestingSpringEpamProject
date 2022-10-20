package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserAnswerDto {
    private int questionId;
    private Set<Integer> answerIds;
}
