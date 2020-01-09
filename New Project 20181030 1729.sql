-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.39-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema roadsigner
--

CREATE DATABASE IF NOT EXISTS roadsigner;
USE roadsigner;

--
-- Definition of table `audit`
--

DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `idaudit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `timer` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdby` int(10) unsigned NOT NULL,
  `action` varchar(200) NOT NULL,
  PRIMARY KEY (`idaudit`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audit`
--

/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` (`idaudit`,`timer`,`createdby`,`action`) VALUES 
 (1,'2020-01-03 13:35:49',1,'logged into the system at  Fri Jan 03 13:35:49 EAT 2020'),
 (2,'2020-01-03 13:37:46',1,'logged into the system at  Fri Jan 03 13:37:46 EAT 2020'),
 (3,'2020-01-03 13:41:56',1,'logged into the system at  Fri Jan 03 13:41:56 EAT 2020'),
 (4,'2020-01-03 13:42:22',1,'logged into the system at  Fri Jan 03 13:42:22 EAT 2020'),
 (5,'2020-01-03 13:55:00',1,'logged into the system at  Fri Jan 03 13:55:00 EAT 2020'),
 (6,'2020-01-03 13:57:41',1,'logged into the system at  Fri Jan 03 13:57:41 EAT 2020'),
 (7,'2020-01-03 14:15:07',1,'logged into the system at  Fri Jan 03 14:15:07 EAT 2020'),
 (8,'2020-01-03 14:18:08',1,'logged into the system at  Fri Jan 03 14:18:08 EAT 2020'),
 (9,'2020-01-03 18:03:02',1,'logged into the system at  Fri Jan 03 18:03:02 EAT 2020'),
 (10,'2020-01-03 18:10:48',1,'Registered road sign: Zebra Crossing'),
 (11,'2020-01-03 18:15:21',1,'Registered road sign: children crossing'),
 (12,'2020-01-03 18:22:19',1,'logged into the system at  Fri Jan 03 18:22:19 EAT 2020'),
 (13,'2020-01-03 18:22:53',1,'logged into the system at  Fri Jan 03 18:22:53 EAT 2020'),
 (14,'2020-01-03 18:28:52',1,'logged into the system at  Fri Jan 03 18:28:52 EAT 2020'),
 (15,'2020-01-03 18:29:37',1,'logged into the system at  Fri Jan 03 18:29:37 EAT 2020'),
 (16,'2020-01-03 18:30:02',1,'logged into the system at  Fri Jan 03 18:30:02 EAT 2020'),
 (17,'2020-01-03 18:57:12',1,'logged into the system at  Fri Jan 03 18:57:12 EAT 2020'),
 (18,'2020-01-03 19:00:21',1,'logged into the system at  Fri Jan 03 19:00:21 EAT 2020'),
 (19,'2020-01-03 19:00:25',1,'logged into the system at  Fri Jan 03 19:00:25 EAT 2020'),
 (20,'2020-01-03 19:01:20',1,'logged into the system at  Fri Jan 03 19:01:20 EAT 2020'),
 (21,'2020-01-03 19:05:23',1,'logged into the system at  Fri Jan 03 19:05:23 EAT 2020'),
 (22,'2020-01-03 19:09:56',1,'logged into the system at  Fri Jan 03 19:09:56 EAT 2020'),
 (23,'2020-01-03 19:10:31',1,'logged into the system at  Fri Jan 03 19:10:31 EAT 2020'),
 (24,'2020-01-03 19:11:57',1,'logged into the system at  Fri Jan 03 19:11:57 EAT 2020'),
 (25,'2020-01-09 20:00:16',1,'logged into the system at  Thu Jan 09 20:00:16 EAT 2020');
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;


--
-- Definition of table `roadsigns`
--

DROP TABLE IF EXISTS `roadsigns`;
CREATE TABLE `roadsigns` (
  `idsigns` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `signid` int(10) unsigned NOT NULL,
  `lat` varchar(45) NOT NULL,
  `longitude` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  `dateinstalled` datetime NOT NULL,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `placeName` varchar(100) NOT NULL,
  `roadName` varchar(100) NOT NULL,
  `status` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idsigns`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roadsigns`
--

/*!40000 ALTER TABLE `roadsigns` DISABLE KEYS */;
/*!40000 ALTER TABLE `roadsigns` ENABLE KEYS */;


--
-- Definition of table `signs`
--

DROP TABLE IF EXISTS `signs`;
CREATE TABLE `signs` (
  `idsigns` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `signname` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `createdBy` int(10) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `photo` varchar(100) NOT NULL,
  PRIMARY KEY (`idsigns`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `signs`
--

/*!40000 ALTER TABLE `signs` DISABLE KEYS */;
INSERT INTO `signs` (`idsigns`,`signname`,`description`,`createdBy`,`createdOn`,`photo`) VALUES 
 (1,'Bumps ahead','This sign indicates the prescence of a bumps on the road. It is an indicator that vehicles should slow down because of activity close to the road ',0,'2020-01-01 18:55:51','none'),
 (2,'Zebra Crossing','This indicates a zebra crossing ',0,'2020-01-03 18:10:48','none'),
 (3,'children crossing','This indicates that theres a nearby school ',1,'2020-01-03 18:15:21','none');
/*!40000 ALTER TABLE `signs` ENABLE KEYS */;


--
-- Definition of table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `idstatus` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idstatus`),
  KEY `FK_status_1` (`createdBy`),
  CONSTRAINT `FK_status_1` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`idstatus`,`name`,`description`,`createdBy`) VALUES 
 (1,'active','the paramedics have been deployed',NULL),
 (2,'inactive','Inactivity',1),
 (3,'vandalized','spoilt road sign ',1);
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `idusers` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `pword` varchar(45) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `department` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` int(10) unsigned DEFAULT NULL,
  `staffID` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idusers`),
  KEY `FK_user_1` (`createdBy`),
  KEY `FK_user_2` (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`idusers`,`username`,`pword`,`createdAt`,`lastLogin`,`department`,`groupID`,`name`,`email`,`phone`,`staffID`,`statusID`,`createdBy`) VALUES 
 (1,'root','root','1000-01-01 00:00:00','1000-01-01 00:00:00',1,1,'Administrator Road signs','roadsigner@gmail.com ',720123456,1,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `idgroups` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `createdBy` int(10) unsigned DEFAULT NULL,
  `statusID` int(10) unsigned DEFAULT NULL,
  `responsibilities` varchar(100) NOT NULL,
  PRIMARY KEY (`idgroups`),
  KEY `FK_groups_1` (`statusID`),
  KEY `FK_groups_2` (`createdBy`),
  CONSTRAINT `FK_group_1` FOREIGN KEY (`createdBy`) REFERENCES `user` (`idusers`),
  CONSTRAINT `FK_group_2` FOREIGN KEY (`statusID`) REFERENCES `status` (`idstatus`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usergroup`
--

/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
INSERT INTO `usergroup` (`idgroups`,`name`,`createdAt`,`createdBy`,`statusID`,`responsibilities`) VALUES 
 (1,'admin',NULL,1,1,'overall administrator');
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
