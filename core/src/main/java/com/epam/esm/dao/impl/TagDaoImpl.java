package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagDaoMapper;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
//@AllArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags (name) VALUES (?)";
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE id = ?;";
    private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT * FROM tags WHERE name = ?;";
    private static final String SELECT_ALL_TAGS_QUERY = "SELECT * FROM tags;";
    private static final String UPDATE_TAG_QUERY = "UPDATE tags SET name = ? WHERE id = ?;";
    private static final String DELETE_TAG_QUERY = "DELETE FROM tags WHERE id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("simpleJdbcInsertTags")
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    public Tag saveTag(Tag tag) {
        Map<String, Object> tagParameters = new HashMap<>();
        tagParameters.put("name", tag.getName());
        tag.setId(BigInteger.valueOf(simpleJdbcInsert.executeAndReturnKey(tagParameters).longValue()));
        return tag;
    }

    @Override
    public Tag selectTagById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_TAG_BY_ID_QUERY, new TagDaoMapper(), id);
    }

    @Override
    public Tag selectTagByName(String name) {
        return jdbcTemplate.queryForObject(SELECT_TAG_BY_NAME_QUERY, new TagDaoMapper(), name);
    }

    public Tag selectOrSaveTag(Tag tag) {
        try {
            tag = selectTagByName(tag.getName());
        } catch (EmptyResultDataAccessException e) {
            tag = saveTag(tag);
        }
        return tag;
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
