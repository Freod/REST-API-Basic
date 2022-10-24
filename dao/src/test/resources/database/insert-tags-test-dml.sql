INSERT INTO tags(name)
VALUES ('bear');
INSERT INTO tags(name)
VALUES ('toy');
INSERT INTO tags(name)
VALUES ('car');
INSERT INTO tags(name)
VALUES ('puzzle');
INSERT INTO tags(name)
VALUES ('game');
INSERT INTO tags(name)
VALUES ('lego');
INSERT INTO tags(name)
VALUES ('doll');
INSERT INTO tags(name)
VALUES ('cloth');
INSERT INTO tags(name)
VALUES ('hat');
INSERT INTO tags(name)
VALUES ('shoes');
INSERT INTO tags(name)
VALUES ('dress');
INSERT INTO tags(name)
VALUES ('shirt');

INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Teddy', 'Teddy bear gift', 10.4, 5, '2022-06-26T12:04:01', '2022-06-26T12:04:01');

INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (1, 1);