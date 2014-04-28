package com.picke.service;

import com.picke.dao.UserDAO;
import com.picke.entity.User;
import org.apache.log4j.Logger;
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
        return userDao.getUserByUserName(userName);
    }

    @Override
    public List<String> getAuthoritiesByUserName(String userName) {
        return userDao.getAuthoritiesByUserName(userName);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
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
