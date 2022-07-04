package com.epam.esm.dao;

import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;

import java.math.BigInteger;
import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate saveCertificate(GiftCertificate giftCertificate);

    GiftCertificate selectCertificateById(BigInteger id);

    List<GiftCertificate> selectAllCertificates(Filter filter);

    void updateCertificate(GiftCertificate giftCertificate);

    void deleteCertificateById(BigInteger id);
}
