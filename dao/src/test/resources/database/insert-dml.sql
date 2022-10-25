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
INSERT INTO tags(name)
VALUES ('mostUsedTag');

INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Teddy', 'Teddy bear gift', 10.4, 5, '2022-06-26T12:04:01', '2022-06-26T12:04:01');
INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Many', 'Many gifts', 50, 3, '2022-06-26T12:13:42', '2022-06-26T12:13:42');
INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Glomer', 'Gift to glomer', 6.5, 2, '2022-06-26T20:47:15', '2022-06-26T20:47:15');
INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Car', 'Car gift', 24, 4, '2022-07-16T15:40:33', '2022-07-16T20:47:15');
INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)
VALUES ('Clothes', 'clothes', 30.5, 6, '2022-08-06T08:22:15', '2022-08-07T10:47:15');

INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (1, 1);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (1, 2);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (3, 5);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (4, 3);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (4, 7);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (5, 9);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (5, 10);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (5, 11);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (5, 12);

-- mostUsedTag
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (1, 13);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (2, 13);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (3, 13);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (4, 13);
INSERT INTO gift_certificates_tags(giftcertificate_id, tags_id)
VALUES (5, 13);

INSERT INTO orders(cost, purchaseDate)
VALUES (10.4, '2022-09-09T13:33:23');
INSERT INTO orders(cost, purchaseDate)
VALUES (80.5, '2022-10-10T14:32:43');
INSERT INTO orders(cost, purchaseDate)
VALUES (30.5, '2022-10-11T10:56:21');

INSERT INTO orders_gift_certificates(order_id, giftcertificates_id)
VALUES (1, 1);
INSERT INTO orders_gift_certificates(order_id, giftcertificates_id)
VALUES (2, 2);
INSERT INTO orders_gift_certificates(order_id, giftcertificates_id)
VALUES (2, 3);
INSERT INTO orders_gift_certificates(order_id, giftcertificates_id)
VALUES (2, 4);
INSERT INTO orders_gift_certificates(order_id, giftcertificates_id)
VALUES (3, 5);

INSERT INTO users(username)
VALUES ('john33');
INSERT INTO users(username)
VALUES ('anna2');
INSERT INTO users(username)
VALUES ('rechi3');

INSERT INTO users_orders(user_id, orders_id)
VALUES (1, 1);
INSERT INTO users_orders(user_id, orders_id)
VALUES (1, 2);
INSERT INTO users_orders(user_id, orders_id)
VALUES (2, 3);
