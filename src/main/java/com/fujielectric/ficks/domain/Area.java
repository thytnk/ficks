package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="areas")  @Data
public class Area {
    @Id @Column(name="area_id")
    private Integer id;
    private String name;
    private Integer displayOrder;
}
