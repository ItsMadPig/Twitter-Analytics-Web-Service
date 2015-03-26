import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.String;
import java.lang.Object;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Operation;
import org.apache.hadoop.hbase.client.OperationWithAttributes;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.AbstractClientScanner;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class Q2Hbase {
	static final byte[] tableName = Bytes.toBytes("hbase-cluster-twitter"),
			ctweetID = Bytes.toBytes("tweetId"), cscore = Bytes
					.toBytes("score"), ccencored = Bytes
					.toBytes("censoredText"), family = Bytes.toBytes("family");

	static Configuration config = HBaseConfiguration.create();
	static HConnection connection = null;
	static {
		try {
			config.set("hbase.zookeeper.quorum",
					"ip-172-31-53-147.ec2.internal");
			ExecutorService pool = Executors.newFixedThreadPool(100);
			connection = HConnectionManager.createConnection(config, pool);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String processRequest(HttpServerExchange exchange) {
		String request = exchange.getRequestURL();

		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();
			String tweetTime = paras.get("tweet_time").getFirst();
			// Get response from Hbase though userId and tweetTime
			String response = getMessageFromHbase(userId, tweetTime);
			return response;

			
		} catch (Exception e) {
			System.out.println("erorr is Q2hbase processRequest: "+e);
			return null;
		}

	}


	private String getMessageFromHbase(String userId, String tweetTime) {
		HTable table = null;
		try {

			table = new HTable(tableName, connection);
			String rowKeyStr = userId + '_' + tweetTime;

			final byte[] rowKey = Bytes.toBytes(rowKeyStr);
			Get get = new Get(rowKey);
			get.setMaxVersions(10);
			Result result = table.get(get);

			final NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result
					.getMap();
			final NavigableMap<byte[], NavigableMap<Long, byte[]>> familyMap = map
					.get(family);
			final NavigableMap<Long, byte[]> versionMap_ctweetID = familyMap
					.get(ctweetID);
			final NavigableMap<Long, byte[]> versionMap_cscore = familyMap
					.get(cscore);
			final NavigableMap<Long, byte[]> versionMap_ccencored = familyMap
					.get(ccencored);
			List<String> col1 = new LinkedList<String>();
			List<String> col2 = new LinkedList<String>();
			List<String> col3 = new LinkedList<String>();
			for (final Map.Entry<Long, byte[]> entry : versionMap_ctweetID
					.entrySet()) {
				col1.add(Bytes.toString(entry.getValue()));
			}
			for (final Map.Entry<Long, byte[]> entry : versionMap_cscore
					.entrySet()) {
				col2.add(Bytes.toString(entry.getValue()));
			}
			for (final Map.Entry<Long, byte[]> entry : versionMap_ccencored
					.entrySet()) {
				col3.add(Bytes.toString(entry.getValue()));
			}
			String temp = "";
			ListIterator<String> iter1 = col1.listIterator();
			ListIterator<String> iter2 = col2.listIterator();
			ListIterator<String> iter3 = col3.listIterator();
			while (iter1.hasNext() && iter2.hasNext() && iter3.hasNext()) {
				// String text = StringEscapeUtils.unescapeJson(iter3.next());
				String text = iter3.next();
				// text = text.substring(0, text.length()-1);
				temp = iter1.next() + ':' + iter2.next() + ':'
						+ StringEscapeUtils.unescapeJson(text) + "\n" + temp;
			}
			// System.out.println(temp);

			return temp;
		
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

	private String getResponseQ4(String tag, String startTime, String endTime) {
		String message = getMessageFromHbaseQ4(tag, startTime, endTime);
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron

		String response = String.format("%s,%s,%s,%s\n%s", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, message);

		return response;
	}

	private String getMessageFromHbaseQ4(String tag, String startTime,
			String endTime) {
		HTable table = null;
		try {
			table = new HTable(tableName, connection);
			byte[] tag_bytes = Bytes.toBytes(tag);
			Scan s = new Scan(tag_bytes, tag_bytes);
			// true for inclusive, startTime, endTime for qualifier
			Filter f = new ColumnRangeFilter(Bytes.toBytes(startTime), true,
					Bytes.toBytes(endTime), true);
			s.setFilter(f);
			String temp = "";
			String str_val = "";
			List<String> list_val = new ArrayList<String>();
			ResultScanner rs = table.getScanner(s);
			Result r;
			for (r = rs.next(); r != null; r = rs.next()) {
				// r will now have all HBase columns that start with "fluffy",
				// which would represent a single row
				for (KeyValue kv : r.raw()) {
					// each kv represent - the latest version of - a column
					list_val.add(Bytes.toString(kv.getValue()));
				}
				while (list_val.size() > 1) {
					int oldest = 0;
					int list_size = list_val.size();
					int i = 0;
					for (i = 1; i < list_size; i++) {
						if (Integer.parseInt(list_val.get(oldest).substring(0,
								(list_val.get(oldest).indexOf(',')))) > Integer
								.parseInt(list_val.get(i).substring(0,
										(list_val.get(i).indexOf(','))))) {
							oldest = i;
						}
					}
					temp += list_val.get(oldest);
				}
			}
			return temp;
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
