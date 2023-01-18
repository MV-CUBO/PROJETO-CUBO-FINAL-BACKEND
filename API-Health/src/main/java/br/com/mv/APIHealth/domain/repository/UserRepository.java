package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
