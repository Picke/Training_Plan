package com.picke.entity;

        import java.sql.ResultSet;
        import java.sql.SQLException;

        import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setEnabled(rs.getBoolean("ENABLED"));
        user.setSalt(rs.getString("SALT"));
        return user;
    }
}