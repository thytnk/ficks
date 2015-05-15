package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.*;

import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/documents")
public class DocumentController extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private SolrDocumentRepository documentRepository;

    @Autowired
    private SolrOperations solrTemplate;

    @Autowired
    private GuiUtils gui;

    @RequestMapping(method=GET)
    public String home(DocumentSearchCommand documentSearchCommand, Model model) {
        Query query = new SimpleQuery(new Criteria("id").isNotNull());
        query.addSort(sortByPublishDate());
        Page resultPage = solrTemplate.queryForPage(query, Document.class);

        List<Document> docs = resultPage.getContent();

        gui.addDropDowns(model);
        model.addAttribute("list", resultPage);
        model.addAttribute("mode", "new");
        return "documents";
    }

    @RequestMapping(value="search",method=GET)
    public String search(DocumentSearchCommand documentSearchCommand, Model model) {
        log.info("search: {}", documentSearchCommand);
        FacetQuery query = new SimpleFacetQuery(documentSearchCommand.searchCriteria());
        query.setFacetOptions(new FacetOptions().addFacetOnField("doc_area").addFacetOnField("doc_purpose"));
        query.addSort(sortByPublishDate());
        query.setRows(100);
        FacetPage<Document> resultPage = solrTemplate.queryForFacetPage(query, Document.class);

        List<Document> docs = resultPage.getContent();
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
    public void download(HttpServletResponse res, @PathVariable("code")String code) throws IOException {
        log.info("download: {}", code);
        Document doc = documentRepository.findByCode(code);
        if (doc == null)
            return;

        File file = new File(doc.resourceName);
        String dFilename = new String(file.getName().getBytes("Windows-31J"), "ISO-8859-1");
        res.reset();
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setHeader("Content-Disposition", "attachment; filename=" + dFilename);

        OutputStream os = res.getOutputStream();

        InputStream in = new FileInputStream(file);

        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            os.write(b, 0, len);
        }
        in.close();
        os.close();
    }

    private Sort sortByPublishDate() {
        return new Sort(Sort.Direction.DESC, "doc_publish_date");
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
