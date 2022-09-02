package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto showOrderById(@PathVariable Long id){
        return orderService.showOrderById(id);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> showOrders(){
        return orderService.showOrders();
    }
}
