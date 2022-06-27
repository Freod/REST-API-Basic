package com.epam.esm;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DaoException;
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
public class TagDaoTestsIT {

    @Autowired
    private TagDao tagDao;

    @Test
    @Order(1)
    public void testDaoSelectAll() {
        List<Tag> tagList = tagDao.selectAllTags();

        assertTrue(tagList.size() > 1);
        assertEquals(3, tagList.size());
    }

    @Test
    @Order(2)
    public void testDaoSelectById() {
        Tag tag = new Tag(BigInteger.ONE, "tag1");
        Tag dbTag = tagDao.selectTagById(tag.getId());

        assertEquals(BigInteger.ONE, dbTag.getId());
        assertEquals("tag1", dbTag.getName());
        assertEquals(tag, dbTag);

        DaoException thrown = assertThrows(
                DaoException.class,
                () -> tagDao.selectTagById(BigInteger.ZERO)
        );
        assertEquals("Resource not found (id = 0)", thrown.getMessage());
    }

    @Test
    @Order(3)
    public void testDaoSelectByName() {
        Tag tag = new Tag(BigInteger.ONE, "tag1");
        Tag dbTag = tagDao.selectTagByName(tag.getName());

        assertEquals(BigInteger.ONE, dbTag.getId());
        assertEquals("tag1", dbTag.getName());
        assertEquals(tag, dbTag);

        DaoException thrown = assertThrows(
                DaoException.class,
                () -> tagDao.selectTagByName("")
        );
        assertEquals("Resource not found (name = '')", thrown.getMessage());
    }

    @Test
    @Order(4)
    public void testDaoInsert() {
        List<Tag> tagListBefore = tagDao.selectAllTags();
        Tag tag = new Tag("testing");
        Tag insertedTag = tagDao.saveTag(tag);
        List<Tag> tagListAfter = tagDao.selectAllTags();

        assertNotEquals(tagListAfter, tagListBefore);
        assertEquals(tagListBefore.size() + 1, tagListAfter.size());
        assertEquals(tagListAfter.get(tagListAfter.size()-1), insertedTag);

        DaoException thrown = assertThrows(
                DaoException.class,
                () -> tagDao.saveTag(tag)
        );
        assertEquals("Resource name or primary key violation (name = 'testing')", thrown.getMessage());
    }

    @Test
    @Order(5)
    public void testDaoDelete() {
        Tag dbTag = tagDao.selectTagById(BigInteger.ONE);
        List<Tag> tagListBefore = tagDao.selectAllTags();
        tagDao.deleteTag(dbTag.getId());
        List<Tag> tagListAfter = tagDao.selectAllTags();

        assertNotEquals(tagListBefore, tagListAfter);
        assertEquals(tagListBefore.size()-1, tagListAfter.size());
    }
}
