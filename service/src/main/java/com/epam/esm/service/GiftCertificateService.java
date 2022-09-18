package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final ObjectConverter objectConverter;

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao, ObjectConverter objectConverter) {
        this.giftCertificateDao = Objects.requireNonNull(giftCertificateDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
    }

    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        checkNullValuesGiftCertificate(giftCertificateDto, "save");

        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.save(
                        objectConverter.convertGiftCertificateDtoToGiftCertificate(
                                giftCertificateDto)));
    }

    public GiftCertificateDto selectGiftCertificate(Long id) {
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.findById(id));
    }

    public Page<GiftCertificateDto> selectPageOfGiftCertificates(Integer page, FilterDto filterDto) {
        if (page < 1) throw new WrongPageException("Page cannot be smaller by 1");
        Page<GiftCertificate> giftCertificatePage = giftCertificateDao.findPageUsingFilter(page, objectConverter.convertFiltersDtoToFilters(filterDto));
        return new Page<>(
                giftCertificatePage.getPageNumber(),
                giftCertificatePage.getPageSize(),
                giftCertificatePage.getTotalPages(),
                giftCertificatePage.getCollection()
                        .stream()
                        .map(objectConverter::convertGiftCertificateToGiftCertificateDto)
                        .collect(Collectors.toList()));
    }

    public GiftCertificateDto updateGiftCertificate(Long id, GiftCertificateDto giftCertificateDto) {
        checkNullValuesGiftCertificate(giftCertificateDto, "update");
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.update(
                        id,
                        objectConverter.convertGiftCertificateDtoToGiftCertificate(
                                giftCertificateDto)));
    }

    public GiftCertificateDto addTagToGiftCertificate(Long giftCertificateId, TagDto tagDto) {
        checkTagNullNameValue(tagDto, "add");
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.addTagToGiftCertificate(
                        giftCertificateId, objectConverter.convertTagDtoToTag(tagDto)));
    }

    public GiftCertificateDto removeTagFromGiftCertificate(Long giftCertificateId, TagDto tagDto) {
        checkTagNullNameValue(tagDto, "remove");
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.removeTagFromGiftCertificate(
                        giftCertificateId, objectConverter.convertTagDtoToTag(tagDto)));
    }

    public void deleteGiftCertificate(Long id) {
        giftCertificateDao.removeById(id);
    }

    private void checkNullValuesGiftCertificate(GiftCertificateDto giftCertificateDto, String methodName) {
        if (giftCertificateDto.getName() == null
                || giftCertificateDto.getDescription() == null
                || giftCertificateDto.getDuration() == null
                || giftCertificateDto.getPrice() == null) {
            if (methodName.equals("save")) {
                throw new NullPointerException("cannot save giftCertificate with null values");
            }
            throw new NullPointerException("cannot update giftCertificate with null values");
        }
    }

    private void checkTagNullNameValue(TagDto tagDto, String methodName) {
        if (tagDto.getName() == null) {
            if (methodName.equals("add")) {
                throw new NullPointerException("cannot add tag with null values");
            }
            throw new NullPointerException("cannot remove tag with null values");
        }
    }
}
