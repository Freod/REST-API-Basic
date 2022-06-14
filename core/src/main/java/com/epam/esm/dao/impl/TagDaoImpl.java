package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagDaoMapper;
import com.epam.esm.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
@AllArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (?);";
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE id = ?;";
    private static final String SELECT_ALL_TAGS_QUERY = "SELECT * FROM tags;";
    private static final String UPDATE_TAG_QUERY = "UPDATE tags SET name = ? WHERE id = ?;";
    private static final String DELETE_TAG_QUERY = "DELETE FROM tags WHERE id = ?;";

    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveTag(Tag tag) {
        jdbcTemplate.update(INSERT_TAG_QUERY, tag.getName());
    }

    @Override
    public Tag selectTagById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_TAG_BY_ID_QUERY, new TagDaoMapper(), id);
    }

    @Override
    public List<Tag> selectAllTags() {
        return jdbcTemplate.query(SELECT_ALL_TAGS_QUERY, new TagDaoMapper());
    }

    @Override
    public void updateTag(Tag tag) {
        jdbcTemplate.update(UPDATE_TAG_QUERY, tag.getName(), tag.getId());
    }

    @Override
    public void deleteTag(BigInteger id) {
        jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }
}
