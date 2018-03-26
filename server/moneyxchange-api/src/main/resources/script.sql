DROP DATABASE IF EXISTS moneyxchange;

CREATE DATABASE IF NOT EXISTS moneyxchange;

USE moneyxchange;

CREATE TABLE IF NOT EXISTS `user` (
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'The Id of the user',
    `username` VARCHAR(25) NOT NULL COMMENT 'The username of the user',
	`password` VARCHAR(255) NOT NULL COMMENT 'The encrypted password of the user',
    `name` VARCHAR(25) NOT NULL COMMENT 'The name of the user',
    `lastname` VARCHAR(25) NOT NULL COMMENT 'The last name of the user',
    CONSTRAINT UC_User UNIQUE (`username`)
) ENGINE = InnoDB AUTO_INCREMENT = 1;

-- Test user 
-- Username: oscar.jara
-- Password: admin
INSERT INTO user(username, password, name, lastname) VALUES ('oscar.jara', '$2a$10$i/mwtO2QerGswQ0WNBN2i.Za3vDF0SVvvQibPG7oVcFO5A9C1Apty', 'Oscar', 'Jara');