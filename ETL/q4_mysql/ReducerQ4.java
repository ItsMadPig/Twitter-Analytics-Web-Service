

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;





public class ReducerQ4 {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String oldTag = "";
			String line;
			List<Response> responses = new ArrayList<Response>();
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] parts = line.split("\t");
				String tag_time = parts[0];
				String response = parts[1];

				// new tags, time to print the old tag
				if (oldTag != "" && !tag_time.equals(oldTag)) {
					print(oldTag, responses);
					responses = new ArrayList<Response>();
				}
				responses.add(new Response(response));
				oldTag = tag_time;
			}
			print(oldTag, responses);
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}

	private static void print(String tag_time, List<Response> responses) {
		Collections.sort(responses);
		// print ele_ seperate by _
		StringBuffer ans = new StringBuffer(tag_time + "_");
		Response prev=null;
		for (Response res : responses) {
			if (!res.equals(prev))
			ans.append(res.toString());
			prev = res;
		}
		// terminated by \n
		System.out.println(ans);

	}
	static class Response implements Comparable<Response> {
		long tweetid;
		String others;

		Response(long tweetid, String others) {
			this.tweetid = tweetid;
			this.others = others;
		}

		public Response(String response) {
			String[] strs = response.split(",",2);
			this.tweetid = Long.parseLong(strs[0]);
			this.others = strs[1];
		}
		public boolean equals(Response that) {
			if (that==null) return false;
			return this.tweetid == that.tweetid;
		}

		public String toString() {
			return this.tweetid + "," + this.others + ';';
		}

		public int compareTo(Response that) {
			if (this.tweetid > that.tweetid) {
				return 1;
			}
			else if (this.tweetid < that.tweetid) {
				return -1;
			}
			else
				return 0;
			
		}
	}

}
