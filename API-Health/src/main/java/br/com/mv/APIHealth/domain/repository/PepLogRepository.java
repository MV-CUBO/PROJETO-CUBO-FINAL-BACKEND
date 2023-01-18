package br.com.mv.APIHealth.domain.repository;

import br.com.mv.APIHealth.domain.entity.PepLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PepLogRepository extends JpaRepository<PepLog, UUID> {
}
