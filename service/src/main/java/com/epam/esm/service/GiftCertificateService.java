package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = Objects.requireNonNull(giftCertificateDao);
    }

    public GiftCertificateDto saveGiftCertificate(GiftCertificateDto giftCertificateDto) {
        checkNullValuesGiftCertificate(giftCertificateDto, "save");

        GiftCertificate giftCertificate = convertGiftCertificateDtoToGiftCertificate(giftCertificateDto);

        giftCertificate.setTags(giftCertificate.getTags().stream().distinct().collect(Collectors.toList()));

        return convertGiftCertificateToGiftCertificateDto(giftCertificateDao.saveCertificate(giftCertificate));
    }

    public GiftCertificateDto selectGiftCertificate(BigInteger id) {
        return convertGiftCertificateToGiftCertificateDto(giftCertificateDao.selectCertificateById(id));
    }

    public List<GiftCertificateDto> selectAllGiftCertificates(FilterDto filterDto) {
        List<GiftCertificate> giftCertificateList
                = giftCertificateDao.selectAllCertificates(convertFiltersDtoToFilters(filterDto));

        if (filterDto.getOrderBy() != null) {
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

            if (filterDto.getDirection() != null && filterDto.getDirection().equals("desc")) {
                comparator = comparator.reversed();
            }

            giftCertificateList.sort(comparator);
        }

        return giftCertificateList
                .stream()
                .map(this::convertGiftCertificateToGiftCertificateDto)
                .collect(Collectors.toList());
    }

    public void updateGiftCertificate(BigInteger id, GiftCertificateDto giftCertificateDto) {
        checkNullValuesGiftCertificate(giftCertificateDto, "update");
        GiftCertificate giftCertificate = convertGiftCertificateDtoToGiftCertificate(giftCertificateDto);
        giftCertificate.setId(id);
        giftCertificateDao.updateCertificate(giftCertificate);
    }

    public void addTagToGiftCertificate(BigInteger giftCertificateId, TagDto tagDto) {
        checkTagNullNameValue(tagDto, "add");
        giftCertificateDao.addTagToGiftCertificate(giftCertificateId, TagService.convertTagDtoToTag(tagDto));
    }

    public void removeTagFromGiftCertificate(BigInteger giftCertificateId, TagDto tagDto) {
        checkTagNullNameValue(tagDto, "remove");
        giftCertificateDao.removeTagFromGiftCertificate(giftCertificateId, TagService.convertTagDtoToTag(tagDto));
    }

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
                        .map(TagService::convertTagDtoToTag)
                        .collect(Collectors.toList()));

        return giftCertificate;
    }

    private GiftCertificateDto convertGiftCertificateToGiftCertificateDto(GiftCertificate giftCertificate) {
        return new GiftCertificateDto(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate().toString(),
                giftCertificate.getLastUpdateDate().toString(),
                giftCertificate.getTags()
                        .stream()
                        .map(TagService::convertTagToTagDto)
                        .collect(Collectors.toList()));
    }

    private Filter convertFiltersDtoToFilters(FilterDto filterDto) {
        return new Filter(
                filterDto.getTag(),
                filterDto.getName(),
                filterDto.getDescription());
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
