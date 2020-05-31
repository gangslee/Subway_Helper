-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: temp2
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` varchar(45) NOT NULL,
  `pw` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('test','test');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_info`
--

DROP TABLE IF EXISTS `station_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_info` (
  `stationName` varchar(45) NOT NULL,
  `transfer` tinyint NOT NULL,
  PRIMARY KEY (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_info`
--

LOCK TABLES `station_info` WRITE;
/*!40000 ALTER TABLE `station_info` DISABLE KEYS */;
INSERT INTO `station_info` VALUES ('건대입구',1),('고속터미널',1),('광화문',0),('교대',1),('군자',1),('동대문역사문화공원',1),('동두천',0),('사당',1),('선릉',1),('아차산',0),('어린이대공원',0),('옥수',1),('왕십리',1),('이태원',0),('장지',0),('청구',1),('학동',0),('혜화',0);
/*!40000 ALTER TABLE `station_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_line`
--

DROP TABLE IF EXISTS `station_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_line` (
  `station_info_stationName` varchar(45) NOT NULL,
  `line` varchar(20) NOT NULL,
  PRIMARY KEY (`station_info_stationName`,`line`),
  KEY `fk_station_line_station_info1_idx` (`station_info_stationName`),
  CONSTRAINT `fk_station_line_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_line`
--

LOCK TABLES `station_line` WRITE;
/*!40000 ALTER TABLE `station_line` DISABLE KEYS */;
INSERT INTO `station_line` VALUES ('건대입구','2'),('건대입구','7'),('고속터미널','3'),('고속터미널','7'),('고속터미널','9'),('광화문','5'),('교대','2'),('교대','3'),('군자','5'),('군자','7'),('동대문역사문화공원','2'),('동대문역사문화공원','4'),('동대문역사문화공원','5'),('동두천','1'),('사당','2'),('사당','4'),('선릉','2'),('선릉','분당'),('아차산','5'),('어린이대공원','7'),('옥수','3'),('옥수','경의중앙'),('왕십리','2'),('왕십리','5'),('왕십리','경의중앙'),('이태원','6'),('장지','8'),('청구','5'),('청구','6'),('학동','7'),('혜화','4');
/*!40000 ALTER TABLE `station_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `station_info_stationName` varchar(45) NOT NULL,
  `storeType_storeType` varchar(45) NOT NULL,
  `floor` int NOT NULL,
  PRIMARY KEY (`station_info_stationName`,`floor`),
  KEY `fk_store_storeType1_idx` (`storeType_storeType`),
  CONSTRAINT `fk_store_station_info` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_store_storeType1` FOREIGN KEY (`storeType_storeType`) REFERENCES `storetype` (`storeType`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('동대문역사문화공원','7-Eleven',-1),('옥수','7-Eleven',1),('이태원','7-Eleven',1),('혜화','7-Eleven',-1),('고속터미널','CU',1),('광화문','CU',-3),('교대','CU',0),('선릉','CU',-1),('왕십리','CU',-1),('군자','GS',-1),('사당','GS',0),('어린이대공원','GS',-1),('왕십리','GS',2),('동대문역사문화공원','MiniStop',1),('동두천','MiniStop',2),('청구','MiniStop',0);
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storetype`
--

DROP TABLE IF EXISTS `storetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storetype` (
  `storeType` varchar(45) NOT NULL,
  PRIMARY KEY (`storeType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storetype`
--

LOCK TABLES `storetype` WRITE;
/*!40000 ALTER TABLE `storetype` DISABLE KEYS */;
INSERT INTO `storetype` VALUES ('7-Eleven'),('CU'),('GS'),('MiniStop');
/*!40000 ALTER TABLE `storetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet`
--

DROP TABLE IF EXISTS `toilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `toilet` (
  `floor` int NOT NULL,
  `position` tinyint NOT NULL,
  `station_info_stationName` varchar(45) NOT NULL,
  PRIMARY KEY (`floor`,`station_info_stationName`),
  KEY `fk_toilet_station_info1_idx` (`station_info_stationName`),
  CONSTRAINT `fk_toilet_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet`
--

LOCK TABLES `toilet` WRITE;
/*!40000 ALTER TABLE `toilet` DISABLE KEYS */;
INSERT INTO `toilet` VALUES (-3,1,'광화문'),(-3,1,'청구'),(-1,1,'건대입구'),(-1,1,'고속터미널'),(-1,1,'교대'),(-1,1,'군자'),(-1,1,'동대문역사문화공원'),(-1,0,'사당'),(-1,0,'선릉'),(-1,1,'아차산'),(-1,0,'어린이대공원'),(-1,1,'왕십리'),(-1,1,'이태원'),(-1,1,'장지'),(-1,0,'청구'),(-1,1,'학동'),(-1,1,'혜화'),(1,0,'고속터미널'),(1,0,'동대문역사문화공원'),(1,1,'동두천'),(1,0,'옥수'),(2,1,'건대입구'),(2,1,'고속터미널'),(2,0,'왕십리');
/*!40000 ALTER TABLE `toilet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vanding`
--

DROP TABLE IF EXISTS `vanding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vanding` (
  `machineID` int NOT NULL,
  `floor` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `station_info_stationName` varchar(45) NOT NULL,
  PRIMARY KEY (`machineID`),
  KEY `fk_vanding_station_info1_idx` (`station_info_stationName`),
  KEY `fk_vandingType_type1` (`type`),
  CONSTRAINT `fk_vanding_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sada` FOREIGN KEY (`type`) REFERENCES `vandingtype` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vanding`
--

LOCK TABLES `vanding` WRITE;
/*!40000 ALTER TABLE `vanding` DISABLE KEYS */;
INSERT INTO `vanding` VALUES (2712,-1,'drink','장지'),(12312,2,'snack','왕십리'),(12358,-1,'tissue','청구'),(12485,-4,'drink','광화문'),(13254,-1,'snack','건대입구'),(21941,-2,'snack','이태원'),(23452,-3,'tissue','광화문'),(23458,-1,'drink','아차산'),(32491,-2,'drink','왕십리'),(34681,-2,'drink','군자'),(37529,-2,'drink','사당'),(41251,-2,'drink','이태원'),(43676,1,'drink','고속터미널'),(45034,-3,'drink','동대문역사문화공원'),(45654,-2,'drink','교대'),(45801,-1,'tissue','군자'),(48222,-1,'tissue','선릉'),(48488,-3,'drink','사당'),(56834,-4,'drink','청구'),(57103,-2,'tissue','고속터미널'),(58971,-1,'tissue','이태원'),(62109,2,'drink','건대입구'),(63471,-2,'snack','청구'),(64361,-1,'drink','동대문역사문화공원'),(65709,1,'tissue','왕십리'),(69302,-1,'snack','고속터미널'),(72536,-2,'drink','어린이대공원'),(74712,2,'snack','옥수'),(78954,-2,'drink','학동'),(83407,-1,'tissue','어린이대공원'),(87533,-1,'tissue','교대'),(88854,-2,'snack','혜화'),(90124,1,'drink','동두천'),(95622,-2,'snack','동대문역사문화공원');
/*!40000 ALTER TABLE `vanding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vandingtype`
--

DROP TABLE IF EXISTS `vandingtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vandingtype` (
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vandingtype`
--

LOCK TABLES `vandingtype` WRITE;
/*!40000 ALTER TABLE `vandingtype` DISABLE KEYS */;
INSERT INTO `vandingtype` VALUES ('drink'),('snack'),('tissue');
/*!40000 ALTER TABLE `vandingtype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-31 23:15:01
