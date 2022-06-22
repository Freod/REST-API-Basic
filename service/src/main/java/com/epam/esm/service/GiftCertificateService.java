package com.epam.esm.service;

import com.epam.esm.model.Filters;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate selectGiftCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> selectAllGiftCertificates(Filters filters);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(GiftCertificate giftCertificate);
}
