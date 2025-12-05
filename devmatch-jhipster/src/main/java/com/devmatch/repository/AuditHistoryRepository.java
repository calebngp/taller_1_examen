package com.devmatch.repository;

import com.devmatch.domain.AuditHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuditHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditHistoryRepository extends JpaRepository<AuditHistory, Long> {}
