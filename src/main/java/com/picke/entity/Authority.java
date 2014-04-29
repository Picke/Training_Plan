package com.picke.entity;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    private String authority;

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Authority() {
    }
}
