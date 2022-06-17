package com.epam.esm.dao.mapper;

import com.epam.esm.model.TagRef;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRefMapper implements RowMapper<TagRef> {

    @Override
    public TagRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new TagRef(
                resultSet.getBigDecimal("certificate_id").toBigInteger(),
                resultSet.getBigDecimal("tag_id").toBigInteger(),
                resultSet.getString("tag_name")
        );
    }
}
