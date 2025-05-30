package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.UsuarioDTO;
import com.quipux.apicanciones.ports.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioAdapterTest {

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @InjectMocks
    private UsuarioAdapter usuarioAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería retornar un UsuarioDTO cuando el nombre de usuario existe")
    void findByUsername_UsuarioExiste_DeberiaRetornarUsuarioDTO() {
        String usernameExistente = "testUser";
        UsuarioDTO usuarioMock = new UsuarioDTO();
        usuarioMock.setUsername(usernameExistente);
        usuarioMock.setPassword("hashedPassword");

        when(usuarioPersistencePort.findByUsername(usernameExistente))
                .thenReturn(Optional.of(usuarioMock));

        Optional<UsuarioDTO> resultado = usuarioAdapter.findByUsername(usernameExistente);

        assertTrue(resultado.isPresent(), "El Optional debería contener un valor.");

        assertEquals(usernameExistente, resultado.get().getUsername(), "El nombre de usuario debería coincidir.");

        verify(usuarioPersistencePort, times(1)).findByUsername(usernameExistente);
    }

    @Test
    @DisplayName("Debería retornar un Optional vacío cuando el nombre de usuario no existe")
    void findByUsername_UsuarioNoExiste_DeberiaRetornarOptionalVacio() {

        String usernameNoExistente = "nonExistentUser";

        when(usuarioPersistencePort.findByUsername(usernameNoExistente))
                .thenReturn(Optional.empty());

        Optional<UsuarioDTO> resultado = usuarioAdapter.findByUsername(usernameNoExistente);

        assertFalse(resultado.isPresent(), "El Optional debería estar vacío.");

        verify(usuarioPersistencePort, times(1)).findByUsername(usernameNoExistente);
    }
}