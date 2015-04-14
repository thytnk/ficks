package com.fujielectric.ficks.solr;

import com.fujielectric.ficks.domain.Document;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.*;

import java.util.List;

public interface SolrDocumentRepository extends SolrCrudRepository<Document, String> {
    Document findByCode(String code);

/*
    Page<Document> findByPopularity(Integer popularity, Pageable page);

    Page<Document> findByNameOrDescription(@Boost(2) String name, String description, Pageable page);

    @Highlight
    HighlightPage<Document> findByNameIn(Collection<String> name, Page page);

    @Query(value = "name:?0")
    @Facet(fields = { "cat" }, limit=20)
    FacetPage<Document> findByNameAndFacetOnCategory(String name, Pageable page);
    */

    //List<Document> findByText(String text);
//    List<Document> findByCategoryAndText(String category, String text);
//    @Query("doc_category:?0")
//    List<Document> findByCategory(String category);
//    List<Document> findByTextContaining(String text);

//    @Query("doc_purpose:?0 AND doc_area(?1)")
//    List<Document> query(String purpose,  List<Integer> area);
//    @Query("doc_purpose:?0 AND doc_area:?1")
//    List<Document> query(String purpose,  Integer area);

//    Page<Document> findTop10(Sort sort);

}
