-- MySQL Script generated by MySQL Workbench
-- 08/21/17 22:11:56
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema my_bd
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `my_bd` ;

-- -----------------------------------------------------
-- Schema my_bd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `my_bd` DEFAULT CHARACTER SET utf8 ;
USE `my_bd` ;

-- -----------------------------------------------------
-- Table `my_bd`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_bd`.`account` ;

CREATE TABLE IF NOT EXISTS `my_bd`.`account` (
  `account_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_name` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `additional_info` VARCHAR(512) NULL,
  `password` VARCHAR(128) NOT NULL,
  `creation_date` VARCHAR(64) NULL,
  `image` MEDIUMBLOB NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  UNIQUE INDEX `account_Id_UNIQUE` (`account_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`account_name`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `my_bd`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_bd`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `my_bd`.`user_roles` (
  `user_role_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_name` VARCHAR(64) NOT NULL,
  `role` VARCHAR(64) NULL,
  PRIMARY KEY (`user_role_id`),
  CONSTRAINT `fk_user_roles_Account1`
  FOREIGN KEY (`account_name`)
  REFERENCES `my_bd`.`account` (`account_name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `my_bd`.`persistent_logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_bd`.`persistent_logins` ;

CREATE TABLE IF NOT EXISTS `my_bd`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP(4) NOT NULL,
  PRIMARY KEY (`series`))
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `my_bd`.`account`
-- -----------------------------------------------------
START TRANSACTION;
USE `my_bd`;
INSERT INTO `my_bd`.`account` (`account_id`, `account_name`, `email`, `additional_info`, `password`, `creation_date`, `image`, `enabled`) VALUES (1, 'accountOne', 'accountOne@account.com', 'live in Spb ', '123456', NULL, NULL, 1);
INSERT INTO `my_bd`.`account` (`account_id`, `account_name`, `email`, `additional_info`, `password`, `creation_date`, `image`, `enabled`) VALUES (2, 'accountTwo', 'accountTwo@account.com', 'live in Moscow ', '123456', NULL, NULL, 1);
INSERT INTO `my_bd`.`account` (`account_id`, `account_name`, `email`, `additional_info`, `password`, `creation_date`, `image`, `enabled`) VALUES (3, 'mkyong', 'mkyong@account.com', 'live in Moscow  2', '123456', NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `my_bd`.`user_roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `my_bd`;
INSERT INTO `my_bd`.`user_roles` (`user_role_id`, `account_name`, `role`) VALUES (1, 'accountOne', 'ROLE_ADMIN');
INSERT INTO `my_bd`.`user_roles` (`user_role_id`, `account_name`, `role`) VALUES (2, 'accountTwo', 'ROLE_ADMIN');
INSERT INTO `my_bd`.`user_roles` (`user_role_id`, `account_name`, `role`) VALUES (3, 'mkyong', 'ROLE_ADMIN');
INSERT INTO `my_bd`.`user_roles` (`user_role_id`, `account_name`, `role`) VALUES (4, 'mkyong', 'ROLE_USER');

COMMIT;

