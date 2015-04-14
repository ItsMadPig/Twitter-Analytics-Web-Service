package Test;

import io.undertow.server.HttpServerExchange;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Map;

public class Q5 {
	static private String tableName="q5";
	static class Pair implements Comparable<Pair>{
		String userid;
		long score;
		Pair(String userid, long score) {
			this.userid = userid;
			this.score = score;
		}
		@Override
		public int compareTo(Pair that) {
			if (that.score > this.score) {
				return 1;
			}
			else if (that.score <this.score) {
				return -1;
			
			}
			else {
				long thisid = Long.parseLong(this.userid);
				long thatid = Long.parseLong(that.userid);
				if (thisid < thatid) return -1;
				else if (thisid >thatid) return 1;
			}
			return 0;
		}
	}
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
			ArrayList<Pair> list = new ArrayList<Pair>();
			for (String userid: userlist) {
				long score = queryMysql(userid, startDate, endDate);
				list.add(new Pair(userid,score));
				
			}
			Collections.sort(list);
			for (Pair pair: list) {
				response.append(pair.userid+","+pair.score+"\n");
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
        	PreparedStatement pstmt = con.prepareStatement("SELECT sum(count)+3*max(friends)+5*max(followers) FROM " + tableName + " WHERE  userid=? and tweetdate>=? and tweetdate<=?");
        	pstmt.setLong(1,Long.parseLong(userid));
        	pstmt.setDate(2, startDate);
        	pstmt.setDate(3, endDate);

        	ResultSet rs = pstmt.executeQuery();
        	
        
        	
            if (rs.next()) {
            	score =rs.getLong(1);
      
              
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	MysqlConnection.releaseConnection(con);
        	 return score;
            
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
