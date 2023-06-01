package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDto {
    @Email(message = "{email.account.email}")
    @NotBlank(message = "{notBlank.account.email}")
    private String email;

    @Pattern(message = "{pattern.account.password}", regexp = "^\\w{8,}$")
    @NotBlank(message = "{notBlank.account.password}")
    private String password;
}
