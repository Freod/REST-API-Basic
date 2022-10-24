package com.epam.esm.dao;

import com.epam.esm.config.Config;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@Sql(scripts = "classpath:/database/insert-gift-certificate-test-dml.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GiftCertificateDaoIT {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void whenSaveGiftCertificateShouldSaveAndReturnThisGiftCertificate() {
        //given
        GiftCertificate giftCertificateToSave = new GiftCertificate(
                null,
                "NewGiftCertificateTest",
                "TestDescription",
                34.59,
                7,
                Stream.of(
                        new Tag("tagtestgiftcert1"),
                        new Tag("bear")
                ).collect(Collectors.toCollection(HashSet::new))
        );

        //when
        GiftCertificate actualGiftCertificate = giftCertificateDao.save(giftCertificateToSave);

        //then
        assertNotNull(actualGiftCertificate.getId());
        assertEquals(giftCertificateToSave.getName(), actualGiftCertificate.getName());
        assertEquals(giftCertificateToSave.getDescription(), actualGiftCertificate.getDescription());
        assertEquals(giftCertificateToSave.getPrice(), actualGiftCertificate.getPrice());
        assertEquals(giftCertificateToSave.getDuration(), actualGiftCertificate.getDuration());
        assertEquals(giftCertificateToSave.getTags().size(), actualGiftCertificate.getTags().size());
    }

    @Test
    void whenFindGiftCertificateByIdShouldReturnThisGiftCertificate() {
        //given
        Long idToFind = 1L;

        //when
        GiftCertificate actualGiftCertificate = giftCertificateDao.findById(idToFind);

        //then
        assertEquals(idToFind, actualGiftCertificate.getId());
        assertNotNull(actualGiftCertificate.getName());
        assertNotNull(actualGiftCertificate.getDescription());
        assertNotNull(actualGiftCertificate.getPrice());
        assertNotNull(actualGiftCertificate.getDuration());
        assertNotNull(actualGiftCertificate.getTags());
    }

    @Test
    void whenFindGiftCertificateByIdWhichIsNotExistsShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedExceptionMessage = "GiftCertificate with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.findById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    // TODO: 24/10/2022
    @Test
    void whenFindGiftCertificatePageShouldReturnThisPage() {
        //given


        //when
//        Page<GiftCertificate> giftCertificatePage = giftCertificateDao.findPageUsingFilter();

        //then
//        assertEquals(pageNumber, actualPage.getPageNumber());
//        assertNotNull(actualPage.getPageSize());
//        assertNotNull(actualPage.getTotalPages());
//        assertNotNull(actualPage.getCollection());
        assertTrue(false);
    }

    @Test
    void whenUpdateGiftCertificateShouldReturnUpdatedGiftCertificate() {
        //given
        Long idToFind = 1L;
        GiftCertificate giftCertificate = giftCertificateDao.findById(idToFind);
        LocalDateTime notExpectedDate = giftCertificate.getLastUpdateDate();
        String name = "name";
        String description = "description";
        Integer duration = 4;
        Double price = 4.55;
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setDuration(duration);
        giftCertificate.setPrice(price);

        //when
        GiftCertificate actualGiftCertificate = giftCertificateDao.update(idToFind, giftCertificate);

        //then
        assertEquals(giftCertificate, actualGiftCertificate);
        assertEquals(name, actualGiftCertificate.getName());
        assertEquals(description, actualGiftCertificate.getDescription());
        assertEquals(duration, actualGiftCertificate.getDuration());
        assertEquals(price, actualGiftCertificate.getPrice());
        assertNotEquals(notExpectedDate, actualGiftCertificate.getLastUpdateDate());
    }

    @Test
    void whenUpdateGiftCertificateWhichIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long idToUpdate = 0L;
        String expectedExceptionMessage = "GiftCertificate with id = (" + idToUpdate + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.update(idToUpdate, new GiftCertificate());
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveGiftCertificateByIdShouldRemoveThisGiftCertificate() {
        //given
        Long idToRemove = 2L;
        String expectedExceptionMessage = "GiftCertificate with id = (" + idToRemove + ") isn't exists.";

        //when
        giftCertificateDao.removeById(idToRemove);
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.findById(idToRemove);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveByIdWhichNotExistsShouldThrowResourceNotFoundException() {
        //given
        Long idToFind = 0L;
        String expectedExceptionMessage = "GiftCertificate with id = (" + idToFind + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.removeById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveByIdWhichIsUsedInOrderShouldThrowResourceViolationException() {
        //given
        Long idToFind = 1L;
        String expectedExceptionMessage = "GiftCertificate cannot be removed. GiftCertificate is connected with at least one order.";

        //when
        ResourceViolationException thrown = assertThrows(ResourceViolationException.class, () -> {
            giftCertificateDao.removeById(idToFind);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenAddTagToGiftCertificateShouldReturnGiftCertificateWithThisTag() {
        //given
        Long id = 1L;
        Tag tag = new Tag("new");
        int increasedTagCollectionSize = 1;
        int expectedTagCollectionSize = giftCertificateDao.findById(id).getTags().size() + increasedTagCollectionSize;

        //when
        GiftCertificate actualGiftCertificate = giftCertificateDao.addTagToGiftCertificate(id, tag);

        //then
        assertEquals(id, actualGiftCertificate.getId());
        assertEquals(expectedTagCollectionSize, actualGiftCertificate.getTags().size());
    }

    @Test
    void whenAddTagToGiftCertificateWhichIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long id = 0L;
        Tag tag = new Tag("random");
        String expectedExceptionMessage = "GiftCertificate with id = (" + id + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.addTagToGiftCertificate(id, tag);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }

    @Test
    void whenRemoveTagFromGiftCertificateShouldReturnGiftCertificateWithoutThisTag() {
        //given
        Long id = 1L;
        Tag tagToRemove = new Tag("bear");
        int decreasedTagCollectionSize = 1;
        int expectedTagCollectionSize = giftCertificateDao.findById(id).getTags().size() - decreasedTagCollectionSize;

        //when
        GiftCertificate actualGiftCertificate = giftCertificateDao.removeTagFromGiftCertificate(id, tagToRemove);

        //then
        assertEquals(id, actualGiftCertificate.getId());
        assertEquals(expectedTagCollectionSize, actualGiftCertificate.getTags().size());
    }

    @Test
    void whenRemoveTagFromGiftCertificateWhichIsNotExistShouldThrowResourceNotFoundException() {
        //given
        Long id = 0L;
        Tag tag = new Tag("random");
        String expectedExceptionMessage = "GiftCertificate with id = (" + id + ") isn't exists.";

        //when
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            giftCertificateDao.removeTagFromGiftCertificate(id, tag);
        });

        //then
        assertEquals(expectedExceptionMessage, thrown.getMessage());
    }
}
