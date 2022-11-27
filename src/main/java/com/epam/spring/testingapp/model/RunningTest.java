package com.epam.spring.testingapp.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "RunningTest")
@Table(name = "running_tests")
public class RunningTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false, updatable = false)
    private Test test;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;
    @ManyToMany
    @JoinTable(
            name = "runningTest_answer",
            joinColumns = @JoinColumn(name = "runningTest_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private Set<Answer> userAnswers;
    @OneToOne
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;
    private Timestamp startTime;
}
