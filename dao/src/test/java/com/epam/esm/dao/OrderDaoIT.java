package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@Sql(scripts = "classpath:/database/insert-dml.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderDaoIT {

    @Autowired
    private OrderDao orderDao;

    @Test
    void whenFindOrderByIdShouldReturnThisOrder() {
        //given
        Long idToFind = 1L;

        //when
        Order actualOrder = orderDao.findById(idToFind);

        //then
        assertEquals(idToFind, actualOrder.getId());
        assertNotNull(actualOrder.getGiftCertificates());
        assertNotNull(actualOrder.getCost());
        assertNotNull(actualOrder.getPurchaseDate());
    }

    @Test
    void whenFindOrderByIdWhichNotExistsShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedExceptionMessage = "Order with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            orderDao.findById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenFindOrdersPageShouldReturnThisPage() {
        //given
        int pageNumber = 1;

        //when
        Page<Order> actualPage = orderDao.findPage(pageNumber);

        //then
        assertEquals(pageNumber, actualPage.getPageNumber());
        assertNotNull(actualPage.getPageSize());
        assertNotNull(actualPage.getTotalPages());
        assertNotNull(actualPage.getCollection());
    }
}
