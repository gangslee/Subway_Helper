/* Database export results for db SubwayHelperDB */

/* Preserve session variables */
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS=0;

/* Export data */

/* Table structure for admin */
CREATE TABLE `admin` (
  `id` varchar(45) NOT NULL,
  `pw` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table admin */
INSERT INTO `admin` VALUES ("test","test");

/* Table structure for station_info */
CREATE TABLE `station_info` (
  `stationName` varchar(45) NOT NULL,
  `toilet` tinyint(4) NOT NULL,
  `store` tinyint(4) NOT NULL,
  `vanding` tinyint(4) NOT NULL,
  `transfer` tinyint(4) NOT NULL,
  PRIMARY KEY (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table station_info */
INSERT INTO `station_info` VALUES ("건대입구",1,0,1,1);
INSERT INTO `station_info` VALUES ("고속터미널",1,1,1,1);
INSERT INTO `station_info` VALUES ("광화문",1,0,1,0);
INSERT INTO `station_info` VALUES ("교대",1,1,1,1);
INSERT INTO `station_info` VALUES ("군자",1,1,1,1);
INSERT INTO `station_info` VALUES ("동대문역사문화공원",1,1,1,1);
INSERT INTO `station_info` VALUES ("동두천",1,1,0,0);
INSERT INTO `station_info` VALUES ("사당",1,1,1,1);
INSERT INTO `station_info` VALUES ("선릉",1,1,1,1);
INSERT INTO `station_info` VALUES ("아차산",1,0,0,0);
INSERT INTO `station_info` VALUES ("어린이대공원",1,1,1,0);
INSERT INTO `station_info` VALUES ("옥수",1,0,1,1);
INSERT INTO `station_info` VALUES ("왕십리",1,1,1,1);
INSERT INTO `station_info` VALUES ("이태원",1,1,1,0);
INSERT INTO `station_info` VALUES ("장지",1,0,1,0);
INSERT INTO `station_info` VALUES ("청구",1,1,1,1);
INSERT INTO `station_info` VALUES ("학동",1,0,1,0);
INSERT INTO `station_info` VALUES ("혜화",1,1,1,0);

/* Table structure for station_line */
CREATE TABLE `station_line` (
  `station_info_stationName` varchar(45) NOT NULL,
  `line` varchar(20) NOT NULL,
  PRIMARY KEY (`station_info_stationName`,`line`),
  KEY `fk_station_line_station_info1_idx` (`station_info_stationName`),
  CONSTRAINT `fk_station_line_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table station_line */
INSERT INTO `station_line` VALUES ("건대입구","2");
INSERT INTO `station_line` VALUES ("건대입구","7");
INSERT INTO `station_line` VALUES ("고속터미널","3");
INSERT INTO `station_line` VALUES ("고속터미널","7");
INSERT INTO `station_line` VALUES ("고속터미널","9");
INSERT INTO `station_line` VALUES ("광화문","5");
INSERT INTO `station_line` VALUES ("교대","2");
INSERT INTO `station_line` VALUES ("교대","3");
INSERT INTO `station_line` VALUES ("군자","5");
INSERT INTO `station_line` VALUES ("군자","7");
INSERT INTO `station_line` VALUES ("동대문역사문화공원","2");
INSERT INTO `station_line` VALUES ("동대문역사문화공원","4");
INSERT INTO `station_line` VALUES ("동대문역사문화공원","5");
INSERT INTO `station_line` VALUES ("동두천","1");
INSERT INTO `station_line` VALUES ("사당","2");
INSERT INTO `station_line` VALUES ("사당","4");
INSERT INTO `station_line` VALUES ("선릉","2");
INSERT INTO `station_line` VALUES ("선릉","분당");
INSERT INTO `station_line` VALUES ("아차산","5");
INSERT INTO `station_line` VALUES ("어린이대공원","7");
INSERT INTO `station_line` VALUES ("옥수","3");
INSERT INTO `station_line` VALUES ("옥수","경의중앙");
INSERT INTO `station_line` VALUES ("왕십리","2");
INSERT INTO `station_line` VALUES ("왕십리","5");
INSERT INTO `station_line` VALUES ("왕십리","경의중앙");
INSERT INTO `station_line` VALUES ("이태원","6");
INSERT INTO `station_line` VALUES ("장지","8");
INSERT INTO `station_line` VALUES ("청구","5");
INSERT INTO `station_line` VALUES ("청구","6");
INSERT INTO `station_line` VALUES ("학동","7");
INSERT INTO `station_line` VALUES ("혜화","4");

/* Table structure for store */
CREATE TABLE `store` (
  `station_info_stationName` varchar(45) NOT NULL,
  `storeType_storeType` varchar(45) NOT NULL,
  `floor` int(11) NOT NULL,
  PRIMARY KEY (`station_info_stationName`,`floor`),
  KEY `fk_store_storeType1_idx` (`storeType_storeType`),
  CONSTRAINT `fk_store_station_info` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`),
  CONSTRAINT `fk_store_storeType1` FOREIGN KEY (`storeType_storeType`) REFERENCES `storetype` (`storeType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table store */
INSERT INTO `store` VALUES ("동대문역사문화공원","7-Eleven",-1);
INSERT INTO `store` VALUES ("옥수","7-Eleven",1);
INSERT INTO `store` VALUES ("이태원","7-Eleven",1);
INSERT INTO `store` VALUES ("혜화","7-Eleven",-1);
INSERT INTO `store` VALUES ("고속터미널","CU",1);
INSERT INTO `store` VALUES ("광화문","CU",-3);
INSERT INTO `store` VALUES ("교대","CU",0);
INSERT INTO `store` VALUES ("선릉","CU",-1);
INSERT INTO `store` VALUES ("왕십리","CU",-1);
INSERT INTO `store` VALUES ("군자","GS",-1);
INSERT INTO `store` VALUES ("사당","GS",0);
INSERT INTO `store` VALUES ("어린이대공원","GS",-1);
INSERT INTO `store` VALUES ("왕십리","GS",2);
INSERT INTO `store` VALUES ("동대문역사문화공원","MiniStop",1);
INSERT INTO `store` VALUES ("동두천","MiniStop",2);
INSERT INTO `store` VALUES ("청구","MiniStop",0);

/* Table structure for storetype */
CREATE TABLE `storetype` (
  `storeType` varchar(45) NOT NULL,
  PRIMARY KEY (`storeType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table storetype */
INSERT INTO `storetype` VALUES ("7-Eleven");
INSERT INTO `storetype` VALUES ("CU");
INSERT INTO `storetype` VALUES ("GS");
INSERT INTO `storetype` VALUES ("MiniStop");

/* Table structure for toilet */
CREATE TABLE `toilet` (
  `floor` int(11) NOT NULL,
  `position` tinyint(4) NOT NULL,
  `station_info_stationName` varchar(45) NOT NULL,
  PRIMARY KEY (`floor`,`station_info_stationName`),
  KEY `fk_toilet_station_info1_idx` (`station_info_stationName`),
  CONSTRAINT `fk_toilet_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table toilet */
INSERT INTO `toilet` VALUES (-3,1,"광화문");
INSERT INTO `toilet` VALUES (-3,1,"청구");
INSERT INTO `toilet` VALUES (-1,1,"건대입구");
INSERT INTO `toilet` VALUES (-1,1,"고속터미널");
INSERT INTO `toilet` VALUES (-1,1,"교대");
INSERT INTO `toilet` VALUES (-1,1,"군자");
INSERT INTO `toilet` VALUES (-1,1,"동대문역사문화공원");
INSERT INTO `toilet` VALUES (-1,0,"사당");
INSERT INTO `toilet` VALUES (-1,0,"선릉");
INSERT INTO `toilet` VALUES (-1,1,"아차산");
INSERT INTO `toilet` VALUES (-1,0,"어린이대공원");
INSERT INTO `toilet` VALUES (-1,1,"왕십리");
INSERT INTO `toilet` VALUES (-1,1,"이태원");
INSERT INTO `toilet` VALUES (-1,1,"장지");
INSERT INTO `toilet` VALUES (-1,0,"청구");
INSERT INTO `toilet` VALUES (-1,1,"학동");
INSERT INTO `toilet` VALUES (-1,1,"혜화");
INSERT INTO `toilet` VALUES (1,0,"고속터미널");
INSERT INTO `toilet` VALUES (1,0,"동대문역사문화공원");
INSERT INTO `toilet` VALUES (1,1,"동두천");
INSERT INTO `toilet` VALUES (1,0,"옥수");
INSERT INTO `toilet` VALUES (2,1,"건대입구");
INSERT INTO `toilet` VALUES (2,1,"고속터미널");
INSERT INTO `toilet` VALUES (2,0,"왕십리");

/* Table structure for vanding */
CREATE TABLE `vanding` (
  `machineID` int(11) NOT NULL,
  `floor` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `station_info_stationName` varchar(45) NOT NULL,
  PRIMARY KEY (`machineID`),
  KEY `fk_vanding_station_info1_idx` (`station_info_stationName`),
  CONSTRAINT `fk_vanding_station_info1` FOREIGN KEY (`station_info_stationName`) REFERENCES `station_info` (`stationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* data for Table vanding */
INSERT INTO `vanding` VALUES (2712,-1,"drink","장지");
INSERT INTO `vanding` VALUES (12312,2,"snack","왕십리");
INSERT INTO `vanding` VALUES (12358,-1,"necessary","청구");
INSERT INTO `vanding` VALUES (12485,-4,"drink","광화문");
INSERT INTO `vanding` VALUES (13254,-1,"snack","건대입구");
INSERT INTO `vanding` VALUES (21941,-2,"snack","이태원");
INSERT INTO `vanding` VALUES (23452,-3,"tissue","광화문");
INSERT INTO `vanding` VALUES (23458,-1,"drink","아차산");
INSERT INTO `vanding` VALUES (32491,-2,"drink","왕십리");
INSERT INTO `vanding` VALUES (34681,-2,"drink","군자");
INSERT INTO `vanding` VALUES (37529,-2,"drink","사당");
INSERT INTO `vanding` VALUES (41251,-2,"drink","이태원");
INSERT INTO `vanding` VALUES (43676,1,"drink","고속터미널");
INSERT INTO `vanding` VALUES (45034,-3,"drink","동대문역사문화공원");
INSERT INTO `vanding` VALUES (45654,-2,"drink","교대");
INSERT INTO `vanding` VALUES (45801,-1,"tissue","군자");
INSERT INTO `vanding` VALUES (48222,-1,"tissue","선릉");
INSERT INTO `vanding` VALUES (48488,-3,"drink","사당");
INSERT INTO `vanding` VALUES (56834,-4,"drink","청구");
INSERT INTO `vanding` VALUES (57103,-2,"necessary","고속터미널");
INSERT INTO `vanding` VALUES (58971,-1,"tissue","이태원");
INSERT INTO `vanding` VALUES (62109,2,"drink","건대입구");
INSERT INTO `vanding` VALUES (63471,-2,"snack","청구");
INSERT INTO `vanding` VALUES (64361,-1,"drink","동대문역사문화공원");
INSERT INTO `vanding` VALUES (65709,1,"tissue","왕십리");
INSERT INTO `vanding` VALUES (69302,-1,"snack","고속터미널");
INSERT INTO `vanding` VALUES (72536,-2,"drink","어린이대공원");
INSERT INTO `vanding` VALUES (74712,2,"snack","옥수");
INSERT INTO `vanding` VALUES (78954,-2,"drink","학동");
INSERT INTO `vanding` VALUES (83407,-1,"tissue","어린이대공원");
INSERT INTO `vanding` VALUES (87533,-1,"tissue","교대");
INSERT INTO `vanding` VALUES (88854,-2,"snack","혜화");
INSERT INTO `vanding` VALUES (90124,1,"drink","동두천");
INSERT INTO `vanding` VALUES (95622,-2,"snack","동대문역사문화공원");

/* Restore session variables to original values */
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
