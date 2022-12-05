package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
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
import org.springframework.web.context.WebApplicationContext;

import static com.epam.spring.testingapp.utils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void findAll_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SUBJECT_URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenValidInput_ShouldReturn201() throws Exception {
        SubjectDto subject = getSubjectDto();

        mockMvc.perform(post(SUBJECT_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(status().isCreated());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenBlankName_ShouldReturn400() throws Exception {
        SubjectDto subject = getSubjectDto();
        subject.setName("");

        mockMvc.perform(post(SUBJECT_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void create_GivenDuplicateName_ShouldReturn422() throws Exception {
        SubjectDto subject = getSubjectDto();
        subject.setName("Subject2");

        mockMvc.perform(post(SUBJECT_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenValidInput_ShouldReturn200() throws Exception {
        SubjectDto subject = getSubjectDto();
        int subjectId = 1;

        mockMvc.perform(put(SUBJECT_URL + "/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(subjectId))
                .andExpect(jsonPath("$.name").value(subject.getName()))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenNegativeId_ShouldReturn400() throws Exception {
        SubjectDto subject = getSubjectDto();
        int subjectId = -1;

        mockMvc.perform(put(SUBJECT_URL + "/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenIdOfNotExistingSubject_ShouldReturn404() throws Exception {
        SubjectDto subject = getSubjectDto();
        int subjectId = 123123;

        mockMvc.perform(put(SUBJECT_URL + "/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void update_GivenSubjectIdInBody_ShouldReturn400() throws Exception {
        SubjectDto subject = getSubjectDto();
        subject.setId(1);
        int subjectId = 1;

        mockMvc.perform(put(SUBJECT_URL + "/{subjectId}", subjectId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(subject)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenValidId_ShouldReturn204() throws Exception {
        int subjectId = 1;
        mockMvc.perform(delete(SUBJECT_URL + "/{subjectId}", subjectId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNegativeId_ShouldReturn400() throws Exception {
        int subjectId = -1;
        mockMvc.perform(delete(SUBJECT_URL + "/{subjectId}", subjectId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    })
    void delete_GivenNotExistId_ShouldReturn404() throws Exception {
        int subjectId = 123123;
        mockMvc.perform(delete(SUBJECT_URL + "/{subjectId}", subjectId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}