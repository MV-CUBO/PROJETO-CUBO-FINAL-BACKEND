package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByLogin(String login);
}
