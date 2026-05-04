package com.shami.userservice.adapter.out.persistence;

import com.shami.userservice.domain.model.Role;
import com.shami.userservice.domain.model.User;
import com.shami.userservice.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> User.builder()
            .id(UUID.fromString(rs.getString("id")))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .fullName(rs.getString("full_name"))
            .phone(rs.getString("phone"))
            .role(Role.valueOf(rs.getString("role")))
            .isActive(rs.getBoolean("is_active"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
            .build();

    @Override
    public User save(User user) {
        String sql = """
            INSERT INTO users (email, password, full_name, phone, role, is_active, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?::varchar, ?, ?, ?)
            RETURNING id
            """;

        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getPhone(),
                user.getRole().name(),
                user.isActive(),
                Timestamp.valueOf(user.getCreatedAt()),
                Timestamp.valueOf(user.getUpdatedAt()));

        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public User update(User user) {
        String sql = """
            UPDATE users
            SET full_name = ?, phone = ?, updated_at = ?
            WHERE id = ?
            """;

        jdbcTemplate.update(sql,
                user.getFullName(),
                user.getPhone(),
                Timestamp.valueOf(user.getUpdatedAt()),
                user.getId());

        return user;
    }
}
