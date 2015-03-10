import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ETLMapper {

	public static void main( String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String json;
			while ((json = br.readLine()) != null) {
				if (!json.trim().equals("")) {
					JsonObject inputJsonOject = new Gson().fromJson(json,
							JsonObject.class);
					String userId = inputJsonOject.get("user")
							.getAsJsonObject().get("id").getAsString();
					String timeStamp = dateStampConverter(inputJsonOject.get(
							"created_at").getAsString());
					String tweetId = inputJsonOject.get("id_str").getAsString();
					String text = inputJsonOject.get("text").getAsString();
					text = text.replace("\\", "\\\\").replace("\n", "\\n");
					Integer score = Utility.getScore(text);
					String censoredText = Utility.getCensoredText(text);
					byte[] bytes = censoredText.getBytes("UTF-8");
					censoredText = new String(bytes, Charset.forName("UTF-8"));
					if (timeStamp != null && !userId.equals("")
							&& !timeStamp.equals("") && !tweetId.equals("")
							&& !text.equals("")) {
						String bundle = tweetId + "\t" + userId + "\t"
								+ timeStamp + "\t" + score + "\t"
								+ censoredText;
						System.out.println(bundle);

					} else {
						continue;
					}
				} else {
					continue;
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
		String threahold = "Sun 20 Apr 2014 00:00:00 GMT";
		SimpleDateFormat threaholdDataFormat = new SimpleDateFormat(
				"EEE dd MMM yyyy HH:mm:ss z");
		SimpleDateFormat oldFormat = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss Z yyyy");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date threaholdDate = threaholdDataFormat.parse(threahold);
			Date oldDate = oldFormat.parse(date);
			if (oldDate.compareTo(threaholdDate) >= 0) {
				newFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				String newFormatDate = newFormat.format(oldDate);
				return newFormatDate;
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
