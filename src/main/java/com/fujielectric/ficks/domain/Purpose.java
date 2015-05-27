package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="purposes")  @Data
public class Purpose {
    @Id @Column(name="purpose_id")
    private Integer id;
    private String name;
    private Integer displayOrder;
}
