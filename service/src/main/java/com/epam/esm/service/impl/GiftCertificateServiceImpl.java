package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.Filters;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private GiftCertificateDao giftCertificateDao;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate = giftCertificateDao.saveCertificate(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto selectGiftCertificate(BigInteger id) {
        return modelMapper.map(giftCertificateDao.selectCertificateById(id), GiftCertificateDto.class);
    }

    @Override
    public List<GiftCertificateDto> selectAllGiftCertificates(Filters filters) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.selectAllCertificates(filters);
        return giftCertificates.stream().map(giftCertificate -> modelMapper.map(giftCertificates, GiftCertificateDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificateDao.updateCertificate(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(BigInteger id) {
        giftCertificateDao.deleteCertificateById(id);
    }
}
