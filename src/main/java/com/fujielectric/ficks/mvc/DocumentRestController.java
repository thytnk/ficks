package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/rest/documents")
public class DocumentRestController {
    private Logger log = LoggerFactory.getLogger(DocumentRestController.class);

    @Autowired
    private SolrDocumentRepository documentRepository;

//    @RequestMapping(method=GET)
    public Iterable<Document> getAll() {
        log.info("getAll");
        Iterable<Document> docs =  documentRepository.findAll();
        for (Document doc: docs) {
            System.out.println(doc);
        }
        return docs;
    }

    @RequestMapping(method=GET)
    public List<Document> getAllDocuments(@RequestParam Map<String, String> params) {
        log.info("getAllDocuments");
        params.keySet().forEach(k -> log.info("{} = {}", k, params.get(k)));
        String text = params.get("text");

        if (text != null && !text.isEmpty()) {
            log.info("text:{}", text);
            return documentRepository.findByText(text);
        } else {
            List<Document> docs = new ArrayList<>();
            documentRepository.findAll().forEach(d -> docs.add(d));
            return docs;
        }
    }

//    @RequestMapping(value="/{category}",method=GET)
    public List<Document> getByCategory(@PathVariable("category") String category) {
        log.info("getByCategory category={}", category);
//        List<Document> docs = documentRepository.findByCategory(category);
        List<Document> docs = documentRepository.findByText(category);
        docs.forEach(d -> log.info("category={}",d.category));
        return docs;
    }

//    @RequestMapping("/{q}")
    public List<Document> getByText(@PathVariable("q") String q) {
        return documentRepository.findByText(q);
    }

}
