package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void findAll_GivenValidId_ShouldReturn200() throws Exception {
        int testId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get( TEST_URL + "/{test}"+ QUESTION_URL, testId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void findAll_GivenNegativeId_ShouldReturn400() throws Exception {
        int testId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get( TEST_URL + "/{test}"+ QUESTION_URL, testId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void find_GivenValidInput_ShouldReturns200() throws Exception {
        int questionId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + QUESTION_URL + "/{questionId}", questionId))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id", equalTo(questionId)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void find_GivenNegativeId_ShouldReturns400() throws Exception {
        int questionId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + QUESTION_URL + "/{questionId}", questionId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenValidInput_ShouldReturn201() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int testId = 1;

        mockMvc.perform(post(TEST_URL + "/{test_id}"+ QUESTION_URL, testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.testId").value(testId))
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenNegativeTestId_ShouldReturn400() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int testId = -1;

        mockMvc.perform(post(TEST_URL + "/{test_id}"+ QUESTION_URL, testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenNotExistingTestId_ShouldReturn404() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int testId = 213123;

        mockMvc.perform(post(TEST_URL + "/{test_id}"+ QUESTION_URL, testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_NotGivenDescription_ShouldReturn400() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        questionDto.setDescription(null);
        int testId = 1;

        mockMvc.perform(post(TEST_URL + "/{test_id}"+ QUESTION_URL, testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenValidInput_ShouldReturn200() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int questionId = 1;

        mockMvc.perform(put(TEST_URL + QUESTION_URL+ "/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenNegativeQuestionId_ShouldReturn400() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int questionId = -1;

        mockMvc.perform(put(TEST_URL + QUESTION_URL+ "/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenNotExistingQuestionId_ShouldReturn404() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        int questionId = 123123;

        mockMvc.perform(put(TEST_URL + QUESTION_URL+ "/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenTestIdParam_ShouldReturn400() throws Exception {
        QuestionDto questionDto = getQuestionDto();
        questionDto.setTestId(2);
        int questionId = 1;

        mockMvc.perform(put(TEST_URL + QUESTION_URL+ "/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenValidId_ShouldReturn204() throws Exception {
        int questionId = 1;
        mockMvc.perform(delete(TEST_URL + QUESTION_URL + "/{questionId}", questionId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNegativeId_ShouldReturn400() throws Exception {
        int questionId = -1;
        mockMvc.perform(delete(TEST_URL + QUESTION_URL + "/{questionId}", questionId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/question-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNotExistId_ShouldReturn404() throws Exception {
        int questionId = 123;
        mockMvc.perform(delete(TEST_URL + QUESTION_URL + "/{questionId}", questionId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}