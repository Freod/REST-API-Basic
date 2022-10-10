package com.epam.esm.service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.exception.WrongValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private ObjectConverter objectConverter;
    @InjectMocks
    private OrderService orderService;

    // TODO: 08/10/2022
    /*@Test
    void whenSelectOrderByIdShouldReturnOrder(){
        //given
        Long idToFind = 1L;
        Order returnedOrder;

        //when
        when(orderDao.findById(idToFind)).thenReturn(returnedOrder);
        OrderDto actualOrderDto = orderService.showOrderById(idToFind);

        //then
        assertEquals(expectedOrderDto, actualOrderDto);
    }*/

    @Test
    void whenSelectOrderWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullIdToFind = null;
        String expectedExceptionMessage = "Id cannot be null";

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            orderService.showOrderById(nullIdToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    // TODO: 08/10/2022
    /*
    @Test
    void whenSelectPageOfOrdersShouldReturnThisPage(){

    }*/

    @Test
    void whenSelectPageOfOrdersSmallerThanOneShouldThrowWrongValueException() {
        //given
        String expectedExceptionMessage = "Page cannot be smaller than 1";
        int page = 0;

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            orderService.showPageOfOrders(page);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }
}
