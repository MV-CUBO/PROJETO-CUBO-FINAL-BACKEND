package br.com.mv.APIHealth.domain.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import br.com.mv.APIHealth.domain.entity.Pep;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PepRepository extends JpaRepository<Pep, UUID> {

}
