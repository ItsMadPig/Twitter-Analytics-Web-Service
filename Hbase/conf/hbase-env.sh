export HBASE_CLASSPATH=$(echo /home/hadoop/lib/*.jar | tr " " ":"):/home/hadoop/conf:/home/hadoop/native/Linux-amd64-64
export HBASE_LOG_DIR=/mnt/var/log/hbase
export HBASE_HEAPSIZE=5000
export HBASE_OPTS="-Djava.library.path=/home/hadoop/native/Linux-amd64-64:/home/hadoop/native/Linux-i386-32 -ea -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/mnt/var/log/hbase/ -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSIncrementalMode -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$HBASE_LOG_DIR/gc-hbase.log"
export HBASE_NICENESS=10
export HBASE_PID_DIR=/mnt/var/run/hbase
export HBASE_MANAGES_ZK=true
export HBASE_CONF_DIR=/home/hadoop/conf

if [ -e /home/hadoop/conf/hbase-user-env.sh ] ; then
  . /home/hadoop/conf/hbase-user-env.sh
fi
