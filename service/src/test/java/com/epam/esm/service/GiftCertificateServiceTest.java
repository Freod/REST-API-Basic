package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.attribute.standard.Fidelity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Spy
    private ObjectConverter objectConverter;

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @Test
    void whenSaveNewGiftCertificateShouldReturnGiftCertificateDto() {
        //given
        GiftCertificateDto giftCertificateDtoToSave = takeGiftCertificateDtoToSaveOrUpdate();
        GiftCertificate giftCertificateToSave = takeGiftCertificateToSaveOrUpdate();
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = takeExpectedGiftCertificateDto();

        //when
        when(giftCertificateDao.save(giftCertificateToSave)).thenReturn(returnedGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto
                = giftCertificateService.saveGiftCertificate(giftCertificateDtoToSave);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenSaveNewGiftCertificateWithNullValuesShouldThrowWrongValueException() {
        //given
        GiftCertificateDto giftCertificateWithNulls = takeGiftCertificateDtoWithNulls();
        String expectedExceptionMessage = takeExceptionMessageGiftCertificateNullOrEmptyValues();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.saveGiftCertificate(giftCertificateWithNulls);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectGiftCertificateByIdShouldReturnGiftCertificateWithThatId() {
        //given
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = takeExpectedGiftCertificateDto();
        Long idToSelect = takeId();

        //when
        when(giftCertificateDao.findById(idToSelect)).thenReturn(returnedGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.selectGiftCertificate(idToSelect);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenSelectGiftCertificateWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullId = takeNullId();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.selectGiftCertificate(nullId);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectPageOfGiftCertificatesShouldReturnThisPage() {
        //given
        int page = takePageNumber();
        FilterDto filterDto = takeFilterDto();
        Filter filter = takeFilter();
        Page<GiftCertificate> returnedPage = takeReturnedGiftCertificatePage();
        Page<GiftCertificateDto> expectedPage = takeExpectedGiftCertificateDtoPage();

        //when
        when(giftCertificateDao.findPageUsingFilter(page, filter)).thenReturn(returnedPage);
        Page<GiftCertificateDto> actualPage= giftCertificateService.selectPageOfGiftCertificates(page, filterDto);

        //then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void whenSelectPageOfGiftCertificatesSmallerThanOneShouldThrowWrongValueException() {
        //given
        String expectedExceptionMessage = takeExceptionMessagePageCannotBeSmallerThan();
        int page = 0;
        FilterDto filterDto = takeFilterDto();

        //when
        WrongPageException thrown = Assertions.assertThrows(WrongPageException.class, () -> {
            giftCertificateService.selectPageOfGiftCertificates(page, filterDto);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenUpdateGiftCertificateShouldReturnGiftCertificateDto() {
        //given
        Long idToUpdate = takeId();
        GiftCertificateDto giftCertificateDtoToUpdate = takeGiftCertificateDtoToSaveOrUpdate();
        GiftCertificate giftCertificateToUpdate = takeGiftCertificateToSaveOrUpdate();
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        GiftCertificateDto expectedGiftCertificateDto = takeExpectedGiftCertificateDto();

        //when
        when(giftCertificateDao.update(idToUpdate, giftCertificateToUpdate)).thenReturn(returnedGiftCertificate);
        GiftCertificateDto actualGiftCertificateDto
                = giftCertificateService.updateGiftCertificate(idToUpdate, giftCertificateDtoToUpdate);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenUpdateGiftCertificateWithNullIdShouldThrowWrongValueException() {
        //given
        Long nullId = takeNullId();
        GiftCertificateDto giftCertificateDtoToUpdate = takeGiftCertificateDtoToSaveOrUpdate();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.updateGiftCertificate(nullId, giftCertificateDtoToUpdate);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenUpdateGiftCertificateWithNullValuesShouldThrowWrongValueException() {
        //given
        Long idToUpdate = takeId();
        GiftCertificateDto giftCertificateWithNulls = takeGiftCertificateDtoWithNulls();
        String expectedExceptionMessage = takeExceptionMessageGiftCertificateNullOrEmptyValues();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.updateGiftCertificate(idToUpdate, giftCertificateWithNulls);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenAddTagToGiftCertificateShouldBeAdded() {
        //given
        Long giftCertificateIdToAddTag = takeId();
        TagDto tagDtoToAdd = takeTagDtoToAdd();
        Tag tagToAdd = new Tag(tagDtoToAdd.getName());
        GiftCertificate giftCertificateReturnedWithNewTag = takeGiftCertificateReturnedWithNewTag();
        GiftCertificateDto expectedGiftCertificateDto = takeExpectedGiftCertificateDtoWithNewTag();

        //when
        when(giftCertificateDao.addTagToGiftCertificate(giftCertificateIdToAddTag, tagToAdd))
                .thenReturn(giftCertificateReturnedWithNewTag);
        GiftCertificateDto actualGiftCertificateDto =
                giftCertificateService.addTagToGiftCertificate(giftCertificateIdToAddTag, tagDtoToAdd);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenAddTagToNullIdGiftCertificateShouldThrownWrongValueException() {
        //given
        Long nullGiftCertificateIdToAddTag = takeNullId();
        TagDto tagDtoToAdd = takeTagDtoToAdd();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.addTagToGiftCertificate(nullGiftCertificateIdToAddTag, tagDtoToAdd);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenAddNullTagToGiftCertificateShouldThrowWrongValueException() {
        //given
        Long giftCertificateIdToAddTag = takeId();
        TagDto tagDtoWithNullValues = takeTagDtoWithNullValues();
        String expectedExceptionMessage = takeExceptionMessageTagNullOrEmptyValues();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.addTagToGiftCertificate(giftCertificateIdToAddTag, tagDtoWithNullValues);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemovedTagFromGiftCertificateShouldBeRemove() {
        //given
        Long giftCertificateIdToRemoveTag = takeId();
        TagDto tagDtoToRemove = takeTagDtoToRemove();
        Tag tagToRemove = new Tag(tagDtoToRemove.getName());
        GiftCertificate giftCertificateReturnedWithoutTag = takeGiftCertificateReturnedWithoutTag();
        GiftCertificateDto expectedGiftCertificateDto = takeExpectedGiftCertificateDtoWithoutTag();

        //when
        when(giftCertificateDao.removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagToRemove))
                .thenReturn(giftCertificateReturnedWithoutTag);
        GiftCertificateDto actualGiftCertificateDto =
                giftCertificateService.removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagDtoToRemove);

        //then
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void whenRemoveTagFromNullIdGiftCertificateShouldThrownWrongValueException() {
        //given
        Long nullGiftCertificateIdToAddTag = takeNullId();
        TagDto tagDtoToRemove = takeTagDtoToRemove();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.removeTagFromGiftCertificate(nullGiftCertificateIdToAddTag, tagDtoToRemove);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveNullTagFromGiftCertificateShouldThrowWrongValueException() {
        //given
        Long giftCertificateIdToRemoveTag = takeId();
        TagDto tagDtoWithNullValues = takeTagDtoWithNullValues();
        String expectedExceptionMessage = takeExceptionMessageTagNullOrEmptyValues();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            giftCertificateService.removeTagFromGiftCertificate(giftCertificateIdToRemoveTag, tagDtoWithNullValues);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenDeleteGiftCertificateByIdShouldBeRemove() {
        //given
        Long idToRemove = takeId();

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(idToRemove, invocationOnMock.getArgument(0));
            return idToRemove;
        }).when(giftCertificateDao).removeById(idToRemove);

        giftCertificateService.deleteGiftCertificate(idToRemove);

        //then
    }

    private Long takeId() {
        return 12L;
    }

    private Long takeNullId() {
        return null;
    }

    private int takePageNumber(){
        return 1;
    }

    private int takePageSize(){
        return 8;
    }

    private int takeTotalPages(){
        return 1;
    }

    private TagDto takeTagDtoWithNullValues() {
        return new TagDto(null, null);
    }

    private TagDto takeTagDtoToAdd() {
        return new TagDto(null, "broken");
    }

    private Tag takeTagToAdd() {
        return new Tag(takeTagDtoToAdd().getName());
    }

    private TagDto takeTagDtoToRemove() {
        return new TagDto(null, "bear");
    }

    private Tag takeTagToRemove() {
        return new Tag(takeTagDtoToRemove().getName());
    }

    private Set<Tag> tags() {
        return Stream.of(
                        new Tag(4L, "toy"),
                        new Tag(6L, "bear"),
                        new Tag(9L, "brown"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<Tag> tagsWithoutTag() {
        return Stream.of(
                        new Tag(4L, "toy"),
                        new Tag(9L, "brown"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<TagDto> tagsDto() {
        return Stream.of(
                        new TagDto(4L, "toy"),
                        new TagDto(6L, "bear"),
                        new TagDto(9L, "brown"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<TagDto> tagsDtoWithNewTag() {
        return Stream.of(
                        new TagDto(4L, "toy"),
                        new TagDto(6L, "bear"),
                        new TagDto(9L, "brown"),
                        takeTagDtoToAdd())
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<TagDto> tagsDtoWithoutTag() {
        return Stream.of(
                        new TagDto(4L, "toy"),
                        new TagDto(9L, "brown"))
                .collect(Collectors.toCollection(HashSet::new));
    }

    protected GiftCertificate takeReturnedGiftCertificate() {
        return new GiftCertificate(
                takeId(),
                "Teddy bear",
                "Teddy bear gift certificate",
                32.99,
                5,
                LocalDateTime.of(2022, 10, 23, 10, 50, 44),
                LocalDateTime.of(2022, 10, 23, 10, 50, 44),
                tags());
    }

    private GiftCertificate takeGiftCertificateReturnedWithNewTag() {
        GiftCertificate giftCertificate = takeReturnedGiftCertificate();
        giftCertificate.addTag(takeTagToAdd());
        return giftCertificate;
    }

    private GiftCertificate takeGiftCertificateReturnedWithoutTag() {
        GiftCertificate giftCertificate = takeReturnedGiftCertificate();
        giftCertificate.setTags(tagsWithoutTag());
        return giftCertificate;
    }

    private GiftCertificate takeGiftCertificateToSaveOrUpdate() {
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        return new GiftCertificate(
                takeNullId(),
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getTags());
    }

    private GiftCertificateDto takeExpectedGiftCertificateDto() {
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        return new GiftCertificateDto(
                returnedGiftCertificate.getId(),
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getCreateDate().toString(),
                returnedGiftCertificate.getLastUpdateDate().toString(),
                tagsDto());
    }

    private GiftCertificateDto takeExpectedGiftCertificateDtoWithNewTag() {
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        return new GiftCertificateDto(
                returnedGiftCertificate.getId(),
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getCreateDate().toString(),
                returnedGiftCertificate.getLastUpdateDate().toString(),
                tagsDtoWithNewTag());
    }

    private GiftCertificateDto takeExpectedGiftCertificateDtoWithoutTag() {
        GiftCertificate returnedGiftCertificate = takeReturnedGiftCertificate();
        return new GiftCertificateDto(
                returnedGiftCertificate.getId(),
                returnedGiftCertificate.getName(),
                returnedGiftCertificate.getDescription(),
                returnedGiftCertificate.getPrice(),
                returnedGiftCertificate.getDuration(),
                returnedGiftCertificate.getCreateDate().toString(),
                returnedGiftCertificate.getLastUpdateDate().toString(),
                tagsDtoWithoutTag());
    }

    private GiftCertificateDto takeGiftCertificateDtoToSaveOrUpdate() {
        GiftCertificate giftCertificate = takeGiftCertificateToSaveOrUpdate();
        return new GiftCertificateDto(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                null,
                null,
                tagsDto());
    }

    private GiftCertificateDto takeGiftCertificateDtoWithNulls() {
        return new GiftCertificateDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private FilterDto takeFilterDto(){
        return new FilterDto(new HashSet<>(), null, null, "name", "asc");
    }

    private Filter takeFilter(){
        FilterDto filterDto = takeFilterDto();
        return new Filter(
                filterDto.getTags().stream()
                        .map(tagDto -> new Tag(tagDto.getName()))
                        .collect(Collectors.toCollection(HashSet::new)),
                filterDto.getName(),
                filterDto.getDescription(),
                "name",
                "asc");
    }

    private Page<GiftCertificate> takeReturnedGiftCertificatePage(){
        List<GiftCertificate> giftCertificates = Arrays.asList(takeReturnedGiftCertificate());
        return new Page<>(
                takePageNumber(),
                takePageSize(),
                takeTotalPages(),
                giftCertificates
        );
    }

    private Page<GiftCertificateDto> takeExpectedGiftCertificateDtoPage(){
        List<GiftCertificateDto> giftCertificatesDto = Arrays.asList(takeExpectedGiftCertificateDto());
        return new Page<>(
                takePageNumber(),
                takePageSize(),
                takeTotalPages(),
                giftCertificatesDto
        );
    }

    private String takeExceptionMessageGiftCertificateNullOrEmptyValues() {
        return "GiftCertificate fields cannot be null or empty";
    }

    private String takeExceptionMessageIdCannotBeNull() {
        return "Id cannot be null";
    }

    private String takeExceptionMessageTagNullOrEmptyValues() {
        return "Tag fields cannot be null or empty";
    }

    private String takeExceptionMessagePageCannotBeSmallerThan() {
        return "Page cannot be smaller than 1";
    }
}
