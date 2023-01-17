package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, UUID> {
}
