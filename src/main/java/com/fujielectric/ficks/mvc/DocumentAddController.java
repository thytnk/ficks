package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.domain.DocumentService;
import com.fujielectric.ficks.domain.PrintDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

import java.io.IOException;
import java.nio.file.Paths;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@RequestMapping("/documents/add")
public class DocumentAddController extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(DocumentAddController.class);

    @Autowired
    private GuiUtils gui;

    @Autowired
    private DocumentService documentService;

    @ModelAttribute
    Document setupDocument() {
        Document document = new Document();
        document.setCategory("A");
        document.setArea(99);
        document.setPurpose(99);
        document.setResult(9);
        document.setPrintDirection(PrintDirection.Horizontal);
        document.setIndexed(false);
        return document;
    }

    @ResponseStatus(OK)
    @RequestMapping(method=GET)
    String addForm(Document document, Model model) {
        gui.addDropDowns(model);
        return "input";
    }

    @ResponseStatus(OK)
    @RequestMapping(method=POST)
    String add(@Validated Document document,
                      BindingResult result,
                      Model model,
                      @RequestParam("file") MultipartFile multipartFile
                      ) throws IOException {
        log.debug("add:");
        gui.addDropDowns(model);

        if (multipartFile.isEmpty()) {
            result.addError(new ObjectError("file", "{NotBlank.file}"));
        }

        if (result.hasErrors()) {
            for (ObjectError oe : result.getAllErrors()) {
                log.info("has error: {} - {}", oe.toString(), oe.getDefaultMessage());
            }
            return "input";
        }

        String originalFilename = originalFilename(multipartFile);
        documentService.saveDataAndFile(document, originalFilename, multipartFile.getBytes());

        log.info("add success: {}", document.getCode());
        return "input";
    }

    private String originalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return Paths.get(originalFilename).getFileName().toString();
    }
}
