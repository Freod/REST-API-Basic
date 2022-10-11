package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.*;
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
class UserServiceTest {

    @Mock
    private UserDao userDao;
    @Spy
    private ObjectConverter objectConverter;

    @InjectMocks
    private UserService userService;

    @Test
    void whenUserFindByIdShouldReturnThatUser() {
        //given
        Long idToFind = takeId();
        User returnedUser = takeReturnedUser();
        UserDto expectedUserDto = takeExpectedUserDto();

        //when
        when(userDao.findById(idToFind)).thenReturn(returnedUser);
        UserDto actualUserDto = userService.findById(idToFind);

        //then
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void whenUserWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullId = takeNullId();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            userService.findById(nullId);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectPageOfUsersShouldReturnThisPage() {
        //given
        int page = takePageNumber();
        Page<User> returnedUserPage = takeReturnedUserPage();
        Page<UserDto> expectedPage = takeExpectedUserDtoPage();

        //when
        when(userDao.findPage(page)).thenReturn(returnedUserPage);
        Page<UserDto> actualPage = userService.selectPageOfUsers(page);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void whenSelectPageOfUsersSmallerThanOneShouldThrowWrongValueException() {
        //given
        String expectedExceptionMessage = takeExceptionMessagePageCannotBeSmallerThan();
        int page = 0;

        //when
        WrongPageException thrown = Assertions.assertThrows(WrongPageException.class, () -> {
            userService.selectPageOfUsers(page);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenMakeOrderShouldReturnOrderDto() {
        //given
        Long userId = takeUserId();
        OrderDto orderDto = takeNewOrderDto();
        Order order = takeNewOrder();
        Order returnedOrder = takeReturnedOrder();
        OrderDto expectedOrder = takeExpectedOrderDto();

        //when
        when(userDao.makeAnOrder(userId, order)).thenReturn(returnedOrder);
        OrderDto actualOrder = userService.makeNewOrder(userId, orderDto);

        //then
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void whenMakeOrderWithNullUserIdShouldThrowWrongValueException() {
        //given
        Long userId = takeNullId();
        OrderDto orderDto = takeNewOrderDto();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            userService.makeNewOrder(userId, orderDto);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectMostWidelyUsedTagOfUserWithTheHighestCostOfOrdersShouldReturnThisTag() {
        //given
        Tag returnedTag = takeReturnedTag();
        TagDto expectedTagDto = takeExpectedTagDto();

        //when
        when(userDao.mostWidelyUsedTagOfUserWithTheHighestCostOfOrders()).thenReturn(returnedTag);
        TagDto actualTagDto = userService.mostWidelyUsedTagOfUserWithTheHighestCostOfOrders();

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    private Long takeId() {
        return 8L;
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

    private Long takeUserId() {
        return 1L;
    }

    private GiftCertificate takeGiftCertificate() {
        return new GiftCertificate(
                takeId(),
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

    private User takeReturnedUser() {
        return new User(
                54L,
                "John",
                Arrays.asList(takeReturnedOrder())
        );
    }

    private UserDto takeExpectedUserDto() {
        User user = takeReturnedUser();
        return new UserDto(
                user.getId(),
                user.getUsername(),
                Arrays.asList(takeExpectedOrderDto())
        );
    }

    private int takePageNumber() {
        return 1;
    }

    private int takePageSize() {
        return 8;
    }

    private int takeTotalPages() {
        return 1;
    }

    private Page<User> takeReturnedUserPage() {
        List<User> users = Arrays.asList(takeReturnedUser());
        return new Page<>(
                takePageNumber(),
                takePageSize(),
                takeTotalPages(),
                users
        );
    }

    private Page<UserDto> takeExpectedUserDtoPage() {
        List<UserDto> usersDto = Arrays.asList(takeExpectedUserDto());
        return new Page<>(
                takePageNumber(),
                takePageSize(),
                takeTotalPages(),
                usersDto
        );
    }

    private Order takeReturnedOrder() {
        List<GiftCertificate> giftCertificates = Arrays.asList(takeGiftCertificate());
        return new Order(
                23L,
                giftCertificates,
                giftCertificates.stream().mapToDouble(GiftCertificate::getPrice).sum(),
                LocalDateTime.of(2023, 05, 14, 13, 53, 24)
        );
    }

    private OrderDto takeExpectedOrderDto() {
        Order order = takeReturnedOrder();
        return new OrderDto(
                order.getId(),
                order.getGiftCertificates().stream()
                        .map(giftCertificate -> new GiftCertificateDto(
                                giftCertificate.getId(),
                                giftCertificate.getName(),
                                giftCertificate.getDescription(),
                                giftCertificate.getPrice(),
                                giftCertificate.getDuration(),
                                giftCertificate.getCreateDate().toString(),
                                giftCertificate.getLastUpdateDate().toString(),
                                giftCertificate.getTags().stream().map(tag ->
                                        new TagDto(tag.getId(), tag.getName()
                                        )).collect(Collectors.toCollection(HashSet::new)
                                ))).collect(Collectors.toList()),
                order.getCost(),
                order.getPurchaseDate()
        );
    }

    private OrderDto takeNewOrderDto() {
        OrderDto orderDto = takeExpectedOrderDto();
        return new OrderDto(
                null,
                orderDto.getGiftCertificates().stream()
                        .map(giftCertificateDto -> new GiftCertificateDto(
                                giftCertificateDto.getId(),
                                giftCertificateDto.getName(),
                                giftCertificateDto.getDescription(),
                                giftCertificateDto.getPrice(),
                                giftCertificateDto.getDuration(),
                                null,
                                null,
                                giftCertificateDto.getTags()
                        ))
                        .collect(Collectors.toList()),
                orderDto.getCost(),
                orderDto.getPurchaseDate()
        );
    }

    private Order takeNewOrder() {
        Order order = takeReturnedOrder();
        return new Order(
                null,
                order.getGiftCertificates().stream()
                        .map(giftCertificate -> new GiftCertificate(
                                giftCertificate.getId(),
                                giftCertificate.getName(),
                                giftCertificate.getDescription(),
                                giftCertificate.getPrice(),
                                giftCertificate.getDuration(),
                                null,
                                null,
                                giftCertificate.getTags()
                        ))
                        .collect(Collectors.toList()),
                order.getCost(),
                order.getPurchaseDate()
        );
    }

    private Tag takeReturnedTag() {
        return new Tag(3L, "bear");
    }

    private TagDto takeExpectedTagDto() {
        Tag tag = takeReturnedTag();
        return new TagDto(tag.getId(), tag.getName());
    }
}
