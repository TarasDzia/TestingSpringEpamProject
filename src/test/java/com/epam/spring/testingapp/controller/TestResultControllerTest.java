package com.epam.spring.testingapp.controller;

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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestResultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/finished-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_ShouldReturn200() throws Exception {
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL+"/{accountId}"+TESTRESULT_URL, accountId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/finished-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_GivenNegativeId_ShouldReturn200() throws Exception {
        int accountId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL+"/{accountId}"+TESTRESULT_URL, accountId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/subject-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:init/running-test/finished-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_GivenAccountWithoutResults_ShouldReturn200() throws Exception {
        int accountId = 2;
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL+"/{accountId}"+TESTRESULT_URL, accountId))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
    }

}