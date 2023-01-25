package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.Doctor;
import br.com.mv.APIHealth.domain.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    public Optional<Doctor> findByCpf(String cpf);
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.status = ?1")
    public Long countDoctorByStatus(EStatus value);
}
