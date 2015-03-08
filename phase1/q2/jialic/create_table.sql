USE mysqltwitter;
DROP TABLE IF EXISTS `twitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `twitter` 
  (
  `tweetid` varchar(19) NOT NULL,
  `userid` text,
  `time` text,
  `text` text,
  `score` int(11) DEFAULT NULL,
  `censoredtext` text,
  PRIMARY KEY (`tweetid`)
)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


 ALTER DATABASE `mysqltwitter` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
LOAD DATA LOCAL  INFILE '/Users/Scarlett/Desktop/eBusiness/15619/619Project/Phrase1/q2/db_1000' INTO TABLE twitter FIELDS TERMINATED BY '::|||' LINES TERMINATED BY '::::|||\n';


