package com.quipux.apicanciones.controller;

import com.quipux.apicanciones.dto.request.AuthRequest;
import com.quipux.apicanciones.dto.response.AuthResponse;
import com.quipux.apicanciones.service.JwtService;
import com.quipux.apicanciones.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuhControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuhController authController;

    @Test
    void testLoginSuccess() throws Exception {

        AuthRequest request = new AuthRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        UserDetails userDetails = new User("usuario", "clave", Collections.emptyList());
        String mockToken = "mock-jwt-token";

        when(userDetailsService.loadUserByUsername("usuario")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(mockToken);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof AuthResponse);
        assertEquals(mockToken, ((AuthResponse) response.getBody()).getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLoginWithInvalidCredentials() {

        AuthRequest request = new AuthRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.login(request);
        });

        assertEquals("INVALID_CREDENTIALS", exception.getMessage());
    }

    @Test
    void testLoginWithDisabledUser() {
        AuthRequest request = new AuthRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        doThrow(new DisabledException("User disabled"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.login(request);
        });

        assertEquals("USER_DISABLED", exception.getMessage());
    }

}