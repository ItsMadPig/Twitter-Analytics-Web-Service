USE mysqltwitter;
DROP TABLE IF EXISTS `q4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q4` 
  (
  `hashtag` varchar(100) NOT NULL,
  `tweetdate` DATE NOT NULL,	
  `response` LONGTEXT NOT NULL,
  INDEX hashtag_date (hashtag, tweetdate)
)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE 'q4data' INTO TABLE q4 FIELDS TERMINATED BY '_' LINES TERMINATED BY '\n';


