package com.picke.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails {

    private static final long serialVersionUID = 1L;


    private String username;

    private boolean enabled;

    private String password;

    private String salt;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private transient Collection<GrantedAuthority> authorities;

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {  return authorities;}

    @Transient
    public boolean isAccountNonExpired() { return true;}

    @Transient
    public boolean isAccountNonLocked() { return true; }

    @Transient
    public boolean isCredentialsNonExpired() {return true; }

    @Transient
    public boolean isEnabled() {
        return this.enabled;
    }

    @Transient
    public void setUserAuthorities(List<String> authorities) {
        List<GrantedAuthority> listOfAuthorities = new ArrayList<GrantedAuthority>();
        for (String role : authorities) {
            listOfAuthorities.add(new GrantedAuthorityImpl(role));
        }
        this.authorities = listOfAuthorities;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public User
            () {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
