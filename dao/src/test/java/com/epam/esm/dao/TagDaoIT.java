package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJdbcConfig.class}, loader = AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
public class TagDaoIT {

    @Autowired
    private TagDao tagDao;

    // TODO: 06.07.2022 REMOVE ORDERING

    @Test
    @Order(1)
    void whenSelectAllTagsShouldReturnListTest() {
        //given
        int expectedListSize = 3;

        //when
        List<Tag> actualTagList = tagDao.selectAllTags();

        //then
        assertEquals(expectedListSize, actualTagList.size());
    }

    @Test
    @Order(2)
    void whenSelectTagByIdShouldReturnTagWithSameIdTest() {
        //given
        BigInteger resourceId = BigInteger.valueOf(1);
        Tag expectedTag = new Tag(resourceId, "tag1");

        //when
        Tag actualTag = tagDao.selectTagById(resourceId);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
    @Order(3)
    void whenSelectTagByIdIsNotExistShouldThrowResourceNotFoundException(){
        //given
        BigInteger resourceId = BigInteger.valueOf(0);
        String expectedMessage = "Tag not found (id = 0)";

        //when
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagById(resourceId)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }


    @Test
    @Order(4)
    void whenSelectTagByNameShouldReturnTagWithSameNameTest() {
        //given
        String resourceName = "tag1";
        Tag expectedTag = new Tag(BigInteger.valueOf(1), resourceName);

        //when
        Tag actualTag = tagDao.selectTagByName(resourceName);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
    @Order(5)
    void whenSelectTagByNameIsNotExistShouldThrowResourceNotFoundTest(){
        //given
        String resourceName = "tag444";
        String expectedMessage = "Tag not found (name = 'tag444')";

        //when
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagByName(resourceName)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @Order(6)
    public void whenInsertTagShouldReturnTagAndBeInDbTest() {
        //given
        Tag tagToInsert = new Tag("testing");
        int expectedListIncreased = 1;

        //when
        List<Tag> tagListBeforeInsert = tagDao.selectAllTags();
        Tag insertedTag = tagDao.saveTag(tagToInsert);
        List<Tag> tagListAfterInsert = tagDao.selectAllTags();

        //then
        assertEquals(tagListBeforeInsert.size() + expectedListIncreased, tagListAfterInsert.size());
        assertNotNull(insertedTag.getId());
    }

    @Test
    @Order(7)
    public void whenInsertTagWithDuplicatedNameShouldThrowResourceViolationTest() {
        //given
        Tag tagToInsert = new Tag("testing2");
        String expectedMessage = "Tag name or primary key violation (name = 'testing2')";

        //when
        tagDao.saveTag(tagToInsert);
        ResourceViolation thrown = assertThrows(
                ResourceViolation.class,
                () -> tagDao.saveTag(tagToInsert)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @Order(8)
    public void whenDeleteTagByIdShouldThatTagNotExistTest() {
        //given
        BigInteger resourceId = BigInteger.valueOf(1);
        String expectedMessage = "Tag not found (id = 1)";
        int expectedListDecreased = 1;

        //when
        List<Tag> tagListBeforeRemove = tagDao.selectAllTags();
        tagDao.deleteTag(resourceId);
        List<Tag> tagListAfterRemove = tagDao.selectAllTags();
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagById(resourceId)
        );

        //then
        assertEquals(tagListBeforeRemove.size() - expectedListDecreased, tagListAfterRemove.size());
        assertEquals(expectedMessage, thrown.getMessage());
    }
}
