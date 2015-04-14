

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnection {
	static private List<Connection> connectionPool = new ArrayList<Connection>();  
	static private String jdbcDriver ="com.mysql.jdbc.Driver";
	static private String jdbcURL = "jdbc:mysql://ec2-52-4-231-158.compute-1.amazonaws.com/mysqltwitter";
	synchronized static  Connection getConnection() throws Exception {
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
	
	synchronized static  void releaseConnection(Connection con) {
		synchronized (connectionPool) {
			if (connectionPool.size() > 9){
				try{
					con.close();
				}catch(Exception e){
					
				}
			}else{
				connectionPool.add(con);
			}
		}
	}

}
