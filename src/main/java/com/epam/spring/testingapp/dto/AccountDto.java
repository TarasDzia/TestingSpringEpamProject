package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    @Null(message = "Id of account must be absent")
    private Integer id;

    @Email(message = "Invalid email address format")
    @NotBlank(groups = OnCreate.class, message = "Email cant be empty")
    private String email;

    @Pattern(message = "Password must be at least 8 characters long", regexp = "^\\w{8,}$")
    @NotBlank(groups = OnCreate.class, message = "Password cant be empty")
    @Null(groups = OnUpdate.class, message = "Password must be absent")
    private String password;

//    @Null(message = "Role of account must be absent")
    @Null(groups = OnCreate.class, message = "Role of account must be absent")
    private AccountRole accountRole;

    @Pattern(message = "Invalid firstname format", regexp = "^[А-їA-z`']{3,}$")
    private String firstname;

    @Pattern(message = "Invalid surname format", regexp = "^[А-їA-z`']{3,}$")
    private String surname;

    private Date birthdate;

    @Null(groups = OnCreate.class, message = "Is account banned should be empty")
    private Boolean banned;
}
