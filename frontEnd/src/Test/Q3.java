package Test;

import io.undertow.server.HttpServerExchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.lang.String;

public class Q3 {
	
	
	static private String tableName="q3";

	protected static String processRequest(HttpServerExchange exchange) {
		
		//System.out.println(request+" parameters: "+ exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();
			long id = Long.parseLong(userId);


			String cachedResult = Cache.get(String.valueOf(id));
			if (cachedResult != null){
				return cachedResult;
			}else{
				String response = queryMysql(id).replace('_', '\n');
				Cache.set(String.valueOf(id),response);
	            return response;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	private static String queryMysql(long id) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT y FROM " + tableName + " WHERE x=? limit 1");
        	pstmt.setLong(1,id);
        	ResultSet rs = pstmt.executeQuery();
        	
        	String ans=null;
            if (rs.next()) {
            	ans =rs.getString(1).trim();
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	MysqlConnection.releaseConnection(con);
        	 return ans;
            
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
