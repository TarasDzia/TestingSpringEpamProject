package com.epam.spring.testingapp.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tests_results")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    @ManyToOne(targetEntity = Account.class, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToOne(targetEntity = RunningTest.class, optional = false)
    @JoinColumn(name = "runningTest_id", nullable = false)
    @ToString.Exclude
    private RunningTest runningTest;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private Timestamp completionDate;
}
