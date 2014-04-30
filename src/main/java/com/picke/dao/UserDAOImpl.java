package com.picke.dao;

import com.picke.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public User getUserByUserName(String userName) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{userName},
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<String> getAuthoritiesByUserName(String userName) {
        String sql = "SELECT * FROM authorities " +
                "WHERE id IN (" +
                "SELECT authority_id FROM users_authorities " +
                "WHERE user_id IN (" +
                "SELECT id FROM users " +
                "WHERE username = ?));";

        List<String> authorities = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userName);
        for (Map row : rows) {
            String authority = ((String) row.get("AUTHORITY"));
            authorities.add(authority);
        }

        return authorities;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";

        List<User> users = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            User user = new User();
            user.setId((int) row.get("ID"));
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

        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(), isEnabled, user.getSalt());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE USERS " +
                "SET PASSWORD = ? WHERE USERNAME = ?";

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));

        jdbcTemplate.update(sql, user.getPassword(),
                user.getUsername());
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM USERS WHERE USERNAME = ?";
        jdbcTemplate.update(sql, user.getUsername());
    }
}
