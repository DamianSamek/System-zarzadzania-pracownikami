CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `second_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `id_role` varchar(45) DEFAULT NULL,
  `reset_token` varchar(63) DEFAULT NULL,
  `valid_date` datetime DEFAULT NULL,
  `token_valid` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `fk_user_1_idx` (`id_role`)
);

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  `street_address` varchar(45) DEFAULT NULL,
  `postal_code` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_1_idx` (`user_id`),
  CONSTRAINT `fk_employee_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `agreement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_from` datetime DEFAULT NULL,
  `date_to` datetime DEFAULT NULL,
  `date_of_creation` datetime DEFAULT NULL,
  `id_employee` int(11) NOT NULL,
  `salary` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_employee_UNIQUE` (`id_employee`),
  KEY `fk_agreement_1_idx` (`id_employee`),
  CONSTRAINT `fk_agreement_1` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`)
);

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client` varchar(45) DEFAULT NULL,
  `description` text,
  `fee` int(11) DEFAULT NULL,
  `finished` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `employee_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_employee` int(11) DEFAULT NULL,
  `id_project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_project_1_idx` (`id_employee`),
  KEY `fk_employee_project_2_idx` (`id_project`),
  CONSTRAINT `fk_employee_project_1` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_employee_project_2` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`)
);

CREATE TABLE `raise_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_employee` int(11) DEFAULT NULL,
  `id_agreement` int(11) DEFAULT NULL,
  `salary_request` int(11) DEFAULT NULL,
  `accepted` bit(1) DEFAULT NULL,
  `considered` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_raise_request_1_idx` (`id_agreement`),
  KEY `fk_raise_request_2_idx` (`id_employee`),
  CONSTRAINT `fk_raise_request_1` FOREIGN KEY (`id_agreement`) REFERENCES `agreement` (`id`),
  CONSTRAINT `fk_raise_request_2` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`)
);











