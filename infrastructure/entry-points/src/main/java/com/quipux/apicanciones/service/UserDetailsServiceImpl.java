package com.quipux.apicanciones.service;

import com.quipux.apicanciones.dtos.UsuarioDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.quipux.apicanciones.port.IUsuarioPort;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUsuarioPort iUsuarioPort;

    public UserDetailsServiceImpl(IUsuarioPort iUsuarioPort) {
        this.iUsuarioPort = iUsuarioPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDTO usuario = iUsuarioPort
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList()
        );
    }
}
