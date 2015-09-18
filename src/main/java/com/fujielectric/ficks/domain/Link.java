package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="links")  @Data
public class Link {
    @Id
    @Column(name="link_id")
    private Integer id;
    private String  linkUrl;
    private String  linkName;
    private Integer displayOrder;
}
