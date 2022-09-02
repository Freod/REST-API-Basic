package com.epam.esm.service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderDto showOrderById(Long id) {
        return convertOrderToOrderDto(orderDao.findById(id));
    }

    public List<OrderDto> showOrders() {
        return orderDao
                .findAll()
                .stream()
                .map(OrderService::convertOrderToOrderDto)
                .collect(Collectors.toList());
    }

    static OrderDto convertOrderToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getGiftCertificates()
                        .stream()
                        .map(GiftCertificateService::convertGiftCertificateToGiftCertificateDto)
                        .collect(Collectors.toList()),
                order.getCost(),
                order.getPurchaseDate()
        );
    }

    static Order convertOrderDtoToOrder(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getGiftCertificates()
                        .stream()
                        .map(GiftCertificateService::convertGiftCertificateDtoToGiftCertificate)
                        .collect(Collectors.toList()),
                orderDto.getCost(),
                orderDto.getPurchaseDate()
        );
    }
}
