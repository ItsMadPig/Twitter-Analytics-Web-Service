# Twitter Analytics Web Service－15619_Project
15619 cloud computing team project

startInstances.py- creates a ELB and front end servers

ETL
	map reduce code and post process
FEexec
	replace crontab with /etc/init.d/crontab
	run code to initiate front end
	startFEserver - when reboot or startup, auto started by crontab

mysql/   all mysql code
	load_table -> script for loading table into mysql
	(newest create_table_2col.sql)

Hbase/   all hbase code
	conf/ -> all configuration files
	FE-HBase -> hbase front end
	readmes -> commands done in shell, bulk load data, backup,restart

test/
	data used for testing/debugging

phase2/
	src code and lib etc




