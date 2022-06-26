package com.epam.esm;

import com.epam.esm.dao.TagDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

//@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJdbcConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TagDaoTestsIT {

    @Autowired
    private TagDao tagDao;

    @Test
    public void testDao() {
        System.out.println(tagDao);

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
//                new Tag("Tag2")
//        );
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
    }
}
