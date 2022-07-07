package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Tag;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJdbcConfig.class}, loader = AnnotationConfigContextLoader.class)
class TagDaoIT {

    @Autowired
    private TagDao tagDao;

    @BeforeEach
    void initDatabaseAndInsertToDatabase(@Autowired DataSource dataSource) throws SQLException, FileNotFoundException {
        Reader initReader = new FileReader("src/test/resources/database/init-ddl.sql");
        Reader insertReader = new FileReader("src/test/resources/database/insert-dml.sql");

        RunScript.execute(dataSource.getConnection(), initReader);
        RunScript.execute(dataSource.getConnection(), insertReader);
    }

    @AfterEach
    void dropDatabase(@Autowired DataSource dataSource) throws FileNotFoundException, SQLException {
        Reader dropReader = new FileReader("src/test/resources/database/drop-ddl.sql");

        RunScript.execute(dataSource.getConnection(), dropReader);
    }

    @Test
    void whenSelectAllTagsShouldReturnListTest() {
        //given
        int expectedListSize = 3;

        //when
        List<Tag> actualTagList = tagDao.selectAllTags();

        //then
        assertEquals(expectedListSize, actualTagList.size());
    }

    @Test
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
        String resourceName = "tag1";
        Tag expectedTag = new Tag(BigInteger.valueOf(1), resourceName);

        //when
        Tag actualTag = tagDao.selectTagByName(resourceName);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
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
    void whenInsertTagShouldReturnTagAndBeInDbTest() {
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
    void whenInsertTagWithDuplicatedNameShouldThrowResourceViolationTest() {
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
    void whenDeleteTagByIdShouldThatTagNotExistTest() {
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
