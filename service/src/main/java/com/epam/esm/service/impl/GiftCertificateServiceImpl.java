package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
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

    // TODO: 04.07.2022 null tags
    @Override
    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setTags(giftCertificateDto.getTags().stream().collect(Collectors.toSet()).stream().sorted(Comparator.comparing(tagDto -> tagDto.getId())).collect(Collectors.toList()));
        return convertModelToDto(
                giftCertificateDao.saveCertificate(
                        convertDtoToModel(giftCertificateDto)
                )
        );
    }

    @Override
    public GiftCertificateDto selectGiftCertificate(BigInteger id) {
        return convertModelToDto(giftCertificateDao.selectCertificateById(id));
    }

    @Override
    public List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto) {
        return giftCertificateDao.selectAllCertificates(convertFiltersDtoToFilters(filterDto)).stream().map(this::convertModelToDto).collect(Collectors.toList());
    }

    // TODO: 04.07.2022 think
    @Override
    public void updateGiftCertificate(BigInteger id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = convertDtoToModel(giftCertificateDto);
        giftCertificate.setId(id);
        giftCertificateDao.updateCertificate(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(BigInteger id) {
        giftCertificateDao.deleteCertificateById(id);
    }

    private GiftCertificateDto convertModelToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate().toString());
        giftCertificateDto.setTags(giftCertificate.getTags().stream().map(this::convertTagToTagDto).collect(Collectors.toList()));
        return giftCertificateDto;
    }

    private GiftCertificate convertDtoToModel(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setTags(giftCertificateDto.getTags().stream().map(this::convertTagDtoToTag).collect(Collectors.toList()));
        return giftCertificate;
    }

    private Tag convertTagDtoToTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        return tag;
    }

    private TagDto convertTagToTagDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

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
