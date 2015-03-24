
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MapperQ3 {

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
					JsonElement retweet = inputJsonOject.get("retweeted_status"); //Returns: the member matching the name. Null if no such member exists.
					if (retweet!=null) {
						String retweetId = retweet.getAsJsonObject().get("user")
								.getAsJsonObject().get("id").getAsString();
						System.out.println(userId+"\t"+retweetId);
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

}
