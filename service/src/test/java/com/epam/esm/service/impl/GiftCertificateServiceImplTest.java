package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static GiftCertificate giftCertificate;
    private static GiftCertificateDto giftCertificateDto;

    @BeforeAll
    public static void init() {
        giftCertificate = new GiftCertificate(
                BigInteger.valueOf(1),
                "name1",
                "description",
                3.2,
                6,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        giftCertificate.addTag(new Tag(BigInteger.valueOf(1), "tag1"));
        giftCertificate.addTag(new Tag(BigInteger.valueOf(2), "tag2"));


        giftCertificateDto = new GiftCertificateDto(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate().toString(),
                giftCertificate.getLastUpdateDate().toString(),
                Arrays.asList(
                        new TagDto(BigInteger.valueOf(1), "tag1"),
                        new TagDto(BigInteger.valueOf(2), "tag2")
                ));
    }

    // TODO: 04.07.2022
    @Test
    void whenSaveNewGiftCertificateShouldReturnGiftCertificateTest() {
        //given
        GiftCertificateDto giftCertificateDtoWithoutId = new GiftCertificateDto(
                null,
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getLastUpdateDate(),
                giftCertificateDto.getCreateDate(),
                Arrays.asList(
                        new TagDto(null, "tag1"),
                        new TagDto(null, "tag2")
                )
        );
        GiftCertificate expectedGiftCertificate = giftCertificate = new GiftCertificate(
                null,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getCreateDate()
        );
        giftCertificate.addTag(new Tag("tag1"));
        giftCertificate.addTag(new Tag("tag2"));

        //when
        when(giftCertificateDao.saveCertificate(expectedGiftCertificate)).thenReturn(giftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoWithoutId);

        //then
        assertEquals(giftCertificateDto, actualGiftCertificateDto);
    }

    // TODO: 04.07.2022
    @Test
    void whenSaveNewGiftCertificateWithDuplicateTagDtoShouldReturnWithoutDuplicatesTest() {
        //given
        GiftCertificateDto giftCertificateDtoWithoutId = new GiftCertificateDto(
                null,
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getLastUpdateDate(),
                giftCertificateDto.getCreateDate(),
                Arrays.asList(
                        new TagDto(null, "tag1"),
                        new TagDto(null, "tag2"),
                        new TagDto(null, "tag2")
                )
        );
        GiftCertificate expectedGiftCertificate = giftCertificate = new GiftCertificate(
                null,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getCreateDate()
        );
        giftCertificate.addTag(new Tag("tag1"));
        giftCertificate.addTag(new Tag("tag2"));

        //when
        when(giftCertificateDao.saveCertificate(expectedGiftCertificate)).thenReturn(giftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoWithoutId);

        //then
        assertEquals(giftCertificateDto, actualGiftCertificateDto);
    }

    // TODO: 04.07.2022
    @Test
    void whenSaveNewGiftCertificateWithNullsShouldThrowNullPointerExceptionTest() {
        //given
        GiftCertificateDto giftCertificateWithNulls = new GiftCertificateDto();

        //when
        giftCertificateService.saveGiftCertificate(giftCertificateWithNulls);

        //then
    }

    // TODO: 04.07.2022
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameAscTest(){

    }

    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameDescTest(){

    }

    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByCreateDateDescTest(){

    }

    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByLastUpdateDateDescTest(){

    }

    void whenUpdateGiftCertificateShouldBeUpdatedTest(){

    }

    void whenAddTagToGiftCertificateShouldBeAddedTest(){

    }

    void whenRemovedTagFromGiftCertificateShouldBeRemoveTest(){

    }

    // TODO: 04.07.2022 deleteTest
    @Test
    void deleteGiftCertificateTest() {
        doNothing().when(giftCertificateDao).deleteCertificateById(BigInteger.ONE);
        giftCertificateService.deleteGiftCertificate(BigInteger.ONE);
    }
}
