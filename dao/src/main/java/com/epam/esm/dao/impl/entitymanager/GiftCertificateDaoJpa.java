package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoJpa implements GiftCertificateDao {

    private final EntityManager em;
    private final TagDaoJpa tagDao;
    private final int pageSize;

    // TODO: 16.09.2022
//    @PostConstruct
//    private void initGiftCertificate() {
//        for (int i = 0; i < 50; i++) {
//            GiftCertificate giftCertificate = new GiftCertificate(null, "Giftcertf" + i, "descript", 3.0, 5);
//            if (i < 25) {
//                giftCertificate.addTag(new Tag("tag1"));
//                giftCertificate.addTag(new Tag("tag3"));
//            } else {
//                giftCertificate.addTag(new Tag("tag2"));
//            }
//            this.save(giftCertificate);
//        }
//    }

    @Autowired
    public GiftCertificateDaoJpa(EntityManagerFactory entityManagerFactory, TagDaoJpa tagDao, int pageSize) {
        this.em = entityManagerFactory.createEntityManager();
        this.tagDao = tagDao;
        this.pageSize = pageSize;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
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
                                            } catch (ResourceNotFoundException e) {
                                                return tag;
                                            }
                                        }
                                )
                                .collect(Collectors.toSet()));
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        em.persist(giftCertificate);
        em.getTransaction().commit();
        return giftCertificate;
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            throw new ResourceNotFoundException("GiftCertificate with id = (" + id + ") isn't exists.");
        }
        return giftCertificate;
    }

    // TODO: 10/10/2022
    //Search for gift certificates by several tags (“and” condition).
    @Override
    public Page<GiftCertificate> findPageUsingFilter(Integer page, Filter filter) {
        em.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(giftCertificateRoot.get("name")), "%" + filter.getName().toUpperCase() + "%"));
        }
        if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(giftCertificateRoot.get("description")), "%" + filter.getDescription().toUpperCase() + "%"));
        }

        for (Tag tag : filter.getTags()) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.join("tags").get("name"), "%" + tag.getName() + "%"));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true);

        if (filter.getDirection().equals("asc")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(filter.getOrderBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(filter.getOrderBy())));
        }

        int totalPages = (em.createQuery(criteriaQuery).getResultList().size() - 1) / pageSize + 1;

        TypedQuery<GiftCertificate> typedQuery =
                em.createQuery(criteriaQuery)
                        .setFirstResult(((page - 1) * pageSize))
                        .setMaxResults(pageSize);
        List<GiftCertificate> giftCertificates = typedQuery.getResultList();

        em.getTransaction().commit();

        return new Page<>(page, pageSize, totalPages, giftCertificates);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        em.getTransaction().begin();
        GiftCertificate actualGiftCertificate;
        try {
            actualGiftCertificate = findById(id);
        } catch (ResourceNotFoundException e) {
            em.getTransaction().rollback();
            throw new ResourceNotFoundException(e.getMessage());
        }
        actualGiftCertificate = updateFields(actualGiftCertificate, giftCertificate);
        em.merge(actualGiftCertificate);
        em.getTransaction().commit();
        return actualGiftCertificate;
    }

    @Override
    public void removeById(Long id) {
        em.getTransaction().begin();
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        if (giftCertificate == null) {
            em.getTransaction().rollback();
            throw new ResourceNotFoundException("GiftCertificate with id = (" + id + ") isn't exists.");
        }
        try {
            em.remove(giftCertificate);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new ResourceViolationException("GiftCertificate cannot be removed. GiftCertificate is connected with at least one order.");
        }
    }

    @Override
    public GiftCertificate addTagToGiftCertificate(Long giftCertificateId, Tag tag) {
        em.getTransaction().begin();
        GiftCertificate giftCertificate;
        try {
            giftCertificate = findById(giftCertificateId);
            try {
                tag = tagDao.findByName(tag.getName());
            } catch (ResourceNotFoundException e) {
                tag = em.merge(tag);
            }
            giftCertificate.addTag(tag);
        } catch (ResourceNotFoundException e) {
            em.getTransaction().rollback();
            throw new ResourceNotFoundException(e.getMessage());
        }
        em.merge(giftCertificate);
        em.getTransaction().commit();
        return giftCertificate;
    }

    @Override
    public GiftCertificate removeTagFromGiftCertificate(Long giftCertificateId, Tag tag) {
        em.getTransaction().begin();
        GiftCertificate giftCertificate;
        try {
            giftCertificate = findById(giftCertificateId);
            tag = tagDao.findByName(tag.getName());
            if (!giftCertificate.getTags().contains(tag)) {
                em.getTransaction().rollback();
                throw new ResourceNotFoundException("GiftGertificate with id = (" + giftCertificateId + ") doesn't contains Tag with name = (" + tag.getName() + ").");
            }
            giftCertificate.removeTag(tag);
        } catch (ResourceNotFoundException e) {
            em.getTransaction().rollback();
            throw new ResourceNotFoundException(e.getMessage());
        }
        em.merge(giftCertificate);
        em.getTransaction().commit();
        return giftCertificate;
    }

    private GiftCertificate updateFields(GiftCertificate actualGiftCertificate, GiftCertificate giftCertificate) {
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
