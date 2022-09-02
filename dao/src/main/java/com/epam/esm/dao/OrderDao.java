package com.epam.esm.dao;

import com.epam.esm.model.Order;

import java.util.List;

public interface OrderDao {
//    Order save(Order order);

    Order findById(Long id);

    List<Order> findAll();

    Order findByUserId(Long id);
}
