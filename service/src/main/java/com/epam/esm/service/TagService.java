package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.math.BigInteger;
import java.util.List;

public interface TagService {
    TagDto saveTag(TagDto tagDto);

    TagDto selectTagByName(String name);

    TagDto selectTagById(BigInteger id);

    List<TagDto> selectAllTags();

    void deleteTag(BigInteger id);
}
