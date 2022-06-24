package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private TagService tagService = new TagServiceImpl();

    @GetMapping("/show/id/{id}")
    public TagDto showTag(@PathVariable BigInteger id) {
        return tagService.selectTagById(id);
    }

    @GetMapping("/show/name/{name}")
    public TagDto showTag(@PathVariable String name) {
        return tagService.selectTagByName(name);
    }

    @GetMapping("/showList")
    public List<TagDto> showTagList() {
        return tagService.selectAllTags();
    }

    @PostMapping("/new")
    public TagDto addNewTag(@RequestBody TagDto tagDto) {
        return tagService.saveTag(tagDto);
    }

    @PutMapping("/update/{id}")
    public void updateTag(@PathVariable BigInteger id, @RequestBody TagDto tagDto) {
        tagService.updateTag(id, tagDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTag(@PathVariable BigInteger id) {
        System.out.println("Delete " + id);
        tagService.deleteTag(id);
    }
}
