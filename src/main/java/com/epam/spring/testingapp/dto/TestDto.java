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
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {
    @Null(message = "Id of test must be absent")
    private Integer id;

    @Null(message = "Id of subject in witch this test occurs must be absent")
    private Integer subjectId;

    @NotBlank(message = "Test name can`t be empty")
    @Pattern(message = "Invalid test name format", regexp = "^[`'\\s\\wА-ї]{2,}$")
    private String name;

    @NotNull
    @Range(min = 5, max = 180, message = "Test duration have to be between 5 and 180 minutes")
    private Integer duration;

    @NotNull(message = "Test difficult must be specified")
    private TestDifficult difficult;
    private List<QuestionDto> questions;
}
