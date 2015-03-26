import java.io.IOException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import io.undertow.server.HttpServerExchange;

public class Q3hbase {
	static final byte[] tableName = Bytes.toBytes("q3"), qualifier = Bytes
			.toBytes("y"), family = Bytes.toBytes("f");

	static Configuration config = HBaseConfiguration.create();
	static HConnection connection = null;
	static {
		try {
			config.set("hbase.zookeeper.quorum",
					"ip-172-31-59-153.ec2.internal");
			ExecutorService pool = Executors.newFixedThreadPool(100);
			connection = HConnectionManager.createConnection(config, pool);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String processRequest(HttpServerExchange exchange) {
		// System.out.println(request+" parameters: "+
		// exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();
			
			String response = queryHbase(userId).replace('_', '\n');
			return response;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private static String queryHbase(String id) {
		HTable table = null;
		try {

			table = new HTable(tableName, connection);

			final byte[] rowKey = Bytes.toBytes(id);
			Get get = new Get(rowKey);
			Result result = table.get(get);
			byte[] response = result.getValue(family, qualifier);
			return Bytes.toString(response);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error here.");
			return null;
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
