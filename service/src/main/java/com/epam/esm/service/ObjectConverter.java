package com.epam.esm.service;

import com.epam.esm.dto.*;
import com.epam.esm.model.*;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class ObjectConverter {

    protected TagDto convertTagToTagDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    protected Tag convertTagDtoToTag(TagDto tagDto) {
        return new Tag(
                tagDto.getId(),
                tagDto.getName().toLowerCase(Locale.ROOT)
        );
    }

    protected GiftCertificate convertGiftCertificateDtoToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        return new GiftCertificate(
                giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getTags()
                        .stream()
                        .map(this::convertTagDtoToTag)
                        .collect(Collectors.toSet())
        );
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
}
