package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.DocumentAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentAccessRepository extends JpaRepository<DocumentAccess, Long> {
}
