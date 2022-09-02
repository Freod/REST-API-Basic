package com.epam.esm.controller;

import com.epam.esm.dto.FilterDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Controller for processing REST-api requests for Gift Certificate resource.
 * <p>
 * Works with relative to application context '/giftCertificate'
 * </p>
 * Maps GET, POST, PATCH, DELETE requests.
 */
@RestController
@RequestMapping("/giftCertificate")
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
    public GiftCertificateDto showGiftCertificate(@PathVariable Long id) {
        return giftCertificateService.selectGiftCertificate(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> showGiftCertificateList(@RequestBody FilterDto filterDto) {
        return giftCertificateService.selectAllGiftCertificates(filterDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.saveGiftCertificate(giftCertificateDto);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTagToGiftCertificate(@PathVariable Long id, @RequestBody TagDto tagDto) {
        giftCertificateService.addTagToGiftCertificate(id, tagDto);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTagFromGiftCertificate(@PathVariable Long id, @RequestBody TagDto tagDto) {
        giftCertificateService.removeTagFromGiftCertificate(id, tagDto);
    }
}
