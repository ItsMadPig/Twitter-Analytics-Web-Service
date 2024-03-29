USE mysqltwitter;
DROP TABLE IF EXISTS `q6-fixedrow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q6-fixedrow` 
  (
  `userid` INT UNSIGNED NOT NULL,
  `totalcount` INT UNSIGNED NOT NULL,
  primary key (userid)
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/tmp/q6-final-partial' INTO TABLE `q6-fixedrow` FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';

