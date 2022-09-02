package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.exception.ResourceViolation;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate save(GiftCertificate giftCertificate) throws ResourceViolation;

    GiftCertificate findById(Long id) throws ResourceNotFound;

    List<GiftCertificate> findAllUsingFilter(Filter filter);

    GiftCertificate update(GiftCertificate giftCertificate) throws ResourceNotFound;

    void removeById(Long id);

    void addTagToGiftCertificate(Long giftCertificateId, Tag tag);

    void removeTagFromGiftCertificate(Long giftCertificateId, Tag tag);
}
