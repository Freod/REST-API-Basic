package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User showUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/list")
    @ResponseStatus
    public List<User> showPageOfUsers(){
        return userService.findAll();
    }

    @PostMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto makeNewOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return userService.makeNewOrder(id, orderDto);
    }
}
