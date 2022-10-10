package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.WrongValueException;
import com.epam.esm.model.Page;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagDao tagDao;
    private final ObjectConverter objectConverter;

    @Autowired
    public TagService(TagDao tagDao, ObjectConverter objectConverter) {
        this.tagDao = Objects.requireNonNull(tagDao);
        this.objectConverter = Objects.requireNonNull(objectConverter);
    }

    public TagDto saveTag(TagDto tagDto) {
        if (tagDto.getName() == null || tagDto.getName().isEmpty()) {
            throw new WrongValueException("Field name cannot be empty");
        }

        return objectConverter.convertTagToTagDto(
                tagDao.save(
                        objectConverter.convertTagDtoToTag(tagDto)));
    }

    public TagDto selectTagById(Long id) {
        if (id == null) {
            throw new WrongValueException("Id cannot be null");
        }

        return objectConverter
                .convertTagToTagDto(
                        tagDao.findById(id));
    }

    public TagDto selectTagByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new WrongValueException("Name cannot be null or empty");
        }
        return objectConverter
                .convertTagToTagDto(
                        tagDao.findByName(
                                name.toLowerCase(Locale.ROOT)));

    }

    public Page<TagDto> selectPageOfTags(Integer page) {
        if (page < 1) throw new WrongValueException("Page cannot be smaller than 1");
        Page<Tag> tagPage = tagDao.findPage(page);
        return new Page<>(
                tagPage.getPageNumber(),
                tagPage.getPageSize(),
                tagPage.getTotalPages(),
                tagPage.getCollection()
                        .stream()
                        .map(objectConverter::convertTagToTagDto)
                        .collect(Collectors.toList()));
    }

    public void deleteTag(Long id) {
        if (id == null) {
            throw new WrongValueException("Id cannot be null");
        }

        tagDao.removeById(id);
    }
}
