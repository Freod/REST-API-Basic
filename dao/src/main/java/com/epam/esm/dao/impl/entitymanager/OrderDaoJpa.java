package com.epam.esm.dao.impl.entitymanager;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderDaoJpa implements OrderDao {

    private final EntityManager em;
    private final int pageSize;

    @Autowired
    public OrderDaoJpa(EntityManagerFactory entityManagerFactory, int pageSize) {
        this.em = entityManagerFactory.createEntityManager();
        this.pageSize = pageSize;
    }

    @Override
    public Order findById(Long id) {
        Order order = em.find(Order.class, id);
        if (order == null) {
            throw new ResourceNotFoundException("Order with id = (" + id + ") isn't exists.");
        }
        return order;
    }

    @Override
    public Page<Order> findPage(Integer page) {
        em.getTransaction().begin();
        Query query = em.createQuery("select o from Order o");
        int totalPages = (int) (((long) query.getResultList().size() - 1) / pageSize) + 1;

        List<Order> orders
                = query
                .setFirstResult(((page - 1) * pageSize))
                .setMaxResults(pageSize)
                .getResultList();
        em.getTransaction().commit();
        return new Page<>(
                page,
                pageSize,
                totalPages,
                orders);
    }
}
