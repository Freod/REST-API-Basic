package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class OrderDaoJpa implements OrderDao {

    private final EntityManager em;

    @Autowired
    public OrderDaoJpa(EntityManagerFactory entityManagerFactory) {
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Order findById(Long id) {
        Order order = em.find(Order.class, id);
        if (order == null) {
            throw new ResourceNotFound("Order with id = (" + id + ") isn't exists.");
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = em.createQuery("select o from Order o").getResultList();
        return orders;
    }

    @Override
    public Order findByUserId(Long id) {
        return null;
    }
}
