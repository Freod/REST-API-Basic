//package com.epam.esm.generation;
//
//import com.epam.esm.dao.GiftCertificateDao;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.model.GiftCertificate;
//import com.epam.esm.model.Tag;
//import net.datafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Random;
//import java.util.Set;
//
//@Component
//public class Generate {
//    // TODO: 06/10/2022 remove
//
//    private Random random = new Random(24);
//    private Faker faker = new Faker(random);
//    private final TagDao tagDao;
//    private final GiftCertificateDao giftCertificateDao;
//
//    @Autowired
//    public Generate(TagDao tagDao, GiftCertificateDao giftCertificateDao) {
//        this.tagDao = Objects.requireNonNull(tagDao);
//        this.giftCertificateDao = Objects.requireNonNull(giftCertificateDao);
//    }
//
//    public Set<Tag> generateTags(int size) {
//        Set<Tag> tags = new HashSet<>();
//        while (tags.size() < size) {
//            tags.add(new Tag(faker.name().username()));
//        }
//        return new HashSet<>(tags);
//    }
//
//    public void insertTagsToDb(int size) {
//        Set<Tag> tags = generateTags(size);
//        tags.stream().forEach(tagDao::save);
//    }
//
//    public Set<GiftCertificate> generateGiftCertificates(int size) {
//        Set<GiftCertificate> giftCertificates = new HashSet<>();
//        while (giftCertificates.size() < size) {
//            giftCertificates.add(
//                    new GiftCertificate(
//                            null,
//                            faker.book().title(),
//                            faker.lorem().characters(20, 255),
//                            random.nextDouble(),
//                            random.nextInt()));
//        }
//        return new HashSet<>(giftCertificates);
//    }
//
//    public void insertGiftCertificatesToDb(int size){
//        Set<GiftCertificate> giftCertificates = generateGiftCertificates(size);
//        giftCertificates.stream().forEach(giftCertificateDao::save);
//    }
//}
