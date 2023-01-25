package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.Patient;
import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatus;
import br.com.mv.APIHealth.domain.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    public Optional<Patient> findByCpf(String cpf);

    @Query("SELECT pep FROM Patient p WHERE p.id = :id")
    public Optional<Pep> findPepById(@Param("id") UUID id);
    // @Query(value = "SELECT COUNT(p) FROM Patient p WHERE p.status" + " = 'ACTIVATE'")
    @Query("SELECT COUNT(p) FROM Patient p WHERE p.status = ?1")
    public Long countPatientByStatus(EStatus value);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.gender = ?1")
    public Long countPatientByGender(Gender value);
}
