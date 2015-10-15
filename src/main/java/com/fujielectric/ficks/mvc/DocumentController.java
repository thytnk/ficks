package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.config.GuiProperties;
import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.domain.history.*;

import com.fujielectric.ficks.jpa.DocumentRepository;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import java.util.List;
import java.util.stream.Collectors;

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
    private SolrOperations solrTemplate;

    @Autowired
    private GuiUtils gui;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private HistoryRepository historyRepository;

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

    @ModelAttribute("recentSearchKeywords")
    List<String> getRecentSearchKeywords(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        List<History> historyList = historyRepository.findByUserAndAction(loginUserDetails.getUser(), Action.SEARCH);

        return historyList.stream()
                .sorted((a, b) -> b.getAccessDate().compareTo(a.getAccessDate()))
                .map(h -> (SearchHistory) h)
                .map(h -> h.getKeyword())
                .filter(s -> StringUtils.isNotBlank(s))
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
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
                         @RequestParam(value="page", defaultValue="0") int page,
                         @AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        log.info("search: {}", documentSearchCommand);
        FacetQuery query = new SimpleFacetQuery(documentSearchCommand.searchCriteria());
        query.setFacetOptions(new FacetOptions().addFacetOnField("doc_area").addFacetOnField("doc_purpose"));
//        query.addSort(sortByRegisterDate());
//        query.setRows(5);
//        query.setPageRequest(new PageRequest(page, 10, Sort.Direction.DESC, "doc_register_date"));
        query.setPageRequest(new PageRequest(page, 10));
        FacetPage<Document> resultPage = solrTemplate.queryForFacetPage(query, Document.class);

        saveSearchHistory(documentSearchCommand, page, loginUserDetails.getUser(), resultPage);

        Page<FacetFieldEntry> areaFacet = resultPage.getFacetResultPage("doc_area");
        model.addAttribute("areaFacet", areaFacet);

        Page<FacetFieldEntry> purposeFacet = resultPage.getFacetResultPage("doc_purpose");
        model.addAttribute("purposeFacet", purposeFacet);

        gui.addDropDowns(model);
        model.addAttribute("list", resultPage);
        model.addAttribute("mode", "search");
        return "documents";
    }

    private void saveSearchHistory(DocumentSearchCommand documentSearchCommand, int page, User user, Page<Document> resultPage) {
        SearchHistory history = documentSearchCommand.getSearchHistory(user);
        history.setPage(page + 1);
        history.setCount(resultPage.getTotalElements());
        historyRepository.save(history);
    }

    @RequestMapping(value="/{code}/download", produces="application/force-download")
    public void download(HttpServletResponse res, @PathVariable("code")String code,
                         @RequestParam("ref") String referrer,
                         @RequestParam("refcount") String refindex,
                         @RequestParam("refpage") String refpage,
                         @AuthenticationPrincipal LoginUserDetails loginUserDetails) throws IOException {
        log.info("download: {}", code);
        Document doc = solrDocumentRepository.findByCode(code);
        if (doc == null) {
            log.error("download failed: {}", code);
            return;
        }

        saveDownloadHistory(doc, referrer, refindex, refpage, loginUserDetails.getUser());

        Path path = documentService.getPathOf(code);
        String dFilename = new String(doc.getFileName().getBytes("Windows-31J"), "ISO-8859-1");
        res.reset();
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setHeader("Content-Disposition", "attachment; filename=" + dFilename);

        OutputStream os = res.getOutputStream();

        InputStream in = Files.newInputStream(path);

        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            os.write(b, 0, len);
        }
        in.close();
        os.close();
    }

    private void saveDownloadHistory(Document document,
                                     String referrer, String refindex, String refpage,
                                     User user) {
        DownloadHistory history = new DownloadHistory(user, document);
        history.setReferrer(referrer);
        history.setReferrerPage(NumberUtils.isNumber(refpage) ? NumberUtils.createInteger(refpage) : null);
        history.setReferrerIndex(NumberUtils.isNumber(refindex) ? NumberUtils.createInteger(refindex) : null);
        historyRepository.save(history);
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
