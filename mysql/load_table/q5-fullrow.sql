USE mysqltwitter;
DROP TABLE IF EXISTS `q5-fullrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q5-fullrow`(
  `userid_date` varchar(22) NOT NULL,
  `count` INT UNSIGNED NOT NULL,
  `friends` INT  NOT NULL,
  `followers` INT NOT NULL,
   primary key(userid_date)
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/tmp/q5data-new' INTO TABLE `q5-fullrow` FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';


/*CREATE INDEX userid_date_ind ON q5 (userid, tweetdate)*/;
