package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);

    User findById(Long id) throws ResourceNotFound;

    List<User> findAll();

    Order makeAnOrder(Long id, Order order);
}
