package com.epam.esm.dao;

import com.epam.esm.model.Order;
import com.epam.esm.model.Page;

public interface OrderDao {
//    Order save(Order order);

    Order findById(Long id);

    Page<Order> findPage(Integer page);
}
