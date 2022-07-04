package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.extractor.GiftCertificateExtractor;
import com.epam.esm.dao.mapper.GiftCertificateDaoMapper;
import com.epam.esm.exception.ResourceNotFound;
import com.epam.esm.model.Filter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private final TagDao tagDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertGiftCertificates;

    @Autowired
    public GiftCertificateDaoImpl(TagDao tagDao, JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsertGiftCertificates) {
        this.tagDao = Objects.requireNonNull(tagDao);
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate);
        this.simpleJdbcInsertGiftCertificates = Objects.requireNonNull(simpleJdbcInsertGiftCertificates);
    }

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

        System.out.println(giftCertificate);

        giftCertificate.setId(BigInteger.valueOf(simpleJdbcInsertGiftCertificates.executeAndReturnKey(certificateParameters).longValue()));

        giftCertificate.setTags(giftCertificate.getTags().stream().map(tag -> tag = tagDao.selectOrSaveTag(tag)).collect(Collectors.toList()));

        giftCertificate.getTags().stream().forEach(tag -> saveCertificateTags(giftCertificate.getId(), tag.getId()));

        return giftCertificate;
    }

    @Override
    public GiftCertificate selectCertificateById(BigInteger id) {
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SELECT_CERTIFICATE_BY_ID_QUERY, new GiftCertificateDaoMapper(), id);
            return giftCertificate;
        }catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFound("Certificate not found (id = " + id + ")");
        }
    }

    // TODO: 04.07.2022  
    @Override
    public List<GiftCertificate> selectAllCertificates(Filter filter) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, new GiftCertificateExtractor(), filter.getName(), filter.getDescription(), filter.getTag());

        return giftCertificates;
    }

    @Override
    public void updateCertificate(GiftCertificate giftCertificate) {
        GiftCertificate dbGiftCertificate = selectCertificateById(giftCertificate.getId());
        dbGiftCertificate = replaceChangedFields(dbGiftCertificate, giftCertificate);
        jdbcTemplate.update(UPDATE_CERTIFICATE_QUERY, dbGiftCertificate.getName(), dbGiftCertificate.getDescription(), dbGiftCertificate.getPrice(), dbGiftCertificate.getDuration(), dbGiftCertificate.getLastUpdateDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), dbGiftCertificate.getId());
    }

    @Override
    @Transactional
    public void addTagToGiftCertificate(BigInteger giftCertificateId, Tag tag) {
        tag = tagDao.selectOrSaveTag(tag);
        saveCertificateTags(giftCertificateId, tag.getId());
    }

    @Override
    @Transactional
    public void removeTagFromGiftCertificate(BigInteger giftCertificateId, Tag tag) {
        tag = tagDao.selectOrSaveTag(tag);
        jdbcTemplate.update(DELETE_TAG_FROM_CERTIFICATE_QUERY, giftCertificateId, tag.getId());
    }

    @Override
    public void deleteCertificateById(BigInteger id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUERY, id);
    }

    private void saveCertificateTags(BigInteger certificateId, BigInteger tagId) {
        jdbcTemplate.update(INSERT_CERTIFICATE_TAGS_QUERY, certificateId, tagId);
    }

    private GiftCertificate replaceChangedFields(GiftCertificate dbGiftCertificate, GiftCertificate changedGiftCertificate) {
        if (!dbGiftCertificate.equals(changedGiftCertificate)) {
            if (changedGiftCertificate.getName()!=null) {
                dbGiftCertificate.setName(changedGiftCertificate.getName());
            }
            if (changedGiftCertificate.getDescription()!=null) {
                dbGiftCertificate.setDescription(changedGiftCertificate.getDescription());
            }
            if (changedGiftCertificate.getPrice()!=null) {
                dbGiftCertificate.setPrice(changedGiftCertificate.getPrice());
            }
            if (changedGiftCertificate.getDuration()!=null) {
                dbGiftCertificate.setDuration(changedGiftCertificate.getDuration());
            }

            dbGiftCertificate.setLastUpdateDate(LocalDateTime.now());

            return dbGiftCertificate;
        }
        return dbGiftCertificate;
    }
}
