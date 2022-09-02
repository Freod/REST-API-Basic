package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    // TODO: 02.09.2022  
    @PostConstruct
    private void initUsers(){
        userDao.save(new User(
                "user1"
        ));
    }

    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }


    public User findById(Long id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public OrderDto makeNewOrder(Long id, OrderDto orderDto) {
        return OrderService.convertOrderToOrderDto(userDao.makeAnOrder(id, OrderService.convertOrderDtoToOrder(orderDto)));
    }
}
