package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.model.Page;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;
    private ObjectConverter objectConverter;

    @Autowired
    public UserService(UserDao userDao, ObjectConverter objectConverter) {
        this.userDao = Objects.requireNonNull(userDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
    }

    public UserDto findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Id cannot be null.");
        }

        return objectConverter.convertUserToUserDto(
                userDao.findById(id));
    }

    public Page<UserDto> selectPageOfUsers(Integer page) {
        if (page < 1) throw new WrongPageException("Page cannot be smaller by 1");
        Page<User> userPage = userDao.findPage(page);
        return new Page<>(
                userPage.getPageNumber(),
                userPage.getPageSize(),
                userPage.getTotalPages(),
                userPage.getCollection()
                        .stream()
                        .map(objectConverter::convertUserToUserDto)
                        .collect(Collectors.toList()));
    }

    public OrderDto makeNewOrder(Long id, OrderDto orderDto) {
        if (id == null) {
            throw new NullPointerException("Id cannot be null.");
        }

        return objectConverter.convertOrderToOrderDto(
                userDao.makeAnOrder(id, objectConverter.convertOrderDtoToOrder(orderDto)));
    }

    // TODO: 10/10/2022
    //Get the most widely used tag of a user with the highest cost of all orders.
    //Create separate endpoint for this query.
    //Demonstrate SQL execution plan for this query (explain).
    public TagDto mostWidelyUsedTagOfUserWithTheHighestCostOfOrders() {
        return null;
    }
}
