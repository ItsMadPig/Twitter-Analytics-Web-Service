import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;
public class ETLReducer {
	public static void main (String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
			String input;
			String tweetId;
			String oldtweetId = null;
			while ((input=br.readLine())!=null) {
				System.out.println(StringEscapeUtils.unescapeJson(input));
				//String[] parts = input.split("\t");
				//tweetId = parts[0];
				/*
				if (tweetId != null && tweetId.equals(oldtweetId)) {
					continue;
				} else {
					//System.out.println(input);
					System.out.println(json2plain(input));
					oldtweetId = tweetId;
				}*/
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	static private String json2plain(String json) {
		JsonObject inputJsonOject = new Gson().fromJson(json, JsonObject.class);
		String tweetid = inputJsonOject.get("tweetid").getAsString();
		String userid = inputJsonOject.get("userid").getAsString();
		String time = inputJsonOject.get("time").getAsString();
		String text = inputJsonOject.get("text").getAsString();
		Integer score = inputJsonOject.get("score").getAsInt();
		String censoredtext = inputJsonOject.get("censoredtext").getAsString();
		String bundle = tweetid + "\t" + userid + "\t" + time + "\t" + text + "\t" + score + "\t" + censoredtext + "\0";
		return bundle;
	}
}
