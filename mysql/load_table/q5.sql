USE mysqltwitter;
DROP TABLE IF EXISTS `q5new`;
CREATE TABLE `q5new`(
  `userid` INT UNSIGNED NOT NULL,
  `tweetdate` DATE NOT NULL,	
  `count` INT UNSIGNED NOT NULL,
  `friends` INT  NOT NULL,
  `followers` INT NOT NULL
)  CHARACTER SET latin1 COLLATE latin1_general_cs;

LOAD DATA LOCAL  INFILE '/mnt/tmp/q5data' INTO TABLE q5new FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n';


CREATE INDEX userid_date_ind ON q5new (userid, tweetdate, friends, followers);
