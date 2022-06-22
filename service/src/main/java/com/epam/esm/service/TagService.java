package com.epam.esm.service;

import com.epam.esm.model.Filters;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag selectTagByName(Tag tag);

    Tag selectTagById(Tag tag);

    List<Tag> selectAllTags();

    void updateTag(Tag tag);

    void deleteTag(Tag tag);
}
