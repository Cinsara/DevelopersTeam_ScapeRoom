-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema EscapeRoom
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `room` ;

CREATE TABLE IF NOT EXISTS `room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `room_theme` ENUM("Love Affair", "Fantastic", "Mystery", "Sci-Fi") NOT NULL,
  `room_difficulty` ENUM("Easy", "Medium", "Hard") NOT NULL,
  PRIMARY KEY (`room_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `clue` ;

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
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prop`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `prop` ;

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
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `costumer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `costumer` ;

CREATE TABLE IF NOT EXISTS `costumer` (
  `costumer_id` INT NOT NULL AUTO_INCREMENT,
  `costumner_name` VARCHAR(45) NOT NULL,
  `costumer_lastname` VARCHAR(45) NOT NULL,
  `costumer_dob` DATE NULL,
  `costumer_mail` VARCHAR(45) NOT NULL,
  `costumer_phone_number` VARCHAR(45) NULL,
  `costumer_notifications` TINYINT(1) NOT NULL,
  `costumer_signedUpNotifOn` DATE NULL,
  PRIMARY KEY (`costumer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `game` ;

CREATE TABLE IF NOT EXISTS `game` (
  `game_id` INT NOT NULL AUTO_INCREMENT,
  `game_date` DATETIME NOT NULL,
  `game_sucess` TINYINT(1) NULL,
  `game_lengthInSec` INT NULL,
  `room_room_id` INT NOT NULL,
  `captain_costumer_id` INT NULL,
  PRIMARY KEY (`game_id`),
  INDEX `fk_game_room1_idx` (`room_room_id` ASC) VISIBLE,
  INDEX `fk_game_costumer1_idx` (`captain_costumer_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_room1`
    FOREIGN KEY (`room_room_id`)
    REFERENCES `room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_game_costumer1`
    FOREIGN KEY (`captain_costumer_id`)
    REFERENCES `costumer` (`costumer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `game_has_costumer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `game_has_costumer` ;

CREATE TABLE IF NOT EXISTS `game_has_costumer` (
  `game_game_id` INT NOT NULL,
  `costumer_costumer_id` INT NOT NULL,
  PRIMARY KEY (`game_game_id`, `costumer_costumer_id`),
  INDEX `fk_game_has_costumer_costumer1_idx` (`costumer_costumer_id` ASC) VISIBLE,
  INDEX `fk_game_has_costumer_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_game_has_costumer_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_game_has_costumer_costumer1`
    FOREIGN KEY (`costumer_costumer_id`)
    REFERENCES `costumer` (`costumer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tiquet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tiquet` ;

CREATE TABLE IF NOT EXISTS `tiquet` (
  `tiquet_id` INT NOT NULL AUTO_INCREMENT,
  `tiquet_price` FLOAT NOT NULL,
  `tiquet_saleDate` DATE NOT NULL,
  `captain_costumer_id` INT NOT NULL,
  `game_game_id` INT NOT NULL,
  PRIMARY KEY (`tiquet_id`),
  INDEX `fk_tiquet_costumer1_idx` (`captain_costumer_id` ASC) VISIBLE,
  INDEX `fk_tiquet_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_tiquet_costumer1`
    FOREIGN KEY (`captain_costumer_id`)
    REFERENCES `costumer` (`costumer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tiquet_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `certificate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `certificate` ;

CREATE TABLE IF NOT EXISTS `certificate` (
  `certificate_id` INT NOT NULL AUTO_INCREMENT,
  `game_game_id` INT NOT NULL,
  `costumer_costumer_id` INT NOT NULL,
  PRIMARY KEY (`certificate_id`),
  INDEX `fk_certificate_game1_idx` (`game_game_id` ASC) VISIBLE,
  INDEX `fk_certificate_costumer1_idx` (`costumer_costumer_id` ASC) VISIBLE,
  CONSTRAINT `fk_certificate_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_certificate_costumer1`
    FOREIGN KEY (`costumer_costumer_id`)
    REFERENCES `costumer` (`costumer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `reward`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `reward` ;

CREATE TABLE IF NOT EXISTS `reward` (
  `reward_id` INT NOT NULL AUTO_INCREMENT,
  `costumer_costumer_id` INT NOT NULL,
  `game_game_id` INT NOT NULL,
  PRIMARY KEY (`reward_id`),
  INDEX `fk_reward_costumer1_idx` (`costumer_costumer_id` ASC) VISIBLE,
  INDEX `fk_reward_game1_idx` (`game_game_id` ASC) VISIBLE,
  CONSTRAINT `fk_reward_costumer1`
    FOREIGN KEY (`costumer_costumer_id`)
    REFERENCES `costumer` (`costumer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_reward_game1`
    FOREIGN KEY (`game_game_id`)
    REFERENCES `game` (`game_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notification` ;

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `notification_content` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`notification_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
