package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.AuthTokenDto;
import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.service.AccountService;
import com.epam.spring.testingapp.service.JwtService;
import com.epam.spring.testingapp.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final JwtService jwtServiceImpl;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthTokenDto register(@RequestBody @Validated(OnCreate.class) AccountDto accountDto) {
        log.info("register({})", accountDto);
        Account account = AccountMapper.INSTANCE.toAccount(accountDto);
        return AuthTokenDto.builder().access(
                jwtServiceImpl.generateToken(accountService.register(account)))
                .build();
    }

    @PostMapping
    public AuthTokenDto authenticate(@RequestBody AuthDto authDto){
        log.info("Login with such credits: {}", authDto);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authDto.getEmail(),
                    authDto.getPassword()));
        }catch (BadCredentialsException e){
            throw new UnprocessableEntityException("Your account name or password is incorrect.");
        }catch (DisabledException | LockedException e ){
            throw new UnprocessableEntityException("Your attempting to login using banned account.");
        }
        final UserDetails account = accountService.loadUserByUsername(authDto.getEmail());
        AuthTokenDto authTokenDto = AuthTokenDto.builder().access(jwtServiceImpl.generateToken(account)).build();
        log.info("Authenticated: {} ", authTokenDto);
        return authTokenDto;
    }

    @GetMapping("/jwt/verify")
    public ResponseEntity<Boolean> isAuthenticated () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            boolean authenticated = authentication.isAuthenticated();
            log.info("isAuthenticated = {}", authenticated);
            return ResponseEntity.ok(authenticated);
        }
        return ResponseEntity.ok(false);
    }
}
