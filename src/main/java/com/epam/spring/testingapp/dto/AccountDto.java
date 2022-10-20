package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.model.enumerate.AccountRole;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@Builder
public class AccountDto {
    private int id;
    @Email(message = "Invalid email address")
    @NotBlank
    private String email;
    @NotNull
    private AccountRole accountRole;
    @Pattern(message = "Invalid firstname format", regexp = "^[А-їA-z`']{3,}$")
    private String firstname;
    @Pattern(message = "Invalid surname format", regexp = "^[А-їA-z`']{3,}$")
    private String surname;
    private Date birthdate;
    private boolean banned;
}
