package com.epam.spring.testingapp.model;

import com.epam.spring.testingapp.model.enumerate.AccountRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
//    @SequenceGenerator(name = "idGenerator", sequenceName = "accountId")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated
    @Column(nullable = false)
    private AccountRole accountRole;
    private String firstname;
    private String surname;
    private Date birthdate;
    private boolean banned;
}
