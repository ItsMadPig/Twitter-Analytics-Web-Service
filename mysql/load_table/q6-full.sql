USE mysqltwitter;
DROP TABLE IF EXISTS `q6full`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q6full` 
  (
  `userid` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `totalcount` INT UNSIGNED NOT NULL,
  PRIMARY KEY (userid)
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/tmp/q6-final-full' INTO TABLE q6full FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';

