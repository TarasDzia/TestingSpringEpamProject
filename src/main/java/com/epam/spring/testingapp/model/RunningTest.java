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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "runningTest", cascade = CascadeType.ALL)
    private Set<UserAnswer> userAnswers;
    @OneToOne
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;

    private Timestamp startTime;
//    private boolean finished;
}
