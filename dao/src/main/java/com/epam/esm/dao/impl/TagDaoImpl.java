package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagDaoMapper;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE id = ?;";
    private static final String SELECT_TAG_BY_NAME_QUERY = "SELECT * FROM tags WHERE name = ?;";
    private static final String SELECT_ALL_TAGS_QUERY = "SELECT * FROM tags;";
    private static final String DELETE_TAG_QUERY = "DELETE FROM tags WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertTags;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsertTags) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsertTags = simpleJdbcInsertTags;
    }

    @Override
    public Tag saveTag(Tag tag) {
        Map<String, Object> tagParameters = new HashMap<>();
        tagParameters.put("name", tag.getName());
        try {
            tag.setId(BigInteger.valueOf(simpleJdbcInsertTags.executeAndReturnKey(tagParameters).longValue()));
            return tag;
        } catch (DuplicateKeyException exception) {
            throw new ResourceViolation("Resource name or primary key violation (name = '" + tag.getName() + "')");
        }
    }

    @Override
    public Tag selectTagById(BigInteger id) throws ResourceNotFound {
        try {
            return jdbcTemplate.queryForObject(SELECT_TAG_BY_ID_QUERY, new TagDaoMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFound("Resource not found (id = " + id + ")");
        }
    }

    @Override
    public Tag selectTagByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_TAG_BY_NAME_QUERY, new TagDaoMapper(), name);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFound("Resource not found (name = '" + name + "')");
        }
    }

    public Tag selectOrSaveTag(Tag tag) {
        try {
            tag = selectTagByName(tag.getName());
        } catch (ResourceNotFound e) {
            tag = saveTag(tag);
        }
        return tag;
    }

    @Override
    public List<Tag> selectAllTags() {
        return jdbcTemplate.query(SELECT_ALL_TAGS_QUERY, new TagDaoMapper());
    }

    @Override
    public void deleteTag(BigInteger id) {
        jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }
}
