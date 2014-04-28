package com.picke.entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="users")
public class User implements Serializable, UserDetails {

//    private static final long serialVersionUID = 1L;

    //Original props

    @Id
    @Column(name="username")
    private String username;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="password")
    private String password;

    //bi-directional one-to-one association to Authority
//    @OneToOne
//    @JoinColumn(name="username")
//    private String authority;

    private String salt;

    // Getters & Setters for original props

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

    //Getters and setters for relation property

//    public String getAuthority() {
//        return this.authority;
//    }
//
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }

    //Spring Security props

    private transient Collection<GrantedAuthority> authorities;

    //UserDetails methods

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
        this.authorities = (Collection<GrantedAuthority>) listOfAuthorities;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    //Constructors

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
