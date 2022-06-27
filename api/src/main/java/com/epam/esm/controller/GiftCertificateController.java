package com.epam.esm.controller;

import com.epam.esm.dto.FiltersDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Gets Gift Certificate resource by requested id.
     *
     * @param id to find Gift Certificate resource
     * @return Gift Certificate resource that was found
     */
    @ResponseBody
    @GetMapping("/show/{id}")
    public GiftCertificateDto showGiftCertificate(@PathVariable BigInteger id) {
        return giftCertificateService.selectGiftCertificate(id);
    }

    /**
     * Gets list of Gift Certificate resources.
     *
     * @param filtersDto
     * @return list of all giftCertificates
     */
    //todo filters
    @GetMapping("/showList")
    public List<GiftCertificateDto> showGiftCertificateList(@RequestBody FiltersDto filtersDto) {
        return giftCertificateService.selectAllGiftCertificates(filtersDto);
    }

    /**
     * Creates new Gift Certificate resource
     * <p>Example JSON request:{
     *     "name": "Name1",
     *     "description": "Description",
     *     "price": 30.4,
     *     "duration": 5,
     *     "tags": [
     *         {
     *             "name": "tag1"
     *         },
     *         {
     *             "name": "tag2"
     *         }
     *     ]
     * }</p>
     * <p>Example JSON response:{
     *     "id": 6,
     *     "name": "Name1",
     *     "description": "Description",
     *     "price": 30.4,
     *     "duration": 5,
     *     "createDate": "2022-06-27T21:05:34.618",
     *     "lastUpdateDate": "2022-06-27T21:05:34.619",
     *     "tags": [
     *         {
     *             "id": 12,
     *             "name": "tag2"
     *         },
     *         {
     *             "id": 6,
     *             "name": "tag1"
     *         }
     *     ]
     * }</p>
     *
     * @param giftCertificateDto Gift Certificate resource to create
     * @return Gift Certificate resource that was created
     */
    @PostMapping("/new")
    public GiftCertificateDto addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.saveGiftCertificate(giftCertificateDto);
    }

    /**
     *
     * @param id
     * @param giftCertificateDto
     */
    // TODO: 27.06.2022  
    @PatchMapping("/update/{id}")
    public void updateGiftCertificate(@PathVariable BigInteger id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
    }

    /**
     * Removes Gift Certificate resource with requested id.
     *
     * @param id to remove Gift Certificate resource
     */
    @DeleteMapping("/delete/{id}")
    public void deleteGiftCertificate(@PathVariable BigInteger id) {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
