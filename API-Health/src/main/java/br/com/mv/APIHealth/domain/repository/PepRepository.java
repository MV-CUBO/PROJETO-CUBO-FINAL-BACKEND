package br.com.mv.APIHealth.domain.repository;


import br.com.mv.APIHealth.domain.entity.Pep;
import br.com.mv.APIHealth.domain.enums.EStatePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PepRepository extends JpaRepository<Pep, UUID> {

    Optional<Pep> findByPepNumber(Integer pepNumber);

    List<Pep> findAllByStatus(EStatePatient status);

    long countByStatus(EStatePatient Status);



}
