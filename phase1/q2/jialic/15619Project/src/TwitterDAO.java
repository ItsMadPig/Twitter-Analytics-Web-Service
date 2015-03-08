import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TwitterDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();
	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	public TwitterDAO(String jdbcDriver, String jdbcURL, String tableName) throws Exception {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = tableName;
	}
	private synchronized Connection getConnection() throws Exception {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
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
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}
	public List<TwitterBean> getUserTweets(String userId, String tweetTime) {
    	Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE userid=? AND time=?");
        	pstmt.setString(1,userId);
        	pstmt.setString(2, tweetTime);
        	ResultSet rs = pstmt.executeQuery();
        	
        	List<TwitterBean> list = new ArrayList<TwitterBean>();
            while (rs.next()) {
            	TwitterBean bean = new TwitterBean();
                bean.setTweetid(rs.getString("tweetid"));
                bean.setScore(rs.getInt("score"));
                bean.setCensoredtext(rs.getString("censoredtext"));
               
            	list.add(bean);
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
