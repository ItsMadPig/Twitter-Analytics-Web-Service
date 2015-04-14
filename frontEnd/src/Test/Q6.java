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

public class Q6 {
	static private String tableName="q6";
	public static String processRequest(HttpServerExchange exchange) {
		//q5?userlist=12,16,18&start=2010-01-01&end=2014-12-31
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String m= paras.get("m").getFirst();
			String n = paras.get("n").getFirst();
			
			long nNum = query(Long.parseLong(n));
			long mNum = Long.parseLong(m)-1;
			if (mNum <0) mNum =0;
			mNum = query(mNum);
			String response = String.valueOf(nNum - mNum);
			
		
		    return response;


 		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private static long query(long userid) {
    	Connection con = null;
    	long score =0;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("select totalcount from q6 where q6.userid = (select max(userid) from q6 where userid<?)");
        	pstmt.setLong(1,userid);
        	
        	
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
