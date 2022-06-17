package com.epam.esm.dao.mapper;

import com.epam.esm.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GiftCertificateDaoMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GiftCertificate(
                resultSet.getBigDecimal("id").toBigInteger(),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getInt("duration"),
                LocalDateTime.parse(resultSet.getString("create_date")),
                LocalDateTime.parse(resultSet.getString("last_update_date"))
        );
    }
}
