package ports;

import dtos.Usuario;
import java.util.Optional;

public interface IUsuarioPersistencePort {
    Optional<Usuario> findByUsername(String username);
}
