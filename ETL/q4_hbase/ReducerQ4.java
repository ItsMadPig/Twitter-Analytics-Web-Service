import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;



public class ReducerQ4 {

	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			String oldTag = "";
			String line;
			List<String> daysArray = new ArrayList<String>(71);
			//String format;
			Set<String> tweetDateAndId = new HashSet<String>();
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\t");
				String tag = parts[0];
				String tweetDate = parts[1];
				String tweetId = parts[2];
				String others = parts[3];	
				if (tag.equals(oldTag)) {
					//duplicated tweetId for the same tag and date.
					if (tweetDateAndId.contains(tweetDate+tweetId)) {
						continue;
					} else {
						tweetDateAndId.add(tweetDate+tweetId);
						int index = date2Index(tweetDate);
						daysArray.set(index, daysArray.get(index) + "&" + tweetId + "," + others);
					}
				} else {
					//do important things here with daysArray 
					formatPrint(oldTag,daysArray);
					daysArray.clear();
					tweetDateAndId.clear();
					tweetDateAndId.add(tweetDate+tweetId);
					oldTag = tag;
					//format = tag;
					int index = date2Index(tweetDate);
					daysArray.set(index, daysArray.get(index) + "&" + tweetId + "," + others);
				}
			}
			//do last line with daysArray 
			formatPrint(oldTag,daysArray);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}
	private static void formatPrint(String rowKey, List<String> daysArray){
		String formatOutput = rowKey;
		for (int i = 0; i < daysArray.size(); i++) {
			formatOutput = formatOutput + "\t" + daysArray.get(i);
		}
		System.out.println(formatOutput);
	}
	private static List<String> updateCell(List<String> daysArray, int index, String newString) {
		daysArray.set(index, daysArray.get(index) + "&" + newString);
		return daysArray;
	} 
	/**
	 * If input is "2014-03-21", the output is 0;[0~70]
	 * @param dateString
	 * @return
	 */
	private static int date2Index(String dateString) {
		String beginDateString = "2014-03-21";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate = format.parse(dateString);
			Date beginDate = format.parse(beginDateString);
			long diff = Math.abs(currentDate.getTime() - beginDate.getTime());
			long diffDays = diff / (24 * 60 * 60 * 1000);
			return (int)diffDays;
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
