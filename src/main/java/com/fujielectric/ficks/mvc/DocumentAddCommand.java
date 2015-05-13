package com.fujielectric.ficks.mvc;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Pattern;

@Data
public class DocumentAddCommand {
    private Logger log = LoggerFactory.getLogger(DocumentAddCommand.class);

    /** 資料別 */
    @NotBlank //(message="{Department.code.Required.message}")
    @Pattern(regexp="[A-Z]")//, message="{Department.code.Ascii.message}")
    public String category;

    /** ドキュメント種類 */
    @NotBlank
    @Pattern(regexp="^\\d+$")
    public String purpose;

    /** 分野 */
    public String area;
    /** 成否 */
    public String result;
    /** 成否要因 */
    public String reason;
    /** 従業員番号 */
    public String empNumber;
    /** 担当者名 */
    public String authorName;

    /** コメント */
    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9 ]+$")//, message="{Department.code.Ascii.message}")
    public String description;
    /** 発行日 */
    public String publishedDate;
    /** 顧客名 */
    public String customerName;
}
