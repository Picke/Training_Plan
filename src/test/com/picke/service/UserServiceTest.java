package com.picke.service;

import com.picke.dao.UserDAO;
import com.picke.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        User user = new User();
        user.setUsername("user");
        when(userDAO.getUserByUserName("user")).thenReturn(user);
        user = userService.getUserByUserName("user");
        assertNotNull("User was not found", user);
        assertEquals("Expected 'user'", "user", user.getUsername());
    }

    @Test
    public void testGetAuthoritiesByUserName() throws Exception {
        List<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        authorities.add("ROLE_ADMIN");
        when(userDAO.getAuthoritiesByUserName("admin")).thenReturn(authorities);
        assertEquals("Expected 2 authorities to admin", 2, (userService.getAuthoritiesByUserName("admin").size()));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<User>();
        userList.add(new User());
        userList.add(new User());
        when(userDAO.getAllUsers()).thenReturn(userList);
        assertEquals("Expected 2 users", 2, userService.getAllUsers().size());
    }

    @Test(expected=Exception.class)
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("user");
        doThrow(new Exception()).when(userDAO).addUser(user);
        userService.addUser(user);
    }

    @Test(expected=Exception.class)
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setUsername("user");
        doThrow(new Exception()).when(userDAO).updateUser(user);
        userService.updateUser(user);
    }

    @Test(expected=Exception.class)
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setUsername("user");
        doThrow(new Exception()).when(userDAO).deleteUser(user);
        userService.deleteUser(user);
    }
}
