package com.quipux.apicanciones.service;

import com.quipux.apicanciones.dtos.UsuarioDTO;
import com.quipux.apicanciones.port.IUsuarioPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private IUsuarioPort usuarioPort;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("john");
        usuarioDTO.setPassword("password123");

        when(usuarioPort.findByUsername("john")).thenReturn(Optional.of(usuarioDTO));

        UserDetails userDetails = userDetailsService.loadUserByUsername("john");

        assertEquals("john", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(usuarioPort.findByUsername("jane")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("jane");
        });
    }

}