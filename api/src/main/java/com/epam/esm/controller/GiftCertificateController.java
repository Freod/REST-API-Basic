package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/giftCertificate")
public class GiftCertificateController {
    @GetMapping("/show/{id}")
    public String showGiftCertificate(@PathVariable BigInteger id) {
        return id.toString();
    }

    @GetMapping("/showList")
    public String showGiftCertificateList() {
        return "list";
    }

    @PostMapping("/new")
    public GiftCertificateDto addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        System.out.println(giftCertificateDto);
        return giftCertificateDto;
    }

    @PutMapping("/update/{id}")
    public void updateGiftCertificate(@PathVariable BigInteger id, @RequestBody GiftCertificateDto giftCertificateDto) {
        System.out.println(id + " " + giftCertificateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGiftCertificate(@PathVariable BigInteger id) {
        System.out.println("Delete" + id);
    }
}
