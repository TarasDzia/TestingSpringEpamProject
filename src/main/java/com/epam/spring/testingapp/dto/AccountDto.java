package com.epam.spring.testingapp.dto;

import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.validator.NameConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Null(message = "{null.account.id}")
    private Integer id;

    @Email(message = "{email.account.email}")
    @NotBlank(groups = OnCreate.class, message = "{notBlank.account.email}")
    private String email;

    @Pattern(message = "{pattern.account.password}", regexp = "^\\w{8,}$")
    @NotBlank(groups = OnCreate.class, message = "{notBlank.account.password}")
    @Null(groups = OnUpdate.class, message = "{null.account.password}")
    private String password;

    @Null(groups = OnCreate.class, message = "{null.account.role}")
    private AccountRole accountRole;

    @NameConstraint(message = "{nameConstraint.account.firstname}")
    private String firstname;

    @NameConstraint(message = "{nameConstraint.account.surname}")
    private String surname;

    @JsonFormat(pattern="dd.MM.yyyy")
    private Date birthdate;

    @Null(groups = OnCreate.class, message = "{null.account.banned}")
    private Boolean banned;
}
