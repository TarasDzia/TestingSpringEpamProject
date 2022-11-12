package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/rest-template")
public class RestTemplateController {
    private final RestTemplate restTemplate;

    @GetMapping("/accounts")
    public ResponseEntity<List> getAccounts(){
        log.info("getAccounts()");
        ResponseEntity<List> responseEntity = restTemplate.exchange("http://localhost:8080/account/", HttpMethod.GET,
                null, List.class);

        return responseEntity;
    }

    @PostMapping("/account")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto account) {
        log.info("createAccount({})", account);

        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange("http://localhost:8080/account", HttpMethod.POST,
                new HttpEntity<>(account), AccountDto.class);
        return responseEntity;
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto account, @PathVariable Integer accountId) {
        log.info("createAccount({}, {})", account, accountId);

        ResponseEntity<AccountDto> responseEntity = restTemplate.exchange("http://localhost:8080/account/"+ accountId, HttpMethod.PUT,
                new HttpEntity<>(account), AccountDto.class);
        return responseEntity;
    }

    @DeleteMapping("/test/{testId}")
    public ResponseEntity<Void> deleteTest(@PathVariable Integer testId) {
        log.info("deleteTest({})", testId);

        return restTemplate.exchange(new RequestEntity<>(HttpMethod.DELETE,
                URI.create("http://localhost:8080/test/"+ testId)), Void.class);
    }
}
