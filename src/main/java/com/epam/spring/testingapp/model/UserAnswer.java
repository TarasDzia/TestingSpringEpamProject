package com.epam.spring.testingapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;
    @ManyToOne(targetEntity = RunningTest.class)
    @JoinColumn(name = "running_test_id")
    private RunningTest runningTest;

    @ManyToOne
    private Question question;

    @ManyToMany
    @JoinTable(name="choose_answers")
    @EqualsAndHashCode.Exclude
    private Set<Answer> answers;
}
