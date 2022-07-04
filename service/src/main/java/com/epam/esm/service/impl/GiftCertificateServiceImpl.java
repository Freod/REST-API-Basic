package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = Objects.requireNonNull(giftCertificateDao);
    }

    @Override
    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = convertGiftCertificateDtoToGiftCertificate(giftCertificateDto);

        giftCertificate.setTags(giftCertificate.getTags().stream().collect(Collectors.toSet()).stream().collect(Collectors.toList()));

        return convertGiftCertificateToGiftCertificateDto(giftCertificateDao.saveCertificate(giftCertificate));
    }

    @Override
    public GiftCertificateDto selectGiftCertificate(BigInteger id) {
        return convertGiftCertificateToGiftCertificateDto(giftCertificateDao.selectCertificateById(id));
    }

    @Override
    public List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto) {
        List<GiftCertificate> giftCertificateList
                = giftCertificateDao.selectAllCertificates(convertFiltersDtoToFilters(filterDto));

        Comparator comparator;
        switch (filterDto.getOrderBy()) {
            case "lastUpdateDate":
                comparator = Comparator.comparing(GiftCertificate::getLastUpdateDate);
                break;
            case "createDate":
                comparator = Comparator.comparing(GiftCertificate::getCreateDate);
                break;
            default:
                comparator = Comparator.comparing(GiftCertificate::getName);
        }

        if (filterDto.getDirection().equals("desc")) {
            comparator = comparator.reversed();
        }

        giftCertificateList.sort(comparator);

        return giftCertificateList
                .stream()
                .map(this::convertGiftCertificateToGiftCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateGiftCertificate(BigInteger id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = convertGiftCertificateDtoToGiftCertificate(giftCertificateDto);
        giftCertificate.setId(id);
        giftCertificateDao.updateCertificate(giftCertificate);
    }

    @Override
    public void addTagToGiftCertificate(BigInteger GiftCertificateId, TagDto tagDto) {
        giftCertificateDao.addTagToGiftCertificate(GiftCertificateId, TagServiceImpl.convertTagDtoToTag(tagDto));
    }

    @Override
    public void removeTagFromGiftCertificate(BigInteger GiftCertificateId, TagDto tagDto) {
        giftCertificateDao.removeTagFromGiftCertificate(GiftCertificateId, TagServiceImpl.convertTagDtoToTag(tagDto));
    }

    @Override
    public void deleteGiftCertificate(BigInteger id) {
        giftCertificateDao.deleteCertificateById(id);
    }

    private GiftCertificate convertGiftCertificateDtoToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setTags(
                giftCertificateDto.getTags()
                        .stream()
                        .map(TagServiceImpl::convertTagDtoToTag)
                        .collect(Collectors.toList()));

        return giftCertificate;
    }

    private GiftCertificateDto convertGiftCertificateToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate().toString(),
                giftCertificate.getLastUpdateDate().toString(),
                giftCertificate.getTags()
                        .stream()
                        .map(TagServiceImpl::convertTagToTagDto)
                        .collect(Collectors.toList()));

        return giftCertificateDto;
    }

    private Filter convertFiltersDtoToFilters(FilterDto filterDto) {
        return new Filter(
                filterDto.getTag(),
                filterDto.getName(),
                filterDto.getDescription());
    }
}
