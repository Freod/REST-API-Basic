package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class TagDaoJpa implements TagDao {

    private final EntityManager em;
    private final int pageSize;
    private static final String IS_NOT_EXIST= ") isn't exists.";

    @Autowired
    public TagDaoJpa(EntityManagerFactory entityManagerFactory, int pageSize) {
        this.em = Objects.requireNonNull(entityManagerFactory.createEntityManager());
        this.pageSize = pageSize;
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        em.getTransaction().begin();
        try {
            em.persist(tag);
            em.getTransaction().commit();
            return tag;
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new ResourceViolationException("Tag with name=(" + tag.getName() + ") already exists.");
        }
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = em.find(Tag.class, id);
        if (tag == null) {
            throw new ResourceNotFoundException("Tag with id = (" + id + IS_NOT_EXIST);
        }
        return tag;
    }

    @Override
    public Tag findByName(String name) {
        try {
            return (Tag) em.createQuery("select t from Tag t where t.name like :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("Tag with name = (" + name + IS_NOT_EXIST);
        }
    }

    @Override
    public Page<Tag> findPage(Integer page) {
        em.getTransaction().begin();
        int totalPages = (int) (((long) em.createQuery("select count (t.id) from Tag t").getSingleResult() - 1) / pageSize) + 1;
        Query query = em.createQuery("select t from Tag t order by t.id");
        List<Tag> tagList = query
                .setFirstResult(((page - 1) * pageSize))
                .setMaxResults(pageSize)
                .getResultList();
        em.getTransaction().commit();
        return new Page<>(
                page,
                pageSize,
                totalPages,
                tagList);
    }

    @Override
    public void removeById(Long id) {
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class, id);
        if (tag == null) {
            em.getTransaction().rollback();
            throw new ResourceNotFoundException("Tag with id = (" + id + IS_NOT_EXIST);
        }
        try {
            em.remove(tag);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new ResourceViolationException("Tag cannot be removed. Tag is connected with at least one certificate.");
        }
    }
}
