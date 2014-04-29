package com.picke.dao;

import com.picke.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/dao-test-context.xml"})
public class UserDAOTest {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private UserDAO userDAO;

    @Before
    public void initialize() {
//        deleting users
        String deleteSql = "DELETE FROM USERS WHERE 1";
        jdbcTemplate.update(deleteSql);

//        adding user/user
        User user = new User();
        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword("user");
        user.setUsername("user");
        user.setEnabled(true);
        String sql = "INSERT INTO USERS " +
                "(USERNAME, PASSWORD, ENABLED, SALT) VALUES (?, ?, ?, ?)";
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(), 1, user.getSalt());

//        adding admin/admin
        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword("admin");
        user.setUsername("admin");
        user.setEnabled(true);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(), 1, user.getSalt());

//        deleting authorities
        deleteSql = "DELETE FROM AUTHORITIES WHERE 1";
        jdbcTemplate.update(deleteSql);

//        adding 'ROLE_USER'
        sql = "INSERT INTO AUTHORITIES " +
                "(AUTHORITY) VALUES ('ROLE_USER')";
        jdbcTemplate.update(sql);

//        adding 'ROLE ADMIN'
        sql = "INSERT INTO AUTHORITIES " +
                "(AUTHORITY) VALUES ('ROLE_ADMIN')";
        jdbcTemplate.update(sql);

//        deleting users authorities
        deleteSql = "DELETE FROM USERS_AUTHORITIES WHERE 1";
        jdbcTemplate.update(deleteSql);

//        adding 'ROLE_USER' to user
        sql = "INSERT INTO USERS_AUTHORITIES " +
                "(ID, USER, AUTHORITY) VALUES ('1', 'user', 'ROLE_USER')";
        jdbcTemplate.update(sql);

//        adding 'ROLE_ADMIN' to admin
        sql = "INSERT INTO USERS_AUTHORITIES " +
                "(ID, USER, AUTHORITY) VALUES ('2', 'admin', 'ROLE_ADMIN')";
        jdbcTemplate.update(sql);

//        adding 'ROLE_USER' to admin
        sql = "INSERT INTO USERS_AUTHORITIES " +
                "(ID, USER, AUTHORITY) VALUES ('3', 'admin', 'ROLE_USER')";
        jdbcTemplate.update(sql);

    }

    @Test
    public void testGetUserByUserName() throws Exception {
        User user = this.userDAO.getUserByUserName("user");

        assertNotNull("User was not found", user);
        assertEquals("Expected 'user'", "user", user.getUsername());
    }

    @Test
    public void testGetAuthoritiesByUserName() throws Exception {
        List<String> authorities = userDAO.getAuthoritiesByUserName("admin");

        assertEquals("Expected 2 authorities to admin", 2, authorities.size());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> userList = userDAO.getAllUsers();

        assertEquals("Expected 2 users", 2, userList.size());
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setEnabled(true);
        user.setUsername("boo");
        user.setPassword("12");
        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        userDAO.addUser(user);
        assertNotNull(userDAO.getUserByUserName(user.getUsername()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setPassword("userr");
        user.setUsername("user");
        user.setEnabled(true);
        user.setSalt(userDAO.getUserByUserName("user").getSalt());

        userDAO.updateUser(user);

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);

        assertEquals("Expected updated password", shaPasswordEncoder.encodePassword("userr", user.getSalt()), user.getPassword());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword("user");
        user.setUsername("user");
        user.setEnabled(true);
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        user.setUserAuthorities(authorities);

        userDAO.deleteUser(user);

        assertEquals("Expected only one user", 1, userDAO.getAllUsers().size());
    }

    @After
    public void backup() {
        initialize();
    }
}
