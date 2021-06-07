-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projekat
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `idalarm` int NOT NULL AUTO_INCREMENT,
  `vreme` datetime NOT NULL,
  `idKorisnik` int NOT NULL,
  `periodican` int NOT NULL,
  `pesma` int NOT NULL,
  PRIMARY KEY (`idalarm`),
  KEY `alarm.idKorisnik_idx` (`idKorisnik`),
  CONSTRAINT `alarm.idKorisnik` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idkorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (66,'2021-06-02 22:36:10',1,0,2),(67,'2021-06-02 22:36:10',1,0,2),(68,'2021-06-03 09:55:10',1,0,1),(69,'2021-06-03 09:55:15',1,0,2),(70,'2021-06-03 10:22:56',1,0,1),(71,'2021-06-03 13:14:42',1,0,1),(72,'2021-06-03 13:14:48',1,0,1),(73,'2021-06-03 14:49:55',1,0,1),(74,'2021-06-03 15:06:03',1,0,1),(75,'2021-06-03 15:11:06',1,0,1),(87,'2021-06-03 15:33:12',1,0,1),(98,'2021-06-03 16:16:24',1,0,1),(99,'2021-06-03 16:09:44',1,0,3),(100,'2021-06-03 16:06:36',1,0,1),(101,'2021-06-03 18:14:56',1,0,3),(102,'2021-06-03 16:23:33',1,0,1),(103,'2021-06-03 16:24:58',1,0,1),(104,'2021-06-03 16:26:18',1,0,1),(105,'2021-06-03 18:41:08',1,0,1),(106,'2021-06-03 19:46:34',1,0,1),(107,'2021-06-03 20:47:37',1,0,1),(108,'2021-06-04 00:44:05',1,0,1),(109,'2021-06-03 18:49:23',1,0,1),(110,'2021-06-03 20:29:57',1,0,1),(111,'2021-06-03 20:30:04',1,0,1),(112,'2021-06-03 20:30:09',1,0,1),(113,'2021-06-03 20:30:54',1,0,1),(114,'2021-06-03 18:59:33',1,0,1),(115,'2021-06-03 20:32:19',1,0,1),(116,'2021-06-03 19:17:34',1,0,1),(117,'2021-06-03 19:17:40',1,0,1),(118,'2021-06-03 19:18:54',1,0,1),(119,'2021-06-03 19:19:04',1,0,1),(120,'2021-06-03 19:31:11',1,0,1),(121,'2021-06-03 20:01:00',1,0,1),(122,'2021-06-03 22:39:18',1,0,1),(123,'2021-06-03 19:58:52',1,0,1),(124,'2021-06-03 19:45:18',1,0,1),(125,'2021-06-03 19:45:23',1,0,1),(126,'2021-06-04 03:17:35',1,0,1),(127,'2021-06-03 20:19:04',1,0,1),(128,'2021-06-03 20:19:09',1,0,2),(129,'2021-06-04 00:00:37',1,0,1),(130,'2021-06-03 21:02:51',1,0,1);
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idkorisnik` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `kuca` varchar(45) NOT NULL,
  PRIMARY KEY (`idkorisnik`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'admin','1234','Beograd'),(2,'milos','1234','Nis');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesma`
--

DROP TABLE IF EXISTS `pesma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesma` (
  `idpesma` int NOT NULL AUTO_INCREMENT,
  `ImePesme` varchar(45) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idpesma`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesma`
--

LOCK TABLES `pesma` WRITE;
/*!40000 ALTER TABLE `pesma` DISABLE KEYS */;
INSERT INTO `pesma` VALUES (1,'Despacito','https://www.youtube.com/watch?v=kJQP7kiw5Fk'),(2,'BabyShark','https://www.youtube.com/watch?v=XqZsoesa55w&ab_channel=Pinkfong%21Kids%27Songs%26Stories'),(3,'Toxic','https://www.youtube.com/watch?v=LOZuxwVk7TU&ab_channel=BritneySpearsVEVO'),(4,'Dont','https://www.youtube.com/watch?v=3TqGEbROFd4&ab_channel=EdSheeran'),(5,'TouchTheSky','https://www.youtube.com/watch?v=l3SmfHiEZ-o&ab_channel=BlackPumasVEVO'),(6,'22','https://www.youtube.com/watch?v=AgFeZr5ptV8&ab_channel=TaylorSwiftVEVO'),(7,'Colors','https://www.youtube.com/watch?v=B7PnQBn5k_E&ab_channel=BlackPumasVEVO'),(8,'KnowYouBetter','https://www.youtube.com/watch?v=iC3kEJnDjHY&ab_channel=BlackPumasVEVO'),(9,'Panda','https://www.youtube.com/watch?v=4NJlUribp3c&ab_channel=60MinSongs60MinSongs'),(10,'AstronautInTheOcean','https://www.youtube.com/watch?v=MEg-oqI9qmw&ab_channel=MaskedWolfMaskedWolfOfficialArtistChannel');
/*!40000 ALTER TABLE `pesma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planer`
--

DROP TABLE IF EXISTS `planer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planer` (
  `idplaner` int NOT NULL AUTO_INCREMENT,
  `pocetak` datetime NOT NULL,
  `kraj` datetime NOT NULL,
  `destinacija` varchar(45) NOT NULL,
  `idKorisnika` int NOT NULL,
  `idget` int DEFAULT NULL,
  PRIMARY KEY (`idplaner`),
  KEY `idKorisnikaPlaner_idx` (`idKorisnika`),
  CONSTRAINT `planer.idKorisnika` FOREIGN KEY (`idKorisnika`) REFERENCES `korisnik` (`idkorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planer`
--

LOCK TABLES `planer` WRITE;
/*!40000 ALTER TABLE `planer` DISABLE KEYS */;
INSERT INTO `planer` VALUES (49,'2021-06-03 21:39:29','2021-06-03 21:59:29','Kraljevo',1,1),(51,'2021-06-04 00:48:28','2021-06-04 01:08:28','Nis',1,1),(54,'2021-06-04 02:07:41','2021-06-04 02:12:41','Kraljevo',1,1);
/*!40000 ALTER TABLE `planer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slusao`
--

DROP TABLE IF EXISTS `slusao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slusao` (
  `idslusao` int NOT NULL AUTO_INCREMENT,
  `idKorisnik` int NOT NULL,
  `idPesma` int NOT NULL,
  PRIMARY KEY (`idslusao`),
  KEY `idKor_idx` (`idKorisnik`),
  KEY `idPesma_idx` (`idPesma`),
  CONSTRAINT `slusao.idKorisnik` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idkorisnik`),
  CONSTRAINT `slusao.idPesma` FOREIGN KEY (`idPesma`) REFERENCES `pesma` (`idpesma`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slusao`
--

LOCK TABLES `slusao` WRITE;
/*!40000 ALTER TABLE `slusao` DISABLE KEYS */;
INSERT INTO `slusao` VALUES (1,1,1),(2,2,2),(3,1,2),(4,1,3),(5,1,3),(6,1,3),(7,1,3),(8,1,6),(9,1,6),(10,1,7),(11,1,7),(12,1,7),(13,1,7),(14,1,7),(15,1,3),(16,1,3),(17,1,1),(18,1,3),(19,2,3),(20,2,6),(21,1,6),(22,1,4),(23,1,6),(24,1,6),(25,1,3),(26,1,7),(27,1,3),(28,1,3),(29,1,4),(30,1,1),(31,1,6),(32,1,9),(33,1,10),(34,1,6),(35,1,4);
/*!40000 ALTER TABLE `slusao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-04 10:48:10
