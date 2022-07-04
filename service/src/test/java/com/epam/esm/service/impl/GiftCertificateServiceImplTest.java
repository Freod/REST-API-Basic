//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.GiftCertificateDao;
//import com.epam.esm.dto.FilterDto;
//import com.epam.esm.dto.GiftCertificateDto;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.model.Filter;
//import com.epam.esm.model.GiftCertificate;
//import com.epam.esm.model.Tag;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//// TODO: 04.07.2022 test
//@ExtendWith(MockitoExtension.class)
//public class GiftCertificateServiceImplTest {
//
//    @Mock
//    private GiftCertificateDao giftCertificateDao;
//
//    @InjectMocks
//    private GiftCertificateServiceImpl giftCertificateService;
//
//    private static GiftCertificate giftCertificate = new GiftCertificate();
//    private static GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
//
//    @BeforeAll
//    public static void init() {
//        giftCertificate.setId(BigInteger.ONE);
//        giftCertificate.setName("name");
//        giftCertificate.setDescription("description");
//        giftCertificate.setPrice(3.2);
//        giftCertificate.setDuration(6);
//        giftCertificate.setTags(Arrays.asList(
//                new Tag(BigInteger.ONE, "tag1"),
//                new Tag(BigInteger.valueOf(2L), "tag2")
//        ));
//
//        giftCertificateDto.setId(giftCertificate.getId());
//        giftCertificateDto.setName(giftCertificate.getName());
//        giftCertificateDto.setDescription(giftCertificate.getDescription());
//        giftCertificateDto.setPrice(giftCertificate.getPrice());
//        giftCertificateDto.setDuration(giftCertificate.getDuration());
//        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate().toString());
//        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
//        List<TagDto> tagDtoList = new ArrayList<>();
//        TagDto tagDto1 = new TagDto();
//        tagDto1.setId(BigInteger.ONE);
//        tagDto1.setName("tag1");
//        TagDto tagDto2 = new TagDto();
//        tagDto2.setId(BigInteger.valueOf(2L));
//        tagDto2.setName("tag2");
//        tagDtoList.add(tagDto1);
//        tagDtoList.add(tagDto2);
//        giftCertificateDto.setTags(tagDtoList);
//
//    }
//
//    @Test
//    public void selectGiftCertificateByIdTest() {
//        when(giftCertificateDao.selectCertificateById(BigInteger.ONE)).thenReturn(giftCertificate);
//        GiftCertificateDto giftCertificateDtoServiceReturn = giftCertificateService.selectGiftCertificate(BigInteger.ONE);
//
//        assertEquals(giftCertificateDto, giftCertificateDtoServiceReturn);
//    }
//
//    @Test
//    public void selectAllGiftCertificatesTest() {
//        when(giftCertificateDao.selectAllCertificates(new Filter())).thenReturn(Arrays.asList(giftCertificate));
//        List<GiftCertificateDto> giftCertificateDtoListReturnService= giftCertificateService.selectAllGiftCertificates(new FilterDto());
//        assertEquals(Arrays.asList(giftCertificateDto), giftCertificateDtoListReturnService);
//    }
//
////    @Test
////    todo problems with date bc of creating object inside service
//    public void insertGiftCertificateTest() {
//        GiftCertificate giftCertificateInsert = new GiftCertificate();
//        giftCertificateInsert.setName(giftCertificate.getName());
//        giftCertificateInsert.setDescription(giftCertificate.getDescription());
//        giftCertificateInsert.setPrice(giftCertificate.getPrice());
//        giftCertificateInsert.setDuration(giftCertificate.getDuration());
////        giftCertificateInsert.setCreateDate(giftCertificate.getCreateDate());
////        giftCertificateInsert.setLastUpdateDate(giftCertificate.getLastUpdateDate());
//        giftCertificateInsert.setTags(giftCertificate.getTags().stream().map(tag -> new Tag(tag.getName())).collect(Collectors.toList()));
//
//        when(giftCertificateDao.saveCertificate(giftCertificateInsert)).thenReturn(giftCertificate);
//        GiftCertificateDto giftCertificateDtoServiceReturn = giftCertificateService.saveGiftCertificate(giftCertificateDto);
//        assertEquals(giftCertificateDto, giftCertificateDtoServiceReturn);
//    }
//
////    @Test
////    todo problems with date bc of creating object inside service
//    public void updateGiftCertificateTest() {
//        GiftCertificate giftCertificateUpdate = new GiftCertificate();
//        giftCertificateUpdate.setId(giftCertificate.getId());
//        giftCertificateUpdate.setName(giftCertificate.getName());
//        giftCertificateUpdate.setDescription(giftCertificate.getDescription());
//        giftCertificateUpdate.setPrice(giftCertificate.getPrice());
//        giftCertificateUpdate.setDuration(giftCertificate.getDuration());
////        giftCertificateUpdate.setCreateDate(giftCertificate.getCreateDate());
////        giftCertificateUpdate.setLastUpdateDate(giftCertificate.getLastUpdateDate());
//        giftCertificateUpdate.setTags(giftCertificate.getTags().stream().map(tag -> new Tag(tag.getName())).collect(Collectors.toList()));
//        doNothing().when(giftCertificateDao).updateCertificate(giftCertificateUpdate);
//        giftCertificateService.updateGiftCertificate(BigInteger.ONE, giftCertificateDto);
//    }
//
//    @Test
//    public void deleteGiftCertificateTest() {
//        doNothing().when(giftCertificateDao).deleteCertificateById(BigInteger.ONE);
//        giftCertificateService.deleteGiftCertificate(BigInteger.ONE);
//    }
//}
