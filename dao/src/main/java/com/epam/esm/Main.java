package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.epam.esm")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TagDao tagDao = context.getBean(TagDaoImpl.class);
        GiftCertificateDao giftCertificateDao = context.getBean(GiftCertificateDaoImpl.class);

//        tagDao.saveTag(new Tag("Tag1"));
//        tagDao.saveTag(new Tag("TestTag2"));
//
//        List<Tag> tags1 = tagDao.selectAllTags();
//        tags1.stream().forEach(System.out::println);
//
//        tagDao.updateTag(new Tag(BigInteger.valueOf(1), "ChangedTag"));
//        System.out.println(tagDao.selectTagById(BigInteger.valueOf(1)));
//
//        Set<Tag> tags = tagDao.selectAllTags().stream().collect(Collectors.toSet());
//
//        Set<Tag> tags = Arrays.asList(new Tag("Tag1"), new Tag("Tag2")).stream().collect(Collectors.toSet());
//        tags.forEach(System.out::println);
//
//        System.out.println(giftCertificateDao.saveCertificate(new GiftCertificate("name", "description", 2.5, 2, tags)));
//        GiftCertificate gc1 = giftCertificateDao.saveCertificate(new GiftCertificate("name2", "description", 2.5, 2, tags));
//        giftCertificateDao.saveCertificate(new GiftCertificate("name", "description", 2.5, 2, tags));
//
//        List<GiftCertificate> giftCertificates = giftCertificateDao.selectAllCertificates(new Filters());
//        giftCertificates.stream().forEach(System.out::println);
//
//        System.out.println(giftCertificateDao.selectCertificateById(BigInteger.valueOf(1)));
//
//        giftCertificateDao.deleteCertificateById(BigInteger.valueOf(1));
//        Set<Tag>
//                tags = Arrays.asList(new Tag("Tag1"), new Tag("Tag3")).stream().collect(Collectors.toSet());
//        giftCertificateDao.updateCertificate(new GiftCertificate(BigInteger.valueOf(1), "name", "description", 2.5, 5, tags));
//        List<Tag> tagList = tags.stream().collect(Collectors.toList());
//        tagList.remove(5);
//        tags = tagList.stream().collect(Collectors.toSet());
//        tags.add(new Tag(BigInteger.valueOf(5), "Tag6"));
//        giftCertificateDao.updateCertificate(new GiftCertificate(BigInteger.valueOf(8), "name", "description", 3.5, 5, tags));

//        System.out.println();
//
//        giftCertificates = giftCertificateDao.selectAllCertificates();
//        giftCertificates.stream().forEach(System.out::println);
//
//        System.out.println(giftCertificateDao.selectCertificateById(BigInteger.valueOf(3)));

    }
}
