package com.akerke.authservice.rowmapper;

import com.akerke.authservice.entity.User;
import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(
            @NonNull ResultSet rs,
            int rowNum
    ) throws SQLException {
        try {
            return User.builder()
                    .id(rs.getLong("id"))
                    .name("name")
                    .surname("surname")
                    .email("email")
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

}
