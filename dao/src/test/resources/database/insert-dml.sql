INSERT INTO tags(name) VALUES('tag1');
INSERT INTO tags(name) VALUES('tag2');
INSERT INTO tags(name) VALUES('tag3');

INSERT INTO gift_certificates(name, description, price, duration, create_date, last_update_date) VALUES('Name1', 'Description1', 3.5, 5, '2022-06-26T12:04:01.891', '2022-06-26T12:04:01.891');
INSERT INTO gift_certificates(name, description, price, duration, create_date, last_update_date) VALUES('Name2', 'Description2', 5, 3, '2022-06-26T12:13:42.122', '2022-06-26T12:13:42.122');
INSERT INTO gift_certificates(name, description, price, duration, create_date, last_update_date) VALUES('Name3', 'Description3', 6.5, 2, '2022-06-26T20:47:15.872', '2022-06-26T20:47:15.872');

INSERT INTO gift_certificates_tags(certificate_id, tag_id) VALUES (1, 1);
INSERT INTO gift_certificates_tags(certificate_id, tag_id) VALUES (1, 2);
INSERT INTO gift_certificates_tags(certificate_id, tag_id) VALUES (2, 3);
