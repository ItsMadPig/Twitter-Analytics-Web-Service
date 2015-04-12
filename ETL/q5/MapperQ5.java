



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MapperQ5 {
    //output:userid_tweetDate \t tweetid friends_count followers_count
	public static void main( String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String json;
			while ((json = br.readLine()) != null) {
				if (!json.trim().equals("")) {
					JsonObject inputJsonOject = new Gson().fromJson(json,
							JsonObject.class);
					String tweetId = inputJsonOject.get("id_str").getAsString();
					String userId = inputJsonOject.get("user")
							.getAsJsonObject().get("id_str").getAsString();
					String friends_count =inputJsonOject.get("user")
							.getAsJsonObject().get("friends_count").getAsString();
					String followers_count = inputJsonOject.get("user")
							.getAsJsonObject().get("followers_count").getAsString();
					String tweetTime = dateStampConverter(inputJsonOject.get(
							"created_at").getAsString());
					
					if (tweetTime == null || userId == null || tweetId==null ||friends_count==null || followers_count==null)
						continue;
					String tweetDate = tweetTime.substring(0, 10);
					System.out.println(userId+"_"+tweetDate+"\t"+tweetId+"_"+friends_count+"_"+followers_count);
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
	
	private static String dateStampConverter(String date) {

		SimpleDateFormat oldFormat = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss Z yyyy");
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

}
