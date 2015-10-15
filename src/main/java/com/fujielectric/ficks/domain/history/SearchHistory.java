package com.fujielectric.ficks.domain.history;

import com.fujielectric.ficks.domain.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="search_history")
@Data
public class SearchHistory extends History {
    /** 資料別 */
    private String category;

    /** 種類 */
    private Integer purpose;

    /** 分野 */
    private Integer area;

    /** 成否 */
    private Integer result;

    /** 成否要因 */
    private Integer reason;

    /** 顧客名 */
    private String customer;

    /** 担当者 */
    private String author;

    /** 発行日FROM */
    private Date publishedFrom;

    /** 発行日TO */
    private Date publishedTo;

    /** キーワード */
    private String keyword;

    /** ページ */
    private int page;

    /** 検索結果件数 */
    private Long count;

    public SearchHistory() {}
    public SearchHistory(User user) {
        super(user, Action.SEARCH);
    }
/*
    @Override
    public User getUser() { return super.getUser(); }
    @Override
    public void setUser(User val) { super.setUser(val); }*/
}
