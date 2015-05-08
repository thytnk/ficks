package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="reasons")  @Data
public class Reason {
    @Id @Column(name="reason_id")
    public Integer id;
    public String name;
    public Integer displayOrder;
}
