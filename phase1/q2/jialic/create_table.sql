USE mysqltwitter;
DROP TABLE IF EXISTS `twitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `twitter` 
  (
  `tweetid` varchar(19) NOT NULL,
  `userid` varchar(19),
  `time` text,
  `text` text,
  `score` int(11) DEFAULT NULL,
  `censoredtext` text,
  PRIMARY KEY (`tweetid`)
)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


 ALTER DATABASE `mysqltwitter` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
<<<<<<< HEAD
LOAD DATA LOCAL  INFILE '/Users/Scarlett/Desktop/eBusiness/15619/619Project/Phrase1/q2/db_1000' INTO TABLE twitter FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\0\n';
=======
LOAD DATA LOCAL  INFILE '/home/ec2-user/rawdata' INTO TABLE twitter FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\0\n';
>>>>>>> 0b311e5f4f9ee39af08c3d5ba3789991a058ea21


