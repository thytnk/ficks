package com.fujielectric.ficks.domain;

import lombok.Data;
import static org.apache.commons.lang3.StringUtils.leftPad;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.solr.core.mapping.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="documents")
@Data
public class Document {

    @Indexed @Field
    @Id @Column(name="document_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="documents_id_seq")
    @SequenceGenerator(name="documents_id_seq", sequenceName = "documents_document_id_seq")
    private Long id;

    /** 登録年: 登録時に生成 */
    private Integer largeCode;

    /** 連番: 登録時に生成 */
    @Generated(GenerationTime.INSERT)
    @Column(name="small_code", insertable=false, updatable=false)
    private Integer smallCode;

    /** リビジョン: 登録時に生成、更新時にインクリメント */
    private Integer revision;

    /** 管理番号 */
    String documentCode() {
        return new StringBuilder()
                .append(category)
                .append(largeCode % 100)
                .append('-')
                .append(leftPad(String.valueOf(smallCode % 100_000).toString(), 5, '0'))
                .append('-')
                .append(leftPad(String.valueOf(revision % 100).toString(), 2, '0'))
                .toString();
    }

    /** 管理番号 (Solr使用時) */
    @Indexed @Field("doc_code")
    private String code;

    /** 資料別 */
    @Indexed @Field("doc_category")
    @NotBlank @Pattern(regexp = "[A-Z]")
    private String category;

    /** 種類 */
    @Indexed @Field("doc_purpose")
    @NotNull
    private Integer purpose;

    /** 分野 */
    @Indexed @Field("doc_area")
    @NotNull
    private Integer area;

    /** 成否 */
    @Indexed @Field("doc_result")
    @NotNull
    private Integer result;

    /** 成否要因 */
    @Indexed @Field("doc_reason")
    @NotNull
    private Integer reason;

    /** 部署名 */
    @Indexed @Field("doc_dept_name")
    private String deptName;

    /** 従業員番号 */
    @Indexed @Field("doc_emp_number")
    private String empNumber;

    /** 担当者名 */
    @Indexed @Field("doc_author_name")
    private String authorName;

    /** コメント */
    @Indexed @Field("doc_description")
    private String description;

    /** 発行日 */
    @Indexed @Field("doc_publish_date")
    @Temporal(TemporalType.DATE)
    @Past
    private Date publishDate;

    /** ファイル名 */
    @Indexed @Field("doc_file_name")
    private String fileName;

    /** 登録日 */
    @Indexed @Field("doc_register_date")
    @Temporal(TemporalType.DATE)
    private Date registerDate;

    /** 顧客名 */
    @Indexed @Field("doc_customer_name")
    private String customerName;

    /** 論理削除 */
    @Indexed @Field("doc_disabled")
    private boolean disabled;

    /** サムネイル印刷方向 */
    @NotNull
    @Enumerated(EnumType.STRING)
    private PrintDirection printDirection;

    /** サムネイル印刷日時 */
    @Temporal(TemporalType.DATE)
    private Date printDate;

    /** Solrインデックス日時 */
    @Temporal(TemporalType.DATE)
    private Date indexDate;
/*
    @Transient
    @Indexed @Field("resourcename")
    public String resourceName;

    @Transient
    @Indexed @Field("content_type")
    public List<String> contentType;

    @Transient
    @Indexed @Field("last_modified")
    public String lastModified;
*/
    //@Transient
    //public String getFileName() {
    //    if (resourceName != null) {
    //        return Paths.get(resourceName).getFileName().toString();
    //    } /*else if (id != null) {
    //        return Paths.get(id).getFileName().toString();
    //    }*/
    //    return "";
    //}

    @SuppressWarnings("UnusedDeclaration")
    @PrePersist void onPrePersist() {
        largeCode = LocalDate.now().getYear() % 100;
        revision = 1;
        registerDate = new Date();
    }

    @SuppressWarnings("UnusedDeclaration")
    @PostPersist void onPostPersist() {
        code = documentCode();
    }

    @Override
    public String toString() {
        return code;
    }
}


