package com.epam.spring.testingapp.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    public String generateToken(UserDetails userDetails, Map<String, Object> claims);
    public String generateToken(UserDetails userDetails);
    public String extractUsername(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public boolean hasClaim(String token, String claimName);
    public Boolean isTokenValid(String token, UserDetails userDetails);
}
