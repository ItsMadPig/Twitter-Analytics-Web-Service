USE mysqltwitter;
DROP TABLE IF EXISTS `q6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q6` 
  (
  	`userid` INT UNSIGNED  primary key,
  `number` INT NOT NULL
)  CHARACTER SET latin1 COLLATE latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/Users/Scarlett/Desktop/eBusiness/15619/619Project/tobeLoadToQ6' INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';

