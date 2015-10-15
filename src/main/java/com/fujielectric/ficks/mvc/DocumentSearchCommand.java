package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.User;
import com.fujielectric.ficks.domain.history.SearchHistory;
import lombok.Data;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.math.NumberUtils.*;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
            for (String s : customer.replaceAll(":"," ").split("[\\s　]+")) {
                criteria = criteria.and(new Criteria("doc_customer_name").expression(s));
            }
        }

        if (author != null && !"".equals(author)) {
            log.debug("author: {}", author);
            for (String s : author.replaceAll(":"," ").split("[\\s　]+")) {
                criteria = criteria.and(new Criteria("doc_author_name").expression(s)
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
            log.debug("keyword: {}", keyword.trim().split("[\\s　\\(\\)]+"));
            List<String> keywords = Arrays.asList(keyword.replaceAll(":", " ").replaceAll("　", " ").trim().split("[\\s\\(\\)]+"));
//            criteria = criteria.and(new Criteria("text").expression(keyword.replaceAll(":"," ")));
            for (String s: keywords) {
                criteria = criteria.and(new Criteria("text").expression(s));
            }
        }

        log.debug("searchCriteria = {}", criteria);
        return criteria;
    }

    SearchHistory getSearchHistory(User user) {
        SearchHistory history = new SearchHistory(user);
        history.setCategory(join(category, ""));

        history.setArea(isNumber(area) ? createInteger(area) : null);
        history.setPurpose(isNumber(purpose) ? createInteger(purpose) : null);
        history.setResult(isNumber(result) ? createInteger(result) : null);
        history.setReason(isNumber(reason) ? createInteger(reason) : null);
        history.setCustomer(trim(customer));
        history.setAuthor(trim(author));
        history.setKeyword(trim(keyword));

        try {
            if (publishedFrom != null && !"".equals(publishedFrom)) {
                history.setPublishedFrom(DateUtils.parseDate(publishedFrom, "yyyy/MM/dd"));
            }

            if (publishedTo != null && !"".equals(publishedTo)) {
                history.setPublishedTo(DateUtils.parseDate(publishedTo, "yyyy/MM/dd"));
            }
        } catch (ParseException e) {
            log.debug("publishedFrom/To parse failed");
        }
        return history;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
