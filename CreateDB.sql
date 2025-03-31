CREATE DATABASE IF NOT EXISTS `escape_room`;
USE `escape_room`;

CREATE TABLE IF NOT EXISTS `room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `room_name` VARCHAR(45) UNIQUE,
  `room_theme` ENUM("Love Affair", "Fantastic", "Mystery", "Sci-Fi") NOT NULL,
  `room_difficulty` ENUM("Easy", "Medium", "Hard") NOT NULL,
  PRIMARY KEY (`room_id`));

CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `customer_name` VARCHAR(45) NOT NULL,
  `customer_lastname` VARCHAR(45) NOT NULL,
  `customer_dob` DATE NOT NULL,
  `customer_mail` VARCHAR(45) NOT NULL,
  `customer_phone_number` VARCHAR(45) NULL,
  `customer_notifications` TINYINT(1) NOT NULL,
  `customer_signedUpNotifOn` DATE NULL,
  PRIMARY KEY (`customer_id`));

CREATE TABLE IF NOT EXISTS `game` (
  `game_id` INT NOT NULL AUTO_INCREMENT,
  `game_date` DATE NOT NULL,
  `game_success` TINYINT(1) NULL,
  `game_lengthInSec` INT NULL,
  `room_room_id` INT NOT NULL,
  `captain_customer_id` INT NULL,
  PRIMARY KEY (`game_id`),
  INDEX `fk_game_room1_idx` (`room_room_id` ASC) VISIBLE,
  INDEX `fk_game_customer1_idx` (`captain_customer_id` ASC) VISIBLE,
  CONSTRAINT unique_game_per_date_day
	UNIQUE (`game_date`, `room_room_id`),
  CONSTRAINT `fk_game_room1`
    FOREIGN KEY (`room_room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_game_customer1`
    FOREIGN KEY (`captain_customer_id`)
    REFERENCES `customer` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `game_has_customer` (
  `game_game_id` INT NOT NULL,
  `customer_customer_id` INT NOT NULL,
  PRIMARY KEY (`game_game_id`, `customer_customer_id`),
  INDEX `fk_game_has_customer_customer1_idx` (`customer_customer_id` ASC) VISIBLE,
  INDEX `fk_game_has_customer_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_has_customer_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_game_has_customer_customer1`
    FOREIGN KEY (`customer_customer_id`)
    REFERENCES `customer` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE IF NOT EXISTS `clue` (
  `clue_id` INT NOT NULL AUTO_INCREMENT,
  `clue_type` ENUM("Enigma", "Indication") NOT NULL,
  `room_room_id` INT NOT NULL,
  PRIMARY KEY (`clue_id`),
  INDEX `fk_clues_rooms1_idx` (`room_room_id` ASC) VISIBLE,
  CONSTRAINT `fk_clues_rooms1`
    FOREIGN KEY (`room_room_id`)
    REFERENCES `room` (`room_id`)
	ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE IF NOT EXISTS `game_uses_clue` (
  `game_game_id` INT NOT NULL,
  `clue_clue_id` INT NOT NULL,
  PRIMARY KEY (`game_game_id`, `clue_clue_id`),
  INDEX `fk_game_uses_clue_clue1_idx` (`clue_clue_id` ASC) VISIBLE,
  INDEX `fk_game_uses_clue_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_uses_clue_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_game_uses_clue_clue1`
    FOREIGN KEY (`clue_clue_id`)
    REFERENCES `clue` (`clue_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `prop` (
  `prop_id` INT NOT NULL AUTO_INCREMENT,
  `prop_type` ENUM("Spade", "Closet", "Mountain") NOT NULL,
  `prop_value` INT NOT NULL,
  `room_room_id` INT NOT NULL,
  PRIMARY KEY (`prop_id`),
  INDEX `fk_props_rooms_idx` (`room_room_id` ASC) VISIBLE,
  CONSTRAINT `fk_props_rooms`
    FOREIGN KEY (`room_room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `ticket_price` FLOAT NOT NULL,
  `ticket_saleDate` DATE NOT NULL,
  `captain_customer_id` INT NOT NULL,
  `game_game_id` INT NOT NULL,
  PRIMARY KEY (`ticket_id`),
  INDEX `fk_ticket_customer1_idx` (`captain_customer_id` ASC) VISIBLE,
  INDEX `fk_ticket_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_customer1`
    FOREIGN KEY (`captain_customer_id`)
    REFERENCES `customer` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ticket_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `certificate` (
  `certificate_id` INT NOT NULL AUTO_INCREMENT,
  `game_game_id` INT NOT NULL,
  `customer_customer_id` INT NOT NULL,
  PRIMARY KEY (`certificate_id`),
  INDEX `fk_certificate_game1_idx` (`game_game_id` ASC) VISIBLE,
  INDEX `fk_certificate_customer1_idx` (`customer_customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_certificate_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_certificate_customer1`
    FOREIGN KEY (`customer_customer_id`)
    REFERENCES `customer` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE IF NOT EXISTS `reward` (
  `reward_id` INT NOT NULL AUTO_INCREMENT,
  `customer_customer_id` INT NOT NULL,
  `game_game_id` INT NOT NULL,
  PRIMARY KEY (`reward_id`),
  INDEX `fk_reward_customer1_idx` (`customer_customer_id` ASC) VISIBLE,
  INDEX `fk_reward_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_reward_customer1`
    FOREIGN KEY (`customer_customer_id`)
    REFERENCES `customer` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_reward_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `notification_content` MEDIUMTEXT NOT NULL,
  `notification_dateSent` DATE NOT NULL,
  PRIMARY KEY (`notification_id`));
