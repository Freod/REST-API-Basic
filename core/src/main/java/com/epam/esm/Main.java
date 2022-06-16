package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ComponentScan("com.epam.esm")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TagDao tagDao = context.getBean(TagDaoImpl.class);
        GiftCertificateDao giftCertificateDao = context.getBean(GiftCertificateDaoImpl.class);

        tagDao.saveTag(new Tag("Tag1"));
        tagDao.saveTag(new Tag("TestTag2"));

        List<Tag> tags1 = tagDao.selectAllTags();
        tags1.stream().forEach(System.out::println);

        tagDao.updateTag(new Tag(BigInteger.valueOf(1), "ChangedTag"));
        System.out.println(tagDao.selectTagById(BigInteger.valueOf(1)));

        tagDao.selectAllTags();

        Set<Tag> tags = Arrays.asList(new Tag("Tag1"), new Tag("Tag2")).stream().collect(Collectors.toSet());
        tags.forEach(System.out::println);

        System.out.println(giftCertificateDao.saveCertificate(new GiftCertificate("name", "description", 2.5, 2, tags)));
        giftCertificateDao.saveCertificate(new GiftCertificate("name", "description", 2.5, 2, tags));
        giftCertificateDao.saveCertificate(new GiftCertificate("name", "description", 2.5, 2, tags));

        List<GiftCertificate> giftCertificates = giftCertificateDao.selectAllCertificates();
        giftCertificates.stream().forEach(System.out::println);

        giftCertificateDao.deleteCertificateById(BigInteger.valueOf(1));

        System.out.println();

        giftCertificates = giftCertificateDao.selectAllCertificates();
        giftCertificates.stream().forEach(System.out::println);

        System.out.println(giftCertificateDao.selectCertificateById(BigInteger.valueOf(3)));
    }
}
