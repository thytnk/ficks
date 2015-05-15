package com.fujielectric.ficks.domain;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.solr.core.mapping.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Entity(name="documents")
@Data
public class Document {

    @Indexed @Field
    @Id @Column(name="document_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documents_seq")
    @SequenceGenerator(name="documents_seq", sequenceName = "documents_document_id_seq")
    public Long id;

    /** 管理番号 */
    @Indexed @Field("doc_code")
    public String code;

    /** 資料別 */
    @Indexed @Field("doc_category")
    @NotBlank @Pattern(regexp = "[A-Z]")
    public String category;

    /** 種類 */
    @Indexed @Field("doc_purpose")
    @NotNull
    public Integer purpose;

    /** 分野 */
    @Indexed @Field("doc_area")
    @NotNull
    public Integer area;

    /** 成否 */
    @Indexed @Field("doc_result")
    @NotNull
    public Integer result;

    /** 成否要因 */
    @Indexed @Field("doc_reason")
    @NotNull
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
    @Temporal(TemporalType.DATE)
    @Past
    public Date publishDate;

    /** ファイル名 */
    public String fileName;

    /** 登録日 */
    @Indexed @Field("doc_register_date")
    @Temporal(TemporalType.DATE)
    public Date registerDate;

    /** 顧客名 */
    @Indexed @Field("doc_customer_name")
    public String customerName;

    /** 論理削除 */
    @Indexed @Field("doc_disabled")
    public boolean disabled;

    /** サムネイル印刷方向 */
    @NotNull
    public PrintDirection printDirection;

    /** サムネイル印刷日時 */
    @Temporal(TemporalType.DATE)
    public Date printDate;

    /** Solrインデックス日時 */
    @Temporal(TemporalType.DATE)
    public Date indexDate;

    @Transient
    @Indexed @Field("resourcename")
    public String resourceName;

    @Transient
    @Indexed @Field("content_type")
    public List<String> contentType;

    @Transient
    @Indexed @Field("last_modified")
    public String lastModified;

    @Transient
    public String getFileName() {
        if (resourceName != null) {
            return Paths.get(resourceName).getFileName().toString();
        } /*else if (id != null) {
            return Paths.get(id).getFileName().toString();
        }*/
        return "";
    }


    @Override
    public String toString() {
        return code;
    }
}


