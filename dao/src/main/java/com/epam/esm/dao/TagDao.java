package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDao {
    void save(Tag tag);

    Tag findById(Long id) throws ResourceNotFound;

    Tag findByName(String name);

    List<Tag> findAll();

    void removeById(Long id);
}
