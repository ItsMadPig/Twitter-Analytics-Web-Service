import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringEscapeUtils;
public class ETLReducer {
	public static void main (String args[]) {
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			String input;
			String oldInput = null;
			while ((input=br.readLine())!=null) {
				String[] parts = input.split("\t");
				if(oldInput!=null && parts[0].equals(oldInput)) {
					continue;
				} else {
					oldInput = parts[0];
					String tweetId = parts[0];
					String userId = parts[1];
					String timeStamp = parts[2];
					String score = parts[3];
					String censoredText = parts[4];
					String bundle = tweetId + "\t" + userId + "\t" + timeStamp + "\t" + score + "\t" + censoredText;
					System.out.println(bundle);
				}		
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
