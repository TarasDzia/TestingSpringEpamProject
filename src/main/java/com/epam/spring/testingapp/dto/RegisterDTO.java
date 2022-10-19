package com.epam.spring.testingapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.sql.Date;

public class RegisterDTO {
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cant be empty")
    private String email;
    @Pattern(message = "Invalid password format", regexp = "^\\w{8,}$")
    @NotBlank(message = "Password cant be empty")
    private String password;
    @Pattern(message = "Invalid firstname format", regexp = "^[А-їA-z`']{3,}$")
    private String firstname;
    @Pattern(message = "Invalid surname format", regexp = "^[А-їA-z`']{3,}$")
    private String surname;
    private Date birthdate;
}
