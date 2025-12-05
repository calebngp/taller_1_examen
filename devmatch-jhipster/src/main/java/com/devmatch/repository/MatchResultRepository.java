package com.devmatch.repository;

import com.devmatch.domain.MatchResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {}
