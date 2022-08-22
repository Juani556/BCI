package ar.com.globallogic.ejercicio.repository;

import ar.com.globallogic.ejercicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    public Optional<Usuario> findByEmail(String email);
}
