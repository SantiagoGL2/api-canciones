package com.quipux.apicanciones.port;

import com.quipux.apicanciones.dtos.Usuario;

import java.util.Optional;

public interface IUsuarioPort {

    Optional<Usuario> findByUsername(String username);

}
