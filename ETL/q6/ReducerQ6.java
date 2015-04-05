import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReducerQ6 {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String oldUserID = "";
			String totalNoPlace = "1";
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] parts = line.split("\t");
				String userID = parts[0];
				String noPlace = parts[1];

				// new tags, time to print the old tag
				if (oldUserID != "" && !userID.equals(oldUserID)){
					System.out.println(oldUserID + "\t" + totalNoPlace);
					oldUserID = userID;
					totalNoPlace = "1";
				}
				oldUserID = userID;
				totalNoPlace = Integer.toString(Integer.parseInt(totalNoPlace) & Integer.parseInt(noPlace));
			}
			System.out.println(oldUserID + "\t" + totalNoPlace);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}
}
