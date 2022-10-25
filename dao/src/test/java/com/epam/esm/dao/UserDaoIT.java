package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@Sql(scripts = "classpath:/database/insert-dml.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDaoIT {

    @Autowired
    private UserDao userDao;
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void whenFindUserByIdShouldReturnThisUser() {
        //given
        Long idToFind = 1L;

        //when
        User actualUser = userDao.findById(idToFind);

        //then
        assertEquals(idToFind, actualUser.getId());
        assertNotNull(actualUser.getUsername());
        assertNotNull(actualUser.getOrders());
    }

    @Test
    void whenFindUserByIdWhichNotExistShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedMessage = "User with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userDao.findById(idToFind);
        });

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenFindPageShouldReturnThisPage() {
        //given
        int pageNumber = 1;

        //when
        Page<User> actualUserPage = userDao.findPage(pageNumber);

        //then
        assertEquals(pageNumber, actualUserPage.getPageNumber());
        assertNotNull(actualUserPage.getPageSize());
        assertNotNull(actualUserPage.getTotalPages());
        assertNotNull(actualUserPage.getCollection());
    }

    @Test
    void whenMakeAnOrderShouldReturnThisOrder() {
        //given
        Long idToFind = 1L;
        List<GiftCertificate> giftCertificateList = Arrays.asList(
                new GiftCertificate(idToFind, null, null, null, null, new HashSet<>())
        );
        Order order = new Order(null, giftCertificateList, null, null);

        //when
        Order actualOrder = userDao.makeAnOrder(idToFind, order);

        //then
        assertNotNull(actualOrder.getId());
        assertNotNull(actualOrder.getGiftCertificates());
        assertNotNull(actualOrder.getCost());
        assertNotNull(actualOrder.getPurchaseDate());
    }

    @Test
    void whenMakeAnOrderWhenUserIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long idToFindUser = 0L;
        Long idToFind = 1L;
        String expectedMessage = "User with id = (" + idToFindUser + ") isn't exists.";
        List<GiftCertificate> giftCertificateList = Arrays.asList(
                new GiftCertificate(idToFind, null, null, null, null, new HashSet<>())
        );
        Order order = new Order(null, giftCertificateList, null, null);

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userDao.makeAnOrder(idToFindUser, order);
        });

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenMakeAnOrderWhenGiftCertificateIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long idToFindGiftCertificate = 0L;
        Long idToFind = 1L;
        String expectedMessage = "GiftCertificate with id = (" + idToFindGiftCertificate + ") isn't exists.";
        List<GiftCertificate> giftCertificateList = Arrays.asList(
                new GiftCertificate(idToFindGiftCertificate, null, null, null, null, new HashSet<>())
        );
        Order order = new Order(null, giftCertificateList, null, null);

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userDao.makeAnOrder(idToFind, order);
        });

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenSelectMostWidelyUsedTagOfUserWithHighestCostOfOrdersShouldReturnThisTag() {
        //given
        String expectedTagName = "mostUsedTag";

        //when
        Tag tag = userDao.mostWidelyUsedTagOfUserWithTheHighestCostOfOrders();

        //then
        assertNotNull(tag.getId());
        assertEquals(expectedTagName, tag.getName());
    }
}
