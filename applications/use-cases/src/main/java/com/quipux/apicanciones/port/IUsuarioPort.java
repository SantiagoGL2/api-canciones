package com.quipux.apicanciones.port;

import com.quipux.apicanciones.dtos.UsuarioDTO;

import java.util.Optional;

public interface IUsuarioPort {

    Optional<UsuarioDTO> findByUsername(String username);

}
