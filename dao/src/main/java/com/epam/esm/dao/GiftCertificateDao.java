package com.epam.esm.dao;

import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;

public interface GiftCertificateDao {
    void save(GiftCertificate giftCertificate);

    GiftCertificate findById(Long id);

    List<GiftCertificate> findAllUsingFilter(Filter filter);

    void update(GiftCertificate giftCertificate);

    void removeById(Long id);

    void addTagToGiftCertificate(Long giftCertificateId, Tag tag);

    void removeTagFromGiftCertificate(Long giftCertificateId, Tag tag);
}
