package com.epam.esm.dao;

import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface TagDao {
    Tag saveTag(Tag tag);

    Tag selectTagById(BigInteger id);

    Tag selectTagByName(String name);

    Tag selectOrSaveTag(Tag tag);

    List<Tag> selectAllTags();

    void updateTag(Tag tag);

    void deleteTag(BigInteger id);
}
