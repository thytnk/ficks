package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="results")  @Data
public class Result {
    @Id @Column(name="result_id")
    private Integer id;
    private String name;
    private Integer displayOrder;
}
