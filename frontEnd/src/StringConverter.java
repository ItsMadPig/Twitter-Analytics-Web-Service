import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;
 
public class StringConverter {
 
   public static void printBytes(byte[] array, String name) {
      for (int k = 0; k < array.length; k++) {
         System.out.println(name + "[" + k + "] = " + "0x" +"");
       //     UnicodeFormatter.byteToHex(array[k]));
      }
   }
 
   public static void main(String[] args) throws IOException {
	   String s="\u003B";
	   String uns=StringEscapeUtils.unescapeJava(s);
	   System.out.println(StringEscapeUtils.unescapeJava(s));
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
	   Scanner sc = new Scanner(System.in); 
	  // String st = sc.nextLine();
	  String st = br.readLine();
	  st =st+"I input one, is it still one? "+st;
	   byte[] ptext = st.getBytes("UTF-8");
		st = new String(ptext, "UTF-8");
       System.out.println(st); 
       System.out.println("length of \t: "+st.length());
	   String ss="\t";
	   System.out.println("length of \t: "+ss.length());
	   System.out.println(ss);
	   br.close();
 
 
       
   } // main
   
   static void method() {
	     System.out.println(System.getProperty("file.encoding"));
	      System.setProperty("file.encoding","UTF-8");
	      System.out.println(Charset.defaultCharset());
	    // System.setProperties(props);
	      String original = new String("A" + "\u00ea" + "\u00f1"
	                                 + "\u00fc" + "C");
	    
	      System.out.println("original = " + original);
	      System.out.println();
	    
	      try {
	          byte[] utf8Bytes = original.getBytes("UTF8");
	          byte[] defaultBytes = original.getBytes();
	  
	          String roundTrip = new String(utf8Bytes, "UTF8");
	          System.out.println(Charset.defaultCharset());
	          System.out.println("roundTrip = " + roundTrip);
	  
	          System.out.println();
	          printBytes(utf8Bytes, "utf8Bytes");
	          System.out.println();
	          printBytes(defaultBytes, "defaultBytes");
	      } catch (UnsupportedEncodingException e) {
	          e.printStackTrace();
	      }
	   
   }
 
}