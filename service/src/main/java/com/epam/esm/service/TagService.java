package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
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
        return
                convertTagToTagDto(
                        tagDao.save(
                                convertTagDtoToTag(tagDto)
                        )
                );
    }

    public TagDto selectTagByNameOrId(TagDto tagDto) {
        if (tagDto.getId() != null) {
            return convertTagToTagDto(tagDao.findById(tagDto.getId()));
        } else if (tagDto.getName() != null) {
            return convertTagToTagDto(tagDao.findByName(tagDto.getName().toLowerCase(Locale.ROOT)));
        }
        throw new NullPointerException("id or name can't be null");
    }

    public Set<TagDto> selectAllTags() {
        return tagDao.findAll().stream().map(TagService::convertTagToTagDto).collect(Collectors.toSet());
    }

    public void deleteTag(Long id) {
        tagDao.removeById(id);
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
