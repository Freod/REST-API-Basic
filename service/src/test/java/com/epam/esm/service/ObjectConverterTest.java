package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ObjectConverterTest {

//    @InjectMocks
    private ObjectConverter objectConverter;

    @Test
    void whenCallConvertTagDtoToTagShouldReturnTagWithSameFields() {
        //given
        TagDto tagDtoToConvert = new TagDto(1L, "tag1");
        Tag expectedTag = new Tag(tagDtoToConvert.getId(), tagDtoToConvert.getName());

        //when
        Tag actualTag = objectConverter.convertTagDtoToTag(tagDtoToConvert);

        //then
        assertEquals(expectedTag, actualTag);
        assertEquals(expectedTag.getId(), actualTag.getId());
        assertEquals(expectedTag.getName(), actualTag.getName());
    }

    @Test
    void whenCallConvertTagToTagDtoShouldReturnTagDtoWithSameFields() {
        //given
        Tag tagToConvert = new Tag(1L, "tag1");
        TagDto expectedTagDto = new TagDto(tagToConvert.getId(), tagToConvert.getName());

        //when
        TagDto actualTagDto = objectConverter.convertTagToTagDto(tagToConvert);

        //then
        assertEquals(expectedTagDto, actualTagDto);
        assertEquals(expectedTagDto.getId(), actualTagDto.getId());
        assertEquals(expectedTagDto.getName(), actualTagDto.getName());
    }

    @Test
    void whenCallConvertGiftCertificateDtoToGiftCertificateShouldReturnGiftCertificateWithSameFields() {
        //given
        GiftCertificateDto giftCertificateDtoToConvert
                = new GiftCertificateDto(
                1L,
                "giftCert1",
                "description",
                30.2,
                3,
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                new TreeSet<>(Arrays.asList(
                        new TagDto(1L, "tag1"),
                        new TagDto(2L, "tag2")
                ))
        );
        GiftCertificate expectedGiftCertificate = new GiftCertificate();

        //when
        GiftCertificate actualGiftCertificate
                = objectConverter.convertGiftCertificateDtoToGiftCertificate(giftCertificateDtoToConvert);

        //then
        assertEquals(expectedGiftCertificate, actualGiftCertificate);
        // TODO: 06/10/2022 more asserts?
    }

    @Test
    void whenCallConvertGiftCertificateToGiftCertificateDtoShouldReturnGiftCertificateDtoWithSameFields() {
//        //given
//        GiftCertificate giftCertificateToConvert = new GiftCertificate();
//        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto();
//
//        //when
//        GiftCertificateDto actualGiftCertificateDto
//                = objectConverter.convertGiftCertificateToGiftCertificateDto(giftCertificateToConvert);
//
//        //then
//        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
        // TODO: 06/10/2022 more asserts?
    }

    @Test
    void whenCallConvertFiltersDtoToFiltersShouldReturnFiltersWithSameFields() {
        //given

        //when
        // TODO: 06/10/2022

        //then
    }

    @Test
    void whenCallConvertOrderToOrderDtoShouldReturnOrderDtoWithSameFields() {
        //given

        //when
        // TODO: 06/10/2022


        //then
    }

    @Test
    void whenCallConvertOrderDtoToOrderShouldReturnOrderWithSameFields() {
        //given

        //when
        // TODO: 06/10/2022

        //then
    }

    @Test
    void whenCallConvertUserToUserDtoShouldReturnUserDtoWithSameFields() {
        //given

        //when
        // TODO: 06/10/2022

        //then
    }

    @Test
    void whenCallConvertUserDtoToUserShouldReturnUserWithSameFields() {
        //given

        //when
        // TODO: 06/10/2022

        //then
    }
}
