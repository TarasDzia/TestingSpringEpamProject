package com.epam.spring.testingapp.model;

import com.epam.spring.testingapp.model.enumerate.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private int id;
    private String email;
    private String password;
    private AccountRole accountRole;
    private String firstname;
    private String surname;
    private Date birthdate;
    private boolean banned;
}
