package com.fujielectric.ficks.domain;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

//@Entity(name="documents")
@Data
public class Document {

    @Id
    @Indexed @Field
    public String id;

    /** 管理番号 */
    @Indexed @Field("doc_code")
    public String code;

    /** 資料別 */
    @Indexed @Field("doc_category")
    public String category;

    /** 種類 */
    @Indexed @Field("doc_purpose")
    public Integer purpose;

    /** 分野 */
    @Indexed @Field("doc_area")
    public Integer area;

    /** 成否 */
    @Indexed @Field("doc_result")
    public Integer result;

    /** 成否要因 */
    @Indexed @Field("doc_reason")
    public Integer reason;

    /** 部署名 */
    @Indexed @Field("doc_dept_name")
    public String deptName;

    /** 従業員番号 */
    @Indexed @Field("doc_emp_number")
    public String empNumber;

    /** 担当者名 */
    @Indexed @Field("doc_author_name")
    public String authorName;

    /** コメント */
    @Indexed @Field("doc_description")
    public String description;

    /** 発行日 */
    @Indexed @Field("doc_publish_date")
    public Date publishedDate;

    /** 顧客名 */
    @Indexed @Field("doc_customer_name")
    public String customerName;

    @Indexed @Field("resourcename")
    public String resourceName;

    @Indexed @Field("content_type")
    public List<String> contentType;

    @Indexed @Field("last_modified")
    public String lastModified;

    public String getFileName() {
        if (resourceName != null) {
            return Paths.get(resourceName).getFileName().toString();
        } else if (id != null) {
            return Paths.get(id).getFileName().toString();
        }
        return "";
    }


    @Override
    public String toString() {
        return id;
    }
}


