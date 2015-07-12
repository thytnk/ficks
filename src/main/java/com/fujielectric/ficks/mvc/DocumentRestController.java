package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.domain.DocumentService;
import com.fujielectric.ficks.jpa.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
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
    @RequestMapping(method=POST)
    Document add(@Validated Document document,
               BindingResult result,
               @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        log.debug("add from api:");

        if (multipartFile.isEmpty()) {
            result.addError(new ObjectError("file", "{NotBlank.file}"));
        }

        if (result.hasErrors()) {
            for (ObjectError oe : result.getAllErrors()) {
                log.info("has error: {} - {}", oe.toString(), oe.getDefaultMessage());
            }
            return null;
        }
        // APIではOriginalFileNameも既に入っている想定
        Document resultDocument = documentService.saveDataAndFile(document, multipartFile.getBytes());

        log.info("add success: {}", document.getCode());
        return resultDocument;
    }

    @ResponseStatus(OK)
    @RequestMapping(value="index/{code}", method=POST)
    public void updateIndex(@PathVariable("code") String code) {
        Optional<Document> opt = jpaRepository.findByCode(code);
        opt.ifPresent(document -> {
            documentService.updateIndex(document);
            log.info("インデックスを更新しました: {}", code);
        });
    }
}
