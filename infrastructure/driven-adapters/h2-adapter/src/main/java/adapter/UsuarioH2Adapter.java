package adapter;

import dtos.Usuario;
import org.springframework.stereotype.Service;
import ports.IUsuarioPersistencePort;
import repository.IUsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioH2Adapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository iUsuarioRepository;

    public UsuarioH2Adapter(IUsuarioRepository iUsuarioRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return iUsuarioRepository.findByUsername(username)
                .map(entity -> new Usuario(entity.getUsername(), entity.getPassword()));
    }
}
