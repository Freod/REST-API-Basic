package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface TagDao {
    Tag saveTag(Tag tag);

    Tag selectTagById(BigInteger id) throws ResourceNotFound;

    Tag selectTagByName(String name);

    Tag selectOrSaveTag(Tag tag);

    List<Tag> selectAllTags();

    void deleteTag(BigInteger id);
}
