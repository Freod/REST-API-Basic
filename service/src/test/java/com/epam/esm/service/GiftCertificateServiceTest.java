package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @Test
    void whenSaveNewGiftCertificateShouldReturnGiftCertificate() {
        //given
        GiftCertificate returnGiftCertificate = getReturnGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = getExpectedGiftCertificateDto(returnGiftCertificate);
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

        //when
        when(giftCertificateDao.saveCertificate(any(GiftCertificate.class))).thenReturn(returnGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoToSave);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenSaveNewGiftCertificateWithDuplicateTagDtoShouldReturnWithoutDuplicates() {
        //given
        GiftCertificate returnGiftCertificate = getReturnGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = getExpectedGiftCertificateDto(returnGiftCertificate);
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

        //when
        when(giftCertificateDao.saveCertificate(any(GiftCertificate.class))).thenReturn(returnGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDtoToSave);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenSaveNewGiftCertificateWithNullValuesShouldThrowNullPointerException() {
        //given
        GiftCertificateDto giftCertificateWithNulls = new GiftCertificateDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        String expectedExceptionMessage = "cannot save giftCertificate with null values";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            giftCertificateService.saveGiftCertificate(giftCertificateWithNulls);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameAsc() {
        //given
        FilterDto filterDtoToPass = new FilterDto("", "", "", "name", "asc");
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByNameDesc() {
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByCreateDateAsc() {
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByCreateDateDesc() {
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByLastUpdateDateAsc() {
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectAllGiftCertificatesShouldReturnListGiftCertificateDtoOrderByLastUpdateDateDesc() {
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
        when(giftCertificateDao.selectAllCertificates(getFilterToPass())).thenReturn(getReturnGiftCertificateList());
        List<GiftCertificateDto> actualGiftCertificateDtoList = giftCertificateService.selectAllGiftCertificates(filterDtoToPass);

        //then
        assertEquals(expectedGiftCertificateDtoList, actualGiftCertificateDtoList);
    }

    @Test
    void whenSelectGiftCertificateByIdShouldReturnGiftCertificateWithThatId() {
        //given
        GiftCertificate returnGiftCertificate = getReturnGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = getExpectedGiftCertificateDto(returnGiftCertificate);
        BigInteger idToSelect = BigInteger.valueOf(1);

        //when
        when(giftCertificateDao.selectCertificateById(idToSelect)).thenReturn(returnGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.selectGiftCertificate(idToSelect);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenUpdateGiftCertificateShouldBeUpdated() {
        //given
        BigInteger idToUpdate = BigInteger.valueOf(1);
        GiftCertificateDto giftCertificateDtoToUpdate =
                new GiftCertificateDto(
                        null,
                        "nameChange",
                        "descriptionChange",
                        33.5,
                        5,
                        null,
                        null,
                        new ArrayList<>());
        GiftCertificate expectedGiftCertificate =
                new GiftCertificate(
                        idToUpdate,
                        giftCertificateDtoToUpdate.getName(),
                        giftCertificateDtoToUpdate.getDescription(),
                        giftCertificateDtoToUpdate.getPrice(),
                        giftCertificateDtoToUpdate.getDuration(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

        //when
        doAnswer(invocationOnMock -> {
            GiftCertificate actualGiftCertificate = invocationOnMock.getArgument(0, GiftCertificate.class);

            assertEquals(expectedGiftCertificate.getId(), actualGiftCertificate.getId());
            assertEquals(expectedGiftCertificate.getName(), actualGiftCertificate.getName());
            assertEquals(expectedGiftCertificate.getDescription(), actualGiftCertificate.getDescription());
            assertEquals(expectedGiftCertificate.getPrice(), actualGiftCertificate.getPrice());
            assertEquals(expectedGiftCertificate.getDuration(), actualGiftCertificate.getDuration());

            return actualGiftCertificate;
        }).when(giftCertificateDao).updateCertificate(any(GiftCertificate.class));
        giftCertificateService.updateGiftCertificate(idToUpdate, giftCertificateDtoToUpdate);

        //then
    }

    @Test
    void whenUpdateGiftCertificateWithNullValuesShouldThrowNullPointerException() {
        //given
        BigInteger idToUpdate = BigInteger.valueOf(1);
        GiftCertificateDto giftCertificateDtoWithNullValues = new GiftCertificateDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        String expectedExceptionMessage = "cannot update giftCertificate with null values";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            giftCertificateService.updateGiftCertificate(idToUpdate, giftCertificateDtoWithNullValues);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenAddTagToGiftCertificateShouldBeAdded() {
        //given
        BigInteger giftCertificateIdToAddTag = BigInteger.valueOf(1);
        TagDto tagDtoToAdd = new TagDto(null, "tag1");
        Tag tagToAdd = new Tag("tag1");

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(giftCertificateIdToAddTag, invocationOnMock.getArgument(0));

            Tag actualTag = invocationOnMock.getArgument(1);
            assertEquals(tagToAdd, actualTag);

            return invocationOnMock.getArguments();
        }).when(giftCertificateDao).addTagToGiftCertificate(giftCertificateIdToAddTag, tagToAdd);

        giftCertificateService.addTagToGiftCertificate(giftCertificateIdToAddTag, tagDtoToAdd);

        //then
    }

    @Test
    void whenAddNullTagToGiftCertificateShouldThrowNullPointerException() {
        //given
        BigInteger giftCertificateIdToAddTag = BigInteger.valueOf(1);
        TagDto tagDtoWithNullValues = new TagDto(null, null);
        String expectedExceptionMessage = "cannot add tag with null values";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            giftCertificateService.addTagToGiftCertificate(giftCertificateIdToAddTag, tagDtoWithNullValues);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemovedTagFromGiftCertificateShouldBeRemove() {
        //given
        BigInteger giftCertificateIdToRemoveTag = BigInteger.valueOf(1);
        TagDto tagDtoToRemove = new TagDto(null, "tag1");
        Tag tagToRemove = new Tag("tag1");

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(giftCertificateIdToRemoveTag, invocationOnMock.getArgument(0));

            Tag actualTag = invocationOnMock.getArgument(1);
            assertEquals(tagToRemove, actualTag);

            return invocationOnMock.getArguments();
        }).when(giftCertificateDao).removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagToRemove);

        giftCertificateService.removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagDtoToRemove);

        //then
    }

    @Test
    void whenRemoveNullTagFromGiftCertificateShouldThrowNullPointerException() {
        //given
        BigInteger giftCertificateIdToRemoveTag = BigInteger.valueOf(1);
        TagDto tagDtoWithNullValues = new TagDto(null, null);
        String expectedExceptionMessage = "cannot remove tag with null values";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            giftCertificateService.removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagDtoWithNullValues);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenDeleteGiftCertificateByIdShouldBeRemove() {
        //given
        BigInteger idToRemove = BigInteger.valueOf(1);

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(idToRemove, invocationOnMock.getArgument(0));
            return idToRemove;
        }).when(giftCertificateDao).deleteCertificateById(idToRemove);

        giftCertificateService.deleteGiftCertificate(idToRemove);

        //then
    }

    private GiftCertificate getReturnGiftCertificate() {
        GiftCertificate returnGiftCertificate = new GiftCertificate(
                BigInteger.valueOf(1),
                "name1",
                "description",
                3.2,
                6,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        returnGiftCertificate.addTag(new Tag(BigInteger.valueOf(1), "tag1"));
        returnGiftCertificate.addTag(new Tag(BigInteger.valueOf(2), "tag2"));

        return returnGiftCertificate;
    }

    private GiftCertificateDto getExpectedGiftCertificateDto(GiftCertificate returnGiftCertificate) {
        GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto(
                returnGiftCertificate.getId(),
                returnGiftCertificate.getName(),
                returnGiftCertificate.getDescription(),
                returnGiftCertificate.getPrice(),
                returnGiftCertificate.getDuration(),
                returnGiftCertificate.getCreateDate().toString(),
                returnGiftCertificate.getLastUpdateDate().toString(),
                Arrays.asList(
                        new TagDto(BigInteger.valueOf(1), "tag1"),
                        new TagDto(BigInteger.valueOf(2), "tag2")
                ));
        return expectedGiftCertificateDto;
    }

    private List<GiftCertificate> getReturnGiftCertificateList() {
        return Arrays.asList(
                new GiftCertificate(
                        BigInteger.valueOf(1),
                        "name1",
                        "description1",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20),
                        LocalDateTime.of(2022, 07, 05, 16, 20, 20)),
                new GiftCertificate(
                        BigInteger.valueOf(2),
                        "name2",
                        "description2",
                        3.5,
                        5,
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30),
                        LocalDateTime.of(2022, 07, 05, 16, 21, 30))
        );
    }

    private Filter getFilterToPass() {
        return new Filter("", "", "");
    }
}
