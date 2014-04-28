package com.picke.dao;

import com.picke.entity.User;
import com.picke.entity.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public User getUserByUserName(String userName) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        User user;
        try{
        user = (User) jdbcTemplate.queryForObject(
                sql, new Object[]{userName}, new UserRowMapper());
        }
        catch (Exception ex) {
            return null;
        }

        return user;
    }

    @Override
    public List<String> getAuthoritiesByUserName(String userName) {
        List<String> authorities = new ArrayList<String>();
        if (userName.equals("user")) {
            authorities.add("ROLE_USER");
        } else if (userName.equals("admin")) {
            authorities.add("ROLE_ADMIN");
        }
        return authorities;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";

        List<User> users = new ArrayList<User>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            User user = new User();
            user.setUsername((String) row.get("USERNAME"));
            user.setPassword((String) row.get("PASSWORD"));
            user.setSalt((String) row.get("SALT"));
            user.setEnabled((Boolean) (row.get("ENABLED")));
            users.add(user);
        }

        return users;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO USERS " +
                "(USERNAME, PASSWORD, ENABLED, SALT) VALUES (?, ?, ?, ?)";

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        int isEnabled = user.isEnabled() ? 1 : 0;

        jdbcTemplate.update(sql, new Object[]{user.getUsername(),
                user.getPassword(), isEnabled, user.getSalt()
        });
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE USERS " +
                "SET PASSWORD = ? WHERE USERNAME = ?";

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        jdbcTemplate.update(sql, new Object[]{user.getPassword(),
                user.getUsername(),
        });
    }

    @Override
    public void deleteUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
