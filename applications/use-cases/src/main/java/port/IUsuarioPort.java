package port;

import dtos.Usuario;

import java.util.Optional;

public interface IUsuarioPort {

    Optional<Usuario> findByUsername(String username);

}
