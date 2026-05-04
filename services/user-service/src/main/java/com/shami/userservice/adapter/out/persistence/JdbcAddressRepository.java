package com.shami.userservice.adapter.out.persistence;

import com.shami.userservice.domain.model.Address;
import com.shami.userservice.domain.port.out.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JdbcAddressRepository implements AddressRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Address> addressRowMapper = (rs, rowNum) -> Address.builder()
            .id(UUID.fromString(rs.getString("id")))
            .userId(UUID.fromString(rs.getString("user_id")))
            .label(rs.getString("label"))
            .street(rs.getString("street"))
            .city(rs.getString("city"))
            .country(rs.getString("country"))
            .postalCode(rs.getString("postal_code"))
            .isDefault(rs.getBoolean("is_default"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .build();

    @Override
    public Address save(Address address) {
        String sql = """
            INSERT INTO addresses (user_id, label, street, city, country, postal_code, is_default, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
            """;

        UUID id = jdbcTemplate.queryForObject(sql, UUID.class,
                address.getUserId(),
                address.getLabel(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                address.isDefault(),
                Timestamp.valueOf(address.getCreatedAt()));

        address.setId(id);
        return address;
    }

    @Override
    public List<Address> findByUserId(UUID userId) {
        String sql = "SELECT * FROM addresses WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, addressRowMapper, userId);
    }

    @Override
    public void unsetDefaultAddresses(UUID userId) {
        String sql = "UPDATE addresses SET is_default = FALSE WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
