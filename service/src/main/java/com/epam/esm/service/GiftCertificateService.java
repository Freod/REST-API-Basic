package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.Filters;

import java.math.BigInteger;
import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto selectGiftCertificate(BigInteger id);

    List<GiftCertificateDto> selectAllGiftCertificates(Filters filters);

    void updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificate(BigInteger id);
}
