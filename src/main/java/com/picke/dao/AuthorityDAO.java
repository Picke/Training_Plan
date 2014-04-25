package com.picke.dao;

import java.util.List;

import com.picke.entity.Authority;

public interface AuthorityDAO {

    public Authority getAuthorityByUserName(String username);

    List<String> getUsersByAuthotity(String authority);

    public List<Authority> getAllAuthorities();

    public void addAuthority(Authority authority);

    public void updateAuthority(Authority authority);

    public void deleteAuthority(Authority authority);

}
