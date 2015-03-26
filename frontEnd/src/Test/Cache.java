package Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cache {

	private static final int Max_entries = 500000;
	static Map<String, String> map = new LinkedHashMap<String,String>(Max_entries,.75F,true);

	public synchronized String get(String key){
		return map.get(key);
	}

	public synchronized void set(String key, String val){
		map.put(key,val);
	}
	

}
