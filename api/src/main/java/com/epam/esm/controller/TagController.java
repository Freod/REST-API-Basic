package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Controller for processing REST-api requests for Tag resource.
 * <p>
 * Works with relative to application context '/tag'
 * </p>
 * Maps GET, POST, DELETE requests.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    /**
     * Service for working with Tag resource.
     */
    private final TagService tagService;

    /**
     * Construct controller with injected Tag service.
     *
     * @param tagService {@link TagController#tagService}
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Gets Tag resource with requested id.
     * <p>Example JSON response: { "id": 1, "name": "tag1"}</p>
     *
     * @param id to find Tag resource
     * @return Tag resource that was found
     */
    @GetMapping("/show/id/{id}")
    public TagDto showTag(@PathVariable BigInteger id) {
        return tagService.selectTagById(id);
    }

    /**
     * Gets Tag resource with requested name.
     * <p>Example JSON response: { "id": 1, "name": "tag1"}</p>
     *
     * @param name to find Tag resource
     * @return Tag resource that was found
     */
    @GetMapping("/show/name/{name}")
    public TagDto showTag(@PathVariable String name) {
        return tagService.selectTagByName(name);
    }

    /**
     * Gets list of Tag resources.
     *
     * @return list of Tag resources that was found
     */
    @GetMapping("/showList")
    public List<TagDto> showTagList() {
        return tagService.selectAllTags();
    }

    /**
     * Creates new Tag resource.
     * <p>
     * Example JSON request: {
     *     "name": "tag7"
     * }
     * </p>
     * <p>Example JSON response:{
     *     "id": 1,
     *     "name": "tag1"
     * }
     * </p>
     *
     * @param tagDto Tag resource to create
     * @return Tag resource that was created
     */
    @PostMapping("/new")
    public TagDto addNewTag(@RequestBody TagDto tagDto) {
        return tagService.saveTag(tagDto);
    }

    /**
     * Removes Tag resource with requested id.
     *
     * @param id to remove Tag resource
     */
    @DeleteMapping("/delete/{id}")
    public void deleteTag(@PathVariable BigInteger id) {
        tagService.deleteTag(id);
    }
}
