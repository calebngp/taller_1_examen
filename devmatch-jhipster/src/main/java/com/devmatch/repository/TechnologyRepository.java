package com.devmatch.repository;

import com.devmatch.domain.Technology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Technology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {}
