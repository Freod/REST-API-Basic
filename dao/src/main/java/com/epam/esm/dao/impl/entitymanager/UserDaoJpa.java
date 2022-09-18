package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoJpa implements UserDao {

    private final EntityManager em;
    private final GiftCertificateDao giftCertificateDao;
    private final int pageSize;


    // TODO: 02.09.2022
    @PostConstruct
    private void initUsers() {
        for (int i = 0; i < 50; i++) {
            User user = new User("user" + i);
            this.save(user);

//            GiftCertificate giftCertificate = new GiftCertificate();
//            giftCertificate.setId(54L);
//            Order order = new Order();
//            order.setGiftCertificates(Arrays.asList(giftCertificate));
//            this.makeAnOrder(user.getId(), order);
        }
    }

    @Autowired
    public UserDaoJpa(EntityManagerFactory entityManagerFactory, GiftCertificateDao giftCertificateDao, int pageSize) {
        this.em = entityManagerFactory.createEntityManager();
        this.giftCertificateDao = giftCertificateDao;
        this.pageSize = pageSize;
    }

    @Override
    public User save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new ResourceNotFoundException("User with id = (" + id + ") isn't exists.");
        }
        return user;
    }

    @Override
    public Page<User> findPage(Integer page) {
        em.getTransaction().begin();
        Query query = em.createQuery("select u from User u");

        int totalPages = (int) (((long) query.getResultList().size() - 1) / pageSize) + 1;

        List<User> users
                = query
                .setFirstResult(((page - 1) * pageSize))
                .setMaxResults(pageSize)
                .getResultList();
        em.getTransaction().commit();

        return new Page<>(
                page,
                pageSize,
                totalPages,
                users);
    }

    //fixme problem with add new order with used certificate
    @Override
    public Order makeAnOrder(Long id, Order order) {
        em.getTransaction().begin();
        try {
            User user = findById(id);

            order.setCost((double) 0);
            order.setGiftCertificates(order.getGiftCertificates()
                    .stream()
                    .map(giftCertificate -> {
                        giftCertificate = giftCertificateDao.findById(giftCertificate.getId());
                        order.setCost(order.getCost() + giftCertificate.getPrice());
                        return giftCertificate;
                    }).collect(Collectors.toList()));
            order.setPurchaseDate(LocalDateTime.now());
            em.persist(order);
            user.addOrder(order);
            em.persist(user);
            em.getTransaction().commit();
            return order;
        } catch (ResourceNotFoundException e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
}
