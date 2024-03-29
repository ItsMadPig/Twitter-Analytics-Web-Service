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

public class Q2Hbase {
	static final byte[] tableName = Bytes.toBytes("hbase-cluster-twitter"),
            ctweetID=Bytes.toBytes("tweetId"),
            cscore=Bytes.toBytes("score"),
            ccencored=Bytes.toBytes("censoredText"),
            family = Bytes.toBytes("family");
		//Go to find hbase-site.xml in class path
	static	Configuration config = HBaseConfiguration.create();
	static HConnection connection = null;
	static {
		try {
			config.set("hbase.zookeeper.quorum", "ip-172-31-53-147.ec2.internal");
			ExecutorService pool = Executors.newFixedThreadPool(10);
			connection = HConnectionManager.createConnection(config,  pool); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    protected void processRequest(HttpServerExchange exchange) {
        String request = exchange.getRequestURL();
       //System.out.println(request+" parameters: "+ exchange.getQueryString());
       Map<String, Deque<String>> paras = exchange.getQueryParameters();
       try {
               String userId = paras.get("userid").getFirst();
               String tweetTime = paras.get("tweet_time").getFirst();
               //Get response from Hbase though userId and tweetTime
               String response = getResponse(userId, tweetTime);
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
	private String getResponse(String userId, String tweetTime) {
		 
	       String message = getMessageFromHbase(userId, tweetTime);
	       String teamID = "Oak";
	       final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
	       AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
	       AWS_ACCOUNT_ID3 = "588767211863";// Aaron
	
	       String response = String.format("%s,%s,%s,%s\n%s", teamID,
	                       AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, message);
	
	       return response;
	}

	private String getMessageFromHbase(String userId, String tweetTime) {
		HTable table = null;
        try {
        	//= HConnectionManager.getConnection(config);
        	table = new HTable(tableName,  connection);
			
	        String rowKeyStr= userId+'_'+tweetTime;
	        //String rowKeyStr= userId;
	        final byte[] rowKey = Bytes.toBytes(rowKeyStr);
	        Get get = new Get(rowKey);
	        get.setMaxVersions(10);
	        Result result = table.get(get);
	        final NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();
	        final NavigableMap<byte[], NavigableMap<Long, byte[]>> familyMap = map.get(family);
	        final NavigableMap<Long, byte[]> versionMap_ctweetID = familyMap.get(ctweetID);
	        final NavigableMap<Long, byte[]> versionMap_cscore = familyMap.get(cscore);
	        final NavigableMap<Long, byte[]> versionMap_ccencored = familyMap.get(ccencored);
	        List<String> col1 = new LinkedList<String>();
	        List<String> col2 = new LinkedList<String>();
	        List<String> col3 = new LinkedList<String>();
	        for (final Map.Entry<Long, byte[]> entry : versionMap_ctweetID.entrySet())
	        {
	                col1.add(Bytes.toString(entry.getValue()));
	        }
	        for (final Map.Entry<Long, byte[]> entry : versionMap_cscore.entrySet())
	        {
	                col2.add(Bytes.toString(entry.getValue()));
	        }
	        for (final Map.Entry<Long, byte[]> entry : versionMap_ccencored.entrySet())
	        {
	                col3.add(Bytes.toString(entry.getValue()));
	        }
            String temp = "";
            ListIterator<String> iter1 = col1.listIterator();
            ListIterator<String> iter2 = col2.listIterator();
            ListIterator<String> iter3 = col3.listIterator();
            while(iter1.hasNext() && iter2.hasNext() && iter3.hasNext()){
                    String text = StringEscapeUtils.unescapeJson(iter3.next());
                    temp =  iter1.next() + ':' + iter2.next() + ':' + text.substring(0, text.length()-1) + '\n' + temp;
            }
            return temp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}
