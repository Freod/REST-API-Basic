package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.MethodOrderer;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiftCertificateDaoIT {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    @Order(1)
    public void testDaoGiftCertificateSelectAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.selectAllCertificates(new Filter());

        assertTrue(giftCertificateList.size() > 1);
        assertEquals(3, giftCertificateList.size());
    }

    @Test
    @Order(2)
    public void testDaoGiftCertificateSelectById() {
        GiftCertificate giftCertificate = giftCertificateDao.selectCertificateById(BigInteger.ONE);

        assertEquals(BigInteger.ONE, giftCertificate.getId());
        assertEquals("Name1", giftCertificate.getName());
        assertEquals("Description1", giftCertificate.getDescription());
        assertEquals(3.5, giftCertificate.getPrice());
        assertEquals(5, giftCertificate.getDuration());
        assertEquals("2022-06-26T12:04:01.891", giftCertificate.getLastUpdateDate().toString());
        assertEquals("2022-06-26T12:04:01.891", giftCertificate.getCreateDate().toString());

        ResourceNotFound thrown = assertThrows(
                ResourceNotFound.class,
                () -> giftCertificateDao.selectCertificateById(BigInteger.ZERO)
        );
        assertEquals("Certificate not found (id = 0)", thrown.getMessage());
    }

    @Test
    @Order(3)
    public void testDaoGiftCertificateInsert() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("TestingName");
        giftCertificate.setDescription("TestingDescription");
        giftCertificate.setPrice(4.5);
        giftCertificate.setDuration(6);
        giftCertificate.addTag(new Tag("tag1"));
        giftCertificate.addTag(new Tag("tag4"));

        List<GiftCertificate> giftCertificatesBefore = giftCertificateDao.selectAllCertificates(new Filter());
        GiftCertificate giftCertificateDb = giftCertificateDao.saveCertificate(giftCertificate);
        List<GiftCertificate> giftCertificatesAfter = giftCertificateDao.selectAllCertificates(new Filter());

        assertEquals(giftCertificatesBefore.size() + 1, giftCertificatesAfter.size());
        assertEquals(giftCertificate.getName(), giftCertificateDb.getName());
        assertEquals(giftCertificate.getDescription(), giftCertificateDb.getDescription());
        assertEquals(giftCertificate.getPrice(), giftCertificateDb.getPrice());
        assertEquals(giftCertificate.getDuration(), giftCertificateDb.getDuration());
        assertNotNull(giftCertificateDb.getId());
        assertEquals(giftCertificate.getTags().size(), giftCertificateDb.getTags().size());

        GiftCertificate giftCertificateSelect = giftCertificateDao.selectCertificateById(giftCertificateDb.getId());

        assertEquals(giftCertificateDb.getId(), giftCertificateSelect.getId());
        assertEquals(giftCertificateDb.getName(), giftCertificateSelect.getName());
        assertEquals(giftCertificateDb.getDescription(), giftCertificateSelect.getDescription());
        assertEquals(giftCertificateDb.getPrice(), giftCertificateSelect.getPrice());
        assertEquals(giftCertificateDb.getDuration(), giftCertificateSelect.getDuration());
        assertEquals(giftCertificateDb.getTags(), giftCertificateSelect.getTags());
    }

    @Test
    @Order(4)
    public void testDaoGiftCertificateUpdate() {
        GiftCertificate giftCertificate = giftCertificateDao.selectCertificateById(BigInteger.ONE);
        giftCertificate.setName("changedName");
        giftCertificate.setDescription("changedDescription");
        giftCertificate.setPrice(2.0);
        giftCertificate.setDuration(2);
        giftCertificate.addTag(new Tag("tag5"));

        giftCertificateDao.updateCertificate(giftCertificate);
        GiftCertificate giftCertificateChangedDb = giftCertificateDao.selectCertificateById(giftCertificate.getId());

        assertEquals(giftCertificate.getId(), giftCertificateChangedDb.getId());
        assertEquals(giftCertificate.getName(), giftCertificateChangedDb.getName());
        assertEquals(giftCertificate.getDescription(), giftCertificateChangedDb.getDescription());
        assertEquals(giftCertificate.getPrice(), giftCertificateChangedDb.getPrice());
        assertEquals(giftCertificate.getDuration(), giftCertificateChangedDb.getDuration());
        assertEquals(giftCertificate.getTags(), giftCertificateChangedDb.getTags());
        assertEquals(giftCertificate.getCreateDate(), giftCertificateChangedDb.getCreateDate());
        assertNotEquals(giftCertificate.getLastUpdateDate(), giftCertificateChangedDb.getLastUpdateDate());
    }

    @Test
    @Order(5)
    public void testDaoGiftCertificateDelete() {
        List<GiftCertificate> giftCertificateListBefore = giftCertificateDao.selectAllCertificates(new Filter());
        giftCertificateDao.deleteCertificateById(BigInteger.ONE);
        List<GiftCertificate> giftCertificateListAfter = giftCertificateDao.selectAllCertificates(new Filter());

        assertNotEquals(giftCertificateListBefore, giftCertificateListAfter);
        assertEquals(giftCertificateListBefore.size() - 1, giftCertificateListAfter.size());
    }
}
