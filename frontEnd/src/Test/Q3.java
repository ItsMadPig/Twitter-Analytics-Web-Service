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

public class Q3 {
	static private List<Connection> connectionPool = new ArrayList<Connection>();  
	static private String jdbcDriver ="com.mysql.jdbc.Driver";
	static private String jdbcURL = "jdbc:mysql://ec2-52-4-103-140.compute-1.amazonaws.com/mysqltwitter";
	static private String tableName="q3";

	private synchronized static  Connection getConnection() throws Exception {
		synchronized (connectionPool) {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new Exception(e);
        }
		
	}
	
	private synchronized static  void releaseConnection(Connection con) {
		synchronized (connectionPool) {
		connectionPool.add(con);
		}
	}
	protected static String processRequest(HttpServerExchange exchange) {
		
		//System.out.println(request+" parameters: "+ exchange.getQueryString());
		Map<String, Deque<String>> paras = exchange.getQueryParameters();
		try {
			String userId = paras.get("userid").getFirst();
			long id = Long.parseLong(userId);
			String response = queryMysql(id).replace('_', '\n');
            return response;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	private static String queryMysql(long id) {
    	Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT y FROM " + tableName + " WHERE x=? limit 1");
        	pstmt.setLong(1,id);
        	ResultSet rs = pstmt.executeQuery();
        	
        	String ans=null;
            if (rs.next()) {
            	ans =rs.getString(1).trim();
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
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
