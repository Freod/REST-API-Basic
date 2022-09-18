package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;

public interface TagDao {
    Tag save(Tag tag);

    Tag findById(Long id) throws ResourceNotFoundException;

    Tag findByName(String name);

    Page<Tag> findPage(Integer page);

    void removeById(Long id);
}
