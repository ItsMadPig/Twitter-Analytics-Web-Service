package Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;

public class Cache {
	static private final int Max_entries = 500;
	static private LinkedHashMap<String, String> map = new LinkedHashMap<String,String>(Max_entries,0.75f,true){
		protected boolean removeEldestEntry(Map.Entry eldest) {
		//automatically removes oldest entry when put and size>1000
		return size() > Max_entries;
    }};

	synchronized static String get(String key){
		try{
			return map.get(key);
		}catch(Exception e) {
			return "";
		}
	}

	synchronized static void set(String key, String val){
		try{
			map.put(key,val);
		}catch (Exception e) {

		}
	}
	

}
