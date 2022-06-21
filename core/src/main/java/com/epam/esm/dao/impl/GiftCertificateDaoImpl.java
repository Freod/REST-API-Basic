package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.extractor.GiftCertificateExtractor;
import com.epam.esm.dao.mapper.GiftCertificateDaoMapper;
import com.epam.esm.model.Filters;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String INSERT_CERTIFICATE_TAGS_QUERY = "INSERT INTO gift_certificates_tags (certificate_id, tag_id) VALUES (?, ?);";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT g_c.*, t.id as tag_id, t.name as tag_name FROM gift_certificates AS g_c LEFT JOIN gift_certificates_tags as g_c_t on g_c.id = g_c_t.certificate_id LEFT JOIN tags as t on t.id = g_c_t.tag_id WHERE g_c.id = ?;";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT g_c.*, t.id as tag_id, t.name as tag_name FROM gift_certificates AS g_c LEFT JOIN gift_certificates_tags as g_c_t on g_c.id = g_c_t.certificate_id LEFT JOIN tags as t on t.id = g_c_t.tag_id WHERE UPPER(g_c.name) LIKE CONCAT('%%', UPPER(?), '%%') AND UPPER(g_c.description) LIKE CONCAT('%%', UPPER(?), '%%') AND UPPER(t.name) LIKE CONCAT('%%', UPPER(?), '%%');";
    private static final String UPDATE_CERTIFICATE_QUERY = "UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_CERTIFICATE_BY_ID_QUERY = "DELETE FROM gift_certificates WHERE id = ?";
    private static final String DELETE_TAG_FROM_CERTIFICATE_QUERY = "DELETE FROM gift_certificates_tags where certificate_id = ? AND tag_id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TagDao tagDao;
    @Autowired
    @Qualifier("simpleJdbcInsertGiftCertificates")
    private SimpleJdbcInsert simpleJdbcInsert;

    @Override
    @Transactional
    public GiftCertificate saveCertificate(GiftCertificate giftCertificate) {
        Map<String, Object> certificateParameters = new HashMap<>();
        certificateParameters.put("name", giftCertificate.getName());
        certificateParameters.put("description", giftCertificate.getDescription());
        certificateParameters.put("price", giftCertificate.getPrice());
        certificateParameters.put("duration", giftCertificate.getDuration());
        certificateParameters.put("create_date", giftCertificate.getCreateDate());
        certificateParameters.put("last_update_date", giftCertificate.getLastUpdateDate());

        giftCertificate.setId(BigInteger.valueOf(simpleJdbcInsert.executeAndReturnKey(certificateParameters).longValue()));

        giftCertificate.setTags(giftCertificate.getTags().stream().map(tag -> tag = tagDao.selectOrSaveTag(tag)).collect(Collectors.toSet()));

        giftCertificate.getTags().stream().forEach(tag -> saveCertificateTags(giftCertificate.getId(), tag.getId()));

        return giftCertificate;
    }

    private void saveCertificateTags(BigInteger certificateId, BigInteger tagId) {
        jdbcTemplate.update(INSERT_CERTIFICATE_TAGS_QUERY, certificateId, tagId);
    }


    @Override
    public GiftCertificate selectCertificateById(BigInteger id) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SELECT_CERTIFICATE_BY_ID_QUERY, new GiftCertificateDaoMapper(), id);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> selectAllCertificates(Filters filters) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, new GiftCertificateExtractor(), filters.getName(), filters.getDescription(), filters.getTag());

        Comparator comparator;
        switch (filters.getOrderBy()) {
            case "last_update_date":
                comparator = Comparator.comparing(GiftCertificate::getLastUpdateDate);
                break;
            case "create_date":
                comparator = Comparator.comparing(GiftCertificate::getCreateDate);
                break;
            default:
                comparator = Comparator.comparing(GiftCertificate::getName);
        }

        if(filters.getDirection().equals("desc")){
            comparator = comparator.reversed();
        }

        giftCertificates.sort(comparator);

        return giftCertificates;
    }

    @Override
    @Transactional
    public void updateCertificate(GiftCertificate giftCertificate) {
        GiftCertificate dbGiftCertificate = selectCertificateById(giftCertificate.getId());
        dbGiftCertificate = replaceChangedFields(dbGiftCertificate, giftCertificate);
        jdbcTemplate.update(UPDATE_CERTIFICATE_QUERY, dbGiftCertificate.getName(), dbGiftCertificate.getDescription(), dbGiftCertificate.getPrice(), dbGiftCertificate.getPrice(), dbGiftCertificate.getLastUpdateDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), dbGiftCertificate.getId());
    }

    private GiftCertificate replaceChangedFields(GiftCertificate dbGiftCertificate, GiftCertificate changedGiftCertificate) {
        if (!dbGiftCertificate.equals(changedGiftCertificate)) {
            if (!dbGiftCertificate.getName().equals(changedGiftCertificate.getName())) {
                dbGiftCertificate.setName(changedGiftCertificate.getName());
            }
            if (!dbGiftCertificate.getDescription().equals(changedGiftCertificate.getDescription())) {
                dbGiftCertificate.setDescription(changedGiftCertificate.getDescription());
            }
            if (dbGiftCertificate.getPrice() != changedGiftCertificate.getPrice()) {
                dbGiftCertificate.setPrice(changedGiftCertificate.getPrice());
            }
            if (dbGiftCertificate.getDuration() != changedGiftCertificate.getDuration()) {
                dbGiftCertificate.setDuration(changedGiftCertificate.getDuration());
            }

            changedGiftCertificate.setTags(changedGiftCertificate.getTags().stream().map(tag -> tag = tagDao.selectOrSaveTag(tag)).collect(Collectors.toSet()));

            if (!dbGiftCertificate.getTags().equals(changedGiftCertificate.getTags())) {
                Set<Tag> tagsToRemove = new HashSet<>(dbGiftCertificate.getTags());
                Set<Tag> tagsToAdd = new HashSet<>(changedGiftCertificate.getTags());

                tagsToRemove.removeAll(changedGiftCertificate.getTags());
                tagsToAdd.removeAll(dbGiftCertificate.getTags());

                tagsToRemove.stream().forEach(tag -> deleteTagFromCertificate(dbGiftCertificate.getId(), tag.getId()));
                tagsToAdd.stream().forEach(tag -> saveCertificateTags(dbGiftCertificate.getId(), tag.getId()));
            }

            dbGiftCertificate.setLastUpdateDate(LocalDateTime.now());

            return dbGiftCertificate;
        }
        return dbGiftCertificate;
    }

    @Override
    public void deleteCertificateById(BigInteger id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUERY, id);
    }

    private void deleteTagFromCertificate(BigInteger certificateId, BigInteger tagId) {
        jdbcTemplate.update(DELETE_TAG_FROM_CERTIFICATE_QUERY, certificateId, tagId);
    }
}
