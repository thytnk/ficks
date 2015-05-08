package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private Logger log = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private SolrDocumentRepository documentRepository;

    @Autowired
    private SolrOperations solrTemplate;

    @Autowired
    private GuiUtils gui;


    @RequestMapping(method=GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");

        Query query = new SimpleQuery(new Criteria("id").isNotNull());
        query.addSort(sortByPublishedDate());
        Page resultPage = solrTemplate.queryForPage(query, Document.class);

        List<Document> docs = resultPage.getContent();

        gui.addDropDowns(mav);
        mav.addObject("command", new DocumentSearchCommand());
        mav.addObject("list", resultPage);
        mav.addObject("mode", "new");
        return mav;
    }

    @RequestMapping(value="search",method=GET)
    public ModelAndView search(DocumentSearchCommand command) {
        log.info("search: {}", command);
        ModelAndView mav = new ModelAndView("index");

        Query query = new SimpleQuery(command.searchCriteria());
        query.addSort(sortByPublishedDate());
        query.setRows(100);
        Page resultPage = solrTemplate.queryForPage(query, Document.class);

        List<Document> docs = resultPage.getContent();

        gui.addDropDowns(mav);
        mav.addObject("command", command);
        mav.addObject("list", resultPage);
        mav.addObject("mode", "search");
        return mav;
    }

    @RequestMapping(value="/{code}/download", produces="application/force-download")
    public void download(HttpServletResponse res, @PathVariable("code")String code) throws IOException {
        log.info("download: {}", code);
        Document doc = documentRepository.findByCode(code);
        if (doc == null)
            return;

        File file = new File(doc.id);
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

    private Sort sortByPublishedDate() {
        return new Sort(Sort.Direction.DESC, "doc_publish_date");
    }
}
