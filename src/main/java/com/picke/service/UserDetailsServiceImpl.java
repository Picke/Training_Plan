package com.picke.service;


import com.picke.dao.UserDAO;
import com.picke.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDao;

    @Autowired
    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userDao.getUserByUserName(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(
                    "getUserByUserName returned null.");
        }
        List<String> authorities = new ArrayList<String>();

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));
//        authorities.add("ROLE_USER");
        user.setUserAuthorities(userDao.getAuthoritiesByUserName(username));

        return (UserDetails) user;
        }
}