



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class ReducerQ5 {

	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String line, prevLine=null;
			// userid+tweetDate
			String prevKey ="";
			//count(tweetId)
			  int prevCount=0;
			  //max(friends_count) for userid+date
			 int prevFriends =0;
			 //max(followers_count) for userid+date
			 int prevFollowers_count =0;
			 String[] firstPart =null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				//delete  duplicate
				if (line.equals("") || line.equals(prevLine))
					continue;
				prevLine = line;
				String[] words = line.split("\t");

				String[] secondPart = words[1].split("_");
				int friends_count =Integer.parseInt(secondPart[1]);
				int followers_count = Integer.parseInt(secondPart[2]);
				
				if (words[0].equals(prevKey)) {
					prevCount++;
					 prevFriends =friends_count >prevFriends ? friends_count:prevFriends;
					prevFollowers_count  = followers_count >prevFollowers_count ? followers_count:prevFollowers_count;
					
				}
				else {
					//make sure it is not the first record
					if (!prevKey.equals("")) {
						firstPart = prevKey.split("_");
						System.out.println(firstPart[0]+"\t"+firstPart[1]+"\t"+prevCount+"\t"+prevFriends+"\t"+prevFollowers_count);
					}
					// initialize for new record
					prevCount =1;
					prevFriends = friends_count;
					prevFollowers_count = followers_count;
					prevKey =words[0];
					
				}
				
		
			}
			// the last element
			firstPart = prevKey.split("_");
			System.out.println(firstPart[0]+"\t"+firstPart[1]+"\t"+prevCount+"\t"+prevFriends+"\t"+prevFollowers_count);
			
		

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}






}
