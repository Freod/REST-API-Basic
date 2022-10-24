package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Page;
import com.epam.esm.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoIT {

    @Autowired
    private UserDao userDao;

    @Test
    void whenFindUserByIdShouldReturnThisUser(){
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
    void whenFindUserByIdWhichNotExistShouldThrowResourceNotFoundException(){
        //given
        Long idToFind = 0L;
        String expectedMessage = "";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userDao.findById(idToFind);
        });

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenFindPageShouldReturnThisPage(){
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
    void whenMakeAnOrderShouldReturnThisOrder(){
        //given
        //when
        //then
    }

    @Test
    void whenSelectMostWidelyUsedTagOfUserWithHighestCostOfOrdersShouldReturnThisTag(){
        //given
        //when
        //then
    }
}
