package br.com.mv.APIHealth.domain.repository;




import br.com.mv.APIHealth.domain.enums.EStatePatient;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.mv.APIHealth.domain.entity.Pep;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PepRepository extends JpaRepository<Pep, UUID> {

    Optional<Pep> findByPepNumber(String pepNumber);

    List<Pep> findAllByStatus(EStatePatient status);

    long countByStatus(EStatePatient Status);


}
