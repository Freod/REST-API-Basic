package com.epam.esm;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.Tag;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigInteger;
import java.util.List;

@ComponentScan("com.epam.esm")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TagDao tagDao = context.getBean(TagDaoImpl.class);

        tagDao.saveTag(new Tag("TestTag"));
        tagDao.saveTag(new Tag("TestTag2"));

        List<Tag> tags = tagDao.selectAllTags();

        for (Tag tag1: tags) {
            System.out.println(tag1);
        }

        tagDao.updateTag(new Tag(BigInteger.valueOf(1), "ChangedTag"));
        System.out.println(tagDao.selectTagById(BigInteger.valueOf(1)));
    }
}
