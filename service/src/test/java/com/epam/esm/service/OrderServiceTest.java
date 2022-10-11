package com.epam.esm.service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @Spy
    private ObjectConverter objectConverter;

    @InjectMocks
    private OrderService orderService;

    @Test
    void whenSelectOrderByIdShouldReturnOrder() {
        //given
        Long idToFind = takeId();
        Order returnedOrder = takeReturnedOrder();
        OrderDto expectedOrderDto = takeExpectedOrderDto();

        //when
        when(orderDao.findById(idToFind)).thenReturn(returnedOrder);
        OrderDto actualOrderDto = orderService.showOrderById(idToFind);

        //then
        assertEquals(expectedOrderDto, actualOrderDto);
    }

    @Test
    void whenSelectOrderWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullIdToFind = takeNullId();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            orderService.showOrderById(nullIdToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectPageOfOrdersShouldReturnThisPage() {
        //given
        int page = 1;
        Page<Order> returnedOrderPage = takeReturnedOrderPage();
        Page<OrderDto> expectedPage = takeExpectedOrderDtoPage();

        //when
        when(orderDao.findPage(page)).thenReturn(returnedOrderPage);
        Page<OrderDto> actualPage = orderService.showPageOfOrders(page);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void whenSelectPageOfOrdersSmallerThanOneShouldThrowWrongValueException() {
        //given
        String expectedExceptionMessage = takeExceptionMessagePageCannotBeSmallerThan();
        int page = 0;

        //when
        WrongPageException thrown = Assertions.assertThrows(WrongPageException.class, () -> {
            orderService.showPageOfOrders(page);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    private Long takeId() {
        return 3L;
    }

    private Long takeNullId() {
        return null;
    }

    private String takeExceptionMessagePageCannotBeSmallerThan() {
        return "Page cannot be smaller than 1";
    }

    private String takeExceptionMessageIdCannotBeNull() {
        return "Id cannot be null";
    }

    private Order takeReturnedOrder() {
        List<GiftCertificate> giftCertificateList = Arrays.asList(takeGiftCertificate());
        return new Order(
                takeId(),
                giftCertificateList,
                giftCertificateList.stream().mapToDouble(GiftCertificate::getPrice).sum(),
                LocalDateTime.of(2023, 01, 23, 3, 50, 55)
        );
    }

    private OrderDto takeExpectedOrderDto() {
        Order order = takeReturnedOrder();
        List<GiftCertificateDto> giftCertificateDtoList = order.getGiftCertificates().stream()
                .map(giftCertificate -> new GiftCertificateDto(
                        giftCertificate.getId(),
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getPrice(),
                        giftCertificate.getDuration(),
                        giftCertificate.getCreateDate().toString(),
                        giftCertificate.getLastUpdateDate().toString(),
                        giftCertificate.getTags().stream()
                                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                                .collect(Collectors.toCollection(HashSet::new))))
                .collect(Collectors.toList());
        return new OrderDto(
                order.getId(),
                giftCertificateDtoList,
                giftCertificateDtoList.stream().mapToDouble(GiftCertificateDto::getPrice).sum(),
                order.getPurchaseDate()
        );
    }

    private int takePageToFind() {
        return 5;
    }

    private GiftCertificate takeGiftCertificate() {
        return new GiftCertificate(
                12L,
                "Teddy bear",
                "Teddy bear gift certificate",
                32.99,
                5,
                LocalDateTime.of(2022, 10, 23, 10, 50, 44),
                LocalDateTime.of(2022, 10, 23, 10, 50, 44),
                Stream.of(
                                new Tag(4L, "toy"),
                                new Tag(6L, "bear"),
                                new Tag(9L, "brown"))
                        .collect(Collectors.toCollection(HashSet::new)));
    }

    private Page<Order> takeReturnedOrderPage() {
        Order order = takeReturnedOrder();
        List<Order> orders = Arrays.asList(order, order);
        return new Page<>(
                takePageToFind(),
                8,
                5,
                orders
        );
    }

    private Page<OrderDto> takeExpectedOrderDtoPage() {
        Page<Order> orderPage = takeReturnedOrderPage();
        OrderDto orderDto = takeExpectedOrderDto();
        List<OrderDto> orderDtoList = Arrays.asList(orderDto, orderDto);
        return new Page<>(
                orderPage.getPageNumber(),
                orderPage.getPageSize(),
                orderPage.getTotalPages(),
                orderDtoList
        );
    }
}
