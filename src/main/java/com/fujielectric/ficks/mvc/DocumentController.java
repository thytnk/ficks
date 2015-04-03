package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.solr.SolrDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

//    @Autowired
//    private Map<String, String> categories;


    @RequestMapping(method=GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");
//        List<Document> docs = new ArrayList<>();
//        documentRepository.findAll().forEach(d -> docs.add(d));

        Query query = new SimpleQuery(new Criteria("id").isNotNull());
        query.addSort(sortByPublishedDate());
        Page resultPage = solrTemplate.queryForPage(query, Document.class);

        List<Document> docs = resultPage.getContent();

//        return docs;
        mav.addObject("categories", categories());
        mav.addObject("areas", areas());
        mav.addObject("purposes", purposes());
        mav.addObject("results", results());
        mav.addObject("reasons", reasons());

        mav.addObject("command", new DocumentSearchCommand());
        mav.addObject("list", docs);
        return mav;
    }

    @RequestMapping(value="search",method=GET)
    public ModelAndView search(DocumentSearchCommand command) {
        ModelAndView mav = new ModelAndView("index");

        //List<Document> docs = documentRepository.findByTextContaining(searchCommand.keyword);
//        List<Document> docs = documentRepository.query(command.purpose, command.areaCommand());
//        List<Document> docs = documentRepository.query(command.purpose, Integer.parseInt(command.area));

        Query query = new SimpleQuery(command.searchCriteria());
        query.addSort(sortByPublishedDate());
//solrTemplate.queryForCursor(query, Document.class);

        Page resultPage = solrTemplate.queryForPage(query, Document.class);

        List<Document> docs = resultPage.getContent();

        mav.addObject("categories", categories());
        mav.addObject("areas", areas());
        mav.addObject("purposes", purposes());
        mav.addObject("results", results());
        mav.addObject("reasons", reasons());


        mav.addObject("command", command);
        mav.addObject("list", docs);
        return mav;
    }

    private Sort sortByPublishedDate() {
        return new Sort(Sort.Direction.DESC, "doc_publish_date");
    }

    private Map<String, String> categories() {
        Map<String, String> map = new Hashtable<>();
        map.put("A", "提案資料");
        map.put("B", "技術資料");
        map.put("C", "設定資料");
        map.put("D", "手順資料");
        map.put("E", "業務窓口");
        return map;
    }

    private Map<String, String> areas() {
        Map<String, String> map = new Hashtable<>();
        map.put("1", "小学校");
        map.put("2", "中学校");
        map.put("3", "高校");
        map.put("4", "大学");
        map.put("5", "公共");
        map.put("6", "金融");
        map.put("7", "産業");
        map.put("99", "その他");
        return map;
    }

    private Map<String, String> purposes() {
        Map<String, String> map = new Hashtable<>();
        map.put("1", "提案書");
        map.put("2", "技術書");
        map.put("3", "設定書");
        map.put("4", "マニュアル");
        map.put("5", "仕様書");
        map.put("6", "事例集");
        map.put("7", "手順書");
        map.put("99", "その他");
        return map;
    }

    private Map<String, String> results() {
        Map<String, String> map = new Hashtable<>();
        map.put("1", "成功");
        map.put("0", "失敗");
        map.put("9", "なし");
        return map;
    }

    private Map<String, String> reasons() {
        Map<String, String> map = new Hashtable<>();
        map.put("1", "価格");
        map.put("2", "顧客要件");
        map.put("3", "プレゼン力");
        map.put("4", "機能・性能");
        map.put("5", "サービス品質");
        map.put("6", "差別化");
        map.put("7", "政治的判断");
        map.put("8", "総合的判断");
        return map;
    }

}
