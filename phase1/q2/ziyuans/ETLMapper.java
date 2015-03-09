import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;
public class ETLMapper {
	
	public static void main(String args[]) {
		
		//String a = "Thu May 29 21:44:22 +0000 2014";
		//System.out.println(dateStampConverter(a));
		//String a = "3: لأن أمركِ يهمني🌹";
		//System.out.println(escapeNonAscii(a));
		//System.out.println(StringEscapeUtils.escapeJava(a));
		//String b = StringEscapeUtils.escapeJson(a) + "sdf";
		//System.out.println(b);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String json;
			while ((json = br.readLine()) != null) {
				if (!json.trim().equals("")){
					JsonObject inputJsonOject = new Gson().fromJson(json, JsonObject.class);
					String userId = inputJsonOject.get("user").getAsJsonObject().get("id").getAsString();
					String timeStamp = dateStampConverter(inputJsonOject.get("created_at").getAsString());
					String tweetId = inputJsonOject.get("id_str").getAsString();
					String text = inputJsonOject.get("text").getAsString();
					StringBuilder filteredText = new StringBuilder("");
					for (int i=0; i<text.length(); i++) {
						filteredText.append(text.charAt(i));
						if (text.charAt(i)=='\\') filteredText.append(text.charAt(i));
					}
					text=String.valueOf(filteredText);
					Integer score = Utility.getScore(text);
					String censoredText = Utility.getCensoredText(text);
					if (timeStamp!=null && !userId.equals("") && !timeStamp.equals("")&& !tweetId.equals("") && !text.equals("")) {
						//ETLMapper handler = new ETLMapper();
						//ETLMapper.KeyValue kv = handler.new KeyValue(tweetId+'\t',userId,timeStamp,text,score,censoredText);
						//String bundleJson = new Gson().toJson(kv);
						//String bundle = userId + "\t" + timeStamp + "," + tweetId + "," + text + "," + score + "," + censoredText;
						//String bundle = tweetId + "," + userId + "," + timeStamp + "," + text + "," + score + "," + censoredText;
						String bundle = tweetId + "\t" + userId + "\t" + timeStamp + "\t" + text + "\t" + score + "\t" + censoredText + "\0";
						//System.out.println(bundleJson);
						System.out.println(StringEscapeUtils.escapeJson(bundle));
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	private static String dateStampConverter(String date){
		String threahold = "Sun 20 Apr 2014 00:00:00 GMT";
		SimpleDateFormat threaholdDataFormat = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z");
		SimpleDateFormat oldFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date threaholdDate = threaholdDataFormat.parse(threahold);
			Date oldDate = oldFormat.parse(date);
			if (oldDate.compareTo(threaholdDate) >= 0) {
				newFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				String newFormatDate =newFormat.format(oldDate);
				return newFormatDate;
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	class KeyValue {
		private String tweetid = "";
		private String userid = "";
		private String time = "";
		private String text = "";
		private Integer score = 0;
		private String censoredtext = "";
		KeyValue(String tweetid,String userid,String time,String text,Integer score,String censoredtext) {
			this.tweetid = tweetid;
			this.userid = userid;
			this.time = time;
			this.text =text;
			this.score = score;
			this.censoredtext = censoredtext;
		}
	}
	private static String escapeNonAscii(String str) {
		  StringBuilder retStr = new StringBuilder();
		  for(int i=0; i<str.length(); i++) {
		    int cp = Character.codePointAt(str, i);
		    int charCount = Character.charCount(cp);
		    if (charCount > 1) {
		      i += charCount - 1; // 2.
		      if (i >= str.length()) {
		        throw new IllegalArgumentException("truncated unexpectedly");
		      }
		    }

		    if (cp < 128) {
		      retStr.appendCodePoint(cp);
		    } else {
		      retStr.append(String.format("\\u%04x", cp));
		    }
		  }
		  return retStr.toString();
		}
}
