package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.UsuarioDTO;
import com.quipux.apicanciones.entity.UsuarioEntity;
import com.quipux.apicanciones.repository.IUsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioH2AdapterTest {

    @Mock
    private IUsuarioRepository iUsuarioRepository;

    @InjectMocks
    private UsuarioH2Adapter usuarioH2Adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería encontrar y mapear un usuario existente")
    void findByUsername_usuarioExiste() {

        String username = "testuser";
        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsername(username);
        entity.setPassword("pass");
        when(iUsuarioRepository.findByUsername(username)).thenReturn(Optional.of(entity));

        Optional<UsuarioDTO> resultado = usuarioH2Adapter.findByUsername(username);

        assertTrue(resultado.isPresent());
        assertEquals(username, resultado.get().getUsername());
        verify(iUsuarioRepository).findByUsername(username);
    }

    @Test
    @DisplayName("Debería retornar un Optional vacío si el usuario no existe")
    void findByUsername_usuarioNoExiste() {

        String username = "nonexistent";
        when(iUsuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<UsuarioDTO> resultado = usuarioH2Adapter.findByUsername(username);

        assertFalse(resultado.isPresent());
        verify(iUsuarioRepository).findByUsername(username);
    }
}