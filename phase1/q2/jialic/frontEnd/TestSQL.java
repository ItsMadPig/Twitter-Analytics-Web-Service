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


public class Test {

	public static void main(String[] args) {
		connectSQL();
		Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0")
				.setHandler(new HttpHandler() {
					
					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						String requestPath = exchange.getRequestPath();
						if (requestPath.equals("/q1")) {
							Q1 q1 = new Q1();
							q1.processRequest(exchange);
						}
						else 
						 if (requestPath.equals("/q2")) {
							 if(args.length<1 || args[0]=="")
							    {
							        System.out.println("Proper Usage is: java Test <DNS name of mysql server>");
							        System.exit(0);
							    }
							 long startTime = System.currentTimeMillis();
							// //System.out.println(Arrays.toString(args));
							// Q2 q2 = new Q2(args[0]);
							// Q2Hbase q2 = new Q2Hbase();
							// q2.processRequest(exchange);
							 getResponse(exchange);
							 long endTime   = System.currentTimeMillis();
							 long totalTime = endTime - startTime;
							 System.out.println("runtime: "+totalTime);
							 
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
	protected static void getResponse(HttpServerExchange exchange) {
		 String request = exchange.getRequestURL();
			//System.out.println(request+" parameters: "+ exchange.getQueryString());
			Map<String, Deque<String>> paras = exchange.getQueryParameters();
			try {
				String userId = paras.get("userid").getFirst();

				String tweetTime = paras.get("tweet_time").getFirst();

				String response = getResponse(userId, tweetTime);

				exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
						"text/plain");
				exchange.getResponseSender().send(response);
			} catch (Exception e) {
				System.out.println(e);
			}
		
	}
	private static String getResponse(String userId, String tweetTime) {
		String message = getMessage(userId, tweetTime);
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron

		String response = String.format("%s,%s,%s,%s\n%s", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, message);

		return response;
	}
	private static String getMessage(String userId, String tweetTime) {
		List<TwitterBean> message = querySQL(userId+"_"+tweetTime);
		StringBuilder response=new StringBuilder("");
		//System.out.println("empty response: "+response+"  size: "+message.size());
		if (message!=null && message.size()>0) {
			for (TwitterBean tweet:message) {
				response.append(tweet.getResponse()+"\n");
			}
		}
		//System.out.println("response: "+response);
		return response.toString();
	}
	static Connection con=null;
	static String jdbcTable="twitter";
	static void connectSQL(){
		try {
		String jdbcDriverName = "com.mysql.jdbc.Driver";
		//Put DNS address of mysql here
		String jdbcURL = "jdbc:mysql:///mysqltwitter";
		Class.forName(jdbcDriverName);
		con=DriverManager.getConnection(jdbcURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static List<TwitterBean>  querySQL(String userid_time){
		try {
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + jdbcTable + " WHERE userid_time=?");
    	
			pstmt.setString(1,userid_time);
		
    	ResultSet rs = pstmt.executeQuery();
    	
    	List<TwitterBean> list = new ArrayList<TwitterBean>();
        while (rs.next()) {
        	TwitterBean bean = new TwitterBean();
            bean.setUserid_time(rs.getString("userid_time"));
            bean.setResponse(rs.getString("response").trim());
        	list.add(bean);
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
