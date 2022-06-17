package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.GiftCertificateDaoMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String INSERT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_CERTIFICATE_TAGS_QUERY = "INSERT INTO gift_certificates_tags (certificate_id, tag_id) VALUES (?, ?);";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY = "SELECT g_c.* FROM gift_certificates AS g_c WHERE id = ?";
    private static final String SELECT_CERTIFICATE_ID_AND_TAG_BY_ID_QUERY = "SELECT g_c_t.certificate_id, g_c_t.tag_id, t.name as tag_name FROM gift_certificates_tags as g_c_t LEFT JOIN tags as t on g_c_t.tag_id=t.id where g_c_t.certificate_id = ?;";
    private static final String SELECT_ALL_CERTIFICATES_QUERY = "SELECT g_c.* FROM gift_certificates AS g_c";
    private static final String SELECT_CERTIFICATE_ID_AND_TAG_QUERY = "SELECT g_c_t.certificate_id, g_c_t.tag_id, t.name as tag_name FROM gift_certificates_tags as g_c_t LEFT JOIN tags as t on g_c_t.tag_id=t.id;";
    private static final String UPDATE_CERTIFICATE_QUERY = "UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_CERTIFICATE_BY_ID_QUERY = "DELETE FROM gift_certificates WHERE id = ?";

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
    @Transactional
    public GiftCertificate selectCertificateById(BigInteger id) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SELECT_CERTIFICATE_BY_ID_QUERY, new GiftCertificateDaoMapper(), id);
        List<TagRef> tagRefs = jdbcTemplate.query(SELECT_CERTIFICATE_ID_AND_TAG_BY_ID_QUERY, new RowMapper<TagRef>() {
            @Override
            public TagRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new TagRef(
                        resultSet.getBigDecimal("certificate_id").toBigInteger(),
                        resultSet.getBigDecimal("tag_id").toBigInteger(),
                        resultSet.getString("tag_name")
                );
            }
        }, id);

        for (TagRef tagRef : tagRefs) {
            giftCertificate.addTag(new Tag(tagRef.getTagId(), tagRef.getTagName()));
        }

        return giftCertificate;
    }

    @Override
    @Transactional
    public List<GiftCertificate> selectAllCertificates() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SELECT_ALL_CERTIFICATES_QUERY, new GiftCertificateDaoMapper());
        List<TagRef> tagRefs = jdbcTemplate.query(SELECT_CERTIFICATE_ID_AND_TAG_QUERY, new RowMapper<TagRef>() {
            @Override
            public TagRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new TagRef(
                        resultSet.getBigDecimal("certificate_id").toBigInteger(),
                        resultSet.getBigDecimal("tag_id").toBigInteger(),
                        resultSet.getString("tag_name")
                );
            }
        });

        for (GiftCertificate giftCertificate : giftCertificates) {
            for (TagRef tagRef : tagRefs) {
                if (tagRef.getCertificateId() == giftCertificate.getId()) {
                    giftCertificate.addTag(new Tag(tagRef.getTagId(), tagRef.getTagName()));
                }
            }
        }

        return giftCertificates;
    }

    @Override
    @Transactional
    public void updateCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate.getId() != null) {
            try {
                GiftCertificate dbGiftCertificate = selectCertificateById(giftCertificate.getId());
                dbGiftCertificate = replaceChangedFields(dbGiftCertificate, giftCertificate);
                jdbcTemplate.update(UPDATE_CERTIFICATE_QUERY, dbGiftCertificate.getName(), dbGiftCertificate.getDescription(), dbGiftCertificate.getPrice(), dbGiftCertificate.getPrice(), dbGiftCertificate.getLastUpdateDate(), dbGiftCertificate.getId());

                //            if(dbGiftCertificate.getTags().equals(changedGiftCertificate.getTags()))
//            TODO: TAGS

            } catch (EmptyResultDataAccessException e) {
                throw new IllegalArgumentException("This id doesn't exist in db");
            }
        } else {
            throw new IllegalArgumentException("giftCertificate doesn't have id");
        }
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
            dbGiftCertificate.setLastUpdateDate(LocalDateTime.now());

            return dbGiftCertificate;
        }
        return dbGiftCertificate;
    }

    @Override
    public void deleteCertificateById(BigInteger id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUERY, id);
    }
}
