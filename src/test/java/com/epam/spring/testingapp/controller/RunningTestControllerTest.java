package com.epam.spring.testingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.epam.spring.testingapp.utils.TEST_URL;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RunningTestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void startTest_GivenValidInput_ShouldReturn201() throws Exception {
        int testId = 1;
        int accountId = 1;
        mockMvc.perform(post(TEST_URL+"/{testId}/passing", testId)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.test.id").value(testId))
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void startTest_GivenNotExistIdOfAccount_ShouldReturn404() throws Exception {
        int testId = 1;
        int accountId = 123;
        mockMvc.perform(post(TEST_URL+"/{testId}/passing", testId)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void startTest_GivenNotExistIdOfTest_ShouldReturn404() throws Exception {
        int testId = 123;
        int accountId = 1;
        mockMvc.perform(post(TEST_URL+"/{testId}/passing", testId)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void startTest_GivenNegativeId_ShouldReturn400() throws Exception {
        int testId = -1;
        int accountId = -1;
        mockMvc.perform(post(TEST_URL+"/{testId}/passing", testId)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void addChosenAnswers_GivenOneId_ShouldReturn200() throws Exception {
        List<Integer> answerIds = List.of(1);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(jsonPath("$.userAnswers", hasSize(answerIds.size())))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void addChosenAnswers_GivenTwoIds_ShouldReturn200() throws Exception {
        List<Integer> answerIds = List.of(1,2);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(jsonPath("$.userAnswers", hasSize(answerIds.size())))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void addChosenAnswers_GivenNotExistIdOfAccount_ShouldReturn400() throws Exception {
        List<Integer> answerIds = List.of(1,2);
        int accountId = 123;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/finished-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void addChosenAnswers_NoTestIsRunningNow_ShouldReturn404() throws Exception {
        List<Integer> answerIds = List.of(1,2);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/one-oldrunning-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void addChosenAnswers_NoTestIsRunningNow_ShouldReturn422() throws Exception {
        List<Integer> answerIds = List.of(1,2);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    })
    void addChosenAnswers_GivenIdOfNotExistingAnswer_ShouldReturn422() throws Exception {
        List<Integer> answerIds = List.of(1,7);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

    })
    void addChosenAnswers_GivenAnswerIdOfNotRelevantToThisTest_ShouldReturn422() throws Exception {
        List<Integer> answerIds = List.of(1,3);
        int accountId = 1;

        mockMvc.perform(patch(TEST_URL+"/passing/answer")
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerIds)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void getQuestion_GivenSequenceNumberOfFirstQuestion_ShouldReturn200() throws Exception {
        int sequenceNumber = 0;
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL+ "/passing/question/{sequenceNumber}", sequenceNumber)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.answers", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/finished-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void getQuestion_NoTestIsRunningNow_ShouldReturn404() throws Exception {
        int sequenceNumber = 0;
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL+"/passing/question/{sequenceNumber}", sequenceNumber)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void getQuestion_GivenTooBigSequenceNumber_ShouldReturn404() throws Exception {
        int sequenceNumber = 123;
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL+"/passing/question/{sequenceNumber}", sequenceNumber)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void getQuestion_GivenNegativeSequenceNumber_ShouldReturn400() throws Exception {
        int sequenceNumber = -1;
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL+"/passing/question/{sequenceNumber}", sequenceNumber)
                        .param("accountId", String.valueOf(accountId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/one-running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-answer/correct-answers-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void finishTest_GivenCorrectAnswers_ShouldReturn200_and_maxScore() throws Exception {
        int accountId = 1;
        mockMvc.perform(post(TEST_URL+"/passing/finish")
                        .param("accountId", String.valueOf(accountId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(jsonPath("$.score").value(100))
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/one-running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-answer/bad-answers-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void finishTest_GivenCorrectAnswers_ShouldReturn200_and_ZeroScore() throws Exception {
        int accountId = 1;
        mockMvc.perform(post(TEST_URL+"/passing/finish")
                        .param("accountId", String.valueOf(accountId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/one-running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-answer/bad-answers-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void finishTest_GivenNotExistingAccount_ShouldReturn404() throws Exception {
        int accountId = 123;
        mockMvc.perform(post(TEST_URL+"/passing/finish")
                        .param("accountId", String.valueOf(accountId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/one-running-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/user-answer/bad-answers-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void finishTest_GivenNegativeId_ShouldReturn400() throws Exception {
        int accountId = -2;
        mockMvc.perform(post(TEST_URL+"/passing/finish")
                        .param("accountId", String.valueOf(accountId)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}