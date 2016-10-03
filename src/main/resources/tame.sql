DROP TABLE IF EXISTS `tame`;
CREATE SCHEMA `tame` ;

DROP TABLE IF EXISTS `active`;
CREATE TABLE `active` (
  `taskId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `taskName` varchar(45) DEFAULT NULL,
  `taskExpirationDate` datetime DEFAULT NULL,
  `taskPriority` varchar(45) DEFAULT NULL,
  `taskStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `id_UNIQUE` (`taskId`)
);

DROP TABLE IF EXISTS `completed`;
CREATE TABLE `completed` (
  `taskId` int(10) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(45) NOT NULL,
  `taskExpirationDate` varchar(45) NOT NULL,
  `taskPriority` varchar(45) NOT NULL,
  `taskStatus` varchar(45) NOT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `id_UNIQUE` (`taskId`)
)