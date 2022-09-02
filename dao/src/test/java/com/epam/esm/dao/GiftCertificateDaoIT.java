//package com.epam.esm.dao;
//
//import com.epam.esm.config.Config;
//import com.epam.esm.exception.ResourceNotFound;
//import com.epam.esm.model.Filter;
//import com.epam.esm.model.GiftCertificate;
//import com.epam.esm.model.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ActiveProfiles("dev")
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {Config.class}, loader = AnnotationConfigContextLoader.class)
//class GiftCertificateDaoIT {
//
//    @Autowired
//    private GiftCertificateDao giftCertificateDao;
//
////    @BeforeEach
////    void initDatabaseAndInsertToDatabase(@Autowired DataSource dataSource) throws SQLException, FileNotFoundException {
////        Reader initReader = new FileReader("src/test/resources/database/init-ddl.sql");
////        Reader insertReader = new FileReader("src/test/resources/database/insert-dml.sql");
////
////        RunScript.execute(dataSource.getConnection(), initReader);
////        RunScript.execute(dataSource.getConnection(), insertReader);
////    }
////
////    @AfterEach
////    void dropDatabase(@Autowired DataSource dataSource) throws FileNotFoundException, SQLException {
////        Reader dropReader = new FileReader("src/test/resources/database/drop-ddl.sql");
////
////        RunScript.execute(dataSource.getConnection(), dropReader);
////    }
//
//    @Test
//    void whenSelectAllGiftCertificateShouldReturnGiftCertificateList() {
//        //given
//        Filter filterToSelect = new Filter();
//        int expectedListSize = 3;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForNameShouldReturnGiftCertificateWithThisNameList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setName("Name1");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForDescriptionShouldReturnGiftCertificateWithThisDescriptionList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setDescription("Description1");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForTagNameShouldReturnGiftCertificateWithThisTagNameList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setTag("tag3");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForNameAndDescriptionShouldReturnGiftCertificateWithThisNameAndDescriptionList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setName("Name1");
//        filterToSelect.setDescription("Description1");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForNameAndTagNameShouldReturnGiftCertificateWithThisNameAndTagNameList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setName("Name1");
//        filterToSelect.setTag("tag2");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateWithFilterOnlyForDescriptionAndTagNameShouldReturnGiftCertificateWithThisDescriptionAndTagNameList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setDescription("Description1");
//        filterToSelect.setTag("tag2");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectAllGiftCertificateFiltersShouldReturnGiftCertificateWithFiltersList(){
//        //given
//        Filter filterToSelect = new Filter();
//        filterToSelect.setName("Name2");
//        filterToSelect.setDescription("Description2");
//        filterToSelect.setTag("tag3");
//        int expectedListSize = 1;
//
//        //when
//        List<GiftCertificate> actualGiftCertificateList = giftCertificateDao.findAllUsingFilter(filterToSelect);
//
//        //then
//        assertEquals(expectedListSize, actualGiftCertificateList.size());
//    }
//
//    @Test
//    void whenSelectGiftCertificateByIdShouldReturnGiftCertificateWithThatId() {
//        //given
//        Long resourceIdToFind = 1L;
//        GiftCertificate expectedGiftCertificate =
//                new GiftCertificate(
//                        1L,
//                        "Name1",
//                        "Description1",
//                        3.5,
//                        5,
//                        LocalDateTime.parse("2022-06-26T12:04:01.891"),
//                        LocalDateTime.parse("2022-06-26T12:04:01.891")
//                );
//
//        //then
//        GiftCertificate actualGiftCertificate = giftCertificateDao.findById(resourceIdToFind);
//
//        //then
//        assertEquals(expectedGiftCertificate.getId(), actualGiftCertificate.getId());
//        assertMainValuesOfGiftCertificate(expectedGiftCertificate, actualGiftCertificate);
//        assertEquals(expectedGiftCertificate.getLastUpdateDate(), actualGiftCertificate.getLastUpdateDate());
//        assertEquals(expectedGiftCertificate.getCreateDate(), actualGiftCertificate.getCreateDate());
//
//    }
//
//    @Test
//    void whenSelectGiftCertificateThatNotExistShouldThrowResourceNotFoundException() {
//        //given
//        String expectedMessage = "Certificate not found (id = 0)";
//        Long resourceId = 0L;
//
//        //when
//        ResourceNotFound thrown = assertThrows(
//                ResourceNotFound.class,
//                () -> giftCertificateDao.findById(resourceId)
//        );
//
//        //then
//        assertEquals(expectedMessage, thrown.getMessage());
//    }
//
//    @Test
//    void whenInsertGiftCertificateShouldInsertThatGiftCertificateIntoDB() {
//        //given
//        int expectedIncreasedListSize = 1;
//        GiftCertificate giftCertificateToInsert = new GiftCertificate();
//        giftCertificateToInsert.setName("TestingName");
//        giftCertificateToInsert.setDescription("TestingDescription");
//        giftCertificateToInsert.setPrice(4.5);
//        giftCertificateToInsert.setDuration(6);
//        giftCertificateToInsert.addTag(new Tag("tag1"));
//        giftCertificateToInsert.addTag(new Tag("tag4"));
//
//        //when
//        List<GiftCertificate> giftCertificatesBefore = giftCertificateDao.findAllUsingFilter(new Filter());
//        // TODO: 29.07.2022
////        GiftCertificate giftCertificateInserted =
//                giftCertificateDao.save(giftCertificateToInsert);
//        List<GiftCertificate> giftCertificatesAfter = giftCertificateDao.findAllUsingFilter(new Filter());
//
//        //then
//        assertEquals(giftCertificatesBefore.size() + expectedIncreasedListSize, giftCertificatesAfter.size());
////        assertMainValuesOfGiftCertificate(giftCertificateToInsert, giftCertificateInserted);
////        assertNotNull(giftCertificateInserted.getId());
////        assertEquals(giftCertificateToInsert.getTags().size(), giftCertificateInserted.getTags().size());
//    }
//
//    @Test
//    void whenUpdateGiftCertificateShouldUpdateThatGiftCertificateInDB() {
//        //given
//        Long resourceId = 1L;
//        GiftCertificate giftCertificateToUpdate = giftCertificateDao.findById(resourceId);
//        giftCertificateToUpdate.setName("changedName");
//        giftCertificateToUpdate.setDescription("changedDescription");
//        giftCertificateToUpdate.setPrice(2.0);
//        giftCertificateToUpdate.setDuration(2);
//
//        //when
//        giftCertificateDao.update(giftCertificateToUpdate);
//        GiftCertificate giftCertificateFromDB = giftCertificateDao.findById(resourceId);
//
//        //then
//        assertEquals(giftCertificateToUpdate.getId(), giftCertificateFromDB.getId());
//        assertMainValuesOfGiftCertificate(giftCertificateToUpdate, giftCertificateFromDB);
//        assertEquals(giftCertificateToUpdate.getCreateDate(), giftCertificateFromDB.getCreateDate());
//        assertNotEquals(giftCertificateToUpdate.getLastUpdateDate(), giftCertificateFromDB.getLastUpdateDate());
//    }
//
//    @Test
//    void whenAddTagToGiftCertificateShouldBeAdded() {
//        //given
//        Long resourceId = 1L;
//        Tag tagToAdd = new Tag("tag10");
//        GiftCertificate giftCertificateBeforeAdd = giftCertificateDao.findById(resourceId);
//        int expectedTagListIncreased = 1;
//
//        //when
//        giftCertificateDao.addTagToGiftCertificate(resourceId, tagToAdd);
//        GiftCertificate giftCertificateAfterAdd = giftCertificateDao.findById(resourceId);
//
//        //then
//        assertEquals(giftCertificateBeforeAdd.getTags().size() + expectedTagListIncreased, giftCertificateAfterAdd.getTags().size());
//        assertTrue(giftCertificateAfterAdd.getTags().contains(tagToAdd));
//    }
//
//    @Test
//    void whenRemoveTagFromGiftCertificateShouldBeRemoved() {
//        //given
//        Long resourceId = 1L;
//        Tag tagToAdd = new Tag("tag1");
//        GiftCertificate giftCertificateBeforeRemove = giftCertificateDao.findById(resourceId);
//        int expectedTagListDecreased = 1;
//
//        //when
//        giftCertificateDao.removeTagFromGiftCertificate(resourceId, tagToAdd);
//        GiftCertificate giftCertificateAfterRemove = giftCertificateDao.findById(resourceId);
//
//        //then
//        assertEquals(giftCertificateBeforeRemove.getTags().size() - expectedTagListDecreased, giftCertificateAfterRemove.getTags().size());
//        assertFalse(giftCertificateAfterRemove.getTags().contains(tagToAdd));
//    }
//
//    @Test
//    void whenDeleteGiftCertificateByIdShouldRemoveResourceWithThatId() {
//        //given
//        int expectedListDecreased = 1;
//        Long resourceIdToRemove = 1L;
//        String expectedMessage = "Certificate not found (id = 1)";
//
//        //when
//        List<GiftCertificate> giftCertificateListBefore = giftCertificateDao.findAllUsingFilter(new Filter());
//        giftCertificateDao.removeById(resourceIdToRemove);
//        List<GiftCertificate> giftCertificateListAfter = giftCertificateDao.findAllUsingFilter(new Filter());
//        ResourceNotFound thrown = assertThrows(
//                ResourceNotFound.class,
//                () -> giftCertificateDao.findById(resourceIdToRemove)
//        );
//
//        //then
//        assertNotEquals(giftCertificateListBefore, giftCertificateListAfter);
//        assertEquals(giftCertificateListBefore.size() - expectedListDecreased, giftCertificateListAfter.size());
//        assertEquals(expectedMessage, thrown.getMessage());
//    }
//
//    private void assertMainValuesOfGiftCertificate(GiftCertificate expectedGiftCertificate, GiftCertificate actualGiftCertificate) {
//        assertEquals(expectedGiftCertificate.getName(), actualGiftCertificate.getName());
//        assertEquals(expectedGiftCertificate.getDescription(), actualGiftCertificate.getDescription());
//        assertEquals(expectedGiftCertificate.getPrice(), actualGiftCertificate.getPrice());
//        assertEquals(expectedGiftCertificate.getDuration(), actualGiftCertificate.getDuration());
//    }
//}
