package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTests {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    private static TagDto tagDto;
    private static Tag tag;

    @BeforeAll
    public static void init() {
        tag = new Tag(BigInteger.ONE, "tag1");

        tagDto = new TagDto();
        tagDto.setId(BigInteger.ONE);
        tagDto.setName("tag1");
    }

    @Test
    public void selectTagByIdTest(){
        when(tagDao.selectTagById(tag.getId())).thenReturn(tag);
        TagDto tagDtoServiceReturn = tagService.selectTagById(BigInteger.ONE);

        assertEquals(tagDto, tagDtoServiceReturn);
    }

    @Test
    public void selectTagByNameTest(){
        when(tagDao.selectTagByName(tag.getName())).thenReturn(tag);
        TagDto tagDtoServiceReturn = tagService.selectTagByName(tagDto.getName());

        assertEquals(tagDto, tagDtoServiceReturn);
    }

    @Test
    public void saveNewTagTest(){
        when(tagDao.saveTag(new Tag(tag.getName()))).thenReturn(tag);
        TagDto tagDtoServiceReturn = tagService.saveTag(tagDto);

        assertEquals(tagDto, tagDtoServiceReturn);
    }

    @Test
    public void selectAllTagsTest(){
        List<Tag> tagList = Arrays.asList(new Tag(BigInteger.ONE, "tag1"), new Tag(BigInteger.valueOf(2L), "tag2"));
        List<TagDto> tagDtoList = new ArrayList<>();
        TagDto tagDto1 = new TagDto();
        tagDto1.setId(BigInteger.ONE);
        tagDto1.setName("tag1");
        TagDto tagDto2 = new TagDto();
        tagDto2.setId(BigInteger.valueOf(2L));
        tagDto2.setName("tag2");
        tagDtoList.add(tagDto1);
        tagDtoList.add(tagDto2);
        when(tagDao.selectAllTags()).thenReturn(tagList);

        List<TagDto> tagDtoListServiceReturn = tagService.selectAllTags();
        assertEquals(tagDtoList, tagDtoListServiceReturn);
    }

    @Test
    public void deleteTagTest(){
        tagService.deleteTag(BigInteger.ONE);
    }
}
