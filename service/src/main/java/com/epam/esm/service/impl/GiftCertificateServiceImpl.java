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
        System.out.println(giftCertificateDto);
        // TODO: 04.07.2022  
        System.out.println("dee");
//        if (giftCertificateDto.getTags().size() > 0) {
//            giftCertificateDto.setTags(giftCertificateDto.getTags().stream().collect(Collectors.toSet()).stream().sorted(Comparator.comparing(tagDto -> tagDto.getId())).collect(Collectors.toList()));
//        }
        System.out.println("dewww");
        return convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.saveCertificate(
                        convertGiftCertificateDtoToGiftCertificate(giftCertificateDto)
                )
        );
    }

    @Override
    public GiftCertificateDto selectGiftCertificate(BigInteger id) {
        return convertGiftCertificateToGiftCertificateDto(giftCertificateDao.selectCertificateById(id));
    }

    @Override
    // TODO: 04.07.2022
    public List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto) {
        return giftCertificateDao.selectAllCertificates(convertFiltersDtoToFilters(filterDto)).stream().map(this::convertGiftCertificateToGiftCertificateDto).collect(Collectors.toList());
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
        giftCertificate.setTags(giftCertificateDto.getTags().stream().map(TagServiceImpl::convertTagDtoToTag).collect(Collectors.toList()));
        return giftCertificate;
    }

    private GiftCertificateDto convertGiftCertificateToGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate().toString());
        giftCertificateDto.setTags(giftCertificate.getTags().stream().map(TagServiceImpl::convertTagToTagDto).collect(Collectors.toList()));
        return giftCertificateDto;
    }

    // TODO: 04.07.2022
    private Filter convertFiltersDtoToFilters(FilterDto filterDto) {
        Filter filter = new Filter();
        if (filterDto.getTag() != null)
            filter.setTag(filterDto.getTag());
        if (filterDto.getName() != null)
            filter.setName(filterDto.getName());
        if (filterDto.getDirection() != null)
            filter.setDescription(filterDto.getDescription());
        if (filterDto.getDirection() != null)
            filter.setDirection(filterDto.getDirection());
        if (filterDto.getOrderBy() != null)
            filter.setOrderBy(filterDto.getOrderBy());
        return filter;
    }
}
