package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="purposes")  @Data
public class Purpose {
    @Id @Column(name="purpose_id")
    public Integer id;
    public String name;
    public Integer displayOrder;
}
