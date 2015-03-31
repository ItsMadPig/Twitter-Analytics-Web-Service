import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class HbaseConnPool {
	static private List<HconnObject> connectionPool = new ArrayList<HconnObject>(); 
	synchronized static HconnObject getConnection(Configuration config){
		synchronized (connectionPool) {
			if (connectionPool.size() > 0) {
				HconnObject conn = connectionPool.get(connectionPool.size()-1);
				conn.deThreadNum();
				if (conn.getThreadNum() > 0) {
					System.out.println(conn.getThreadNum());
					return connectionPool.get(connectionPool.size()-1);
				} else {
					return connectionPool.remove(connectionPool.size()-1);
				}
			} else {
				HconnObject conn = new HconnObject(config);
				connectionPool.add(conn);
				return conn;
			}
		}
	}
	synchronized static  void releaseConnection(HconnObject conn) {
		synchronized (connectionPool) {
			if (conn.getThreadNum() > 0) {
				conn.inThreadNum();
			} else {
				conn.inThreadNum();
				connectionPool.add(conn);
			}
		}
	}
}



