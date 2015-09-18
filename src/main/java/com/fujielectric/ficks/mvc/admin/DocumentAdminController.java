package com.fujielectric.ficks.mvc.admin;

import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.jpa.DocumentRepository;
import com.fujielectric.ficks.mvc.GuiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 */
@Controller
@RequestMapping("/admin/documents")
public class DocumentAdminController {
    private Logger log = LoggerFactory.getLogger(DocumentAdminController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ThumbnailService thumbnailService;

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private GuiUtils gui;

    @ModelAttribute
    User loginUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        return loginUserDetails.getUser();
    }

    @ResponseStatus(OK)
    @RequestMapping(method=GET)
    String list(Model model,
                @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        List<Document> list = documentService.listSortedByDate();
        model.addAttribute("documents", list);
        return "admin/documents/index";
    }

    @ModelAttribute
    Document setupDefaultDocument() {
        Document document = new Document();
        document.setCategory("A");
//        document.setArea(99);
//        document.setPurpose(99);
//        document.setResult(9);
        document.setIndexed(false);
        return document;
    }

    @ResponseStatus(OK)
    @RequestMapping(value="add", method=GET)
    String addForm(Document document, Model model,
                   @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        gui.addDropDowns(model);
        return "admin/documents/add";
    }

    @ResponseStatus(OK)
    @RequestMapping(value="add", method=POST)
    String add(@Validated Document document,
               BindingResult result,
               Model model,
               @RequestParam("file") MultipartFile multipartFile,
               @RequestParam("thumbnail") MultipartFile thumbnailFile,
               @AuthenticationPrincipal LoginUserDetails loginUserDetails
    ) throws IOException {
        log.debug("add:");
        gui.addDropDowns(model);

        if (multipartFile.isEmpty()) {
            result.addError(new ObjectError("file", "{NotBlank.file}"));
        }

/*        if (!thumbnailFile.isEmpty()) {
            if (!thumbnailFile.getName().endsWith(".jpg"))
                result.addError(new ObjectError("thumbnail", "{typeMismatch}"));
        }*/

        if (result.hasErrors()) {
            result.getAllErrors().forEach(oe -> log.info("入力エラー: {} - {}", oe.toString(), oe.getDefaultMessage()));
            return "admin/documents/add";
        }

        document.setOriginalFileName(originalFilename(multipartFile));
        documentService.saveDataAndFile(document, multipartFile.getBytes());

        if (!thumbnailFile.isEmpty()) {
            thumbnailService.saveThumbnail(document, thumbnailFile.getBytes());
        }

        model.addAttribute("appStatus", "success");

        log.info("add success: {}", document.getCode());
        return "redirect:/admin/documents/add";
    }

    @ResponseStatus(OK)
    @RequestMapping(value="{code}/update", method=GET)
    public String updateForm(@PathVariable("code") String code, Model model,
                             @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        gui.addDropDowns(model);
        Optional<Document> result = repository.findByCode(code);
        result.ifPresent(document -> model.addAttribute("document", document));
        return "admin/documents/update";
    }

    @ResponseStatus(OK)
    @RequestMapping(value="{code}/update", method=POST)
    String update(@Validated Document document,
               BindingResult result,
               Model model,
               @RequestParam("file") MultipartFile multipartFile,
               @RequestParam("thumbnail") MultipartFile thumbnailFile,
               @AuthenticationPrincipal LoginUserDetails loginUserDetails
    ) throws IOException {
        log.debug("update:");
        gui.addDropDowns(model);
/*
        if (!thumbnailFile.isEmpty() && !thumbnailFile.getName().endsWith(".jpg")) {
            result.addError(new ObjectError("thumbnail", "{typeMismatch}"));
        }
*/
        if (result.hasErrors()) {
            result.getAllErrors().forEach(oe -> log.info("入力エラー: {} - {}", oe.toString(), oe.getDefaultMessage()));
            return "admin/documents/update";
        }

        if (multipartFile.isEmpty()) {
            documentService.saveData(document);
        } else {
            document.setOriginalFileName(originalFilename(multipartFile));
            documentService.saveDataAndFile(document, multipartFile.getBytes());
        }

        if (!thumbnailFile.isEmpty()) {
            thumbnailService.saveThumbnail(document, thumbnailFile.getBytes());
        }

        List<String> appMessages = new ArrayList<>();
        appMessages.add("success.update.resume");
        model.addAttribute("appMessages", appMessages);

        log.info("update success: {}", document.getCode());
        return "redirect:/admin/documents";
    }

    private String originalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return Paths.get(originalFilename).getFileName().toString();
    }
}
