package com.epam.spring.testingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    private int id;
    private Question question;
    private String description;
    private boolean correct;
}
