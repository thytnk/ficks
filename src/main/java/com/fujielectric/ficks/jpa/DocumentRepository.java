package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//    @Query("SELECT COUNT(*) FROM Document AS d WHERE d.registerDate > :baseDate ")
//    public Long countDocumentThisYear(@Param("baseDate") Date baseDate);

    Optional<Document> findByCode(String code);
}
