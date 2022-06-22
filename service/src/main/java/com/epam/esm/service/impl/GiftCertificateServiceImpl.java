package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.Filters;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.saveCertificate(giftCertificate);
    }

    @Override
    public GiftCertificate selectGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.selectCertificateById(giftCertificate.getId());
    }

    @Override
    public List<GiftCertificate> selectAllGiftCertificates(Filters filters) {
        return giftCertificateDao.selectAllCertificates(filters);
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateDao.updateCertificate(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateDao.deleteCertificateById(giftCertificate.getId());
    }
}
