package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
