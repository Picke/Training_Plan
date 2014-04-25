package com.picke.dao;

import com.picke.entity.User;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserByUserName(String userName) {
        User user = new User();
        if (userName.equals("user")) {
            user.setUsername("user");
            user.setPassword("user");
            user.setAuthority("ROLE_USER");
            user.setEnabled("true");
            user.setSalt(RandomStringUtils.randomAlphabetic(10));
        } else if (userName.equals("admin")) {
            user.setUsername("admin");
            user.setPassword("admin");
            user.setAuthority("ROLE_ADMIN");
            user.setEnabled("true");
            user.setSalt(RandomStringUtils.randomAlphabetic(10));
        }
        return user;

    }

    @Override
    public List<String> getAuthoritiesByUserName(String userName) {
        List<String> authorities = new ArrayList<String>();
        if(userName.equals("user")){
            authorities.add("ROLE_USER");
        }
        else  if(userName.equals("admin")){
            authorities.add("ROLE_ADMIN");
        }
        return authorities;
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
}
