package com.picke.dao;

import com.picke.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/dao-test-context.xml" })
public class UserDAOTest{

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private UserDAO userDAO;

    @Before
    public void initialize() {

        JdbcTemplate template = new JdbcTemplate(dataSource);
        String deleteSql = "DELETE FROM USERS WHERE 1";

        template.update(deleteSql);

        User user = new User();
        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword("user");
        user.setUsername("user");
        user.setEnabled(true);
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        user.setUserAuthorities(authorities);

        String sql = "INSERT INTO USERS " +
                "(USERNAME, PASSWORD, ENABLED, SALT) VALUES (?, ?, ?, ?)";

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        int isEnabled = user.isEnabled() ? 1 : 0;

        jdbcTemplate.update(sql, new Object[]{user.getUsername(),
                user.getPassword(), isEnabled, user.getSalt()
        });

        user.setSalt(RandomStringUtils.randomAlphanumeric(10));
        user.setPassword("admin");
        user.setUsername("admin");
        user.setEnabled(true);
        authorities.set(0, "ROLE_ADMIN");
        user.setUserAuthorities(authorities);

        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        isEnabled = user.isEnabled() ? 1 : 0;

        jdbcTemplate.update(sql, new Object[]{user.getUsername(),
                user.getPassword(), isEnabled, user.getSalt()
        });
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        User user = this.userDAO.getUserByUserName("user");

        assertNotNull("User was not found", user);
        assertEquals("Expected 'user'", "user",  user.getUsername());
    }

    @Test
    public void testGetAuthoritiesByUserName() throws Exception {
        List<String> authorities = userDAO.getAuthoritiesByUserName("user");

        assertEquals("Expected 'ROLE_USER' authority" , "ROLE_USER",  authorities.get(0));

        authorities = userDAO.getAuthoritiesByUserName("admin");

        assertEquals("Expected 'ROLE_ADMIN' authority" , "ROLE_ADMIN",  authorities.get(0));

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
        user.setPassword("user");
        user.setUsername("userr");
        user.setEnabled(true);
        ArrayList<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_ADMIN");
        user.setUserAuthorities(authorities);

        userDAO.updateUser(user);

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        assertEquals("Expected updated password", shaPasswordEncoder.encodePassword("userr", user.getSalt()), user.getPassword());
    }

    @Test
    public void testDeleteUser() throws Exception {

    }
}
