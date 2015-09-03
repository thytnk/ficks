package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.config.GuiProperties;
import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.jpa.DocumentAccessRepository;
import com.fujielectric.ficks.jpa.DocumentRepository;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;

import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/documents")
public class DocumentController extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private SolrDocumentRepository solrDocumentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentAccessRepository documentAccessRepository;

    @Autowired
    private SolrOperations solrTemplate;

    @Autowired
    private GuiUtils gui;

    @Autowired
    private DocumentService documentService;

    @Autowired
    GuiProperties guiProperties;

    @ModelAttribute
    GuiProperties guiProperties() {
        return guiProperties;
    }

    @ModelAttribute
    User loginUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        return loginUserDetails.getUser();
    }

    @RequestMapping(method=GET)
    public String home(DocumentSearchCommand documentSearchCommand, Model model) {
        Query query = new SimpleQuery(new Criteria("id").isNotNull());
        query.addSort(sortByRegisterDate());
        Page<Document> latestList = documentRepository.findLatest(new PageRequest(0, 10));
        model.addAttribute("latestList", latestList);

        Page<Document> mostAccessedList = documentRepository.findMostAccessed(new PageRequest(0, 10));
        model.addAttribute("mostAccessedList", mostAccessedList);
        model.addAttribute("list", mostAccessedList);

        gui.addDropDowns(model);
        model.addAttribute("mode", "new");
        return "documents";
    }

    @RequestMapping(value="search",method=GET)
    public String search(DocumentSearchCommand documentSearchCommand, Model model,
                         @RequestParam(value="page", defaultValue="0") int page) {
        log.info("search: {}", documentSearchCommand);
        FacetQuery query = new SimpleFacetQuery(documentSearchCommand.searchCriteria());
        query.setFacetOptions(new FacetOptions().addFacetOnField("doc_area").addFacetOnField("doc_purpose"));
//        query.addSort(sortByRegisterDate());
//        query.setRows(5);
//        query.setPageRequest(new PageRequest(page, 10, Sort.Direction.DESC, "doc_register_date"));
        query.setPageRequest(new PageRequest(page, 10));
        FacetPage<Document> resultPage = solrTemplate.queryForFacetPage(query, Document.class);

        Page<FacetFieldEntry> areaFacet = resultPage.getFacetResultPage("doc_area");
        model.addAttribute("areaFacet", areaFacet);

        Page<FacetFieldEntry> purposeFacet = resultPage.getFacetResultPage("doc_purpose");
        model.addAttribute("purposeFacet", purposeFacet);

/*        for (Page<? extends FacetEntry> page : resultPage.getAllFacets()) {
            page.
            for (FacetEntry facetEntry : page.getContent()) {
                facetEntry.
                String categoryName = facetEntry.getValue();  // name of the category
                long count = facetEntry.getValueCount();      // number of books in this category
            }
        }*/
        gui.addDropDowns(model);
//        model.addAttribute("command", command);
        model.addAttribute("list", resultPage);
        model.addAttribute("mode", "search");
        return "documents";
    }

    @RequestMapping(value="/{code}/download", produces="application/force-download")
    public void download(HttpServletResponse res, @PathVariable("code")String code,
                         @AuthenticationPrincipal LoginUserDetails loginUserDetails) throws IOException {
        log.info("download: {}", code);
        Document doc = solrDocumentRepository.findByCode(code);
        if (doc == null) {
            log.error("download failed: {}", code);
            return;
        }

        DocumentAccess documentAccess = new DocumentAccess(loginUserDetails.getUser(), doc);
        documentAccessRepository.save(documentAccess);

        Path path = documentService.getPathOf(code);
        String dFilename = new String(doc.getFileName().getBytes("Windows-31J"), "ISO-8859-1");
        res.reset();
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setHeader("Content-Disposition", "attachment; filename=" + dFilename);

        OutputStream os = res.getOutputStream();

        InputStream in = Files.newInputStream(path);
        //InputStream in = new FileInputStream(file);

        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            os.write(b, 0, len);
        }
        in.close();
        os.close();
    }

    private Sort sortByRegisterDate() {
        return new Sort(Sort.Direction.DESC, "doc_register_date");
    }
/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView exceptionBadRequest(MethodArgumentNotValidException ex){
        try {
            log.warn("入力値チェックエラーが発生しました");

            List<String> errorList = new ArrayList<String>();
            BindingResult br = ex.getBindingResult();
            // エラーメッセージを拾ってJSONで返す
//            for (ObjectError err : br.getAllErrors()){
  //              errorList.add(messageSourceHelper.getMessage(req, err));
    //        }
            return new ModelAndView("index");
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }*/
}
