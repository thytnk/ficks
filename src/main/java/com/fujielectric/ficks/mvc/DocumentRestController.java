package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.domain.DocumentService;
import com.fujielectric.ficks.jpa.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestController {
    private Logger log = LoggerFactory.getLogger(DocumentRestController.class);

    @Autowired
    private DocumentRepository jpaRepository;

    @Autowired
    private DocumentService documentService;

    @ResponseStatus(OK)
    @RequestMapping(value="{code}/index", method=GET)
    public void updateIndex(@PathVariable("code") String code) {
        Optional<Document> opt = jpaRepository.findByCode(code);
        opt.ifPresent(document -> {
            documentService.updateIndex(document);
            log.info("インデックスを更新しました: {}", code);
        });
    }
}
