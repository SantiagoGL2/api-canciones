package com.quipux.apicanciones.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "mysecretkeymysecretkeymysecretkey12";

    private UserDetails userDetails;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        // Inyectar el valor del campo privado @Value
        Field secretField = JwtService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtService, secret);

        userDetails = new User("testuser", "password", Collections.emptyList());
    }

    @Test
    void testGenerateAndExtractUsername() {
        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testIsTokenValid_shouldReturnTrueForValidToken() {
        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid_shouldReturnFalseForInvalidUser() {
        String token = jwtService.generateToken(userDetails);

        UserDetails otherUser = new User("anotheruser", "password", Collections.emptyList());

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void testExtractClaim_shouldReturnCorrectSubject() {
        String token = jwtService.generateToken(userDetails);

        Claims claims = jwtService.extractClaim(token, c -> c);
        assertEquals("testuser", claims.getSubject());
    }

    @Test
    void testIsTokenExpired_shouldReturnFalseForFreshToken() {
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);

        assertTrue(expiration.after(new Date()));
    }

}