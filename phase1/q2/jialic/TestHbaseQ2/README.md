目前默认端口是：8080
命令：
cd cloud-compputing-project/phase1/q2/jialic/TestHbaseQ2/src
#没有javac的时候要download:
sudo yum install java-devel
然后就可以compile:
javac -cp '../lib/*:.' *.java
And run:
java -cp '../lib/*:.' Test

要改变Hbase 的dns位置，请进cloud-compputing-project/phase1/q2/jialic/TestHbaseQ2/bin/Hbase-site.xml里修改
如果把hbase-site.xml删掉，默认front end  和hbase server（back end)在同一台机器上

Test Request URL:
GET /q1?key=306063896731552281713201727176392168770237379582172677299123272033941091616817696059536783089054693601&message=URYEXYBJB
GET /q2?userid=2363462839&tweet_time=2014-05-14+00:27:15

