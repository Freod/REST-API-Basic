package com.epam.esm.controller;

import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Page;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// TODO: 05/10/2022 javadocs
/**
 * Controller for processing REST-api requests for Gift Certificate resource.
 * <p>
 * Works with relative to application context '/giftCertificate'
 * </p>
 * Maps GET, POST, PATCH, DELETE requests.
 */
@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    /**
     * Service for working with Gift Certificate resource.
     */
    private final GiftCertificateService giftCertificateService;

    /**
     * Construct controller with injected Gift Certificate service.
     *
     * @param giftCertificateService {@link GiftCertificateController#giftCertificateService}
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = Objects.requireNonNull(giftCertificateService);
    }

    /**
     * Gets Gift Certificate resource by requested id.
     *
     * @param id to find Gift Certificate resource
     * @return Gift Certificate resource that was found
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> showGiftCertificate(@PathVariable Long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.selectGiftCertificate(id);
        addSelectGiftCertificateLink(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> showPageOfGiftCertificates(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") Set<String> tags) {
        FilterDto filterDto = new FilterDto(
                tags.stream().map(s -> new TagDto(null, s)).collect(Collectors.toSet()),
                name, description, orderBy, direction);
        Page<GiftCertificateDto> giftCertificateDtoPage = giftCertificateService.selectPageOfGiftCertificates(page, filterDto);
        Collection<GiftCertificateDto> giftCertificateDtoCollection =
                giftCertificateDtoPage.getCollection()
                        .stream()
                        .map(GiftCertificateController::addSelectGiftCertificateLink)
                        .collect(Collectors.toList());

        List<Link> linkList = new LinkedList<>();
        try {
            if (page > 1 && giftCertificateDtoPage.getTotalPages() > 0) {
                linkList.add(linkTo(
                        GiftCertificateController.class
                                .getMethod("showPageOfGiftCertificates",
                                        Integer.class, String.class, String.class,
                                        String.class, String.class, Set.class),
                        1,
                        filterDto.getOrderBy(),
                        filterDto.getDirection(),
                        filterDto.getName(),
                        filterDto.getDescription(),
                        filterDto.getTags().stream().map(tagDto -> tagDto.getName()).collect(Collectors.toList()))
                        .withRel("firstPage"));
            }

            if (page > 2 && page <= giftCertificateDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        GiftCertificateController.class
                                .getMethod("showPageOfGiftCertificates",
                                        Integer.class, String.class, String.class,
                                        String.class, String.class, Set.class),
                        page - 1,
                        filterDto.getOrderBy(),
                        filterDto.getDirection(),
                        filterDto.getName(),
                        filterDto.getDescription(),
                        filterDto.getTags().stream().map(tagDto -> tagDto.getName()).collect(Collectors.toList()))
                        .withRel("previousPage"));
            }

            if (page <= giftCertificateDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        GiftCertificateController.class
                                .getMethod("showPageOfGiftCertificates",
                                        Integer.class, String.class, String.class,
                                        String.class, String.class, Set.class),
                        page,
                        filterDto.getOrderBy(),
                        filterDto.getDirection(),
                        filterDto.getName(),
                        filterDto.getDescription(),
                        filterDto.getTags().stream().map(tagDto -> tagDto.getName()).collect(Collectors.toList()))
                        .withSelfRel());
            }

            if (page + 1 < giftCertificateDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        GiftCertificateController.class
                                .getMethod("showPageOfGiftCertificates",
                                        Integer.class, String.class, String.class,
                                        String.class, String.class, Set.class),
                        page + 1,
                        filterDto.getOrderBy(),
                        filterDto.getDirection(),
                        filterDto.getName(),
                        filterDto.getDescription(),
                        filterDto.getTags().stream().map(tagDto -> tagDto.getName()).collect(Collectors.toList()))
                        .withRel("nextPage"));
            }

            if (giftCertificateDtoPage.getTotalPages() > 0 && page != giftCertificateDtoPage.getTotalPages()) {
                linkList.add(linkTo(
                        GiftCertificateController.class
                                .getMethod("showPageOfGiftCertificates",
                                        Integer.class, String.class, String.class,
                                        String.class, String.class, Set.class),
                        giftCertificateDtoPage.getTotalPages(),
                        filterDto.getOrderBy(),
                        filterDto.getDirection(),
                        filterDto.getName(),
                        filterDto.getDescription(),
                        filterDto.getTags().stream().map(tagDto -> tagDto.getName()).collect(Collectors.toList()))
                        .withRel("lastPage"));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return CollectionModel.of(giftCertificateDtoCollection, linkList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificateDto> addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto = giftCertificateService.saveGiftCertificate(giftCertificateDto);
        addSelectGiftCertificateLink(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    /**
     * Updates Gift Certificate resource by id.
     * <p>Example JSON request:{
     * "name": "Na3me1",
     * "description": "Descript3ion"
     * }</p>
     *
     * @param id                 to find Gift Certificate resource
     * @param giftCertificateDto Gift Certificate resource to update
     */
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> updateGiftCertificate(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto = giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
        addSelectGiftCertificateLink(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    /**
     * Removes Gift Certificate resource with requested id.
     *
     * @param id to remove Gift Certificate resource
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable Long id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

    /**
     * Adds Tag resource to Gift Certificate by id.
     * <p>Example JSON request:{
     * "name": "tag3"
     * }</p>
     *
     * @param id     to find Gift Certificate resource
     * @param tagDto Tag resource to add
     */
    @PutMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> addTagToGiftCertificate(@PathVariable Long id, @RequestBody TagDto tagDto) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.addTagToGiftCertificate(id, tagDto);
        addSelectGiftCertificateLink(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    /**
     * Removes Tag resource from Gift Certificate by id.
     * <p>Example JSON request:{
     * "name": "tag3"
     * }</p>
     *
     * @param id
     * @param tagDto
     */
    @DeleteMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificateDto> removeTagFromGiftCertificate(@PathVariable Long id, @RequestBody TagDto tagDto) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.removeTagFromGiftCertificate(id, tagDto);
        addSelectGiftCertificateLink(giftCertificateDto);
        return EntityModel.of(giftCertificateDto);
    }

    protected static GiftCertificateDto addSelectGiftCertificateLink(GiftCertificateDto giftCertificateDto) {
        try {
            giftCertificateDto.getTags().stream().map(TagController::addSelectTagDtoLinks).collect(Collectors.toList());
            Link linkById =
                    linkTo(
                            GiftCertificateController.class
                                    .getMethod("showGiftCertificate", Long.class), giftCertificateDto.getId())
                            .withSelfRel();
            giftCertificateDto.add(linkById);
            return giftCertificateDto;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
