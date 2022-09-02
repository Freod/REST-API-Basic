package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TagDaoJpa implements TagDao {

    private final EntityManager em;

    @Autowired
    public TagDaoJpa(EntityManagerFactory entityManagerFactory) {
        this.em = entityManagerFactory.createEntityManager();
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
            throw new ResourceViolation("Tag with name=(" + tag.getName() + ") already exists.");
        }
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = em.find(Tag.class, id);
        if (tag == null) {
            throw new ResourceNotFound("Tag with id = (" + id + ") isn't exists.");
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
            throw new ResourceNotFound("Tag with name = (" + name + ") isn't exists.");
        }
    }

//   todo pagination
    @Override
    public List<Tag> findAll() {
        List<Tag> tags = em.createQuery("select t from Tag t").getResultList();
        return tags;
    }

    @Override
    public void removeById(Long id) {
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class, id);
        if (tag == null) {
            em.getTransaction().rollback();
            throw new ResourceNotFound("Tag with id = (" + id + ") isn't exists.");
        }
        try {
            em.remove(tag);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            throw new ResourceViolation("Tag cannot be removed. Tag is connected with at least of one certificate.");
        }
    }

    public EntityManager getEm() {
        return em;
    }
}
