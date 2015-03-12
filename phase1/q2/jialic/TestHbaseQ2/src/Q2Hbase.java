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
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class Q2Hbase {
	static final byte[] tableName = Bytes.toBytes("twitter"),
			 qualifier=Bytes.toBytes("response"),
			family = Bytes.toBytes("family");
		//Go to find hbase-site.xml in class path
	static	Configuration config = HBaseConfiguration.create();
	static	 HTableFactory factory = new HTableFactory();
    static final HTableInterface table = factory.createHTableInterface(config, tableName);
		 
	protected void processRequest(HttpServerExchange exchange) {
		 String request = exchange.getRequestURL();
		//System.out.println(request+" parameters: "+ exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();

			String tweetTime = paras.get("tweet_time").getFirst();

			
			//Get response from Hbase though userId and tweetTime
			String response = getResponse(userId, tweetTime);

			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
					"text/plain");
			exchange.getResponseSender().send(response);
		} catch (Exception e) {
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
		try {
		String rowKeyStr= userId+'_'+tweetTime;
		final byte[] rowKey = Bytes.toBytes(rowKeyStr);
		Get get = new Get(rowKey);
		  Result result;
			result = table.get(get);
	        byte[] value = result.getValue(family, qualifier);
		return Bytes.toString(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}



}
