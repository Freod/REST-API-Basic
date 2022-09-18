package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Page;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = Objects.requireNonNull(userService);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> showUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        addSelectUserDtoLink(userDto);
        return EntityModel.of(userDto);
    }

    @GetMapping
    @ResponseStatus
    public CollectionModel<UserDto> showPageOfUsers(@RequestParam(defaultValue = "1") Integer page){
        Page<UserDto> userDtoPage = userService.selectPageOfUsers(page);
        Collection<UserDto> userDtoCollection =
                userDtoPage.getCollection()
                        .stream()
                        .map(UserController::addSelectUserDtoLink)
                        .collect(Collectors.toList());

        List<Link> linkList = new LinkedList<>();
        try {
            if (page > 1 && userDtoPage.getTotalPages() > 0) {
                linkList.add(linkTo(
                        UserController.class
                                .getMethod("showPageOfUsers", Integer.class), 1)
                        .withRel("firstPage"));
            }

            if (page > 2 && page <= userDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        UserController.class
                                .getMethod("showPageOfUsers", Integer.class), page - 1)
                        .withRel("previousPage"));
            }

            if (page <= userDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        UserController.class
                                .getMethod("showPageOfUsers", Integer.class), page)
                        .withSelfRel());
            }

            if (page + 1 < userDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        UserController.class
                                .getMethod("showPageOfUsers", Integer.class), page + 1)
                        .withRel("nextPage"));
            }

            if (userDtoPage.getTotalPages() > 0 && page != userDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        UserController.class
                                .getMethod("showPageOfUsers", Integer.class), userDtoPage.getTotalPages())
                        .withRel("lastPage"));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return CollectionModel.of(userDtoCollection, linkList);
    }

    @PostMapping("{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> makeNewOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        orderDto = userService.makeNewOrder(id, orderDto);
        OrderController.addSelectOrderDtoLink(orderDto);
        return EntityModel.of(orderDto);
    }

    private static UserDto addSelectUserDtoLink(UserDto userDto){
        try {
            userDto.getOrders().stream().map(OrderController::addSelectOrderDtoLink).collect(Collectors.toList());
            Link linkById = linkTo(UserController.class.getMethod("showUserById", Long.class), userDto.getId()).withSelfRel();
            userDto.add(linkById);
            return userDto;
        }
        catch (NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }
}
