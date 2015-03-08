import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class BannedDecoding {

	public static void main(String[] args) throws IOException {
		 String filePath = new File("").getAbsolutePath();
	        System.out.println (filePath);
		String fileName="/Users/Scarlett/Desktop/eBusiness/15619/619Project/Phrase1/q2/banned.txt";
		FileReader inputFile = new FileReader(fileName);
		BufferedReader br = new BufferedReader(inputFile);
		String line;
		while (((line=br.readLine())!=null)) {
			char[] chs= line.toCharArray();
			for (char ch:chs ){
			
				if (ch>='a' && ch<='m')
					ch = (char)(ch+13);
				else if (ch>'m' && ch<='z')
					ch = (char)(ch-13);

			}
			System.out.println(String.valueOf(chs));
		}
		br.close();

	}

}
