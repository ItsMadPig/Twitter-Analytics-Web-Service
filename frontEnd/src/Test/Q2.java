package Test;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Q2 {

	protected static String processRequest(HttpServerExchange exchange) {
		
		//System.out.println(request+" parameters: "+ exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();

			String tweetTime = paras.get("tweet_time").getFirst();

			String response = getMessage(userId+"_"+tweetTime);
            return response;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}


	/*
	 * Get text from Mysql
	 */
	private static  String getMessage(String userid_time) {
		List<String> message = TwitterDAO.getUserTweets(userid_time);
		Collections.sort(message);
		StringBuilder response=new StringBuilder("");
		//System.out.println("empty response: "+response+"  size: "+message.size());
		if (message!=null && message.size()>0) {
			for (String tweet:message) {
				response.append(tweet+"\n");
			}
		}
		//System.out.println("response: "+response);
		return response.toString();
	}

}
