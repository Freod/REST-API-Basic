package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.Page;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    private static final String SHOW_PAGE_OF_ORDERS_METHOD_NAME = "showPageOfOrders";

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> showOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.showOrderById(id);
        addSelectOrderDtoLink(orderDto);
        return EntityModel.of(orderDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> showPageOfOrders(@RequestParam(defaultValue = "1") Integer page) {
        Page<OrderDto> orderDtoPage = orderService.showPageOfOrders(page);
        Collection<OrderDto> orderDtoCollection =
                orderDtoPage.getCollection()
                        .stream()
                        .map(OrderController::addSelectOrderDtoLink)
                        .collect(Collectors.toList());

        List<Link> linkList = new LinkedList<>();
        try {
            if (page > 1 && orderDtoPage.getTotalPages() > 0) {
                linkList.add(linkTo(
                        OrderController.class
                                .getMethod(SHOW_PAGE_OF_ORDERS_METHOD_NAME, Integer.class), 1)
                        .withRel("firstPage"));
            }

            if (page > 2 && page <= orderDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        OrderController.class
                                .getMethod(SHOW_PAGE_OF_ORDERS_METHOD_NAME, Integer.class), page - 1)
                        .withRel("previousPage"));
            }

            if (page <= orderDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        OrderController.class
                                .getMethod(SHOW_PAGE_OF_ORDERS_METHOD_NAME, Integer.class), page)
                        .withSelfRel());
            }

            if (page + 1 < orderDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        OrderController.class
                                .getMethod(SHOW_PAGE_OF_ORDERS_METHOD_NAME, Integer.class), page + 1)
                        .withRel("nextPage"));
            }

            if (orderDtoPage.getTotalPages() > 0 && page != orderDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        OrderController.class
                                .getMethod(SHOW_PAGE_OF_ORDERS_METHOD_NAME, Integer.class), orderDtoPage.getTotalPages())
                        .withRel("lastPage"));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return CollectionModel.of(orderDtoCollection, linkList);
    }

    protected static OrderDto addSelectOrderDtoLink(OrderDto orderDto) {
        try {
            orderDto.getGiftCertificates().forEach(GiftCertificateController::addSelectGiftCertificateLink);
            Link linkById = linkTo(OrderController.class.getMethod("showOrderById", Long.class), orderDto.getId()).withSelfRel();
            orderDto.add(linkById);
            return orderDto;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
