USE mysqltwitter;
DROP TABLE IF EXISTS `q3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `q3` 
  (
  `x` INT UNSIGNED primary key,
  `y` LONGTEXT  NOT NULL
)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOAD DATA LOCAL  INFILE '/mnt/tmp/q3data0412' INTO TABLE q3 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';


