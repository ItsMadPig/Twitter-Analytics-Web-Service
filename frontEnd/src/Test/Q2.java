package Test;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Q2 {
	static private String tableName="q2";


	protected static String processRequest(HttpServerExchange exchange) {
		
		//System.out.println(request+" parameters: "+ exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();

			String tweetTime = paras.get("tweet_time").getFirst();

			String fullStr = userId+"_"+tweetTime;

			String cachedResult = Cache.get(fullStr);

			if (cachedResult != null){
				return cachedResult;
			}else{
				String response = getMessage(fullStr);
				Cache.set(fullStr,response);
	            return response;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}


	/*
	 * Get text from Mysql
	 */
	private static  String getMessage(String userid_time) {
		List<String> message = getUserTweets(userid_time);
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
	


	public static List<String> getUserTweets(String userid_time) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT response FROM " + tableName + " WHERE userid_time=?");
        	pstmt.setString(1,userid_time);
        	ResultSet rs = pstmt.executeQuery();
        	
        	List<String> list = new ArrayList<String>();
            while (rs.next()) {
            	list.add(rs.getString(1).trim());
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	MysqlConnection.releaseConnection(con);
        	 return list;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	try {
				throw new Exception(e);
			} catch (Exception e1) {
				
				e1.printStackTrace();
				
			}
        	return null;
        }
	}
		

}
