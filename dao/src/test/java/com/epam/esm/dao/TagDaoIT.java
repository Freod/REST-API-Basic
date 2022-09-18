package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.generation.Generate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class}, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagDaoIT {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private Generate generate;

    private int generatedSize = 20;

    // TODO: 31.08.2022 fix it, work too long
    @BeforeEach
    void beforeTests(){
        generate.insertTagsToDb(generatedSize);
    }

    // TODO: 31.08.2022 pagination
    @Test
    void whenSelectAllTagsShouldReturnListTest() {
        //given
        int expectedListSize = generatedSize;

        //when
        List<Tag> actualTagList = tagDao.findAll();

        //then
        assertEquals(expectedListSize, actualTagList.size());
    }

    @Test
    void whenSelectTagByIdShouldReturnTagWithSameIdTest() {
        //given
        Long resourceId = 1L;

        //when
        Tag actualTag = tagDao.findById(resourceId);

        //then
        assertEquals(resourceId, actualTag.getId());
        assertNotNull(actualTag.getName());
    }

    @Test
    void whenSelectTagByIdIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long resourceId = 0L;
        String expectedMessage = "Tag with id = (0) isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> tagDao.findById(resourceId)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }


    @Test
    void whenSelectTagByNameShouldReturnTagWithSameNameTest() {
        //given
        Long resourceId = 1L;
        Tag expectedTag = tagDao.findById(resourceId);
        String resourceName = expectedTag.getName();

        //when
        Tag actualTag = tagDao.findByName(resourceName);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void whenSelectTagByNameIsNotExistShouldThrowResourceNotFoundTest() {
        //given
        String resourceName = "tag404";
        String expectedMessage = "Tag with name = (tag404) isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> tagDao.findByName(resourceName)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenInsertTagShouldReturnTagAndBeInDbTest() {
        //given
        Tag tagToInsert = new Tag("testing");
        int expectedListIncreased = 1;

        //when
        List<Tag> tagListBeforeInsert = tagDao.findAll();
        Tag insertedTag = tagDao.save(tagToInsert);
        List<Tag> tagListAfterInsert = tagDao.findAll();

        //then
        assertEquals(tagListBeforeInsert.size() + expectedListIncreased, tagListAfterInsert.size());
        assertNotNull(insertedTag.getId());
    }

    @Test
    void whenInsertTagWithDuplicatedNameShouldThrowResourceViolationTest() {
        //given
        Tag tagToInsert = new Tag("tag");
        Tag tagToRepeatedInsert = new Tag("tag");
        String expectedMessage = "Tag with name=(tag) already exists.";

        //when
        tagDao.save(tagToInsert);
        ResourceViolationException thrown = assertThrows(
                ResourceViolationException.class,
                () -> tagDao.save(tagToRepeatedInsert)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenDeleteTagByIdShouldThatTagNotExistTest() {
        //given
        Long resourceId = 1L;
        String expectedMessage = "Tag with id = (1) isn't exists.";
        int expectedListDecreased = 1;

        //when
        List<Tag> tagListBeforeRemove = tagDao.findAll();
        tagDao.removeById(resourceId);
        List<Tag> tagListAfterRemove = tagDao.findAll();
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> tagDao.findById(resourceId)
        );

        //then
        assertEquals(tagListBeforeRemove.size() - expectedListDecreased, tagListAfterRemove.size());
        assertEquals(expectedMessage, thrown.getMessage());
    }
}
