package com.epam.esm.dao.extractor;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<BigInteger, GiftCertificate> result = new HashMap<>();

        while (resultSet.next()) {
            BigInteger id = resultSet.getBigDecimal("id").toBigInteger();

            GiftCertificate giftCertificate = new GiftCertificate(
                    id,
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("duration"),
                    LocalDateTime.parse(resultSet.getString("create_date")),
                    LocalDateTime.parse(resultSet.getString("last_update_date"))
            );

            if (result.containsKey(id)) {
                giftCertificate = result.get(id);
            }

            if (resultSet.getBigDecimal("tag_id") != null && resultSet.getString("tag_name") != null) {
                Tag tag = new Tag(resultSet.getBigDecimal("tag_id").toBigInteger(), resultSet.getString("tag_name"));
                giftCertificate.addTag(tag);
            }

            result.put(id, giftCertificate);
        }

        return new ArrayList(result.values());
    }
}
