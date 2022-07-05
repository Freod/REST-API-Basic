package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Filter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static GiftCertificate returnedGiftCertificate;
    private static GiftCertificateDto expectedGiftCertificateDto;
    private static List<GiftCertificate> returnedGiftCertificateList;
    private static Filter filterToPass;

    @BeforeAll
    public static void init() {
        returnedGiftCertificate = new GiftCertificate(
                BigInteger.valueOf(1),
                "name1",
                "description",
                3.2,
                6,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        returnedGiftCertificate.addTag(new Tag(BigInteger.valueOf(1), "tag1"));
        returnedGiftCertificate.addTag(new Tag(BigInteger.valueOf(2), "tag2"));


        expectedGiftCertificateDto = new GiftCertificateDto(
                returnedGiftCertificate.getId(),
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getCreateDate().toString(),
                returnedGiftCertificate.getLastUpdateDate().toString(),
                Arrays.asList(
                        new TagDto(BigInteger.valueOf(1), "tag1"),
                        new TagDto(BigInteger.valueOf(2), "tag2")
                ));

        returnedGiftCertificateList = Arrays.asList(
                new GiftCertificate(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.now(),
                        LocalDateTime.now()),
                new GiftCertificate(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.now(),
                        LocalDateTime.now())
        );

        filterToPass = new Filter("", "", "");
    }

    // TODO: 04.07.2022
    @Test
    void whenSaveNewGiftCertificateShouldReturnGiftCertificateTest() {
        //given
        GiftCertificateDto giftCertificateDtoToSave = new GiftCertificateDto(
                null,
                expectedGiftCertificateDto.getName(),
                expectedGiftCertificateDto.getDescription(),
                expectedGiftCertificateDto.getPrice(),
                expectedGiftCertificateDto.getDuration(),
                expectedGiftCertificateDto.getLastUpdateDate(),
                expectedGiftCertificateDto.getCreateDate(),
                Arrays.asList(
                        new TagDto(null, "tag1"),
                        new TagDto(null, "tag2")
                )
        );
        GiftCertificate giftCertificateToSave = returnedGiftCertificate = new GiftCertificate(
                null,
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getLastUpdateDate(),
                returnedGiftCertificate.getCreateDate()
        );
        returnedGiftCertificate.addTag(new Tag("tag1"));
        returnedGiftCertificate.addTag(new Tag("tag2"));

        //when
        when(giftCertificateDao.saveCertificate(giftCertificateToSave)).thenReturn(returnedGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoToSave);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    // TODO: 04.07.2022
    @Test
    void whenSaveNewGiftCertificateWithDuplicateTagDtoShouldReturnWithoutDuplicatesTest() {
        //given
        GiftCertificateDto giftCertificateDtoToSave = new GiftCertificateDto(
                null,
                expectedGiftCertificateDto.getName(),
                expectedGiftCertificateDto.getDescription(),
                expectedGiftCertificateDto.getPrice(),
                expectedGiftCertificateDto.getDuration(),
                expectedGiftCertificateDto.getLastUpdateDate(),
                expectedGiftCertificateDto.getCreateDate(),
                Arrays.asList(
                        new TagDto(null, "tag1"),
                        new TagDto(null, "tag2"),
                        new TagDto(null, "tag2")
                )
        );
        GiftCertificate giftCertificateToSave = returnedGiftCertificate = new GiftCertificate(
                null,
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getLastUpdateDate(),
                returnedGiftCertificate.getCreateDate()
        );
        returnedGiftCertificate.addTag(new Tag("tag1"));
        returnedGiftCertificate.addTag(new Tag("tag2"));

        //when
        when(giftCertificateDao.saveCertificate(giftCertificateToSave)).thenReturn(returnedGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoToSave);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
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
    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameAscTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "name", "asc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.now().toString(),
                        LocalDateTime.now().toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.now().toString(),
                        LocalDateTime.now().toString(),
                        new ArrayList<>())
        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameDescTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "name", "desc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        new ArrayList<>())
        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByCreateDateAscTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "createDate", "asc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        new ArrayList<>())
        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByCreateDateDescTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "createDate", "desc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        new ArrayList<>())

        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByLastUpdateDateAscTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "lastUpdateDate", "asc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        new ArrayList<>())
        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByLastUpdateDateDescTest() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "lastUpdateDate", "desc");
        List<GiftCertificateDto> expectedGiftCertificateDtoList = Arrays.asList(
                new GiftCertificateDto(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30).toString(),
                        new ArrayList<>()),
                new GiftCertificateDto(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20).toString(),
                        new ArrayList<>())

        );

        //when
        when(giftCertificateDao.selectAllCertificates(filterToPass)).thenReturn(returnedGiftCertificateList);
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenUpdateGiftCertificateShouldBeUpdatedTest() {
        //given


        //when
//        when(giftCertificateDao.updateCertificate()).thenReturn();

        //then
    }

    @Test
    void whenAddTagToGiftCertificateShouldBeAddedTest() {
        //given

        //when

        //then
    }

    @Test
    void whenRemovedTagFromGiftCertificateShouldBeRemoveTest() {
        //given

        //when

        //then
    }

    // TODO: 04.07.2022 deleteTest
    @Test
    void deleteGiftCertificateTest() {
        //given
        BigInteger idToRemove = BigInteger.valueOf(1);

        //when
        doNothing().when(giftCertificateDao).deleteCertificateById(idToRemove);
        giftCertificateService.deleteGiftCertificate(idToRemove);

        //then
    }
}
