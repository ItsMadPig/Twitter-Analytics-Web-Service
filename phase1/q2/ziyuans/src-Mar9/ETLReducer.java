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
			while ((input=br.readLine())!=null) {
				System.out.println(StringEscapeUtils.unescapeJava(input));
				//System.out.println(encodeUTF8(StringEscapeUtils.unescapeJava(input)));
				/*
				String[] parts = input.split("\t");
				String espInput = parts[0];
				for(int i = 1; i < parts.length; i++) {
					espInput = '\t' + StringEscapeUtils.escapeJava(parts[i]);
				}
				System.out.println(espInput);*/
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	static private String encodeUTF8(String input) {
		byte ptext[];
		String temp = "";
		try {
			ptext = input.getBytes("UTF-8");
			temp = new String(ptext, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
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
