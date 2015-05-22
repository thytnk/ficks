package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//    @Query("SELECT COUNT(*) FROM Document AS d WHERE d.registerDate > :baseDate ")
//    public Long countDocumentThisYear(@Param("baseDate") Date baseDate);

    public Optional<Document> findByCode(String code);
}
