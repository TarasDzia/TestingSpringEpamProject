package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.model.enumerate.TestDifficult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {
    private int id;
    private int subjectId;
    @NotBlank(message = "Test name can`t be empty")
    @Pattern(message = "Invalid test name format", regexp = "^[А-їA-z`']{2,}$")
    private String name;
    @NotNull
    @Range(min = 10, max = 180, message = "Test duration have to be between 10 and 180 minutes")
    private int duration;
    @NotNull(message = "Test difficult must be specified")
    private TestDifficult difficult;
    private List<QuestionDto> questions;
}
