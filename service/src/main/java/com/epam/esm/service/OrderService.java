package com.epam.esm.service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final ObjectConverter objectConverter;

    @Autowired
    public OrderService(OrderDao orderDao, ObjectConverter objectConverter) {
        this.orderDao = Objects.requireNonNull(orderDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
    }

    public OrderDto showOrderById(Long id) {
        if (id == null) {
            throw new WrongValueException("Id cannot be null");
        }
        return objectConverter.convertOrderToOrderDto(orderDao.findById(id));
    }

    public Page<OrderDto> showPageOfOrders(Integer page) {
        if (page < 1) throw new WrongPageException("Page cannot be smaller by 1");
        Page<Order> orderPage = orderDao.findPage(page);
        return new Page<>(
                orderPage.getPageNumber(),
                orderPage.getPageSize(),
                orderPage.getTotalPages(),
                orderPage.getCollection()
                        .stream()
                        .map(objectConverter::convertOrderToOrderDto)
                        .collect(Collectors.toList()));
    }


}
