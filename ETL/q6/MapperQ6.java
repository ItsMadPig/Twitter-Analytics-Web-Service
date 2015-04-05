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

public class MapperQ6 {

	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String json;
			while ((json = br.readLine()) != null) {
				if (!json.trim().equals("")) {
					JsonObject inputJsonObject = new Gson().fromJson(json,
							JsonObject.class);
					String userId = inputJsonObject.get("user")
							.getAsJsonObject().get("id").getAsString();

					
					JsonElement place = inputJsonObject.get("place");
					if (userId == null){
						continue;
					}

					if(place.isJsonNull() || place.getAsJsonObject().get("name").isJsonNull()){
						System.out.println(userId+"\t"+"1");
					}else{
						System.out.println(userId+"\t"+"0");
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
