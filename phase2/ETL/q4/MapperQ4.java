
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MapperQ4 {

	public static void main( String args[]) {
		//System.out.println(isValidString("sdfasdf11111000sSSF"));
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			String json;
			while ((json = br.readLine()) != null) {
				if (!json.trim().equals("")) {
					JsonObject inputJsonOject = new Gson().fromJson(json,JsonObject.class);
					String userId = inputJsonOject.get("user").getAsJsonObject().get("id_str").getAsString();					
					String tweetTime = dateStampConverter(inputJsonOject.get("created_at").getAsString());
					String tweetDate = dateStampConverter2(inputJsonOject.get("created_at").getAsString());
					if (tweetDate == null || userId == null) {
						continue;
					}
					String tweetId = inputJsonOject.get("id_str").getAsString();
					JsonArray hashtags = inputJsonOject.get("entities").getAsJsonObject().get("hashtags").getAsJsonArray(); 
					if (hashtags!=null) {
						for (int i = 0; i < hashtags.size(); i++) {
							String text = hashtags.get(i).getAsJsonObject().get("text").getAsString();
							if (isValidString(text)) {
								System.out.println(text+"\t"+tweetDate+"\t"+tweetId+"\t"+userId+","+tweetTime);
							}
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}
	/**
	 * 0-9, a-z, A-Z is valid string
	 */
	private static boolean isValidString (String tag) {
		if (tag == null || tag.equals("")) {
			return false;
		}
		String remain = tag.replaceAll("[0-9a-zA-Z]", "");
		if(remain.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	private static String dateStampConverter(String date) {
		//SimpleDateFormat threaholdDataFormat = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z");
		SimpleDateFormat oldFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
		try {
			Date oldDate = oldFormat.parse(date);
			newFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String newFormatDate = newFormat.format(oldDate);
			return newFormatDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	//Only keep date not time.
	private static String dateStampConverter2(String date) {
		//SimpleDateFormat threaholdDataFormat = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z");
		SimpleDateFormat oldFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date oldDate = oldFormat.parse(date);
			newFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			String newFormatDate = newFormat.format(oldDate);
			return newFormatDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
