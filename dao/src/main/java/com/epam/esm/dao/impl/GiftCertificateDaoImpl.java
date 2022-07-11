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
    private static final String INSERT_CERTIFICATE_TAGS_QUERY =
            "INSERT INTO gift_certificates_tags (certificate_id, tag_id) VALUES (?, ?);";
    private static final String SELECT_CERTIFICATE_BY_ID_QUERY =
            "SELECT g_c.*, t.id as tag_id, t.name AS tag_name FROM gift_certificates AS g_c" +
                    " LEFT JOIN gift_certificates_tags AS g_c_t ON g_c.id = g_c_t.certificate_id" +
                    " LEFT JOIN tags AS t ON t.id = g_c_t.tag_id WHERE g_c.id = ?;";
    private static final String SELECT_ALL_CERTIFICATES_QUERY =
            "SELECT g_c.*, t.id as tag_id, t.name AS tag_name FROM gift_certificates AS g_c" +
                    " LEFT JOIN gift_certificates_tags AS g_c_t ON g_c.id = g_c_t.certificate_id" +
                    " LEFT JOIN tags AS t ON t.id = g_c_t.tag_id";
    private static final String COUNT_ALL_CERTIFICATE_TAGS_BY_CERTIFICATE_ID_AND_TAG_ID_QUERY =
            "SELECT COUNT(*) FROM gift_certificates_tags AS g_c_t WHERE g_c_t.certificate_id = ? AND g_c_t.tag_id = ?;";
    private static final String UPDATE_CERTIFICATE_QUERY =
            "UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_CERTIFICATE_BY_ID_QUERY =
            "DELETE FROM gift_certificates WHERE id = ?";
    private static final String DELETE_TAG_FROM_CERTIFICATE_QUERY =
            "DELETE FROM gift_certificates_tags where certificate_id = ? AND tag_id = ?;";

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

        giftCertificate.setId(
                BigInteger.valueOf(
                        simpleJdbcInsertGiftCertificates.executeAndReturnKey(certificateParameters)
                                .longValue()));

        giftCertificate.setTags(
                giftCertificate.getTags()
                        .stream()
                        .map(tag -> tag = tagDao.selectOrSaveTag(tag))
                        .collect(Collectors.toList()));

        giftCertificate.getTags()
                .stream()
                .forEach(tag -> saveCertificateTags(giftCertificate.getId(), tag.getId()));

        return giftCertificate;
    }

    @Override
    public GiftCertificate selectCertificateById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CERTIFICATE_BY_ID_QUERY, new GiftCertificateDaoMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFound("Certificate not found (id = " + id + ")");
        }
    }

    @Override
    public List<GiftCertificate> selectAllCertificates(Filter filter) {
        List<Object> parameters = new ArrayList<>();
        return jdbcTemplate.query(
                prepareSelectQueryBasedOnFilter(filter, parameters),
                parameters.toArray(),
                new GiftCertificateExtractor());
    }

    @Override
    public void updateCertificate(GiftCertificate giftCertificate) {
        GiftCertificate dbGiftCertificate = selectCertificateById(giftCertificate.getId());
        replaceChangedFields(dbGiftCertificate, giftCertificate);
        jdbcTemplate.update(
                UPDATE_CERTIFICATE_QUERY,
                dbGiftCertificate.getName(),
                dbGiftCertificate.getDescription(),
                dbGiftCertificate.getPrice(),
                dbGiftCertificate.getDuration(),
                dbGiftCertificate.getLastUpdateDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                dbGiftCertificate.getId());
    }

    @Override
    @Transactional
    public void addTagToGiftCertificate(BigInteger giftCertificateId, Tag tag) {
        tag = tagDao.selectOrSaveTag(tag);
        int numberOfTagInDB = jdbcTemplate.queryForObject(
                COUNT_ALL_CERTIFICATE_TAGS_BY_CERTIFICATE_ID_AND_TAG_ID_QUERY,
                Integer.class,
                giftCertificateId,
                tag.getId());
        if (numberOfTagInDB < 1) {
            saveCertificateTags(giftCertificateId, tag.getId());
        }
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
            if (changedGiftCertificate.getName() != null) {
                dbGiftCertificate.setName(changedGiftCertificate.getName());
            }
            if (changedGiftCertificate.getDescription() != null) {
                dbGiftCertificate.setDescription(changedGiftCertificate.getDescription());
            }
            if (changedGiftCertificate.getPrice() != null) {
                dbGiftCertificate.setPrice(changedGiftCertificate.getPrice());
            }
            if (changedGiftCertificate.getDuration() != null) {
                dbGiftCertificate.setDuration(changedGiftCertificate.getDuration());
            }

            dbGiftCertificate.setLastUpdateDate(LocalDateTime.now());

            return dbGiftCertificate;
        }
        return dbGiftCertificate;
    }

    private String prepareSelectQueryBasedOnFilter(Filter filter, List<Object> parameters) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_ALL_CERTIFICATES_QUERY);

        if (filter.getName() != null || filter.getDescription() != null || filter.getTag() != null) {
            queryBuilder.append(" WHERE");
            if (filter.getName() != null) {
                parameters.add(filter.getName());
                queryBuilder.append(" UPPER(g_c.name) LIKE CONCAT('%%', UPPER(?), '%%')");
                if (filter.getDescription() != null || filter.getTag() != null) {
                    queryBuilder.append(" AND");
                }
            }
            if (filter.getDescription() != null) {
                parameters.add(filter.getDescription());
                queryBuilder.append(" UPPER(g_c.description) LIKE CONCAT('%%', UPPER(?), '%%')");
                if (filter.getTag() != null) {
                    queryBuilder.append(" AND");
                }
            }
            if (filter.getTag() != null) {
                parameters.add(filter.getTag());
                queryBuilder.append(" UPPER(t.name) LIKE CONCAT('%%', UPPER(?), '%%')");
            }
        }

        queryBuilder.append(";");

        return queryBuilder.toString();
    }
}
