package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="users")
@Data
public class User {

    @Id @Column(name="user_id")
    private Long id;

//    @Pattern(regexp = "(\\d{6})?", message="半角数字6文字です。")
    private String empNumber;

    private String empName;

    private String role;

    private boolean disabled;
}
