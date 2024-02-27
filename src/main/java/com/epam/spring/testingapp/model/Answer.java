package com.epam.spring.testingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
@Table(name = "answers", indexes = {@Index(name = "idx_answer_in_question_unique", columnList = "question_id, description", unique = true)})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @ManyToOne(targetEntity = Question.class, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private Question question;

    @ManyToMany(mappedBy = "userAnswers")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RunningTest> runningTests;

    @Column(nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    private boolean correct;
}
