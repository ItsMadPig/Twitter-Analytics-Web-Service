

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class MysqlDAO {
	public static List<String> getQ2(String userid_time) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT response FROM q2 WHERE userid_time=?");
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
	public static String getQ3(long id) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT y FROM q3 WHERE x=? limit 1");
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
	
	public static String getQ4(String hashtag, Date startDate, Date endDate) {
    	Connection con = null;
        try {
        	con = MysqlConnection.getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT response FROM q4 WHERE  hashtag=? and tweetdate>=? and tweetdate<=? order by tweetdate");
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
	
	public static long getQ5(String userid, Date startDate, Date endDate) {
		Connection con = null;
		long score = 0;
		try {
			con = MysqlConnection.getConnection();
			PreparedStatement pstmt = con
					.prepareStatement("SELECT sum(count)+3*max(friends)+5*max(followers) FROM q5 WHERE  userid=? and tweetdate>=? and tweetdate<=?");
			pstmt.setLong(1, Long.parseLong(userid));
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, endDate);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				score = rs.getLong(1);

			}

			rs.close();
			pstmt.close();
			MysqlConnection.releaseConnection(con);
			return score;

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e2) { /* ignore */
			}
			try {
				throw new Exception(e);
			} catch (Exception e1) {

				e1.printStackTrace();

			}
			return 0;
		}

	}
	
	
}
