package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void find_GivenValidInput_ShouldReturns200() throws Exception {
        int accountId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL+"/{accountId}", accountId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void find_GivenNegativeId_ShouldReturns400() throws Exception {
        int accountId = -1;
        mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL+ "/{accountId}", accountId)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void register_GivenValidInput_ShouldReturn200() throws Exception {
        AccountDto accountDto = getAccountDto();

        mockMvc.perform(post(ACCOUNT_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated());

    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void register_NotGivenEmail_ShouldReturn400() throws Exception {
        AccountDto accountDto = getAccountDto();
        accountDto.setId(1231213);
        accountDto.setEmail(null);

        mockMvc.perform(post(ACCOUNT_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void update_GivenValidInput_ShouldReturn200() throws Exception {
        AccountDto account = getAccountDto();
        account.setPassword(null);
        account.setAccountRole(AccountRole.ADMIN);
        account.setBanned(false);

        int accountId = 1;
        mockMvc.perform(put(ACCOUNT_URL+ "/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:init/account-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void update_GivenNegativeId_ShouldReturn400() throws Exception {
        Account account = getAccount();

        int accountId = -1;
        mockMvc.perform(put(ACCOUNT_URL + "/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }
}