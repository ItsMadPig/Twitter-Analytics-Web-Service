import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;




import java.util.Deque;
import java.util.List;
import java.util.Map;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
/*
 * Prepared team info, delete Twitter Bean, change list<Twitter bean> to list String
 */

public class TestSQL2 {
	public static void main(final String[] args) {
		 if(args.length<1)
		    {
		        System.out.println("Proper Usage is: java Test <DNS name adress>");
		        System.exit(0);
		    }
		final String teamInfo=getTeamInfo();
		connectSQL(args[0]);
		Undertow server = Undertow.builder().addHttpListener(80, "0.0.0.0")
				.setHandler(new HttpHandler() {
					
					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						String requestPath = exchange.getRequestPath();
						 if (requestPath.equals("/q2")) {
							 String response=getResponse(exchange);
							 exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
										"text/plain");
								exchange.getResponseSender().send(teamInfo+response);
						 }
						 else
						{
							System.out
									.println("Waiting for another correct url request");
							exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
									"text/plain");
							exchange.getResponseSender().send("I got your data.:D");
							
						}
					}
				}).build();
		server.start();

	}
	private static String getTeamInfo() {
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron
		String response = String.format("%s,%s,%s,%s\n", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3);
		return response;
		
	}
	protected static String getResponse(HttpServerExchange exchange) {
			Map<String, Deque<String>> paras = exchange.getQueryParameters();
			try {
				String userId = paras.get("userid").getFirst();

				String tweetTime = paras.get("tweet_time").getFirst();

				String response = getMessage(userId, tweetTime);
                return response;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		
	}
	private static String getMessage(String userId, String tweetTime) {
		List<String> message = querySQL(userId+"_"+tweetTime);
		StringBuilder response=new StringBuilder("");
		if (message!=null && message.size()>0) {
			for (String tweet:message) {
				response.append(tweet+"\n");
			}
		}
		
		return response.toString();
	}
	static Connection con=null;
	static String jdbcTable="twitter";
  // static PreparedStatement pstmt;
	static void connectSQL(String dns){
		try {
		String jdbcDriverName = "com.mysql.jdbc.Driver";
		//Put DNS address of mysql here
		String jdbcURL = "jdbc:mysql://"+dns+"/mysqltwitter";
		Class.forName(jdbcDriverName);
		con=DriverManager.getConnection(jdbcURL);
		// pstmt = con.prepareStatement("SELECT * FROM " + jdbcTable + " WHERE userid_time=?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static List<String>  querySQL(String userid_time){
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + jdbcTable + " WHERE userid_time=?");
			pstmt.setString(1,userid_time);
    	ResultSet rs = pstmt.executeQuery();
    	
    	List<String> list = new ArrayList<String>();
        while (rs.next()) {
        	list.add(rs.getString("response").trim());
        }
    	rs.close();
    	pstmt.close();
    	return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
