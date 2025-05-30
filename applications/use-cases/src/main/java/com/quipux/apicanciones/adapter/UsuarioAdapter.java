package com.quipux.apicanciones.adapter;

import com.quipux.apicanciones.dtos.Usuario;
import com.quipux.apicanciones.port.IUsuarioPort;
import org.springframework.stereotype.Service;
import com.quipux.apicanciones.ports.IUsuarioPersistencePort;

import java.util.Optional;

@Service
public class UsuarioAdapter implements IUsuarioPort {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    public UsuarioAdapter(IUsuarioPersistencePort usuarioPersistencePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return this.usuarioPersistencePort.findByUsername(username);
    }
}
