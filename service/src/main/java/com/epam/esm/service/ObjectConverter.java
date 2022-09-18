package com.epam.esm.service;

import com.epam.esm.dto.*;
import com.epam.esm.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class ObjectConverter {

    protected TagDto convertTagToTagDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    protected Tag convertTagDtoToTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName().toLowerCase(Locale.ROOT));
        return tag;
    }

    protected GiftCertificate convertGiftCertificateDtoToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setTags(
                giftCertificateDto.getTags()
                        .stream()
                        .map(this::convertTagDtoToTag)
                        .collect(Collectors.toSet()));

        return giftCertificate;
    }

    protected GiftCertificateDto convertGiftCertificateToGiftCertificateDto(GiftCertificate giftCertificate) {
        return new GiftCertificateDto(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate().toString(),
                giftCertificate.getLastUpdateDate().toString(),
                giftCertificate.getTags()
                        .stream()
                        .map(this::convertTagToTagDto)
                        .collect(Collectors.toSet()));
    }

    protected Filter convertFiltersDtoToFilters(FilterDto filterDto) {
        return new Filter(
                filterDto.getTags()
                        .stream()
                        .map(this::convertTagDtoToTag)
                        .collect(Collectors.toSet()),
                filterDto.getName(),
                filterDto.getDescription(),
                filterDto.getOrderBy(),
                filterDto.getDirection());
    }

    protected OrderDto convertOrderToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getGiftCertificates()
                        .stream()
                        .map(this::convertGiftCertificateToGiftCertificateDto)
                        .collect(Collectors.toList()),
                order.getCost(),
                order.getPurchaseDate()
        );
    }

    protected Order convertOrderDtoToOrder(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getGiftCertificates()
                        .stream()
                        .map(this::convertGiftCertificateDtoToGiftCertificate)
                        .collect(Collectors.toList()),
                orderDto.getCost(),
                orderDto.getPurchaseDate()
        );
    }

    protected UserDto convertUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getOrders().stream().map(this::convertOrderToOrderDto).collect(Collectors.toList())
        );
    }

    protected User convertUserDtoToUser(UserDto userDto) {
        return new User(
                userDto.getUsername()
        );
    }
}
