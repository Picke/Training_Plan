package com.picke.service;

import com.picke.dao.UserDAO;
import com.picke.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    @Override
    public User getUserByUserName(String userName) {
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setAuthority("ROLE_USER");
        return user;
    }

    @Override
    public List<GrantedAuthority> getAuthoritiesByUserName(String userName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> getAllUsers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        return auth.getName();
    }

    @Override
    public String getCurrentAuthority() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        String authority = null;
        for (Iterator iterator = auth.getAuthorities().iterator(); iterator
                .hasNext();) {
            authority = (String) iterator.next().toString();
        }
        return authority;
    }

    @Override
    public boolean isAuthenticated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
