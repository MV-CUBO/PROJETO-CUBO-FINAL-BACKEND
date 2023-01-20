package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.login.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
