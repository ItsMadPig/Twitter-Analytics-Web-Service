

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
<<<<<<< HEAD
					JsonObject inputJsonOject = new Gson().fromJson(json,JsonObject.class);
					String userId = inputJsonOject.get("user").getAsJsonObject().get("id").getAsString();
					JsonElement retweet = inputJsonOject.get("retweeted_status"); //Returns: the member matching the name. Null if no such member exists.
					if (retweet!=null) {
						String retweetId = retweet.getAsJsonObject().get("user").getAsJsonObject().get("id").getAsString();
						System.out.println(userId+"\t"+retweetId);
=======
					JsonObject inputJsonOject = new Gson().fromJson(json,
							JsonObject.class);
					String userId = inputJsonOject.get("user")
							.getAsJsonObject().get("id_str").getAsString();
					JsonElement retweet = inputJsonOject.get("retweeted_status"); //Returns: the member matching the name. Null if no such member exists.
					if (retweet!=null) {
						String retweetId = retweet.getAsJsonObject().get("user")
								.getAsJsonObject().get("id_str").getAsString();
						System.out.println(userId+"\t"+"-"+retweetId);
						System.out.println(retweetId+"\t"+"+"+userId);
>>>>>>> be511b87826939bde9d3ce8253aefc70084f7037
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
