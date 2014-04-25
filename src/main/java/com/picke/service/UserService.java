package com.picke.service;

import com.picke.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface UserService {

    public User getUserByUserName(String userName);

    public List<GrantedAuthority> getAuthoritiesByUserName(String userName);

    public List<User> getAllUsers();

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);

    public String getCurrentUserName();

    public String getCurrentAuthority();

    public boolean isAuthenticated();

}