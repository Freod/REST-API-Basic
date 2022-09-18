package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Page;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Controller for processing REST-api requests for Tag resource.
 * <p>
 * Works with relative to application context '/tags'
 * </p>
 * Maps GET, POST, DELETE requests.
 */
@RestController
@RequestMapping("/tags")
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

    @GetMapping(params = "id")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> showTagById(@RequestParam Long id) throws NoSuchMethodException {
        TagDto tagDto = tagService.selectTagById(id);
        addSelectTagDtoLinks(tagDto);
        return EntityModel.of(tagDto);
    }

    @GetMapping(params = "name")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> showTagByName(@RequestParam String name) {
        TagDto tagDto = tagService.selectTagByName(name);
        addSelectTagDtoLinks(tagDto);
        return EntityModel.of(tagDto);
    }

    /**
     * Gets page of Tags resources.
     *
     * @return page of Tags resources that was found
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> showPageOfTags(@RequestParam(defaultValue = "1") Integer page) {
        Page<TagDto> tagDtoPage = tagService.selectPageOfTags(page);
        Collection<TagDto> tagDtoSet =
                tagDtoPage.getCollection()
                        .stream()
                        .map(TagController::addSelectTagDtoLinks)
                        .collect(Collectors.toList());

        List<Link> linkList = new LinkedList<>();
        try {
            if (page > 1 && tagDtoPage.getTotalPages() > 0) {
                linkList.add(linkTo(
                        TagController.class
                                .getMethod("showPageOfTags", Integer.class), 1)
                        .withRel("firstPage"));
            }

            if (page > 2 && page <= tagDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        TagController.class
                                .getMethod("showPageOfTags", Integer.class), page - 1)
                        .withRel("previousPage"));
            }

            if (page <= tagDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        TagController.class
                                .getMethod("showPageOfTags", Integer.class), page)
                        .withSelfRel());
            }

            if (page + 1 < tagDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        TagController.class
                                .getMethod("showPageOfTags", Integer.class), page + 1)
                        .withRel("nextPage"));
            }

            if (tagDtoPage.getTotalPages() > 0 && page != tagDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        TagController.class
                                .getMethod("showPageOfTags", Integer.class), tagDtoPage.getTotalPages())
                        .withRel("lastPage"));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return CollectionModel.of(tagDtoSet, linkList);
    }

    /**
     * Creates new Tag resource.
     * <p>
     * Example JSON request: {
     * "name": "tag7"
     * }
     * </p>
     * <p>Example JSON response:{
     * "id": 1,
     * "name": "tag1"
     * }
     * </p>
     *
     * @param tagDto Tag resource to create
     * @return Tag resource that was created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> addNewTag(@RequestBody TagDto tagDto) {
        tagDto = tagService.saveTag(tagDto);
        addSelectTagDtoLinks(tagDto);
        return EntityModel.of(tagDto);
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

    protected static TagDto addSelectTagDtoLinks(TagDto tagDto) {
        try {
            Link linkById =
                    linkTo(
                            TagController.class
                                    .getMethod("showTagById", Long.class), tagDto.getId())
                            .withSelfRel();
            Link linkByName =
                    linkTo(
                            TagController.class
                                    .getMethod("showTagByName", String.class), tagDto.getName())
                            .withSelfRel();
            tagDto.add(linkById, linkByName);
            return tagDto;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
