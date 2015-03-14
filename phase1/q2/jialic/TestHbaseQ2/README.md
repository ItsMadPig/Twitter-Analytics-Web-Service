
To test
Q2 for Hbase:




#要改变Hbase 的dns位置，请进Q2Hbase.java修改这句话：
config.set("hbase.zookeeper.quorum", "ip-172-31-18-251.ec2.internal:2181");

#先进入src目录
cd cloud-compputing-project/phase1/q2/jialic/TestHbaseQ2/src

#没有javac的时候要download:
sudo yum install java-devel


#然后就可以compile:
javac -cp '../lib/*:.' *.java

#And run:
java -cp '../lib/*:.' Test





#Test Request URL:
sudo telnet localhost 80


GET /q1?key=306063896731552281713201727176392168770237379582172677299123272033941091616817696059536783089054693601&message=URYEXYBJB

GET /q2?userid=2363462839&tweet_time=2014-05-14+00:27:15


#如何load data:
//Only importTSV  注意数据是\t为分隔
hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.columns="HBASE_ROW_KEY,family:num" oak input


//importTsv for bulk loading
// it creates a new empty table, and put file to hdfs
hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.columns="HBASE_ROW_KEY,family:num" -Dimporttsv.bulk.output=hdfs://172.31.28.91:9000/user/hadoop/output oak ../input



//Bulk function---load data from dfs to hbase table
hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles hdfs://172.31.28.91:9000/user/hadoop/output 'oak'

