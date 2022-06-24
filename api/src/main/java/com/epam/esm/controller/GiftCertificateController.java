package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.Filters;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/giftCertificate")
public class GiftCertificateController {
//    @Autowired
    private GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl();

    @GetMapping("/show/{id}")
    public GiftCertificateDto showGiftCertificate(@PathVariable BigInteger id) {
        return giftCertificateService.selectGiftCertificate(id);
    }

    @GetMapping("/showList")
    public List<GiftCertificateDto> showGiftCertificateList() {
        return giftCertificateService.selectAllGiftCertificates(new Filters());
    }

    @PostMapping("/new")
    public GiftCertificateDto addNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        System.out.println(giftCertificateDto);
        return giftCertificateDto;
    }

    @PutMapping("/update/{id}")
    public void updateGiftCertificate(@PathVariable BigInteger id, @RequestBody GiftCertificateDto giftCertificateDto) {
//        giftCertificateDto.setId(id);
        System.out.println(id + " " + giftCertificateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGiftCertificate(@PathVariable BigInteger id) {
        System.out.println("Delete " + id);
        giftCertificateService.deleteGiftCertificate(id);
    }
}
