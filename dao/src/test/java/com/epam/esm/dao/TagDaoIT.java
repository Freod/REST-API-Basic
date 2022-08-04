package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class}, loader = AnnotationConfigContextLoader.class)
//@ContextConfiguration(value = {"classpath:/it-test.xml"})
class TagDaoIT {
//
//    @Autowired
//    private EntityManager em;

    @Autowired
    private TagDao tagDao;

//    @BeforeEach
//    void initDatabaseAndInsertToDatabase(@Autowired DataSource dataSource) throws SQLException, FileNotFoundException {
//        Reader initReader = new FileReader("src/test/resources/database/init-ddl.sql");
//        Reader insertReader = new FileReader("src/test/resources/database/insert-dml.sql");
//
//        RunScript.execute(dataSource.getConnection(), initReader);
//        RunScript.execute(dataSource.getConnection(), insertReader);
//    }
//
//    @AfterEach
//    void dropDatabase(@Autowired DataSource dataSource) throws FileNotFoundException, SQLException {
//        Reader dropReader = new FileReader("src/test/resources/database/drop-ddl.sql");
//
//        RunScript.execute(dataSource.getConnection(), dropReader);
//    }
//     void init() {
//        em.getTransaction().begin();
//        em.createNativeQuery("INSERT INTO tags(name) VALUES('tag1');\n" +
//                "INSERT INTO tags(name) VALUES('tag2');\n" +
//                "INSERT INTO tags(name) VALUES('tag3');").executeUpdate();
//        em.getTransaction().commit();
//    }

//    @AfterEach
//    void drop(){
//        em.getTransaction().begin();
//        em.createNativeQuery("DROP TABLE gift_certificates CASCADE;\n" +
//                "DROP TABLE gift_certificates_tags CASCADE;\n" +
//                "DROP TABLE tags CASCADE;").executeUpdate();
//        em.getTransaction().commit();
//    }

    @Test
    void whenSelectAllTagsShouldReturnListTest() {
        //given
        int expectedListSize = 3;

        //when
        List<Tag> actualTagList = tagDao.findAll();

        //then
        assertEquals(expectedListSize, actualTagList.size());
    }

    @Test
    void whenSelectTagByIdShouldReturnTagWithSameIdTest() {
        //given
        Long resourceId = 1L;
        Tag expectedTag = new Tag(resourceId, "tag1");

        //when
        Tag actualTag = tagDao.findById(resourceId);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void whenSelectTagByIdIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long resourceId = 0L;
        String expectedMessage = "Tag not found (id = 0)";

        //when
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.findById(resourceId)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }


    @Test
    void whenSelectTagByNameShouldReturnTagWithSameNameTest() {
        //given
        String resourceName = "tag1";
        Tag expectedTag = new Tag(1L, resourceName);

        //when
        Tag actualTag = tagDao.findByName(resourceName);

        //then
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void whenSelectTagByNameIsNotExistShouldThrowResourceNotFoundTest() {
        //given
        String resourceName = "tag444";
        String expectedMessage = "Tag not found (name = 'tag444')";

        //when
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
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
        // TODO: 29.07.2022
//        Tag insertedTag =
        tagDao.save(tagToInsert);
        List<Tag> tagListAfterInsert = tagDao.findAll();

        //then
        assertEquals(tagListBeforeInsert.size() + expectedListIncreased, tagListAfterInsert.size());
//        assertNotNull(insertedTag.getId());
    }

    @Test
    void whenInsertTagWithDuplicatedNameShouldThrowResourceViolationTest() {
        //given
        Tag tagToInsert = new Tag("testing2");
        String expectedMessage = "Tag name or primary key violation (name = 'testing2')";

        //when
        tagDao.save(tagToInsert);
        ResourceViolation thrown = assertThrows(
                ResourceViolation.class,
                () -> tagDao.save(tagToInsert)
        );

        //then
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void whenDeleteTagByIdShouldThatTagNotExistTest() {
        //given
        Long resourceId = 1L;
        String expectedMessage = "Tag not found (id = 1)";
        int expectedListDecreased = 1;

        //when
        List<Tag> tagListBeforeRemove = tagDao.findAll();
        tagDao.removeById(resourceId);
        List<Tag> tagListAfterRemove = tagDao.findAll();
        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> tagDao.findById(resourceId)
        );

        //then
        assertEquals(tagListBeforeRemove.size() - expectedListDecreased, tagListAfterRemove.size());
        assertEquals(expectedMessage, thrown.getMessage());
    }
}
