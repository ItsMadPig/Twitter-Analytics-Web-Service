import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class HbaseConnPool {
	static private List<HConnection> connectionPool = new ArrayList<HConnection>();  	
	synchronized static HConnection getConnection(Configuration config){
		synchronized (connectionPool) {
			if (connectionPool.size() > 0) {
				System.out.println(connectionPool.size()-1);
				return connectionPool.remove(connectionPool.size()-1);
			} else {
				try {
					return HConnectionManager.createConnection(config);
				} catch (ZooKeeperConnectionException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
	}
	synchronized static  void releaseConnection(HConnection conn) {
		synchronized (connectionPool) {
			connectionPool.add(conn);
		}
	}
}

