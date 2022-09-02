package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoJpa implements UserDao {

    private final EntityManager em;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public UserDaoJpa(EntityManagerFactory entityManagerFactory, GiftCertificateDao giftCertificateDao) {
        this.em = entityManagerFactory.createEntityManager();
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public User save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }

    @Override
    public User findById(Long id) throws ResourceNotFound {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new ResourceNotFound("User with id = ("+id+") isn't exists.");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = em.createQuery("select u from User u").getResultList();
        return users;
    }

    //fixme problem with add new order with used certificate
    @Override
    public Order makeAnOrder(Long id, Order order) {
        em.getTransaction().begin();
        User user = findById(id);
        order.setCost(Double.valueOf(0));
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
    }
}
