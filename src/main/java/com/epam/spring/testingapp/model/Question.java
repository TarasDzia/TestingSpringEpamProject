package com.epam.spring.testingapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    @ManyToOne(optional = false, targetEntity = Test.class)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Test test;

    @Column(nullable = false)
    private String description;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers;
}
