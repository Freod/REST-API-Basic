package com.epam.esm;

import com.epam.esm.dao.TagDao;
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

    @Test
    void whenSelectAllTagsShouldReturnListTest() {
        //given
        int expectedListSize = 3;

        //when
        List<Tag> tagList = tagDao.selectAllTags();

        //then
        assertEquals(expectedListSize, tagList.size());
    }

    @Test
    void whenSelectTagByIdShouldReturnTagWithSameIdTest() {
        //given
        Tag tag = new Tag(BigInteger.valueOf(1), "tag1");

        //when
        Tag dbTag = tagDao.selectTagById(tag.getId());

        //then
        assertEquals(tag, dbTag);
    }

    @Test
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
    void whenSelectTagByNameShouldReturnTagWithSameNameTest() {
        //given
        Tag tag = new Tag(BigInteger.valueOf(1), "tag1");

        //when
        Tag dbTag = tagDao.selectTagByName(tag.getName());

        //then
        assertEquals(tag, dbTag);
    }

    @Test
    void whenSelectTagByNameIsNotExistShouldThrowResourceNotFoundTest(){
        //given
        String resourceName = "";
        String expectedMessage = "Tag not found (name = '')";

        //when
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagByName(resourceName)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void whenInsertTagShouldReturnTagAndBeInDbTest() {
        //given
        Tag tag = new Tag("testing");
        int expectedListIncreased = 1;

        //when
        List<Tag> tagListBeforeInsert = tagDao.selectAllTags();
        Tag insertedTag = tagDao.saveTag(tag);
        List<Tag> tagListAfterInsert = tagDao.selectAllTags();

        //then
        assertNotEquals(tagListBeforeInsert, tagListAfterInsert);
        assertEquals(tagListBeforeInsert.size() + expectedListIncreased, tagListAfterInsert.size());
        assertNotNull(insertedTag.getId());
        assertEquals(tag.getName(), insertedTag.getName());
    }

    @Test
    public void whenInsertTagWithDuplicatedNameShouldThrowResourceViolationTest() {
        //given
        Tag tag = new Tag("testing2");
        String expectedMessage = "Tag name or primary key violation (name = 'testing')";

        //when
        tagDao.saveTag(tag);
        ResourceViolation thrown = assertThrows(
                ResourceViolation.class,
                () -> tagDao.saveTag(tag)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    @Order(5)
    public void whenDeleteTagByIdShouldThatTagNotExistTest() {
        //given
        BigInteger resourceId = BigInteger.valueOf(1);
        String expectedMessage = "Tag not found (id = 1)";

        //when
        Tag dbTag = tagDao.selectTagById(resourceId);
        tagDao.deleteTag(resourceId);
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagById(resourceId)
        );

        //then
        assertNotEquals(resourceId, dbTag.getId());
        assertEquals(expectedMessage, thrown.getMessage());
    }
}
