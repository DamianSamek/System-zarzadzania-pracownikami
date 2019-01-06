INSERT INTO `user_role` (`id`, `role`) VALUES ('1', 'ROLE_MANAGER');
INSERT INTO `user_role` (`id`, `role`) VALUES ('2', 'ROLE_EMPLOYEE');

INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('1', 'Marian', 'Kowalski', 'admin@firma.pl', '$2a$04$WAOrZR0ak7FrOB.A6EZ7aukL8G9sh7FiUKZ6X2kMggTS.LP.4Nibe', b'1', '1');
INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('2', 'Jan', 'Nowak', 'jannowak@firma.pl', '$2a$04$oBC1eIMGhKNgqKoWhiAkz.bfmTjGtw/0u8dex3PTrly8byDq2H1uC', b'1', '2');
INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('3', 'Marcin', 'Deresz', 'marcinderesz@firma.pl', '$2a$04$Za4PxGEJM8dz4cBEBYo0qerb9XWLbbdWIgZk2mcAnB7gz1plJM0kq', b'1', '2');
INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('4', 'Katarzyna', 'Kot', 'katarzynakot@firma.pl', '$2a$04$vO4y1qbgSdk9a36n3W3PReUnmQCs112lBoBVp7vTUFBAvc8Cjon1W', b'1', '2');
INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('5', 'Anna', 'Potoczna', 'annapotoczna@firma.pl', '$2a$04$LVKOxVPUzu37j48rOX0yFeO6Sc4rSdq2NPprpiLeRNxkVNQhi1C.S', b'1', '2');
INSERT INTO `user` (`id`, `first_name`, `second_name`, `email`, `password`, `enabled`, `id_role`) VALUES ('6', 'Aleksander', 'Wolski', 'aleksanderwolski@firma.pl', '$2a$04$nbUDpJCGH2duThwKxaeZDOxvXHOSsGtTF.WW7JZSoaftqtqwUCVtG', b'1', '2');

INSERT INTO `employee`(`id`,`phone`,`position`,`street_address`,`postal_code`,`state`,`city`,`enabled`,`user_id`) VALUES ('1','725776151','Programista Java','Krzywa 3/31','33-521','Dolnoslaskie','Legnica',b'1','2');
INSERT INTO `employee`(`id`,`phone`,`position`,`street_address`,`postal_code`,`state`,`city`,`enabled`,`user_id`) VALUES ('2','875001009','Programista JavaScript','Mickiewicza 31','35-011','Podkarpackie','Przeworsk',b'1','3');
INSERT INTO `employee`(`id`,`phone`,`position`,`street_address`,`postal_code`,`state`,`city`,`enabled`,`user_id`) VALUES ('3','866722811','Sekretarka','Rynek 3/11','25-092','Lubelskie','Lublin',b'1','4');
INSERT INTO `employee`(`id`,`phone`,`position`,`street_address`,`postal_code`,`state`,`city`,`enabled`,`user_id`) VALUES ('4','777118234','Grafik','Obrazkowa 7','33-011','Podkarpackie','Sandomierz',b'1','5');
INSERT INTO `employee`(`id`,`phone`,`position`,`street_address`,`postal_code`,`state`,`city`,`enabled`,`user_id`) VALUES ('5','871871871','Programista React.js','Opolska 2/12','37-500','Podkarpackie','Jaroslaw',b'1','6');

INSERT INTO `project` (`id`, `client`, `description`, `fee`, `finished`) VALUES ('1', 'KGHM', 'CRM', '30000', b'0');
INSERT INTO `project` (`id`, `client`, `description`, `fee`, `finished`) VALUES ('2', 'Uniwersytet Rzeszowski', 'Wirtualna Uczelnia', '200000', b'1');
INSERT INTO `project` (`id`, `client`, `description`, `fee`, `finished`) VALUES ('3', 'Netia', 'CRM', '45000', b'1');
INSERT INTO `project` (`id`, `client`, `description`, `fee`, `finished`) VALUES ('4', 'Nike', 'Sklep internetowy', '300000', b'0');
INSERT INTO `project` (`id`, `client`, `description`, `fee`, `finished`) VALUES ('5', 'Hebe', 'Sklep internetowy', '130000', b'1');
