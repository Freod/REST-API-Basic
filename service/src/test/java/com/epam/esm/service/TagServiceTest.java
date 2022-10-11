package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagDao tagDao;
    @Spy
    private ObjectConverter objectConverter;

    @InjectMocks
    private TagService tagService;

    @Test
    void whenSaveNewTagShouldReturnCreatedTag() {
        //given
        Tag tagToInsert = takeTagToInsert();
        Tag insertedTag = takeInsertedTag();
        TagDto tagDtoToSave = takeTagDtoToSave();
        TagDto expectedTagDto = takeExpectedTagDto();

        //when
        when(tagDao.save(tagToInsert)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.saveTag(tagDtoToSave);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSaveNewTagWithoutNameShouldThrowNullPointerException() {
        //given
        TagDto tagDtoToSave = takeTagDtoWithoutName();
        String expectedExceptionMessage = takeExceptionMessageNameCannotBeEmpty();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.saveTag(tagDtoToSave);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSaveNewTagWithEmptyNameShouldThrowWrongValueException() {
        //given
        TagDto tagDtoToSave = takeTagDtoWithEmptyName();
        String expectedExceptionMessage = takeExceptionMessageNameCannotBeEmpty();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.saveTag(tagDtoToSave);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectTagByIdShouldReturnTag() {
        //given
        Long idToFind = takeId();
        Tag insertedTag = takeInsertedTag();
        TagDto expectedTagDto = takeExpectedTagDto();

        //when
        when(tagDao.findById(idToFind)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagById(idToFind);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullIdToFind = takeNullId();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.selectTagById(nullIdToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenSelectTagByNameShouldReturnTag() {
        //given
        String nameToFind = takeNameToFind();
        Tag insertedTag = takeInsertedTag();
        TagDto expectedTagDto = takeExpectedTagDto();

        //when
        when(tagDao.findByName(nameToFind)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagByName(nameToFind);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutNameShouldThrowWrongValueException() {
        //given
        String nullNameToFind = takeNullName();
        String emptyNameToFind = takeEmptyName();
        String expectedExceptionMessage = takeExceptionMessageNameCannotBeEmpty();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.selectTagByName(nullNameToFind);
        });
        WrongValueException thrown2 = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.selectTagByName(emptyNameToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
        assertEquals(expectedExceptionMessage, thrown2.getMessage());
    }

    @Test
    void whenSelectPageOfTagsShouldReturnThisPage() {
        //given
        int pageToFind = 1;
        int totalPages = 3;
        Collection<Tag> tags = Arrays.asList(
                new Tag("tag1"),
                new Tag("tag2"),
                new Tag("tag3"),
                new Tag("tag4"),
                new Tag("tag5"),
                new Tag("tag6"),
                new Tag("tag7"),
                new Tag("tag8")
        );
        Page<Tag> tagPage = new Page<>(
                pageToFind,
                tags.size(),
                totalPages,
                tags
        );

        //when
        when(tagDao.findPage(pageToFind)).thenReturn(tagPage);
        Page<TagDto> actualTagDtoPage = tagService.selectPageOfTags(pageToFind);

        //then
        assertEquals(pageToFind, actualTagDtoPage.getPageNumber());
        assertEquals(totalPages, actualTagDtoPage.getTotalPages());
        assertEquals(tags.size(), actualTagDtoPage.getPageSize());
    }

    @Test
    void whenSelectPageOfTagsSmallerThanOneShouldThrowWrongValueException() {
        //given
        String expectedExceptionMessage = takeExceptionMessagePageCannotBeSmallerThan();
        int page = 0;

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.selectPageOfTags(page);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenDeleteTagShouldBeRemoved() {
        //given
        Long idToRemove = takeId();

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(idToRemove, invocationOnMock.getArgument(0));
            return idToRemove;
        }).when(tagDao).removeById(idToRemove);

        tagService.deleteTag(idToRemove);

        //then
    }

    @Test
    void whenDeleteTagWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullIdToRemove = takeNullId();
        String expectedExceptionMessage = takeExceptionMessageIdCannotBeNull();

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.deleteTag(nullIdToRemove);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    private Long takeId(){
        return 1L;
    }

    private Long takeNullId(){
        return null;
    }

    private String takeNameToFind(){
        return "tag1";
    }

    private String takeNullName(){
        return null;
    }

    private String takeEmptyName(){
        return "";
    }

    private TagDto takeExpectedTagDto(){
        return new TagDto(takeId(), takeNameToFind());
    }

    private TagDto takeTagDtoToSave(){
        return new TagDto(takeNullId(), takeExpectedTagDto().getName());
    }

    private TagDto takeTagDtoWithoutName(){
        return new TagDto(takeNullId(), takeNullName());
    }

    private TagDto takeTagDtoWithEmptyName(){
        return new TagDto(takeNullId(),takeEmptyName());
    }

    private Tag takeInsertedTag(){
        TagDto tagDto = takeExpectedTagDto();
        return new Tag(tagDto.getId(), tagDto.getName());
    }

    private Tag takeTagToInsert(){
        return new Tag(takeInsertedTag().getName());
    }

    private String takeExceptionMessageNameCannotBeEmpty(){
        return "Field name cannot be empty";
    }

    private String takeExceptionMessageIdCannotBeNull() {
        return "Id cannot be null";
    }

    private String takeExceptionMessagePageCannotBeSmallerThan(){
        return "Page cannot be smaller than 1";
    }
}
