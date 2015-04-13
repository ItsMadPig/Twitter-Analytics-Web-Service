USE mysqltwitter;
DROP TABLE IF EXISTS `q5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q5`(
  `userid` INT UNSIGNED NOT NULL,
  `tweetdate` DATE NOT NULL,	
  `count` INT UNSIGNED NOT NULL,
  `friends` INT  NOT NULL,
  `followers` INT NOT NULL,
   primary key(userid,tweetdate)
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/tmp/q5data' INTO TABLE q5 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';


/*CREATE INDEX userid_date_ind ON q5 (userid, tweetdate)*/;
