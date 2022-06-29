package com.epam.esm.controller;

import com.epam.esm.dto.FiltersDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/giftCertificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/show/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto showGiftCertificate(@PathVariable BigInteger id) {
        return giftCertificateService.selectGiftCertificate(id);
    }

    @GetMapping("/showList")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> showGiftCertificateList() {
        return giftCertificateService.selectAllGiftCertificates(new FiltersDto());
    }

    @PostMapping("/showList")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> showGiftCertificateList(@RequestBody FiltersDto filtersDto) {
        return giftCertificateService.selectAllGiftCertificates(filtersDto);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.saveGiftCertificate(giftCertificateDto);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@PathVariable BigInteger id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable BigInteger id) {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
