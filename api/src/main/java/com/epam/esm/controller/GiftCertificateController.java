package com.epam.esm.controller;

import com.epam.esm.dto.FiltersDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/giftCertificate")
public class GiftCertificateController {
//    @Autowired
    private GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl();;

    @GetMapping("/show/{id}")
    public GiftCertificateDto showGiftCertificate(@PathVariable BigInteger id) {
        return giftCertificateService.selectGiftCertificate(id);
    }

    @GetMapping("/showList")
    public List<GiftCertificateDto> showGiftCertificateList() {
        return giftCertificateService.selectAllGiftCertificates(new FiltersDto());
    }

    @PostMapping("/showList")
    public List<GiftCertificateDto> showGiftCertificateList(@RequestBody FiltersDto filtersDto) {
        return giftCertificateService.selectAllGiftCertificates(filtersDto);
    }

    @PostMapping("/new")
    public GiftCertificateDto addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.saveGiftCertificate(giftCertificateDto);
    }

    @PutMapping("/update/{id}")
    public void updateGiftCertificate(@PathVariable BigInteger id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGiftCertificate(@PathVariable BigInteger id) {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
