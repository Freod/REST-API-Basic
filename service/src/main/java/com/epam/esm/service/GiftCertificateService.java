package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongPageException;
import com.epam.esm.exception.WrongValueException;
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
    private static final String EXCEPTION_MESSAGE_ID_CANNOT_BE_NULL = "Id cannot be null";

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao, ObjectConverter objectConverter) {
        this.giftCertificateDao = Objects.requireNonNull(giftCertificateDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
    }

    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        checkNullValuesGiftCertificate(giftCertificateDto);

        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.save(
                        objectConverter.convertGiftCertificateDtoToGiftCertificate(
                                giftCertificateDto)));
    }

    public GiftCertificateDto selectGiftCertificate(Long id) {
        if (id == null) {
            throw new WrongValueException(EXCEPTION_MESSAGE_ID_CANNOT_BE_NULL);
        }
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.findById(id));
    }

    public Page<GiftCertificateDto> selectPageOfGiftCertificates(Integer page, FilterDto filterDto) {
        if (page < 1) throw new WrongPageException("Page cannot be smaller than 1");
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
        if (id == null) {
            throw new WrongValueException(EXCEPTION_MESSAGE_ID_CANNOT_BE_NULL);
        }
        checkNullValuesGiftCertificate(giftCertificateDto);
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.update(
                        id,
                        objectConverter.convertGiftCertificateDtoToGiftCertificate(
                                giftCertificateDto)));
    }

    public GiftCertificateDto addTagToGiftCertificate(Long giftCertificateId, TagDto tagDto) {
        if (giftCertificateId == null) {
            throw new WrongValueException(EXCEPTION_MESSAGE_ID_CANNOT_BE_NULL);
        }
        checkTagNullNameValue(tagDto);
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.addTagToGiftCertificate(
                        giftCertificateId, objectConverter.convertTagDtoToTag(tagDto)));
    }

    public GiftCertificateDto removeTagFromGiftCertificate(Long giftCertificateId, TagDto tagDto) {
        if (giftCertificateId == null) {
            throw new WrongValueException(EXCEPTION_MESSAGE_ID_CANNOT_BE_NULL);
        }
        checkTagNullNameValue(tagDto);
        return objectConverter.convertGiftCertificateToGiftCertificateDto(
                giftCertificateDao.removeTagFromGiftCertificate(
                        giftCertificateId, objectConverter.convertTagDtoToTag(tagDto)));
    }

    public void deleteGiftCertificate(Long id) {
        giftCertificateDao.removeById(id);
    }

    private void checkNullValuesGiftCertificate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() == null
                || giftCertificateDto.getDescription() == null
                || giftCertificateDto.getDuration() == null
                || giftCertificateDto.getPrice() == null) {
            throw new WrongValueException("GiftCertificate fields cannot be null or empty");
        }
    }

    private void checkTagNullNameValue(TagDto tagDto) {
        if (tagDto.getName() == null) {
            throw new WrongValueException("Tag fields cannot be null or empty");
        }
    }
}
