package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GiftCertificateDaoTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    public void testDao(){
        System.out.println(giftCertificateDao);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("name2");
        giftCertificate.setDescription("description2");
        giftCertificate.setPrice(3.4);
        giftCertificate.setDuration(3);
        giftCertificate.addTag(new Tag("Hello2"));

        giftCertificateDao.saveCertificate(giftCertificate);
    }
}
