package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class SubjectDto {
    private int id;
    @NotBlank(message = "Subject name can`t be empty")
    @Pattern(message = "Invalid subject name format", regexp = "^[А-їA-z`']{2,}$")
    private String name;
}
