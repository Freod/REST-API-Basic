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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    private static TagDto tagDto;
    private static Tag tag;

    @BeforeAll
    public static void init() {
        tag = new Tag(BigInteger.valueOf(1), "tag1");
        tagDto = new TagDto(BigInteger.valueOf(1), "tag1");
    }

    @Test
    void whenSaveNewTagShouldReturnCreatedTagTest() {
        //given
        TagDto tagDtoWithName = new TagDto(null, "tag1");
        Tag expectedTag = new Tag(tag.getName());

        //when
        when(tagDao.saveTag(expectedTag)).thenReturn(tag);
        TagDto tagDtoServiceReturn = tagService.saveTag(tagDtoWithName);

        //then
        assertEquals(tagDto, tagDtoServiceReturn);
    }

    @Test
    void whenSaveNewTagWithoutNameShouldThrowNullPointerExceptionTest() {
        //given
        TagDto tagDtoWithoutNameAndId = new TagDto();

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            tagService.saveTag(tagDtoWithoutNameAndId);
        });

        //then
        assertNull(thrown.getMessage());
    }

    @Test
    void whenSelectTagByIdShouldReturnTagTest() {
        //given
        TagDto tagDtoWithId = new TagDto(BigInteger.valueOf(1), null);

        //when
        when(tagDao.selectTagById(tagDtoWithId.getId())).thenReturn(tag);
        TagDto actualTagDto = tagService.selectTagByNameOrId(tagDto);

        //then
        assertEquals(tagDto, actualTagDto);
    }

    @Test
    void whenSelectTagByNameShouldReturnTagTest() {
        //given
        TagDto tagDtoWithName = new TagDto(null, "tag1");

        //when
        when(tagDao.selectTagByName(tagDtoWithName.getName())).thenReturn(tag);
        TagDto actualTagDto = tagService.selectTagByNameOrId(tagDtoWithName);

        //then
        assertEquals(tagDto, actualTagDto);
    }

    @Test
    void whenSelectTagWithoutNameAndIdShouldThrowNullPointerExceptionTest(){
        //given
        TagDto nullTagDto = new TagDto();
        String expectedExceptionMessage = "id or name can't be null";

        //when
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            tagService.selectTagByNameOrId(nullTagDto);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }


    @Test
    void whenSelectAllTagsShouldReturnTagListTest() {
        //given
        List<Tag> tagList = Arrays.asList(
                new Tag(BigInteger.valueOf(1), "tag1"),
                new Tag(BigInteger.valueOf(2), "tag2")
        );
        List<TagDto> expectedTagDtoList = Arrays.asList(
                new TagDto(BigInteger.valueOf(1), "tag1"),
                new TagDto(BigInteger.valueOf(2), "tag2")
        );

        //when
        when(tagDao.selectAllTags()).thenReturn(tagList);
        List<TagDto> actualTagDtoList = tagService.selectAllTags();

        //then
        assertEquals(expectedTagDtoList, actualTagDtoList);
    }

    @Test
    void whenDeleteTagShouldReturnNothingTest() {
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
