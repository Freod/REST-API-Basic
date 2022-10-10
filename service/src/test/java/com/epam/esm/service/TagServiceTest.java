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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagDao tagDao;
    @Mock
    private ObjectConverter objectConverter;

    @InjectMocks
    private TagService tagService;

    private static TagDto expectedTagDto;
    private static Tag insertedTag;

    @BeforeAll
    public static void init() {
        insertedTag = new Tag(1L, "tag1");
        expectedTagDto = new TagDto(1L, "tag1");
    }

    @Test
    void whenSaveNewTagShouldReturnCreatedTag() {
        //given
        TagDto tagDtoToSave = new TagDto(null, insertedTag.getName());
        Tag tagToInsert = new Tag(insertedTag.getName());

        //when
        when(tagDao.save(tagToInsert)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.saveTag(tagDtoToSave);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSaveNewTagWithoutNameShouldThrowNullPointerException() {
        //given
        TagDto tagDtoToSave = new TagDto(null, null);
        String expectedExceptionMessage = "Field name cannot be empty";

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
        TagDto tagDtoToSave = new TagDto(null, "");
        String expectedExceptionMessage = "Field name cannot be empty";

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
        Long idToFind = insertedTag.getId();

        //when
        when(tagDao.findById(idToFind)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagById(idToFind);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutIdShouldThrowWrongValueException() {
        //given
        Long nullIdToFind = null;
        String expectedExceptionMessage = "Id cannot be null";

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
        String nameToFind = insertedTag.getName();

        //when
        when(tagDao.findByName(nameToFind)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagByName(nameToFind);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutNameShouldThrowWrongValueException() {
        //given
        String nullNameToFind = null;
        String emptyNameToFind = "";
        String expectedExceptionMessage = "Name cannot be null or empty";

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
        String expectedExceptionMessage = "Page cannot be smaller than 1";
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
        Long idToRemove = Long.valueOf(1);

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
        Long nullIdToRemove = null;
        String expectedExceptionMessage = "Id cannot be null";

        //when
        WrongValueException thrown = Assertions.assertThrows(WrongValueException.class, () -> {
            tagService.deleteTag(nullIdToRemove);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }
}
