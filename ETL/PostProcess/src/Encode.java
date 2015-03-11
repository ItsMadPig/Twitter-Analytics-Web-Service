import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringEscapeUtils;


public class Encode {
	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while ((input = br.readLine()) != null) {
				String[] parts = input.split("\t");
				String tweetId = parts[0];
				String userId = parts[1];
				String timeStamp = parts[2];
				String score = parts[3];
				String censoredText = StringEscapeUtils.unescapeJava(parts[4]);
				String bundle = userId + "_" + timeStamp + "\t" + tweetId + ":" + score + ":" + censoredText +"\0";
				System.out.println(bundle);
			}
		} catch (IOException io)  {
			io.printStackTrace();
		}
	}
}
