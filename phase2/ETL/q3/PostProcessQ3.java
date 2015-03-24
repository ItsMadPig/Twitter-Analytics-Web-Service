package MapReduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostProcessQ3 {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String line;
			Map<Long, List<Long>> plus = new HashMap<Long, List<Long>>();
			Map<Long, List<Long>> minus = new HashMap<Long, List<Long>>();
			Set<Long> eles = new HashSet<Long>();
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals("")) {
					String[] ids = line.split("\t");
					// x retweets y, so x minus y ---> it also means y plus x
					Long x = Long.parseLong(ids[0]);
					Long y = Long.parseLong(ids[1]);
					eles.add(x);
					eles.add(y);
					// minus
					List<Long> list = new LinkedList<Long>();
					if (minus.containsKey(x)) {
						list = minus.get(x);

					}
					list.add(y);
					minus.put(x, list);

					// plus
					list = new LinkedList<Long>();
					if (plus.containsKey(y)) {
						list = plus.get(y);
					}
					list.add(x);
					plus.put(y, list);

				}
			}
			// if plus and minus all exist, it should be Asterisk
			for (Long x : eles) {
				int count_minus = 0, count_plus = 0;
				long y = 0;
				if (minus.containsKey(x)) {
					// enumerate each y in list-minus[x]
					List<Long> list = minus.get(x);
					if (!list.isEmpty()) {
						y = list.get(0);
						// for each y: get the the count_plus and count_minus
						// sort and print the pair's relations
						count_plus = getCountPlus(plus, x, y);
						count_minus = getCountMinus(minus, x, y);
						print(count_plus, count_minus, x, y);
					}
					minus.remove(x);

				}
				// After enumerate all minus, there might be some plus left to
				// be dealt with
				if (plus.containsKey(x)) {
					List<Long> list = plus.get(x);
					// enumerate each y in list-plus[x]
					// for each y: get the the count_plus and count_minus
					// sort and print the pair's relations
					if (!list.isEmpty()) {
						y = list.get(0);
						count_plus = getCountPlus(plus, x, y);
						// minus part has been done above
						print(count_plus, 0, x, y);
					}
					plus.remove(x);

				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}

	}

	private static void print(int count_plus, int count_minus, long x, long y) {
		List<Response> listX = new ArrayList<Response>();
		if (count_minus==0) {
			listX.add(new Response('+', count_plus, y));
		}
		else if (count_plus==0) {
			listX.add(new Response('-', count_minus, y));
		}
		else
			listX.add(new Response('*', count_plus + count_minus, y));
			
		Collections.sort(listX);
		// print ele
		StringBuffer ans = new StringBuffer(String.valueOf(x) + "\t");
		for (Response res : listX) {
			ans.append(res.toString());
		}
		// terminated by \0\n
		System.out.println(ans + "\0");

	}

	private static int getCountMinus(Map<Long, List<Long>> minus, long x, long y) {
		int count_minus = 0;
		if (minus.containsKey(x)) {
			List<Long> listY = minus.get(x);
			while (listY.contains(y)) {
				listY.remove(y);
				count_minus++;
			}
		}
		return count_minus;
	}

	private static int getCountPlus(Map<Long, List<Long>> plus, long x, long y) {
		int count_plus = 0;
		if (plus.containsKey(x)) {
			List<Long> listY = plus.get(x);
			while (listY.contains(y)) {
				listY.remove(y);
				count_plus++;
			}
		}
		return count_plus;
	}

	static class Response implements Comparable<Response> {
		char type;
		int count;
		long userid;

		Response(char type, int count, long userid) {
			this.type = type;
			this.count = count;
			this.userid = userid;
		}

		public String toString() {
			return this.type + "," + this.count + "," + this.userid + '\n';
		}

		public int compareTo(Response that) {
			if (this.type != that.type) {
				return this.type - that.type;
			} else {
				if (this.count != that.count) {
					return that.count - this.count;
				}
			}
			return (int) (this.userid - that.userid);
		}
	}

}
