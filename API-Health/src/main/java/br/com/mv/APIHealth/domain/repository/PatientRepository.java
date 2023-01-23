package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.Pep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.mv.APIHealth.domain.entity.Patient;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    public Optional<Patient> findByCpf(String cpf);

    @Query("SELECT pep FROM Patient p WHERE p.id = :id")
    public Optional<Pep> findPepById(@Param("id") UUID id);
}
