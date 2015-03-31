import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;


public class HconnObject {
	private final int threadPool = 5;
	private int threadNum = threadPool;
	private ExecutorService pool;
	HConnection conn = null;
	
	HconnObject(Configuration config){
		pool = Executors.newFixedThreadPool(this.threadPool);
		try {
			conn = HConnectionManager.createConnection(config,pool);
			this.threadNum--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	synchronized public int getThreadNum() {
		return this.threadNum;
	}
	synchronized public void deThreadNum() {
		this.threadNum--;
	}
	synchronized public void inThreadNum() {
		this.threadNum++;
	}
	synchronized public void initConnection() {
		this.threadNum = this.threadPool;
	}
	synchronized public HConnection getConn(){
		return this.conn;
	}
}

