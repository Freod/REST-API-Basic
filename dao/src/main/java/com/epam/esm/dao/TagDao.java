package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Tag;

import javax.persistence.EntityManager;
import java.util.List;

public interface TagDao {
    Tag save(Tag tag);

    Tag findById(Long id) throws ResourceNotFound;

    Tag findByName(String name);

    List<Tag> findAll();

    void removeById(Long id);

    // TODO: 31.08.2022
    EntityManager getEm();
}
