CREATE TABLE gift_certificates
(
    id               BIGSERIAL NOT NULL,
    name             CHARACTER VARYING(255),
    description      CHARACTER VARYING(255),
    price            DOUBLE PRECISION,
    duration         INT,
    create_date      CHARACTER VARYING(40),
    last_update_date CHARACTER VARYING(40),
    PRIMARY KEY (id)
);

CREATE TABLE tags
(
    id   BIGSERIAL NOT NULL,
    name CHARACTER VARYING(255),
    PRIMARY KEY (id)
);

CREATE TABLE gift_certificates_tags
(
    id      BIGSERIAL NOT NULL,
    certificate_id BIGINT    NOT NULL,
    tag_id  BIGINT    NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_certificate_id FOREIGN KEY (certificate_id)
        REFERENCES gift_certificates (id) ON DELETE CASCADE,

    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id)
        REFERENCES tags (id) ON DELETE CASCADE
);