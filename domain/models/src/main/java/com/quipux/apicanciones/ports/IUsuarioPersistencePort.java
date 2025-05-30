package com.quipux.apicanciones.ports;

import com.quipux.apicanciones.dtos.UsuarioDTO;

import java.util.Optional;

public interface IUsuarioPersistencePort {
    Optional<UsuarioDTO> findByUsername(String username);
}
