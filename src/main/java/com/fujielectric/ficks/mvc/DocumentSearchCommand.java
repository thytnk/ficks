package com.fujielectric.ficks.mvc;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.solr.core.query.Criteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 文書検索フォーム
 */
@Data
public class DocumentSearchCommand {
    private Logger log = LoggerFactory.getLogger(DocumentSearchCommand.class);

    private final String[] DEFAULT_CATEGORIES = {"A", "B", "C", "D", "E"};

    /** 資料別 */
    public List<String> category = Arrays.asList(DEFAULT_CATEGORIES);
    /** 分野 */
    public String area;
    /** ドキュメント種類 */
    public String purpose;
    /** 顧客名 */
    public String customer;
    /** 担当者 */
    public String author;
    /** 成否 */
    public String result;
    /** 成否要因 */
    public String reason;
    /** 発行日FROM */
    public String publishedFrom;
    /** 発行日TO */
    public String publishedTo;
    /** キーワード */
    public String keyword;

    Criteria searchCriteria() {
        log.debug("### searchCriteria");
        Criteria criteria = new Criteria("doc_disabled").is(false);
//        Criteria criteria = new Criteria("doc_category").in(1, 2, 3, 4);
//        Criteria criteria = new Criteria("doc_area").is(Integer.parseInt(area));

        if (category != null && !category.isEmpty()) {
            log.debug("category: {}", category.toArray());
            criteria = criteria.and(new Criteria("doc_category").in(category));
        }

        if (area != null && !"*".equals(area)) {
            log.debug("area: {}", area);
            criteria = criteria.and(new Criteria("doc_area").is(Integer.parseInt(area)));
        }

        if (purpose != null && !"*".equals(purpose)) {
            log.debug("purpose: {}", purpose);
            criteria = criteria.and(new Criteria("doc_purpose").is(Integer.parseInt(purpose)));
        }

        if (customer != null && !"".equals(customer)) {
            log.debug("customer: {}", customer);
            for (String s : customer.split("\\s")) {
                criteria = criteria.and(new Criteria("doc_customer_name").contains(s));
            }
        }

        if (author != null && !"".equals(author)) {
            log.debug("author: {}", author);
            for (String s : author.split("\\s")) {
                criteria = criteria.and(new Criteria("doc_author_name").contains(s)
                        .or(new Criteria("doc_emp_number").is(s)));
            }
        }

        if (result != null && !"*".equals(result)) {
            log.debug("result: {}", result);
            criteria = criteria.and(new Criteria("doc_result").is(Integer.parseInt(result)));
        }

        if (reason != null && !"*".equals(reason)) {
            log.debug("reason: {}", reason);
            criteria = criteria.and(new Criteria("doc_reason").is(Integer.parseInt(reason)));
        }


        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy/MM/dd");
        if (publishedFrom != null && !"".equals(publishedFrom)) {
            log.debug("publishedFrom: {}", publishedFrom);

            try {
                Date publishedDateFrom = dateParser.parse(publishedFrom);
                criteria = criteria.and(new Criteria("doc_publish_date").greaterThanEqual(publishedDateFrom));
            } catch (ParseException e) {
                log.debug("publishedFrom parse failed");
            }
        }

        if (publishedTo != null && !"".equals(publishedTo)) {
            log.debug("publishedTo: {}", publishedTo);

            try {
                Date publishedDateTo = dateParser.parse(publishedTo);
                criteria = criteria.and(new Criteria("doc_publish_date").lessThanEqual(publishedDateTo));
            } catch (ParseException e) {
                log.debug("publishedTo parse failed");
            }
        }

        if (keyword != null && !"".equals(keyword)) {
            log.debug("keyword: {}", keyword.split("\\s"));
            List<String> keywords = Arrays.asList(keyword.split("\\s"));
            for (String keyword: keywords) {
                criteria = criteria.and(new Criteria("text").expression(keyword));

                /*
                criteria = criteria.and(new Criteria("content").contains(keyword)
                        .or("resourcename").contains(keyword)
                        .or("doc_description").contains(keyword)
                        .or("doc_customer_name").contains(keyword)
                        .or("doc_author_name").contains(keyword)
                        .or("doc_code").contains(keyword));*/
            }
        }

        log.debug("searchCriteria = {}", criteria);
        return criteria;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
