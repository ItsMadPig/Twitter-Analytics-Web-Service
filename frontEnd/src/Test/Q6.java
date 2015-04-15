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
			
			long nNum = Long.parseLong(n);
			long mNum = Long.parseLong(m)-1;
			if (mNum <0) mNum =0;
			long result = query(mNum,nNum);
			String response = String.valueOf(result)+"\n";
			
		
		    return response;


 		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	private static long query(long userid1,long userid2) {
    	Connection con = null;
    	long score1 =0;
    	long score2 = 0;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt1 = con.prepareStatement("select totalcount from q6 where q6.userid = (select max(userid) from q6 where userid<=?)");
        	pstmt.setLong(1,userid1);
        	ResultSet rs1 = pstmt.executeQuery();
        	
        	PreparedStatement pstmt2 = con.prepareStatement("select totalcount from q6 where q6.userid = (select max(userid) from q6 where userid<=?)");
        	pstmt.setLong(1,userid2);
        	ResultSet rs2 = pstmt.executeQuery();
        	
        	score1 =rs1.getLong(1);
        	score2 = rs2.getLong(1);
        	
        	rs1.close();
        	pstmt1.close();
        	rs2.close();
        	pstmt2.close();
        	MysqlConnection.releaseConnection(con);
        	 return score2 - score1;
            
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
