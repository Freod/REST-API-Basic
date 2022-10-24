package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@Sql(scripts = "classpath:/database/insert-tags-test-dml.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagDaoIT {

    @Autowired
    private TagDao tagDao;

    @Test
    void whenSaveTagShouldBeSaved() {
        //given
        Tag tag = new Tag("newtesttag");

        //when
        Tag actualTag = tagDao.save(tag);

        //then
        assertEquals(tag.getName(), actualTag.getName());
        assertNotNull(actualTag.getId());
    }

    @Test
    void whenSaveTagWhichExistsShouldThrowResourceViolationException() {
        //given
        Tag tag = new Tag("bear");
        String expectedExceptionMessage = "Tag with name = (" + tag.getName() + ") already exists.";

        //when
        ResourceViolationException thrown = assertThrows(ResourceViolationException.class, () -> {
            tagDao.save(tag);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenFindTagByIdShouldReturnTagWithThisId() {
        //given
        Long idToFind = 1L;

        //when
        Tag actualTag = tagDao.findById(idToFind);

        //then
        assertEquals(idToFind, actualTag.getId());
        assertNotNull(actualTag.getName());
    }

    @Test
    void whenFindTagByIdWhichIsNotExistsShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedExceptionMessage = "Tag with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            tagDao.findById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenFindTagByNameShouldReturnTagWithThisName() {
        //given
        String name = "bear";

        //when
        Tag actualTag = tagDao.findByName(name);

        //then
        assertEquals(name, actualTag.getName());
        assertNotNull(actualTag.getId());
    }

    @Test
    void whenFindTagByNameWhichNotExistsShouldThrowResourceNotFoundException() {
        //given
        String nameToFind = "tagnothere";
        String expectedExceptionMessage = "Tag with name = (" + nameToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            tagDao.findByName(nameToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenFindPageShouldReturnThisPage() {
        //given
        int pageNumber = 1;

        //when
        Page<Tag> actualPage = tagDao.findPage(pageNumber);

        //then
        assertEquals(pageNumber, actualPage.getPageNumber());
        assertNotNull(actualPage.getPageSize());
        assertNotNull(actualPage.getTotalPages());
        assertNotNull(actualPage.getCollection());
    }

    @Test
    void whenRemoveTagByIdShouldRemoveThisTag() {
        //given
        Long idToRemove = 5L;
        String expectedExceptionMessage = "Tag with id = (" + idToRemove + ") isn't exists.";

        //when
        tagDao.removeById(idToRemove);
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            tagDao.findById(idToRemove);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveByIdWhichNotExistsShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedExceptionMessage = "Tag with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            tagDao.removeById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveByIdWhichIsUsedInGiftCertificateShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 1L;
        String expectedExceptionMessage = "Tag cannot be removed. Tag is connected with at least one certificate.";

        //when
        ResourceViolationException thrown = assertThrows(ResourceViolationException.class, () -> {
            tagDao.removeById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }
}
