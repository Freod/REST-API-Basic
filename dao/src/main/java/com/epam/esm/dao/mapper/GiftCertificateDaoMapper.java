package com.epam.esm.dao.mapper;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GiftCertificateDaoMapper implements RowMapper<GiftCertificate> {
    String tagId = "tag_id";
    String tagName = "tag_name";

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate(
                resultSet.getBigDecimal("id").toBigInteger(),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getInt("duration"),
                LocalDateTime.parse(resultSet.getString("create_date")),
                LocalDateTime.parse(resultSet.getString("last_update_date"))
        );

        if (resultSet.getBigDecimal(tagId) != null && resultSet.getString(tagName) != null) {
            Tag tag = new Tag(resultSet.getBigDecimal(tagId).toBigInteger(), resultSet.getString(tagName));
            giftCertificate.addTag(tag);

            while (resultSet.next()) {
                tag = new Tag(resultSet.getBigDecimal(tagId).toBigInteger(), resultSet.getString(tagName));
                giftCertificate.addTag(tag);
            }
        }

        return giftCertificate;
    }
}
