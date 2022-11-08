package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.*;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.Page;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;
    private ObjectConverter objectConverter;
    private final JwtEncoder jwtEncoder;

    @Autowired
    public UserService(UserDao userDao, ObjectConverter objectConverter, JwtEncoder jwtEncoder) {
        this.userDao = Objects.requireNonNull(userDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
        this.jwtEncoder = Objects.requireNonNull(jwtEncoder);
    }

    public UserDto findById(Long id) {
        if (id == null) {
            throw new WrongValueException("Id cannot be null");
        }

        return objectConverter.convertUserToUserDto(
                userDao.findById(id));
    }

    public Page<UserDto> selectPageOfUsers(Integer page) {
        if (page < 1) throw new WrongPageException("Page cannot be smaller than 1");
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
            throw new WrongValueException("Id cannot be null");
        }

        return objectConverter.convertOrderToOrderDto(
                userDao.makeAnOrder(id, objectConverter.convertOrderDtoToOrder(orderDto)));
    }

    public TagDto mostWidelyUsedTagOfUserWithTheHighestCostOfOrders() {
        return objectConverter.convertTagToTagDto(
                userDao.mostWidelyUsedTagOfUserWithTheHighestCostOfOrders());
    }

    public TokenDto generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return new TokenDto(
                "bearer",
                this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()
        );
    }
}
