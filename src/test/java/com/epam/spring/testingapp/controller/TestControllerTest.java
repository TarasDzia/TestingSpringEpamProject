package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestDtoSubject;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void findAll_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                        .param("size", "10")
                        .param("subjectId", "1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void find_GivenValidInput_ShouldReturns200() throws Exception {
        int testId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL  + "/{testId}", testId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id", equalTo(testId)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void find_GivenNegativeId_ShouldReturns400() throws Exception {
        int testId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL  + "/{testId}", testId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenValidInput_ShouldReturn201() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();

        int subjectId = 1;
        mockMvc.perform(post(SUBJECT_URL+"/{subjectId}"+TEST_URL, subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.subjectId").value(subjectId))
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenNegativeId_ShouldReturn400() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();

        int subjectId = -1;
        mockMvc.perform(post(SUBJECT_URL+"/{subjectId}"+TEST_URL, subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenNotExistingSubjectId_ShouldReturn404() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();

        int subjectId = 213123;
        mockMvc.perform(post(SUBJECT_URL+"/{subjectId}"+TEST_URL, subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_NotGivenName_ShouldReturn400() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();
        testDtoSubject.setName(null);
        int subjectId = 1;
        mockMvc.perform(post(SUBJECT_URL+"/{subjectId}"+TEST_URL, subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenValidInput_ShouldReturn200() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();
        int testId = 1;

        mockMvc.perform(put(TEST_URL + "/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.subjectId").isNumber())
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenSubjectId_ShouldReturn200() throws Exception {
        int newSubjectId = 2;
        TestDtoSubject testDtoSubject = getTestDto(newSubjectId);
        int testId = 1;

        mockMvc.perform(put(TEST_URL + "/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.subjectId").value(newSubjectId))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenNegativeId_ShouldReturn400() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();
        int testId = -1;

        mockMvc.perform(put(TEST_URL + "/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenNotExistingTestId_ShouldReturn404() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();
        int testId = 1231;

        mockMvc.perform(put(TEST_URL + "/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenTooLongDuration_ShouldReturn400() throws Exception {
        TestDtoSubject testDtoSubject = getTestDto();
        testDtoSubject.setDuration(123213);
        int testId = 1;

        mockMvc.perform(put(TEST_URL + "/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(testDtoSubject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenValidId_ShouldReturn204() throws Exception {
        int testId = 1;
        mockMvc.perform(delete(TEST_URL + "/{testId}", testId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNegativeId_ShouldReturn400() throws Exception {
        int testId = -1;
        mockMvc.perform(delete(TEST_URL + "/{testId}", testId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNotExistId_ShouldReturn404() throws Exception {
        int testId = 12322;
        mockMvc.perform(delete(TEST_URL + "/{testId}", testId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}