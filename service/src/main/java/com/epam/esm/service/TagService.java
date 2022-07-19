package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = Objects.requireNonNull(tagDao);
    }

    public TagDto saveTag(TagDto tagDto) {
        Objects.requireNonNull(tagDto.getName());
        return convertTagToTagDto(
                tagDao.saveTag(
                        convertTagDtoToTag(tagDto)
                )
        );
    }

    public TagDto selectTagByNameOrId(TagDto tagDto) {
        if (tagDto.getId() != null) {
            return convertTagToTagDto(tagDao.selectTagById(tagDto.getId()));
        } else if (tagDto.getName() != null) {
            return convertTagToTagDto(tagDao.selectTagByName(tagDto.getName()));
        }
        throw new NullPointerException("id or name can't be null");
    }

    public List<TagDto> selectAllTags() {
        return tagDao.selectAllTags().stream().map(TagService::convertTagToTagDto).collect(Collectors.toList());
    }

    public void deleteTag(BigInteger id) {
        tagDao.deleteTag(id);
    }

    static TagDto convertTagToTagDto(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    static Tag convertTagDtoToTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName().toLowerCase(Locale.ROOT));
        return tag;
    }
}
