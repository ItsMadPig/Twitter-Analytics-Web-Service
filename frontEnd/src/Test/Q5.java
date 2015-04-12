package Test;

import io.undertow.server.HttpServerExchange;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Deque;
import java.util.Map;

public class Q5 {
	static private String tableName="q5";
	public static String processRequest(HttpServerExchange exchange) {
		//q5?userlist=12,16,18&start=2010-01-01&end=2014-12-31
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String[] userlist = paras.get("userlist").getFirst().split(",");
			
			String start = paras.get("start").getFirst();
			String end = paras.get("end").getFirst();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date startDate = Date.valueOf(start);
			Date endDate =Date.valueOf(end);
		       
			StringBuffer response = new StringBuffer();
			for (String userid: userlist) {
				long score = queryMysql(userid, startDate, endDate);
				response.append(userid+","+score+"\n");
			}

		    return response.toString();


 		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private static long queryMysql(String userid, Date startDate, Date endDate) {
    	Connection con = null;
    	long score =0;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE  userid=? and tweetdate>=? and tweetdate<=?");
        	pstmt.setLong(1,Long.parseLong(userid));
        	pstmt.setDate(2, startDate);
        	pstmt.setDate(3, endDate);
        	int maxFriends  = 0;
        	int maxFollowers = 0;
        	ResultSet rs = pstmt.executeQuery();
        	
        	StringBuffer ans=new StringBuffer();
        	
            while (rs.next()) {
            	score +=rs.getInt("count");
            	int friends = rs.getInt("friends") ;
            	int followers = rs.getInt("followers");
            	maxFriends = maxFriends > friends ? maxFriends: friends;
            	maxFollowers = maxFollowers > followers ? maxFollowers: followers;
              
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	MysqlConnection.releaseConnection(con);
        	 return score+3*maxFriends+5*maxFollowers;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	try {
				throw new Exception(e);
			} catch (Exception e1) {
				
				e1.printStackTrace();
				
			}
        	return 0;
        }
		
	}

}
