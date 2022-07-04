package com.epam.esm.service;

import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.math.BigInteger;
import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto selectGiftCertificate(BigInteger id);

    List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto);

    void updateGiftCertificate(BigInteger id, GiftCertificateDto giftCertificateDto);

    void addTagToGiftCertificate(BigInteger GiftCertificateId, TagDto tagDto);

    void removeTagFromGiftCertificate(BigInteger GiftCertificateId, TagDto tagDto);

    void deleteGiftCertificate(BigInteger id);
}
