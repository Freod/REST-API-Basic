package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

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
        this.tagService = Objects.requireNonNull(tagService);
    }

    /**
     * Gets Tag resource with requested name or id.
     * <p>Example JSON request: {"name": "tag7"}</p>
     * <p>Example JSON response: { "id": 1, "name": "tag1"}</p>
     *
     * @param tagDto to find Tag resource
     * @return Tag resource that was found
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDto showTag(@RequestBody TagDto tagDto) {
        return tagService.selectTagByNameOrId(tagDto);
    }

    /**
     * Gets list of Tag resources.
     *
     * @return list of Tag resources that was found
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Set<TagDto> showAllTags() {
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewTag(@RequestBody TagDto tagDto) {
        tagService.saveTag(tagDto);
    }

    /**
     * Removes Tag resource with requested id.
     *
     * @param id to remove Tag resource
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
