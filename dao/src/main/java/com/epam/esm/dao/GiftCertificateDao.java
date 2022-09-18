package com.epam.esm.dao;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceViolationException;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;

public interface GiftCertificateDao {
    GiftCertificate save(GiftCertificate giftCertificate) throws ResourceViolationException;

    GiftCertificate findById(Long id) throws ResourceNotFoundException;

    Page<GiftCertificate> findPageUsingFilter(Integer page, Filter filter);

    GiftCertificate update(Long id, GiftCertificate giftCertificate) throws ResourceNotFoundException;

    void removeById(Long id);

    GiftCertificate addTagToGiftCertificate(Long giftCertificateId, Tag tag);

    GiftCertificate removeTagFromGiftCertificate(Long giftCertificateId, Tag tag);
}
