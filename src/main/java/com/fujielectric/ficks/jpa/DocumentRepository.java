package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//    @Query("SELECT COUNT(*) FROM Document AS d WHERE d.registerDate > :baseDate ")
//    public Long countDocumentThisYear(@Param("baseDate") Date baseDate);

    /** 文書コード指定検索 */
    Optional<Document> findByCode(String code);

    /** アクセスランキング検索 */
    @Query("SELECT d FROM Document AS d      " +
            "WHERE d.indexed  = true         " +
            "  AND d.disabled = false        " +
            "ORDER BY SIZE(d.accessList) DESC")
    Page<Document> findMostAccessed(Pageable pageable);

    /** 新着文書検索 */
    @Query("SELECT d FROM Document AS d  " +
            "WHERE d.indexed  = true     " +
            "  AND d.disabled = false    " +
            "ORDER BY d.registerDate DESC")
    Page<Document> findLatest(Pageable pageable);
}
