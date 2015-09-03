package com.fujielectric.ficks.domain;

import lombok.Data;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.apache.commons.lang3.StringUtils.isBlank;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    /** リビジョン: 登録時に生成、改版時にインクリメント */
//    @NotNull @Min(1) @Max(99)
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
    @NotNull @Pattern(regexp = "([A-Z])?")
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
    private Integer reason;

    /** 部署名 */
    @Indexed @Field("doc_dept_name")
    @Size(max=50)
    private String deptName;

    /** 従業員番号 */
    @Indexed @Field("doc_emp_number")
    @Pattern(regexp = "(\\d{6})?", message="半角数字6文字です。")
    private String empNumber;

    /** 担当者名 */
    @Indexed @Field("doc_author_name")
    @Size(max=50)
    private String authorName;

    /** コメント */
    @Indexed @Field("doc_description")
    @Size(max=1300)
    private String description;

    /** 発行日 */
    @Indexed @Field("doc_publish_date")
    @Temporal(TemporalType.DATE)
    @NotNull @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date publishDate;

    /** ファイル名 */
    @Indexed @Field("doc_file_name")
    @Size(max=200)
    @Pattern(regexp = "(.+\\.[a-zA-Z0-9]{3,5})?")
    private String fileName;

    /** ファイル名 */
    private String originalFileName;

    /** 登録日 */
    @Indexed @Field("doc_register_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date updateDate;

    /** 顧客名 */
    @Indexed @Field("doc_customer_name")
    @Size(max=50,message="{0}文字以内で入力してください。")
    private String customerName;

    /** 論理削除 */
    @Indexed @Field("doc_disabled")
    private boolean disabled;

    /** サムネイル印刷日時 */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date printDate;

    /** Solrインデックス状態(最新ならtrue) */
    private boolean indexed;

    /** Solrインデックス日時 */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date indexDate;

    @OneToMany(mappedBy = "document", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DocumentAccess> accessList;

    @PrePersist void onPrePersist() {
        if (isBlank(fileName)) {
            setFileName(getOriginalFileName());
        }

        if (largeCode == null) {
            setLargeCode(LocalDate.now().getYear() % 100);
        }

        if (revision == null) {
            setRevision(1);
        }
        setRegisterDate(new Date());
        setUpdateDate(new Date());
        setIndexed(false);
    }

    @PostPersist void onPostPersist() {
        code = documentCode();
    }

    @PreUpdate void onPreUpdate() {
        if (isBlank(fileName)) {
            setFileName(getOriginalFileName());
        }

        setUpdateDate(new Date());
        setIndexed(false);
    }

    @Override
    public String toString() {
        return code;
    }
}


