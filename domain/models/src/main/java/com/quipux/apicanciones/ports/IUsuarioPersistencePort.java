package com.quipux.apicanciones.ports;

import com.quipux.apicanciones.dtos.Usuario;

import java.util.Optional;

public interface IUsuarioPersistencePort {
    Optional<Usuario> findByUsername(String username);
}
