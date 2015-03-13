cd ~/Documents/hbase-java-sample/src/
javac -cp "../lib/*" com/yujikosuga/hbase/*.java
java -cp "../lib/*:." com.yujikosuga.hbase.Main <dns> <port>





#in zoo.cfg set quorum
server.0=localhost:2888:3888
