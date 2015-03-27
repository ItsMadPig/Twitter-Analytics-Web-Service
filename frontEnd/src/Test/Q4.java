package Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Deque;
import java.util.Map;

import io.undertow.server.HttpServerExchange;

public class Q4 {
	static private String tableName="q4";
	public static String processRequest(HttpServerExchange exchange) {
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String hashtag = paras.get("hashtag").getFirst();
			String start = paras.get("start").getFirst();
			String end = paras.get("end").getFirst();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date startDate = Date.valueOf(start);
			Date endDate =Date.valueOf(end);
		       //System.out.println(start);	
			String response = queryMysql(hashtag, startDate, endDate).replace(';', '\n');
            return response;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private static String queryMysql(String hashtag, Date startDate, Date endDate) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT response FROM " + tableName + " WHERE  hashtag=? and tweetdate>=? and tweetdate<=?");
        	pstmt.setString(1,hashtag);
        	pstmt.setDate(2, startDate);
        	pstmt.setDate(3, endDate);
        	ResultSet rs = pstmt.executeQuery();
        	
        	StringBuffer ans=new StringBuffer();
        	
            while (rs.next()) {
                 //System.out.println(rs.getString(1));
            	ans.append(rs.getString(1).trim());
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	MysqlConnection.releaseConnection(con);
        	 return ans.toString();
            
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
