package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="categories") @Data
public class Category {
    @Id @Column(name="category_id")
    public Integer id;
    public String code;
    public String name;
    public Integer displayOrder;
}
