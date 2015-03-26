package SQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TwitterDAO {
	static private List<Connection> connectionPool = new ArrayList<Connection>();  
	static private String jdbcDriver ="com.mysql.jdbc.Driver";
	static private String jdbcURL = "jdbc:mysql://ec2-54-173-32-254.compute-1.amazonaws.com/mysqltwitter";
	static private String tableName="twitter";

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
	public static List<String> getUserTweets(String userid_time) {
    	Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT response FROM " + tableName + " WHERE userid_time=?");
        	pstmt.setString(1,userid_time);
        	ResultSet rs = pstmt.executeQuery();
        	
        	List<String> list = new ArrayList<String>();
            while (rs.next()) {
            	list.add(rs.getString(1).trim());
            }
        	
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
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
