import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Remove {
	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			while ((input = br.readLine()) != null) {
				input = input.substring(0, input.length()-1);
				System.out.println(input);
			}
		} catch (IOException io)  {
			io.printStackTrace();
		}
	}
}