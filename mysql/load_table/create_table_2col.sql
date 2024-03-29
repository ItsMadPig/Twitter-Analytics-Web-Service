USE mysqltwitter;
DROP TABLE IF EXISTS `twitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `twitter` 
  (
  `userid_time` varchar(35) NOT NULL,
  `response` text NOT NULL
)  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


 ALTER DATABASE `mysqltwitter` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
LOAD DATA LOCAL  INFILE '/mnt2/finaldata2' INTO TABLE twitter FIELDS TERMINATED BY '\t|||\t' LINES TERMINATED BY '\0\0\n';


