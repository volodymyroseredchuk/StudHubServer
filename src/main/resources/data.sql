-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: studhub
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `approved` bit(1) DEFAULT NULL,
  `body` text,
  `creation_date` datetime DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK3erw1a3t0r78st8ty27x6v3g1` (`question_id`),
  KEY `FK5bp3d5loftq2vjn683ephn75a` (`user_id`),
  CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK5bp3d5loftq2vjn683ephn75a` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,_binary '','test','2019-06-03 00:00:00',1,2,-1),(2,_binary '\0','Prepare yourself for being in the army!','2019-06-03 12:59:00',1,3,0);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `channels`
--

DROP TABLE IF EXISTS `channels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `channels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subject_id_idx` (`subject_id`),
  CONSTRAINT `subject_id` FOREIGN KEY (`subject_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channels`
--

LOCK TABLES `channels` WRITE;
/*!40000 ALTER TABLE `channels` DISABLE KEYS */;
INSERT INTO `channels` VALUES (1,1);
/*!40000 ALTER TABLE `channels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_messages`
--

DROP TABLE IF EXISTS `chat_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `chat_messages` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `content` varchar(5000) NOT NULL,
                                 `sender_user_id` int(11) NOT NULL,
                                 `creation_datetime` datetime NOT NULL,
                                 `chat_id` int(11) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `id_UNIQUE` (`id`),
                                 KEY `msg_sender_idx` (`sender_user_id`),
                                 KEY `msg_chat_idx` (`chat_id`),
                                 CONSTRAINT `msg_chat` FOREIGN KEY (`chat_id`) REFERENCES `chats` (`id`),
                                 CONSTRAINT `msg_sender` FOREIGN KEY (`sender_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_messages`
--

LOCK TABLES `chat_messages` WRITE;
/*!40000 ALTER TABLE `chat_messages` DISABLE KEYS */;
INSERT INTO `chat_messages` VALUES (3,'Test2',3,'2019-07-10 23:47:33',2),(4,'1',3,'2019-07-11 12:12:34',1),(149,'qw',3,'2019-07-12 16:58:45',1),(150,'3',3,'2019-07-12 16:58:54',1),(151,'4',3,'2019-07-12 16:58:55',1),(152,'5',3,'2019-07-12 16:58:57',1),(153,'6',3,'2019-07-12 16:58:59',1),(154,'7',3,'2019-07-12 16:59:01',1),(155,'8',3,'2019-07-12 16:59:04',1),(156,'9',3,'2019-07-12 16:59:06',1),(157,'10',3,'2019-07-12 16:59:09',1),(158,'11',1,'2019-07-12 16:59:13',1),(159,'12',1,'2019-07-12 16:59:15',1),(160,'13',1,'2019-07-12 16:59:21',1),(161,'14',1,'2019-07-12 16:59:28',1),(162,'15',1,'2019-07-12 16:59:31',1),(163,'16',1,'2019-07-12 16:59:33',1),(164,'17',1,'2019-07-12 16:59:38',1),(165,'18',1,'2019-07-12 16:59:41',1),(166,'19',1,'2019-07-12 16:59:44',1),(167,'20',1,'2019-07-12 16:59:47',1),(168,'21',3,'2019-07-12 16:59:52',1),(169,'22',3,'2019-07-12 16:59:54',1),(170,'23',1,'2019-07-12 17:00:00',1),(171,'24',1,'2019-07-12 17:00:02',1),(172,'25',3,'2019-07-12 17:00:47',1),(173,'26',3,'2019-07-12 17:01:24',1),(174,'27',3,'2019-07-12 17:01:40',1),(175,'28',3,'2019-07-12 17:02:46',1),(176,'29',3,'2019-07-12 17:03:27',1),(177,'30',3,'2019-07-12 17:04:12',1),(178,'31',3,'2019-07-12 17:04:57',1),(179,'32',3,'2019-07-12 17:05:26',1),(180,'33',3,'2019-07-12 17:05:38',1),(181,'34',3,'2019-07-12 17:06:12',1),(182,'35',3,'2019-07-12 17:06:14',1),(183,'36',3,'2019-07-12 17:06:14',1),(184,'37',3,'2019-07-12 17:06:15',1),(185,'333333333338',3,'2019-07-12 17:06:19',1),(186,'39',3,'2019-07-12 17:06:20',1),(187,'40',3,'2019-07-12 17:06:21',1),(188,'41',3,'2019-07-12 17:06:23',1),(189,'42',3,'2019-07-12 17:06:25',1),(190,'43',3,'2019-07-12 17:06:26',1),(191,'44',3,'2019-07-12 17:06:27',1),(192,'45',3,'2019-07-12 17:06:29',1),(193,'46',3,'2019-07-12 17:06:30',1),(194,'47',3,'2019-07-12 17:06:31',1),(195,'48',3,'2019-07-12 17:06:33',1),(196,'49',3,'2019-07-12 17:06:34',1),(197,'50',3,'2019-07-12 17:06:36',1),(198,'51',3,'2019-07-12 17:11:12',1),(199,'Hey lol I wanted to meet u ',3,'2019-07-12 17:11:25',1),(200,'Me 2',1,'2019-07-12 17:11:32',1),(201,'go?',1,'2019-07-12 17:16:40',1),(202,'go',3,'2019-07-12 17:16:47',1),(203,'hey',3,'2019-07-12 17:16:52',1),(204,'lol',1,'2019-07-12 17:57:01',1),(205,'hey',1,'2019-07-12 18:00:18',1),(206,'rock',1,'2019-07-12 18:01:09',1),(207,'hey',1,'2019-07-12 18:01:22',1),(208,'come here',1,'2019-07-12 18:11:29',1),(209,'hey ya',3,'2019-07-12 18:11:36',1),(210,'hey',3,'2019-07-12 18:12:03',1),(211,'hey',1,'2019-07-12 18:12:31',1),(212,'qwe',1,'2019-07-12 18:13:30',1),(213,'qwe',1,'2019-07-12 18:15:32',1),(214,'qwe',1,'2019-07-12 18:17:42',1),(215,'qwe',1,'2019-07-12 18:17:51',1),(216,'qwe',1,'2019-07-12 18:17:58',1),(217,'qwe',1,'2019-07-12 18:18:09',1),(218,'khey',1,'2019-07-12 18:20:27',1),(219,'qwe',1,'2019-07-12 18:22:58',1),(220,'qwe',1,'2019-07-12 18:34:01',1),(221,'qwe',1,'2019-07-12 18:42:28',1),(222,'qwe',1,'2019-07-12 18:42:56',1),(223,'qwe',1,'2019-07-12 18:44:01',1),(224,'I\'m here',1,'2019-07-12 19:44:41',1),(225,'u 2?',1,'2019-07-12 19:44:59',1),(226,'yeah',1,'2019-07-12 19:45:29',1),(227,'qwe',1,'2019-07-12 19:48:29',1),(228,'qwe',1,'2019-07-12 19:48:45',1),(229,'qwe',1,'2019-07-12 19:49:25',1),(230,'qwe',1,'2019-07-12 19:49:29',1),(231,'qwewqe',1,'2019-07-12 19:49:55',1),(232,'jhgfjhfghmgfhgfhgfhgfhgfh\ngfhgfhgfhgfhgfhgfhfghgf\ngfhhhhhhhggggggggggggggg',3,'2019-07-12 20:03:08',1),(233,'dsfdsfds',3,'2019-07-12 20:37:40',2),(234,'dsfdsfsd',3,'2019-07-12 20:37:43',2),(235,'dsfds',3,'2019-07-12 20:37:45',2),(236,'sdfsd',3,'2019-07-12 20:37:45',2),(237,'sd',3,'2019-07-12 20:37:46',2),(238,'sdf',3,'2019-07-12 20:37:46',2),(239,'sdsdf',3,'2019-07-12 20:37:46',2),(240,'sfsd',3,'2019-07-12 20:37:47',2),(241,'sd',3,'2019-07-12 20:37:47',2),(242,'f',3,'2019-07-12 20:37:47',2),(243,'sdf',3,'2019-07-12 20:37:47',2),(244,'sd',3,'2019-07-12 20:37:48',2),(245,'fsd',3,'2019-07-12 20:37:48',2),(246,'fsd',3,'2019-07-12 20:37:48',2),(247,'qwe',3,'2019-07-12 20:58:22',1);
/*!40000 ALTER TABLE `chat_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_subscriptions`
--

DROP TABLE IF EXISTS `chat_subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `chat_subscriptions` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `chat_id` int(11) NOT NULL,
                                      `user_id` int(11) NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `id_UNIQUE` (`id`),
                                      KEY `subscr_chat_idx` (`chat_id`),
                                      KEY `subscr_user_idx` (`user_id`),
                                      CONSTRAINT `subscr_chat` FOREIGN KEY (`chat_id`) REFERENCES `chats` (`id`),
                                      CONSTRAINT `subscr_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_subscriptions`
--

LOCK TABLES `chat_subscriptions` WRITE;
/*!40000 ALTER TABLE `chat_subscriptions` DISABLE KEYS */;
INSERT INTO `chat_subscriptions` VALUES (1,1,3),(2,2,3),(3,1,1),(4,3,1),(5,3,2),(6,4,1),(7,4,4),(8,5,2),(9,5,4),(16,9,3),(17,9,4);
/*!40000 ALTER TABLE `chat_subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chats`
--

DROP TABLE IF EXISTS `chats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `chats` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(45) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chats`
--

LOCK TABLES `chats` WRITE;
/*!40000 ALTER TABLE `chats` DISABLE KEYS */;
INSERT INTO `chats` VALUES (1,'Test'),(2,'Test2'),(3,NULL),(4,NULL),(5,NULL),(6,NULL),(7,NULL),(8,NULL),(9,NULL);
/*!40000 ALTER TABLE `chats` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text,
  `creation_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `answer_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoiwlwqmu9qm0tjnafxqr20rd8` (`answer_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKoiwlwqmu9qm0tjnafxqr20rd8` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'WTF?','2019-06-10 00:00:00',NULL,1,4);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `feedbacks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text,
  `mark` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmb4xm2mu42sp99hn4o0v3knf` (`teacher_id`),
  KEY `FKf4nldstwbyijft4d6g1tyvqfx` (`university_id`),
  KEY `FK312drfl5lquu37mu4trk8jkwx` (`user_id`),
  CONSTRAINT `FK312drfl5lquu37mu4trk8jkwx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKf4nldstwbyijft4d6g1tyvqfx` FOREIGN KEY (`university_id`) REFERENCES `universities` (`id`),
  CONSTRAINT `FKkmb4xm2mu42sp99hn4o0v3knf` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
INSERT INTO `feedbacks` VALUES (1,'The worst corrupted university in the whole universe',4,NULL,1,0,1),(2,'Not as bad as they say',3,NULL,1,0,2),(3,'Totally liked that!',5,NULL,1,0,3),(4,'So far so good.',4,NULL,2,0,4),(5,'To pass exam you need only 0.5 bottle of vodka. The funniest learning experience ever)',5,NULL,3,0,1),(6,'Don`t know what to say',2,NULL,4,0,2),(7,'As a lecturer he is the worst, but as a person he\'s good',3,1,NULL,0,3),(8,'bad_bad_bad_bad_bad_bad_bad bad_bad_bad_bad_bad_bad_bad_bad_bad',1,2,NULL,0,4),(9,'good',5,2,NULL,0,1),(10,'Sample text',4,4,NULL,0,2),(11,'Petuch',3,6,NULL,0,3),(12,'ggg',4,1,NULL,0,4);
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 CREATE*/  /*!50003 TRIGGER `recalcFeedbackMarkInsert` BEFORE INSERT ON `feedbacks` FOR EACH ROW BEGIN IF NEW.`teacher_id` IS NOT NULL THEN UPDATE `teachers` SET `mark` = (SELECT (SUM(`mark`) + NEW.`mark`) / (COUNT(*) + 1)FROM `feedbacks` WHERE `feedbacks`.`teacher_id` = NEW.`teacher_id` AND `feedbacks`.`rate` > -5 ) WHERE `teachers`.`id`  = NEW.`teacher_id`; ELSEIF NEW.`university_id` IS NOT NULL THEN UPDATE `universities` SET `mark` = (SELECT (SUM(`mark`) + NEW.`mark`) / (COUNT(*) + 1)FROM `feedbacks` WHERE `feedbacks`.`university_id` = NEW.`university_id` AND `feedbacks`.`rate` > -5 ) WHERE `universities`.`id`  = NEW.`university_id`; END IF; END */;

/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 CREATE*/  /*!50003 TRIGGER `recalcFeedbackMarkUpdate` BEFORE UPDATE ON `feedbacks` FOR EACH ROW BEGIN IF NEW.`teacher_id` IS NOT NULL THEN UPDATE `teachers` SET `mark` = (SELECT (SUM(`mark`) - OLD.`mark` + NEW.`mark`) / (COUNT(*))FROM `feedbacks` WHERE `feedbacks`.`teacher_id` = NEW.`teacher_id` AND `feedbacks`.`rate` > -5 ) WHERE `teachers`.`id`  = NEW.`teacher_id`; ELSEIF NEW.`university_id` IS NOT NULL THEN UPDATE `universities` SET `mark` = (SELECT (SUM(`mark`) - OLD.`mark` + NEW.`mark`) / (COUNT(*))FROM `feedbacks` WHERE `feedbacks`.`university_id` = NEW.`university_id` AND `feedbacks`.`rate` > -5 ) WHERE `universities`.`id`  = NEW.`university_id`; END IF; END */;

/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `freelancers`
--

DROP TABLE IF EXISTS `freelancers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `freelancers` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `quality` int(11) DEFAULT NULL,
                              `price` int(11) DEFAULT NULL,
                              `velocity` int(11) DEFAULT NULL,
                              `contact` int(11) DEFAULT NULL,
                              `user_id` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `user_id` (`user_id`),
                              CONSTRAINT `freelancers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freelancers`
--

LOCK TABLES `freelancers` WRITE;
/*!40000 ALTER TABLE `freelancers` DISABLE KEYS */;
/*!40000 ALTER TABLE `freelancers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_token`
--

DROP TABLE IF EXISTS `password_reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `password_reset_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(200) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `password_reset_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_token`
--

LOCK TABLES `password_reset_token` WRITE;
/*!40000 ALTER TABLE `password_reset_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirm_token`
--

DROP TABLE IF EXISTS `confirm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `confirm_token` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `token` varchar(200) DEFAULT NULL,
                                        `expiry_date` date DEFAULT NULL,
                                        `user_id` int(11) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `user_id` (`user_id`),
                                        CONSTRAINT `confirm_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirm_token`
--

LOCK TABLES `confirm_token` WRITE;
/*!40000 ALTER TABLE `confirm_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirm_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `customers` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `payment` int(11) DEFAULT NULL,
                            `formulation` int(11) DEFAULT NULL,
                            `clarity` int(11) DEFAULT NULL,
                            `contact` int(11) DEFAULT NULL,
                            `user_id` int(11) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `user_id` (`user_id`),
                            CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text,
  `creation_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `team_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjoo8hp6d3gfwctr68dl2iaemj` (`user_id`),
  FULLTEXT KEY `questions_search` (`title`,`body`),
  CONSTRAINT `FKjoo8hp6d3gfwctr68dl2iaemj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Hi! I am applied math student, but i don\'t know math. Completely. I even don\'t know how to multiply numbes. Please help! ','2019-06-01 00:00:00',NULL,'How to math?',1,null),(2,'Hello! Please help me writing diploma. My theme is the philosophy of middle ages. What philosophers ere popular that days?','2019-06-01 00:00:00',NULL,'Philosophy diploma',4,null);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_tags`
--

DROP TABLE IF EXISTS `questions_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `questions_tags` (
  `question_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  KEY `FK7yq8xf1pqv8katljm8v8j8w3c` (`tag_id`),
  KEY `FK4u5xv906wfevngoe973bec6u0` (`question_id`),
  CONSTRAINT `FK4u5xv906wfevngoe973bec6u0` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK7yq8xf1pqv8katljm8v8j8w3c` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_tags`
--

LOCK TABLES `questions_tags` WRITE;
/*!40000 ALTER TABLE `questions_tags` DISABLE KEYS */;
INSERT INTO `questions_tags` VALUES (1,1),(2,4),(2,5);
/*!40000 ALTER TABLE `questions_tags` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `result_submission_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `proposal_id` int(11) DEFAULT NULL,
  `user_creator_id` int(11) DEFAULT NULL,
  `user_executor_id` int(11) DEFAULT NULL,
  `freelancer_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `result_submission_idx` (`result_submission_id`),
  KEY `user_creator_idx` (`user_executor_id`),
  KEY `task_idx` (`task_id`),
  KEY `proposal_idx` (`proposal_id`),
  KEY `user_creator_idx1` (`user_creator_id`),
  KEY `freelancer_idx` (`freelancer_id`),
  KEY `customer_idx` (`customer_id`),
  CONSTRAINT `proposal` FOREIGN KEY (`proposal_id`) REFERENCES `proposals` (`id`),
  CONSTRAINT `result_submission` FOREIGN KEY (`result_submission_id`) REFERENCES `result_submission` (`id`),
  CONSTRAINT `task` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `user_creator` FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `freelancer` FOREIGN KEY (`freelancer_id`) REFERENCES `freelancers` (`id`),
  CONSTRAINT `customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `user_executor` FOREIGN KEY (`user_executor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `result_submission`
--

DROP TABLE IF EXISTS `result_submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `result_submission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result_submission`
--

LOCK TABLES `result_submission` WRITE;
/*!40000 ALTER TABLE `result_submission` DISABLE KEYS */;
/*!40000 ALTER TABLE `result_submission` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `privileges`
--

DROP TABLE IF EXISTS `privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privileges`
--

LOCK TABLES `privileges` WRITE;
/*!40000 ALTER TABLE `privileges` DISABLE KEYS */;
INSERT INTO `privileges` VALUES (1,'ANSWER_READ_PRIVILEGE'),(2,'ANSWER_WRITE_PRIVILEGE'),(3,'COMMENT_READ_PRIVILEGE'),(4,'COMMENT_WRITE_PRIVILEGE'),(5,'FEEDBACK_READ_PRIVILEGE'),(6,'FEEDBACK_WRITE_PRIVILEGE'),(7,'QUESTION_READ_PRIVILEGE'),(8,'QUESTION_WRITE_PRIVILEGE'),(9,'TEACHER_READ_PRIVILEGE'),(10,'TEACHER_WRITE_PRIVILEGE'),(11,'UNIVERSITY_READ_PRIVILEGE'),(12,'UNIVERSITY_WRITE_PRIVILEGE'),(13,'GROUP_READ_PRIVILEGE'),(14,'GROUP_WRITE_PRIVILEGE'),(15,'GRAND_ROLE_PRIVILEGE'),(16,'VOTE_READ_PRIVILEGE'),(17,'VOTE_WRITE_PRIVILEGE'),(18,'QUESTION_DELETE_ANY_PRIVILEGE'),(19,'ANSWER_DELETE_ANY_PRIVILEGE'),(20,'TEACHER_DELETE_ANY_PRIVILEGE'),(21,'FEEDBACK_DELETE_ANY_PRIVILEGE'),(22,'COMMENT_DELETE_ANY_PRIVILEGE'),(23,'GROUP_DELETE_ANY_PRIVILEGE'),(24,'UNIVERSITY_DELETE_ANY_PRIVILEGE'),(32,'TASK_READ_PRIVILEGE'),(33,'TASK_WRITE_PRIVILEGE'),(34,'TASK_DELETE_ANY_PRIVILEGE'),(35,'PROPOSAL_READ_PRIVILEGE'),(36,'PROPOSAL_WRITE_PRIVILEGE'),(37,'PROPOSAL_DELETE_ANY_PRIVILEGE'),(38,'ORDER_DELETE_ANY_PRIVILEGE');
/*!40000 ALTER TABLE `privileges` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ROLE_ADMIN'),(3,'ROLE_MODERATOR'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;



DROP TABLE IF EXISTS `roles_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles_privileges` (
  `role_id` bigint(20) NOT NULL,
  `privilege_id` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`,`privilege_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_privileges`
--

LOCK TABLES `roles_privileges` WRITE;
/*!40000 ALTER TABLE `roles_privileges` DISABLE KEYS */;
INSERT INTO `roles_privileges` VALUES (1,'1'),(1,'10'),(1,'13'),(1,'14'),(1,'16'),(1,'17'),(1,'2'),(1,'3'),(1,'32'),(1,'33'),(1,'35'),(1,'36'),(1,'4'),(1,'5'),(1,'6'),(1,'7'),(1,'8'),(1,'9'),(2,'1'),(2,'10'),(2,'11'),(2,'12'),(2,'13'),(2,'14'),(2,'15'),(2,'16'),(2,'17'),(2,'18'),(2,'19'),(2,'2'),(2,'20'),(2,'21'),(2,'22'),(2,'23'),(2,'24'),(2,'3'),(2,'32'),(2,'33'),(2,'34'),(2,'35'),(2,'36'),(2,'37'),(2,'38'),(2,'4'),(2,'5'),(2,'6'),(2,'7'),(2,'8'),(2,'9'),(3,'1'),(3,'10'),(3,'11'),(3,'12'),(3,'13'),(3,'14'),(3,'16'),(3,'17'),(3,'18'),(3,'19'),(3,'2'),(3,'20'),(3,'21'),(3,'22'),(3,'23'),(3,'24'),(3,'3'),(3,'32'),(3,'33'),(3,'34'),(3,'35'),(3,'36'),(3,'37'),(3,'38'),(3,'4'),(3,'5'),(3,'6'),(3,'7'),(3,'8'),(3,'9');
/*!40000 ALTER TABLE `roles_privileges` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscriptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `channel_id_idx` (`channel_id`),
  CONSTRAINT `channel_id` FOREIGN KEY (`channel_id`) REFERENCES `channels` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES (1,1,1);
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text,
  `creation_date` datetime DEFAULT NULL,
  `deadline_date` datetime DEFAULT NULL,
  `expected_price` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `task_status` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6s1ob9k4ihi75xbxe2w0ylsdh` (`user_id`),
  FULLTEXT KEY `tasks_search` (`title`,`body`),
  CONSTRAINT `FK6s1ob9k4ihi75xbxe2w0ylsdh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'First task body','2019-06-27 00:00:00','2019-07-10 00:00:00',30,NULL,'First task',1,'NEW'),(2,'Second task body','2019-06-27 00:00:00','2019-07-15 00:00:00',50,NULL,'Second task',1,'NEW');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks_tags`
--

DROP TABLE IF EXISTS `tasks_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `tasks_tags` (
                              `tasks_id` int(11) NOT NULL,
                              `tags_id` int(11) NOT NULL,
                              KEY `tasks_id` (`tasks_id`),
                              KEY `tags_id` (`tags_id`),
                              CONSTRAINT `tasks_tags_ibfk_1` FOREIGN KEY (`tasks_id`) REFERENCES `tasks` (`id`) ON DELETE CASCADE,
                              CONSTRAINT `tasks_tags_ibfk_2` FOREIGN KEY (`tags_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks_tags`
--

LOCK TABLES `tasks_tags` WRITE;
/*!40000 ALTER TABLE `tasks_tags` DISABLE KEYS */;
INSERT INTO `tasks_tags` VALUES (1,1),(2,4),(2,5);
/*!40000 ALTER TABLE `tasks_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposals`
--

DROP TABLE IF EXISTS `proposals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `proposals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text,
  `days_count` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3u94vfp3i629csukvqetmy997` (`task_id`),
  KEY `FKo0nyqm5ghqcdte7g3xwn2gsmj` (`user_id`),
  CONSTRAINT `FK3u94vfp3i629csukvqetmy997` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKo0nyqm5ghqcdte7g3xwn2gsmj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposals`
--

LOCK TABLES `proposals` WRITE;
/*!40000 ALTER TABLE `proposals` DISABLE KEYS */;
INSERT INTO `proposals` VALUES (1,'first proposal for first task',5,25,'2019-06-27 00:00:00',1,2),(2,'second proposal for first task',3,50,'2019-06-27 00:00:00',1,3),(3,'first proposal for second task',7,36,'2019-06-27 00:00:00',2,3),(4,'second proposal for second task',6,50,'2019-06-27 00:00:00',2,2);
/*!40000 ALTER TABLE `proposals` ENABLE KEYS */;
UNLOCK TABLES;





--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t48xdq560gs3gap9g7jg36kgc` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (2,'Calculus'),(5,'Diploma'),(3,'Economics'),(1,'Math'),(4,'Philosophy'),(6,'Physics');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `mark` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKed4htex35qkosty8lbt8g37vb` (`university_id`),
  CONSTRAINT `FKed4htex35qkosty8lbt8g37vb` FOREIGN KEY (`university_id`) REFERENCES `universities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'2019-06-02 00:00:00','Bogdan',NULL,'Gnativ',NULL,1,3.5),(2,'2019-06-02 00:00:00','Petro',NULL,'Kostrobiy',NULL,1,0),(3,'2019-06-02 00:00:00','Petro',NULL,'Topulko',NULL,1,0),(4,'2019-06-02 00:00:00','Roman',NULL,'Kalyna',NULL,2,0),(5,'2019-06-02 00:00:00','Olexiy',NULL,'Pidlisnyi',NULL,2,0),(6,'2019-06-02 00:00:00','Oleg',NULL,'Petuchello',NULL,3,0),(7,'2019-06-02 00:00:00','Igor',NULL,'Kolega',NULL,4,0);
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `teams` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `title` varchar(32) DEFAULT NULL,
                         `creator_id` int(11) NOT NULL,
                         `creation_date` datetime DEFAULT NULL,
                         `modified_date` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `creator_id` (`creator_id`),
                         CONSTRAINT `teams_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'dreamteam',3, '2019-06-02 00:00:00', '2019-06-02 00:01:01'),(2,'adminteam',3, '2019-07-02 00:00:02', '2019-07-02 00:01:03'),(3,'tarasteam',1, '2019-09-02 00:00:00', '2019-09-02 00:01:01');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams_users`
--

DROP TABLE IF EXISTS `teams_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `teams_users` (
                               `team_id` int(11) NOT NULL,
                               `user_id` int(11) NOT NULL,
                               KEY `team_id` (`team_id`),
                               KEY `user_id` (`user_id`),
                               CONSTRAINT `teams_users_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `teams_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams_users`
--

LOCK TABLES `teams_users` WRITE;
/*!40000 ALTER TABLE `teams_users` DISABLE KEYS */;
INSERT INTO `teams_users` VALUES (1,1),(1,2),(3,3);
/*!40000 ALTER TABLE `teams_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `universities`
--

DROP TABLE IF EXISTS `universities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `universities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `mark` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `universities`
--

LOCK TABLES `universities` WRITE;
/*!40000 ALTER TABLE `universities` DISABLE KEYS */;
INSERT INTO `universities` VALUES (1,'Lviv','2019-06-01 00:00:00',NULL,NULL,'Lviv Polytechnic National University',4),(2,'Lviv','2019-06-01 00:00:00',NULL,NULL,'Ivan Franko Lviv National University',0),(3,'Lviv','2019-06-01 00:00:00',NULL,NULL,'Bursa',0),(4,'Kyiv','2019-06-01 00:00:00',NULL,NULL,'Kyiv Polytechnic University',0);
/*!40000 ALTER TABLE `universities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,1),(4,1),(3,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `university_id` int(11) DEFAULT NULL,
   `email_subscription` bit(1) NOT NULL DEFAULT _binary '',
   `google_password` varchar(255) DEFAULT NULL,
  `is_activated` bit(1) NOT NULL DEFAULT _binary '\0',
   `cookies_count` INT NOT NULL DEFAULT 1000,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  KEY `FKm6cuniuttvvmhstb2s32jsotf` (`university_id`),
  CONSTRAINT `FKm6cuniuttvvmhstb2s32jsotf` FOREIGN KEY (`university_id`) REFERENCES `universities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2019-05-05','tarasgl@gmail.com','Taras',NULL,'Hlukhovetskyi','$2a$10$vY8c10iRdFKLZLk55C1P/eLHLFF.mn2.IaOcGCWsFLMVlsD4DXPK2','tarasgl',NULL, 1, NULL, 1, 1000),(2,'2019-06-05','sample@gmail.com','Olha',NULL,'Lozinska','$2a$10$vY8c10iRdFKLZLk55C1P/eLHLFF.mn2.IaOcGCWsFLMVlsD4DXPK2','olozh',NULL, 1, NULL, 1, 1000),(3,'2019-06-07','admin@gmail.com','Admin',NULL,'Admin','$2a$10$vY8c10iRdFKLZLk55C1P/eLHLFF.mn2.IaOcGCWsFLMVlsD4DXPK2','admin',NULL, 1, NULL, 1, 1000),(4,'2019-06-08','sample2@gmail.com','Andrii',NULL,'Vashchenok','$2a$10$vY8c10iRdFKLZLk55C1P/eLHLFF.mn2.IaOcGCWsFLMVlsD4DXPK2','avash',NULL, 1, NULL, 1, 1000);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `votes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` int(11) NOT NULL,
  `answer_id` int(11) DEFAULT NULL,
  `feedback_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK153p8dvxvoas3c6kiwxjjxkrd` (`answer_id`),
  KEY `FKruiggkp5t2k72ks0ojsyaj867` (`feedback_id`),
  KEY `FKli4uj3ic2vypf5pialchj925e` (`user_id`),
  CONSTRAINT `FK153p8dvxvoas3c6kiwxjjxkrd` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKli4uj3ic2vypf5pialchj925e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKruiggkp5t2k72ks0ojsyaj867` FOREIGN KEY (`feedback_id`) REFERENCES `feedbacks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votes`
--

LOCK TABLES `votes` WRITE;
/*!40000 ALTER TABLE `votes` DISABLE KEYS */;
INSERT INTO `votes` VALUES (1,1,2,NULL,2),(2,-1,1,NULL,2),(3,1,1,NULL,3),(4,-1,1,NULL,4),(5,-1,2,NULL,1);
/*!40000 ALTER TABLE `votes` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;



/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;


--
-- Dumping events for database 'studhub'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `delete_unactivated_users` */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;
/*!50003 SET time_zone             = 'SYSTEM' */ ;
/*!50106 CREATE*/ /*!50117 */ /*!50106 EVENT `delete_unactivated_users` ON SCHEDULE EVERY 1 DAY STARTS '2019-06-28 23:28:42' ON COMPLETION NOT PRESERVE ENABLE DO DELETE FROM users where DATEDIFF(CURDATE(), users.creation_date) > 1 AND users.is_activated = 0 */ ;
/*!50003 SET time_zone             = @saved_time_zone */ ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-28 23:28:54
