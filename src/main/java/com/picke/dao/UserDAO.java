package com.picke.dao;


import com.picke.entity.User;

import java.util.List;

public interface UserDAO {

    public User getUserByUserName(String userName);

    List<String> getAuthoritiesByUserName(String userName);

    public List<User> getAllUsers();

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);
}