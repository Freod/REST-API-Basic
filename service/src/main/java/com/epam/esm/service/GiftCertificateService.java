package com.epam.esm.service;

import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;

import java.math.BigInteger;
import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto selectGiftCertificate(BigInteger id);

    List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto);

    void updateGiftCertificate(BigInteger id, GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificate(BigInteger id);
}
