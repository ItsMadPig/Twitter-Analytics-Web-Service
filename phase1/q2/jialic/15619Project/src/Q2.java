import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Q2 {
	private TwitterDAO twitterDAO;

	Q2() {
		try {
			String jdbcDriverName = "com.mysql.jdbc.Driver";
			//Put DNS address of mysql here
			String jdbcURL = "jdbc:mysql:///mysqltwitter";
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
			twitterDAO = new TwitterDAO(jdbcDriverName, jdbcURL, "twitter");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void processRequest(HttpServerExchange exchange) {
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

	/*
	 * Response Format: TEAMID,AWS_ACCOUNT_ID1,AWS_ACCOUNT_ID2,AWS_ACCOUNT_ID3\n
	 * Tweet ID1:Score1:TWEETTEXT1\n Tweet ID2:Score2:TWEETTEXT2\n
	 */
	private String getResponse(String userId, String tweetTime) {
		String message = getMessage(userId, tweetTime);
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron

		String response = String.format("%s,%s,%s,%s\n%s", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3, message);

		return response;
	}

	/*
	 * Get text from Mysql
	 */
	private String getMessage(String userId, String tweetTime) {
		List<TwitterBean> message = twitterDAO.getUserTweets(userId, tweetTime);
		StringBuilder response=new StringBuilder("");
		//System.out.println("empty response: "+response+"  size: "+message.size());
		if (message!=null && message.size()>0) {
			for (TwitterBean tweet:message) {
				response.append(tweet.getTweetid()+":"+tweet.getScore()+":"+tweet.getCensoredtext()+"\n");
			}
		}
		System.out.println("response: "+response);
		return response.toString();
	}

}
