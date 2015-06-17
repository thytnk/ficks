package com.fujielectric.ficks.domain;

import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;

@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public LoginUserDetails(User user) {
        super(user.getEmpNumber(), user.getEmpNumber(), AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()));
        this.user = user;
    }
}
