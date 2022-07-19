package com.epam.esm.dao;

import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate saveCertificate(GiftCertificate giftCertificate);

    GiftCertificate selectCertificateById(BigInteger id);

    List<GiftCertificate> selectAllCertificates(Filter filter);

    void updateCertificate(GiftCertificate giftCertificate);

    void addTagToGiftCertificate(BigInteger giftCertificateId, Tag tag);

    void removeTagFromGiftCertificate(BigInteger giftCertificateId, Tag tag);

    void deleteCertificateById(BigInteger id);
}
