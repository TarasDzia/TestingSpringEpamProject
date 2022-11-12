package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {
    @Null(message = "{null.subject.id}")
    private Integer id;

    @NotBlank(message = "{notBlank.subject.name}")
    @Pattern(message = "Invalid subject name format", regexp = "^[`'\\s\\wА-ї]{2,}$")
    private String name;
}
