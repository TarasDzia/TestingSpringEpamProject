package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.model.enumerate.TestDifficult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestDto {
    @Null(message = "{null.test.id}")
    private Integer id;

    @Null(groups = {OnCreate.class},message = "{null.test.subjectId}")
    private Integer subjectId;

    @NotBlank(message = "{notBlank.test.name}")
    @Pattern(message = "{pattern.test.name}", regexp = "^[`'\\s\\wА-ї]{2,}$")
    private String name;

    @NotNull
    @Range(min = 5, max = 180, message = "{range.test.duration}")
    private Integer duration;

    @NotNull(message = "{notNull.test.difficult}")
    private TestDifficult difficult;
    private List<QuestionDto> questions;
}
