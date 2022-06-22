package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.Filters;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag selectTagByName(Tag tag) {
        return tagDao.selectTagByName(tag.getName());
    }

    @Override
    public Tag selectTagById(Tag tag) {
        return tagDao.selectTagById(tag.getId());
    }

    @Override
    public List<Tag> selectAllTags() {
        return tagDao.selectAllTags();
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        tagDao.deleteTag(tag.getId());
    }
}
