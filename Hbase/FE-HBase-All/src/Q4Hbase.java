import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Q4Hbase {
	static final byte[] tableName = Bytes.toBytes("hbase-q4");
	static final byte[] family = Bytes.toBytes("f");
		//Go to find hbase-site.xml in class path
	static	Configuration config = HBaseConfiguration.create();
	static HConnection connection = null;
	static {
		try {
			config.set("hbase.zookeeper.quorum", "ip-172-31-58-171.ec2.internal");
			ExecutorService pool = Executors.newFixedThreadPool(100);
			connection = HConnectionManager.createConnection(config,  pool); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    protected void processRequest(HttpServerExchange exchange) {
       //String request = exchange.getRequestURL();
       //System.out.println(request+" parameters: "+ exchange.getQueryString());
       Map<String, Deque<String>> paras = exchange.getQueryParameters();
       try {
               String tag = paras.get("hashtag").getFirst();
               String start = paras.get("start").getFirst();
               String end = paras.get("end").getFirst();
               String response = getResponse(tag, start,end);
               exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,"text/plain");
               exchange.getResponseSender().send(response);
       } catch (Exception e) {
               System.out.println("Error here!!");
               System.out.println(e);
       }

    }

	/*
	* Response Format: TEAMID,AWS_ACCOUNT_ID1,AWS_ACCOUNT_ID2,AWS_ACCOUNT_ID3\n
	* Tweet ID1:Score1:TWEETTEXT1\n Tweet ID2:Score2:TWEETTEXT2\n
	*/
	private String getResponse(String tag, String start, String end) {
		 
	       String message = getMessageFromHbase(tag, start, end);
	       String teamID = "Oak";
	       final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
	       AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
	       AWS_ACCOUNT_ID3 = "588767211863";// Aaron
	
	       String response = String.format("%s,%s,%s,%s\n%s", teamID,
	                       AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, message);
	
	       return response;
	}

	private String getMessageFromHbase(String tag, String start, String end) {
		HTable table = null;
		String re = "";
		List<String> lre = new ArrayList<String>();
        try {
        	//= HConnectionManager.getConnection(config);
        	table = new HTable(tableName,  connection);
	        Get get = new Get(Bytes.toBytes(tag));
	        int istart = date2Index(start);
	        int iend = date2Index(end);
	        if (istart > iend) {
	        	return "";
	        }
	        /*
	        for (int i = 0; i <= iend - istart; i++) {
	        	int index = istart + i;
	        	byte[] qualifier = Bytes.toBytes("c" + index);
	        	get.addColumn(family, qualifier);
	        }*/
	        
	        Result result = table.get(get); 
	        for (int i = 0; i <= iend - istart; i++) {
	        	int index = istart + i;
	        	byte[] qualifier = Bytes.toBytes("c" + index);
	        	byte[] bcell = result.getValue(family, qualifier);
	        	if (bcell != null) {
	        		String temp = Bytes.toString(bcell);
	        		String[] parts = temp.split(";");
	        		for (int j = 0; j < parts.length - 1; j++) {
	        			lre.add(parts[j]);
	        		}
	        	}	        	
	        }
	        Collections.sort(lre);
	        for (int i = 0; i < lre.size(); i++) {
	        	re = re + lre.get(i) + "\n";
	        }
           	return re;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private static int date2Index(String dateString) {
		String beginDateString = "2014-03-21";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate = format.parse(dateString);
			Date beginDate = format.parse(beginDateString);
			long diff = currentDate.getTime() - beginDate.getTime();
			//long diffDays = diff / (24 * 60 * 60 * 1000);
			long diffDays = diff / 86400000;
			return (int)diffDays;
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}



}
