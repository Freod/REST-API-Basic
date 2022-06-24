package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private TagDao tagDao = new TagDaoImpl();

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public TagDto saveTag(TagDto tagDto) {
        //TODO:MAPPER PROBLEM
        Tag tag = modelMapper.map(tagDto, Tag.class);
        System.out.println(tag);
        tag = tagDao.saveTag(tag);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public TagDto selectTagByName(String name) {
        Tag tag = tagDao.selectTagByName(name);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public TagDto selectTagById(BigInteger id) {
        Tag tag = tagDao.selectTagById(id);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public List<TagDto> selectAllTags() {
        List<Tag> tags = tagDao.selectAllTags();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateTag(BigInteger id, TagDto tagDto) {
        //TODO:MAPPER PROBLEM
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tag.setId(id);
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(BigInteger id) {
        tagDao.deleteTag(id);
    }
}
