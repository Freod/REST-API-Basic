package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    private static TagDto expectedTagDto;
    private static Tag insertedTag;

    @BeforeAll
    public static void init() {
        insertedTag = new Tag(BigInteger.valueOf(1), "tag1");
        expectedTagDto = new TagDto(BigInteger.valueOf(1), "tag1");
    }

    @Test
    void whenSaveNewTagShouldReturnCreatedTag() {
        //given
        TagDto tagDtoToSave = new TagDto(null, "tag1");
        Tag tagToInsert = new Tag(insertedTag.getName());

        //when
        when(tagDao.saveTag(tagToInsert)).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.saveTag(tagDtoToSave);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSaveNewTagWithoutNameShouldThrowNullPointerException() {
        //given
        TagDto tagDtoToSave = new TagDto();

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            tagService.saveTag(tagDtoToSave);
        });

        //then
        assertNull(thrown.getMessage());
    }

    @Test
    void whenSelectTagByIdShouldReturnTag() {
        //given
        TagDto tagDtoToSave = new TagDto(BigInteger.valueOf(1), null);

        //when
        when(tagDao.selectTagById(tagDtoToSave.getId())).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagByNameOrId(tagDtoToSave);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagByNameShouldReturnTag() {
        //given
        TagDto tagDtoToSave = new TagDto(null, "tag1");

        //when
        when(tagDao.selectTagByName(tagDtoToSave.getName())).thenReturn(insertedTag);
        TagDto actualTagDto = tagService.selectTagByNameOrId(tagDtoToSave);

        //then
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutNameAndIdShouldThrowNullPointerException(){
        //given
        TagDto tagDtoToSave = new TagDto();
        String expectedExceptionMessage = "id or name can't be null";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            tagService.selectTagByNameOrId(tagDtoToSave);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }


    @Test
    void whenSelectAllTagsShouldReturnTagList() {
        //given
        List<Tag> returnedTagList = Arrays.asList(
                new Tag(BigInteger.valueOf(1), "tag1"),
                new Tag(BigInteger.valueOf(2), "tag2")
        );
        List<TagDto> expectedTagDtoList = Arrays.asList(
                new TagDto(BigInteger.valueOf(1), "tag1"),
                new TagDto(BigInteger.valueOf(2), "tag2")
        );

        //when
        when(tagDao.selectAllTags()).thenReturn(returnedTagList);
        List<TagDto> actualTagDtoList = tagService.selectAllTags();

        //then
        assertEquals(expectedTagDtoList, actualTagDtoList);
    }

    @Test
    void whenDeleteTagShouldReturnNothing() {
        //given
        BigInteger idToRemove = BigInteger.valueOf(1);

        //when
        doAnswer(invocationOnMock -> {
            assertEquals(idToRemove, invocationOnMock.getArgument(0));
            return idToRemove;
        }).when(tagDao).deleteTag(idToRemove);

        tagService.deleteTag(idToRemove);

        //then
    }
}
