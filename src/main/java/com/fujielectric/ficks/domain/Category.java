package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="categories") @Data
public class Category {
    @Id @Column(name="category_id")
    private Integer id;
    private String code;
    private String name;
    private Integer displayOrder;
}
