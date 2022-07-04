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

// TODO: 04.07.2022 test
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJdbcConfig.class}, loader = AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
public class TagDaoIT {

    @Autowired
    private TagDao tagDao;

    @Test
    @Order(1)
    public void testDaoTagSelectAll() {
        List<Tag> tagList = tagDao.selectAllTags();

        assertTrue(tagList.size() > 1);
        assertEquals(3, tagList.size());
    }

    @Test
    @Order(2)
    public void testDaoTagSelectById() {
        Tag tag = new Tag(BigInteger.ONE, "tag1");
        Tag dbTag = tagDao.selectTagById(tag.getId());

        assertEquals(BigInteger.ONE, dbTag.getId());
        assertEquals("tag1", dbTag.getName());
        assertEquals(tag, dbTag);

        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagById(BigInteger.ZERO)
        );
        assertEquals("Tag not found (id = 0)", thrown.getMessage());
    }

    @Test
    @Order(3)
    public void testDaoTagSelectByName() {
        Tag tag = new Tag(BigInteger.ONE, "tag1");
        Tag dbTag = tagDao.selectTagByName(tag.getName());

        assertEquals(BigInteger.ONE, dbTag.getId());
        assertEquals("tag1", dbTag.getName());
        assertEquals(tag, dbTag);

        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.selectTagByName("")
        );
        assertEquals("Tag not found (name = '')", thrown.getMessage());
    }

    @Test
    @Order(4)
    public void testDaoTagInsert() {
        List<Tag> tagListBefore = tagDao.selectAllTags();
        Tag tag = new Tag("testing");
        Tag insertedTag = tagDao.saveTag(tag);
        List<Tag> tagListAfter = tagDao.selectAllTags();

        assertNotEquals(tagListAfter, tagListBefore);
        assertEquals(tagListBefore.size() + 1, tagListAfter.size());
        assertEquals(tagListAfter.get(tagListAfter.size()-1), insertedTag);

        ResourceViolation thrown = assertThrows(
                ResourceViolation.class,
                () -> tagDao.saveTag(tag)
        );
        assertEquals("Tag name or primary key violation (name = 'testing')", thrown.getMessage());
    }

    @Test
    @Order(5)
    public void testDaoTagDelete() {
        Tag dbTag = tagDao.selectTagById(BigInteger.ONE);
        List<Tag> tagListBefore = tagDao.selectAllTags();
        tagDao.deleteTag(dbTag.getId());
        List<Tag> tagListAfter = tagDao.selectAllTags();

        assertNotEquals(tagListBefore, tagListAfter);
        assertEquals(tagListBefore.size()-1, tagListAfter.size());
    }
}
