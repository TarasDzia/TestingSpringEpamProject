package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.model.enumerate.AccountRole;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
public class AccountDto {
    private int id;
    @Email(message = "Invalid email address")
    @NotBlank
    private String email;
    private String firstname;
    private String surname;
    private Date birthdate;
    @NotNull
    private AccountRole role;
    private boolean banned;
}
