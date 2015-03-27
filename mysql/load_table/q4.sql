USE mysqltwitter;
DROP TABLE IF EXISTS `q4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q4` 
  (
  `hashtag` varchar(200) NOT NULL,
  `tweetdate` DATE NOT NULL,	
  `response` LONGTEXT NOT NULL,
  INDEX hashtag_date (hashtag, tweetdate)
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/q4' INTO TABLE q4 FIELDS TERMINATED BY '_' LINES TERMINATED BY '\n';


