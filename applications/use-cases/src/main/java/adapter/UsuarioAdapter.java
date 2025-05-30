package adapter;

import dtos.Usuario;
import org.springframework.stereotype.Service;
import port.IUsuarioPort;
import ports.IUsuarioPersistencePort;

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
