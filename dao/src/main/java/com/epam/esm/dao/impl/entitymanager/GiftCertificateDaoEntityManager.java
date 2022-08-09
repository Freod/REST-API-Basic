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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoEntityManager implements GiftCertificateDao {

    private final EntityManager em;
    private final TagDaoEntityManager tagDao;

    @Autowired
    public GiftCertificateDaoEntityManager(EntityManagerFactory entityManagerFactory, TagDaoEntityManager tagDao) {
        this.em = entityManagerFactory.createEntityManager();
        this.tagDao = tagDao;
    }

    @Override
    public void save(GiftCertificate giftCertificate) {
        em.getTransaction().begin();
        giftCertificate
                .setTags(
                        giftCertificate
                                .getTags()
                                .stream()
                                .map(tag -> {
                                            try {
                                                tag = tagDao.findByName(tag.getName());
                                                tag = em.merge(tag);
                                                return tag;
                                            } catch (ResourceNotFound e) {
                                                return tag;
                                            }
                                        }
                                )
                                .collect(Collectors.toSet()));
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
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

    // TODO: 09.08.2022 filters
    @Override
    public List<GiftCertificate> findAllUsingFilter(Filter filter) {
        List<GiftCertificate> giftCertificates = em.createQuery("select gc from GiftCertificate gc").getResultList();
        return giftCertificates;
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        em.getTransaction().begin();
        GiftCertificate actualGiftCertificate;
        try {
            actualGiftCertificate = findById(giftCertificate.getId());
        } catch (ResourceNotFound e) {
            em.getTransaction().rollback();
            throw new ResourceNotFound(e.getMessage());
        }
        actualGiftCertificate = updateFields(actualGiftCertificate, giftCertificate);
        em.merge(actualGiftCertificate);
        em.getTransaction().commit();
    }

    //todo cascade
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

    // TODO: 09.08.2022  
    @Override
    public void addTagToGiftCertificate(Long giftCertificateId, Tag tag) {

    }

    // TODO: 09.08.2022
    @Override
    public void removeTagFromGiftCertificate(Long giftCertificateId, Tag tag) {

    }
    
    private GiftCertificate updateFields(GiftCertificate actualGiftCertificate, GiftCertificate giftCertificate){
        if (giftCertificate.getName() != null) {
            actualGiftCertificate.setName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            actualGiftCertificate.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            actualGiftCertificate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            actualGiftCertificate.setDuration(giftCertificate.getDuration());
        }
        actualGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        return actualGiftCertificate;
    }
}
