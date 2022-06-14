package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface TagDao {
    void saveTag(Tag tag);

    Tag selectTagById(BigInteger id);

    List<Tag> selectAllTags();

    void updateTag(Tag tag);

    void deleteTag(BigInteger id);
}
