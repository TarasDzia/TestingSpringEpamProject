package com.epam.spring.testingapp.model;

import com.epam.spring.testingapp.model.enumerate.TestDifficult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test {
    private int id;
    private Subject subject;
    private String name;
    private int duration;
    private TestDifficult difficult;
    private List<Question> questions;
}
