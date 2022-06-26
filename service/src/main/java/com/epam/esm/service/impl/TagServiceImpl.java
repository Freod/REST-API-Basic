package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public TagDto saveTag(TagDto tagDto) {
        return convertTagToTagDto(
                tagDao.saveTag(
                        convertTagDtoToTag(tagDto)
                )
        );
    }

    @Override
    public TagDto selectTagByName(String name) {
        return convertTagToTagDto(tagDao.selectTagByName(name));
    }

    @Override
    public TagDto selectTagById(BigInteger id) {
        return convertTagToTagDto(tagDao.selectTagById(id));
    }

    @Override
    public List<TagDto> selectAllTags() {
        return tagDao.selectAllTags().stream().map(this::convertTagToTagDto).collect(Collectors.toList());
    }

    @Override
    public void updateTag(BigInteger id, TagDto tagDto) {
        Tag tag = convertTagDtoToTag(tagDto);
        tag.setId(id);
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(BigInteger id) {
        tagDao.deleteTag(id);
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
}
