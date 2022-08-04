package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class GiftCertificateDaoEntityManager implements GiftCertificateDao {

    private final EntityManager em;
//    fixme is need?
    private final TagDaoEntityManager tagDao;

    @Autowired
    public GiftCertificateDaoEntityManager(EntityManagerFactory entityManagerFactory, TagDaoEntityManager tagDao) {
        this.em = entityManagerFactory.createEntityManager();
        this.tagDao = tagDao;
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        em.getTransaction().begin();
        em.persist(giftCertificate);
        em.getTransaction().commit();
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            throw new ResourceNotFound("GiftCertificate with id = (" + id + ") isn't exists.");
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllUsingFilter(Filter filter) {
        List<GiftCertificate> giftCertificates = em.createQuery("select gc from GiftCertificate gc").getResultList();
        return giftCertificates;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {

    }

    @Override
    public void removeById(Long id) {
        em.getTransaction().begin();
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            em.getTransaction().rollback();
            throw new ResourceNotFound("GiftCertificate with id = (" + id + ") isn't exists.");
        }
        em.remove(giftCertificate);
        em.getTransaction().commit();
    }

    @Override
    public void addTagToGiftCertificate(Long giftCertificateId, Tag tag) {

    }

    @Override
    public void removeTagFromGiftCertificate(Long giftCertificateId, Tag tag) {

    }
}
