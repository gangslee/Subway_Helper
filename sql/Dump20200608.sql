-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: temp4
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
-- Table structure for table `stationinfo`
--

DROP TABLE IF EXISTS `stationinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stationinfo` (
  `stationName` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `address` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tel` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stationinfo`
--

LOCK TABLES `stationinfo` WRITE;
/*!40000 ALTER TABLE `stationinfo` DISABLE KEYS */;
INSERT INTO `stationinfo` VALUES ('건대입구','서울특별시 광진구 아차산로 243','02-6110-2121'),('고속터미널','서울특별시 서초구 신반포로 188','02-6110-3391'),('광화문','서울특별시 종로구 세종대로 172','02-6311-5331'),('교대','서울특별시 서초구 서초대로 294','02-6110-2231'),('군자','서울특별시 광진구 천호대로 550','02-6311-5441'),('동대문역사문화공원','서울특별시 중구 을지로 279','02-6110-2051'),('동두천','경기도 동두천시 평화로 2687','1544-7788'),('사당','서울특별시 동작구 남부순환로 2089','02-6110-2261'),('선릉','서울특별시 강남구 테헤란로 340','02-6110-2201'),('아차산','서울특별시 광진구 천호대로 657','02-6311-5451'),('어린이대공원','서울특별시 광진구 능동로 210','02-6311-7261'),('옥수','서울특별시 성동구 동호로 21','02-6110-3351'),('왕십리','서울특별시 성동구 왕십리로 300','02-6110-2081'),('이태원','서울특별시 용산구 이태원로 177','02-6311-6301'),('장지','서울특별시 송파구 송파대로 82','02-6311-8191'),('청구','서울특별시 중구 청구로 77','02-6311-5371'),('학동','서울특별시 강남구 학동로 180','02-6311-7311'),('혜화','서울특별시 종로구 대학로 120','02-6110-4201');
/*!40000 ALTER TABLE `stationinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stationline`
--

DROP TABLE IF EXISTS `stationline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stationline` (
  `stationInfo_stationName` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `line` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`stationInfo_stationName`,`line`),
  KEY `fk_station_line_station_info1_idx` (`stationInfo_stationName`),
  CONSTRAINT `fk_station_line_station_info1` FOREIGN KEY (`stationInfo_stationName`) REFERENCES `stationinfo` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stationline`
--

LOCK TABLES `stationline` WRITE;
/*!40000 ALTER TABLE `stationline` DISABLE KEYS */;
INSERT INTO `stationline` VALUES ('건대입구','2'),('건대입구','7'),('고속터미널','3'),('고속터미널','7'),('고속터미널','9'),('광화문','5'),('교대','2'),('교대','3'),('군자','5'),('군자','7'),('동대문역사문화공원','2'),('동대문역사문화공원','4'),('동대문역사문화공원','5'),('동두천','1'),('사당','2'),('사당','4'),('선릉','2'),('선릉','분당'),('아차산','5'),('어린이대공원','7'),('옥수','3'),('옥수','경의중앙'),('왕십리','2'),('왕십리','5'),('왕십리','경의중앙'),('이태원','6'),('장지','8'),('청구','5'),('청구','6'),('학동','7'),('혜화','4');
/*!40000 ALTER TABLE `stationline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `stationInfo_stationName` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `storetype_storeID` int NOT NULL,
  `floor` int NOT NULL,
  PRIMARY KEY (`stationInfo_stationName`,`storetype_storeID`,`floor`),
  KEY `fk_store_storetype1_idx` (`storetype_storeID`),
  CONSTRAINT `fk_store_station_info` FOREIGN KEY (`stationInfo_stationName`) REFERENCES `stationinfo` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_store_storetype1` FOREIGN KEY (`storetype_storeID`) REFERENCES `storetype` (`storeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('동대문역사문화공원',1,-1),('옥수',1,1),('이태원',1,1),('혜화',1,-1),('고속터미널',2,1),('교대',2,1),('선릉',2,-1),('왕십리',2,-1),('광화문',3,-3),('군자',3,-1),('사당',3,-1),('어린이대공원',3,-1),('왕십리',3,2),('동대문역사문화공원',4,-1),('동두천',4,2),('청구',4,1);
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storetype`
--

DROP TABLE IF EXISTS `storetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storetype` (
  `storeID` int NOT NULL,
  `storeType` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`storeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storetype`
--

LOCK TABLES `storetype` WRITE;
/*!40000 ALTER TABLE `storetype` DISABLE KEYS */;
INSERT INTO `storetype` VALUES (1,'7-Eleven'),(2,'CU'),(3,'GS'),(4,'MiniStop');
/*!40000 ALTER TABLE `storetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet`
--

DROP TABLE IF EXISTS `toilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `toilet` (
  `stationInfo_stationName` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `position` tinyint NOT NULL,
  `floor` int NOT NULL,
  PRIMARY KEY (`stationInfo_stationName`,`position`,`floor`),
  KEY `fk_toilet_station_info1_idx` (`stationInfo_stationName`),
  CONSTRAINT `fk_toilet_station_info1` FOREIGN KEY (`stationInfo_stationName`) REFERENCES `stationinfo` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet`
--

LOCK TABLES `toilet` WRITE;
/*!40000 ALTER TABLE `toilet` DISABLE KEYS */;
INSERT INTO `toilet` VALUES ('건대입구',1,-1),('건대입구',1,2),('고속터미널',0,1),('고속터미널',1,-1),('고속터미널',1,2),('광화문',1,1),('교대',1,-1),('군자',1,-1),('동대문역사문화공원',0,1),('동대문역사문화공원',1,-1),('동두천',1,1),('사당',0,-1),('선릉',0,-1),('아차산',1,-1),('어린이대공원',0,-1),('옥수',0,1),('왕십리',0,2),('왕십리',1,-1),('이태원',1,-1),('장지',1,-1),('청구',0,-1),('청구',1,-3),('학동',1,-1),('혜화',1,-1);
/*!40000 ALTER TABLE `toilet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vanding`
--

DROP TABLE IF EXISTS `vanding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vanding` (
  `stationInfo_stationName` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `vandingtype_vandingID` int NOT NULL,
  `floor` int NOT NULL,
  PRIMARY KEY (`stationInfo_stationName`,`vandingtype_vandingID`,`floor`),
  KEY `fk_vanding_station_info1_idx` (`stationInfo_stationName`),
  KEY `fk_vanding_vandingtype1_idx` (`vandingtype_vandingID`),
  CONSTRAINT `fk_vanding_station_info1` FOREIGN KEY (`stationInfo_stationName`) REFERENCES `stationinfo` (`stationName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_vanding_vandingtype1` FOREIGN KEY (`vandingtype_vandingID`) REFERENCES `vandingtype` (`vandingID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vanding`
--

LOCK TABLES `vanding` WRITE;
/*!40000 ALTER TABLE `vanding` DISABLE KEYS */;
INSERT INTO `vanding` VALUES ('건대입구',1,2),('고속터미널',1,1),('광화문',1,-4),('교대',1,-2),('군자',1,-2),('동대문역사문화공원',1,-3),('동대문역사문화공원',1,-1),('동두천',1,1),('사당',1,-3),('사당',1,-2),('아차산',1,-1),('어린이대공원',1,-2),('왕십리',1,-2),('이태원',1,-2),('장지',1,-1),('청구',1,-4),('학동',1,-2),('건대입구',2,-1),('고속터미널',2,-1),('동대문역사문화공원',2,-2),('옥수',2,2),('왕십리',2,2),('이태원',2,-2),('청구',2,-2),('혜화',2,-2),('고속터미널',3,-2),('광화문',3,-3),('교대',3,-1),('군자',3,-1),('선릉',3,-1),('어린이대공원',3,-1),('왕십리',3,1),('이태원',3,-1),('청구',3,-1);
/*!40000 ALTER TABLE `vanding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vandingtype`
--

DROP TABLE IF EXISTS `vandingtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vandingtype` (
  `vandingID` int NOT NULL,
  `vandingType` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`vandingID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vandingtype`
--

LOCK TABLES `vandingtype` WRITE;
/*!40000 ALTER TABLE `vandingtype` DISABLE KEYS */;
INSERT INTO `vandingtype` VALUES (1,'drink'),(2,'snack'),(3,'tissue');
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

-- Dump completed on 2020-06-08  3:26:28
