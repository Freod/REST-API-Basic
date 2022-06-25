//package com.epam.esm;
//
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.model.Tag;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//import java.math.BigInteger;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class TagDaoImplTests {
//    @Test
//    public void testDao() {
//        //TODO:TEST NAME SELECT SELECT OR SAVE
//        DataSource h2DataSource = new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("database/init-ddl.sql")
//                .build();
//
//        TagDao tagDao = new TagDaoImpl(new JdbcTemplate(h2DataSource));
//
//        List<Tag> tags = Arrays.asList(
//                new Tag("Tag1"),
//                new Tag("Tag2"));
//
//        assertEquals(0, tagDao.selectAllTags().size());
//        tags.stream().forEach(tag -> tagDao.saveTag(tag));
//        assertEquals(tags.size(), tagDao.selectAllTags().size());
//
//        int i = 0;
//        BigInteger j = BigInteger.valueOf(i + 1);
//        Tag testTag = tagDao.selectTagById(j);
//        assertEquals(Tag.class, testTag.getClass());
//        assertNotNull(testTag.getId());
//        assertEquals(tags.get(i).getName(), testTag.getName());
//
//        String name = "ChangedTag";
//        tagDao.updateTag(new Tag(j, name));
//        testTag = tagDao.selectTagById(j);
//        assertEquals(name, testTag.getName());
//
//        tagDao.deleteTag(j);
//        assertEquals(tags.size() - 1, tagDao.selectAllTags().size());
//    }
//}
