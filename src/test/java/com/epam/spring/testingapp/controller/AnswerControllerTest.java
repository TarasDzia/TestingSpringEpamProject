package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AnswerDto;
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

import static com.epam.spring.testingapp.utils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_GivenValidId_ShouldReturn200() throws Exception {
        int questionId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get( TEST_URL + QUESTION_URL +"/{questionId}"+ ANSWER_URL, questionId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_GivenNegativeId_ShouldReturn400() throws Exception {
        int questionId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + QUESTION_URL +"/{questionId}"+ ANSWER_URL, questionId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void find_GivenValidInput_ShouldReturns200() throws Exception {
        int answerId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void find_GivenNegativeId_ShouldReturns400() throws Exception {
        int answerId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void create_GivenValidInput_ShouldReturn201() throws Exception {
        AnswerDto answerDto = getAnswerDto();

        int questionId = 1;
        mockMvc.perform(post(TEST_URL + QUESTION_URL +"/{questionId}"+ ANSWER_URL, questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void create_NotGivenDescription_ShouldReturn400() throws Exception {
        AnswerDto answerDto = getAnswerDto();
        answerDto.setDescription(null);

        int questionId = 1;
        mockMvc.perform(post(TEST_URL + QUESTION_URL +"/{questionId}"+ ANSWER_URL, questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void create_GivenNegativeQuestionId_ShouldReturn400() throws Exception {
        AnswerDto answerDto = getAnswerDto();
        answerDto.setDescription(null);

        int questionId = -1;
        mockMvc.perform(post(TEST_URL + QUESTION_URL +"/{questionId}"+ ANSWER_URL, questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void update_GivenValidInput_ShouldReturn200() throws Exception {
        AnswerDto answerDto = getAnswerDto();

        int answerId = 1;
        mockMvc.perform(put(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void update_GivenNegativeAnswerId_ShouldReturn400() throws Exception {
        AnswerDto answerDto = getAnswerDto();

        int answerId = -1;
        mockMvc.perform(put(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void update_NotGivenDescription_ShouldReturn400() throws Exception {
        AnswerDto answerDto = getAnswerDto();
        answerDto.setDescription(null);

        int answerId = 1;
        mockMvc.perform(put(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void delete_GivenValidId_ShouldReturn204() throws Exception {
        int answerId = 1;
        mockMvc.perform(delete(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/answer-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void delete_GivenNegativeId_ShouldReturn400() throws Exception {
        int answerId = -1;
        mockMvc.perform(delete(TEST_URL + QUESTION_URL +ANSWER_URL + "/{answerId}", answerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}