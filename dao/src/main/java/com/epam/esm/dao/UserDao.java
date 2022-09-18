package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import com.epam.esm.model.User;

public interface UserDao {
    User save(User user);

    User findById(Long id) throws ResourceNotFoundException;

    Page<User> findPage(Integer page);

    Order makeAnOrder(Long id, Order order);
}
