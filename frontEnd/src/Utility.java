/*
 * To get score and censoredText from Test
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Utility {
	static Map<String, Integer> scoreMap = new HashMap<String, Integer>();
	static {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Utility.class.getResourceAsStream("afinn.txt")));
	
			String line;
			while ((line = br.readLine()) != null) {
				String[] strs = line.split("\t");
				// Malformed data
				if (strs.length < 2)
					continue;
				// Exclude phrases like: cool stuff 3
				if (strs[0].indexOf(" ") >= 0) {
					continue;
				}
				int score = Integer.parseInt(strs[1]);
				scoreMap.put(strs[0], score);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Set<String> banned = new HashSet<String>();
	static {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Utility.class.getResourceAsStream("banned_decoded.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				banned.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Given a test, return its score
	 */
	static int getScore(String text) {
		int score = 0;
		// split the string into separate “words” wherever a non-alphanumeric
		// character is encountered
		String[] strs = text.split("[^a-zA-Z0-9]");
		for (String str : strs) {
			str = str.toLowerCase();
			if (scoreMap.containsKey(str)) {
				score += scoreMap.get(str);
			}
		}
		return score;
	}

	static String getCensoredText(String text) {
		char[] chs = text.toCharArray();

		int start = 0, len = 0;
		StringBuilder censoredText = new StringBuilder("");
		for (int i = 0; i < chs.length; i++) {
			char ch = chs[i];
			if (isAlphaNumeric(ch)) {
				if (len == 0)
					start = i;
				len++;
				if (i == chs.length - 1) {
					String str = text.substring(start, start + len);
					censoredText.append(ban(str));

				}

			} else {
				if (i > 0 && isAlphaNumeric(chs[i - 1])) {
					String str = text.substring(start, start + len);
					censoredText.append(ban(str));

				}
				censoredText.append(ch);
				
				len = 0;

			}

		}

		return censoredText.toString();

	}

	private static StringBuilder ban(String str) {
		StringBuilder bannedStr = new StringBuilder("");
		if (banned.contains(str.toLowerCase())) {
			for (int j = 0; j < str.length(); j++)
				if (j > 0 && j < str.length() - 1)
					bannedStr.append('*');
				else
					bannedStr.append(str.charAt(j));
		} else
			bannedStr.append(str);
		return bannedStr;
	}

	static boolean isAlphaNumeric(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
				|| (ch <= '9' && ch >= '0');
	}

	public static void main(String[] args) {
		Thread.getContextClassLoader().getResource()
		Utility test = new Utility();
		// System.out
		// .println(test
		// .getScore("We have 6 minutes of high school left. This is weird."));
		// String text =
		// "RT @abo_khalid_03: \u0644\u0623\u0646 \u0623\u0645\u0631\u0643\u0650 \u064a\u0647\u0645\u0646\u064a\ud83c\udf39\n\n\u0635\u0648\u0631 \u0627\u0644\u0646\u0633\u064e\u0627\u0621 \u0641\u064a \u0627\u0644\u0639\u0631\u0636 \u0627\u0645\u0627\u0645 \u0627\u0644\u062c\u0645\u064a\u0639 \u0625\u062d\u062f\u064e\u0649 \u0627\u0644\u0630\u0646\u0648\u0628 \u0627\u0644\u062c\u064e\u0627\u0631\u0651\u064a\u0647\u2757\ufe0f\n\n\u0641\u064e\u0625\u0646 \u0644\u0645 \u062a\u062c\u0639\u0644\u0651\u064a \u0644\u0643\u0650 \u062e\u064a\u0631\u064b\u0627 \u062c\u0627\u0631\u064a \u060c\n\n\u0644\u0627 \u062a\u062c\u0639\u0651\u0644\u064a \u2026";
		// text ="We have 6 minutes of high school left. This is weird.";

		// System.out.println(Arrays.toString(text.toCharArray()));
		// String
		// text="RT @omfgshawty: how do you trust a stranger \nwhen your own blood can fuck you over";
		//String text ="RT @abo_khalid_03: لأن أمركِ يهمني🌹\n\nصور النسَاء في العرض امام الجميع إحدَى الذنوب الجَارّيه❗️\n\nفَإن لم تجعلّي لكِ خيرًا جاري ،\n\nلا تجعّلي …	0	RT @abo_khalid_03: لأن أمركِ يهمني🌹\n\nصور النسَاء في العرض امام الجميع إحدَى الذنوب الجَارّيه❗️\n\nفَإن لم تجعلّي لكِ خيرًا جاري ،\n\nلا تجعّلي …";

		String text="\u0645\u0627 \u0627\u062a\u0646\u0643 \u0645\u0646\u0643 \u0627\u0644\u0627 \u0627\u0644\u0644\u064a \u0635\u062f\u0642\u0648\u0643 \u0628\u064a\u062e\u0644\u0648\u0646 \u0646\u062c\u0648\u0645 \u0627\u0644\u0639\u0627\u0644\u0645 \u0643\u0644\u0647\u0645 \u0648\u0628\u064a\u062c\u0648\u0646 \u0644\u0645\u0627\u062c\u062f \u061f\u061f \u0639\u0645\u0648\u0645\u0627 \u0641\u0639\u0644\u0627 \u0627\u0644\u0641\u0631\u064a\u0642 \u062d\u0642\u0647 \u0643\u0627\u0646 \u0641\u0627\u0634\u0644 \u0648\u0645\u0627\u0634\u0627\u0644\u0647 \u0627\u0644\u0627 \u0645\u0627\u062c\u062f \u0646\u0641\u0633 \u0627\u0644\u0646\u0635\u0631 \u0628\u0636\u0628\u0637";
		
		//System.out.println(getScore(text));
		try {
			byte ptext[] =text.getBytes("UTF-8");
			String text1 = new String(ptext, "UTF-8"); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(text);
		//System.out.println(getCensoredText(text));

	}

}
