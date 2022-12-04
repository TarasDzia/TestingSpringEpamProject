package com.epam.spring.testingapp;

import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.model.enumerate.TestDifficult;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class utils {
    public static Account getAccount(){
        return Account.builder().id(1)
                .email("taras@gmail.com").password("pass1231232112")
                .firstname("Тарас").surname("Дзядик")
                .accountRole(AccountRole.USER)
                .birthdate(Date.valueOf(LocalDate.now()))
                .build();
    }

    public static Subject getSubject(){
        return Subject.builder().id(1)
                .name("OOP").build();
    }

    public static Test getTest(){
        return Test.builder().id(1)
                .name("test1")
                .duration(10).difficult(TestDifficult.EASY)
                .build();
    }

    public static Question getQuestion(Test test){
        return Question.builder().id(2)
                .description("Do u know the way?")
                .test(test).build();
    }

    public static Answer getCorrectAnswer(Question question){
        return Answer.builder().id(2)
                .description("Yes").correct(true)
                .question(question).build();
    }

    public static Answer getBadAnswer(Question question){
        return Answer.builder().id(4)
                .description("No").correct(false)
                .question(question)
                .build();
    }

    public static RunningTest getRunningTest(Account account, Test test){
        return RunningTest.builder().id(1)
                .startTime(Timestamp.valueOf(LocalDateTime.now()))
                .account(account).test(test)
                .build();
    }

    public static TestResult getTestResult(Account account){
        return TestResult.builder().id(1)
                .account(account)
                .completionDate(Timestamp.valueOf(LocalDateTime.now())).build();
    }
}
