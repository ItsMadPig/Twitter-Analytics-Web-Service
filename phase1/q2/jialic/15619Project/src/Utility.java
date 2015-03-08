/*
 * To get score and censoredText from Test
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
			URL url = Utility.class.getResource("afinn.txt");
			File file = new File(url.getPath());
			FileReader inputFile;
			inputFile = new FileReader(file);
			BufferedReader br = new BufferedReader(inputFile);
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
			URL url = Utility.class.getResource("banned_decoded.txt");
			File file = new File(url.getPath());
			FileReader inputFile;
			inputFile = new FileReader(file);
			BufferedReader br = new BufferedReader(inputFile);
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
			} else {
				if (i > 0 && isAlphaNumeric(chs[i - 1])) {
					String str = text.substring(start, start + len);

					if (banned.contains(str.toLowerCase())) {
						for (int j = 0; j < str.length(); j++)
							if (j > 0 && j < str.length() - 1)
								censoredText.append('*');
							else
								censoredText.append(str.charAt(j));
					} else
						censoredText.append(str);

				}
				censoredText.append(ch);
				len = 0;

			}

		}

		return censoredText.toString();

	}

	static boolean isAlphaNumeric(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
				|| (ch <= '9' && ch >= '0');
	}

	public static void main(String[] args) {
		Utility test = new Utility();
		// System.out.println(scoreMap.get("weird"));
		System.out.println(banned.size());
		System.out.println(banned.contains("fuck"));
		System.out
				.println(test
						.getScore("We have 6 minutes of high school left. This is weird."));
		//String text = "RT @abo_khalid_03: \u0644\u0623\u0646 \u0623\u0645\u0631\u0643\u0650 \u064a\u0647\u0645\u0646\u064a\ud83c\udf39\n\n\u0635\u0648\u0631 \u0627\u0644\u0646\u0633\u064e\u0627\u0621 \u0641\u064a \u0627\u0644\u0639\u0631\u0636 \u0627\u0645\u0627\u0645 \u0627\u0644\u062c\u0645\u064a\u0639 \u0625\u062d\u062f\u064e\u0649 \u0627\u0644\u0630\u0646\u0648\u0628 \u0627\u0644\u062c\u064e\u0627\u0631\u0651\u064a\u0647\u2757\ufe0f\n\n\u0641\u064e\u0625\u0646 \u0644\u0645 \u062a\u062c\u0639\u0644\u0651\u064a \u0644\u0643\u0650 \u062e\u064a\u0631\u064b\u0627 \u062c\u0627\u0631\u064a \u060c\n\n\u0644\u0627 \u062a\u062c\u0639\u0651\u0644\u064a \u2026";
		// text ="We have 6 minutes of high school left. This is weird.";
		// System.out.println(text);
		// System.out.println(Arrays.toString(text.toCharArray()));
		String text="RT @omfgshawty: how do you trust a stranger \nwhen your own blood can fuck you over";
		System.out.println(test.getCensoredText(text));

	}

}
