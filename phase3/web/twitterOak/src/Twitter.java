
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@WebServlet("/twitter")
public class Twitter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

		String query = request.getParameter("q");
		if (query==null) {
			response.setContentType("text/html");
			RequestDispatcher d = request.getRequestDispatcher("try.html");
			d.forward(request, response);
			return;
		} else if (query.equals("4")) {
			response.setContentType("text/html");
			String sDate = request.getParameter("sDate");
			String eDate = request.getParameter("eDate");
			String hashTag = request.getParameter("hashTag");
			System.out.println("date: "+sDate+" hashtag: "+hashTag);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = Date.valueOf(sDate);
			Date endDate = Date.valueOf(eDate);
			String q4Answer = MysqlDAO.getQ4(hashTag, startDate, endDate);
			// each line ends with ;
			String[] lines = q4Answer.split(";");
			ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
			for (int i=0; i<lines.length; i++) {
				String[] words = lines[i].split(",");
				//System.out.println(lines[i]);
				//get score
				long score = MysqlDAO.getQ5(words[1], startDate, endDate);
				Tweet tweet = new Tweet(words[0],words[1],words[2],score);
				tweetList.add(tweet);
			}
			Collections.sort(tweetList);
			ArrayList<Tweet> top10 = new ArrayList<Tweet>(tweetList.subList(0, 10));
			
			request.setAttribute("tweetList", top10);
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/try.jsp");
			d.forward(request, response);
		}else if (query.equals("10")) {
			
			request.setCharacterEncoding("utf8");
	        //response.setCharacterEncoding("utf8");
	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        JSONObject obj = new JSONObject();
	        String tweetId = request.getParameter("tweetId");
	        String userId = request.getParameter("userId");
	        String time = request.getParameter("time");
	        List<String> tweetList = MysqlDAO.getQ2(userId + "_" + time.replaceAll("\\+", " "));

	        String rs = "";
	        for (String str: tweetList) {
	        	String[] parts = str.split(":");
	        	if (tweetId.equals(parts[0]) && !parts[2].equals("")) {
	        		rs = parts[2];
	        		break;
	        	}
	        }
	        //TODO according to userId and time to access mysql, 
	        //return a context of its corresponding context.
	        //Assign the value to String(rs)
	        
	        obj.put("message",rs);
	        		
	        //obj.put("message", "According to userId and time to get the contexts of a specific tweedId.");
	        out.print(obj);
		}
		else if (query.equals("11")) {
			
			request.setCharacterEncoding("utf8");
	        //response.setCharacterEncoding("utf8");
	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        String userId = request.getParameter("userId");
	        //TODO: According to userId to get its friendship from Q3.
	        //the resutl will be assigned to JSONArray. Each JSONObject
	        //will have three elements. The first is friend userid, 
	        //The second is retweed number, The third is tag(+,-,*). 
	        //Wrap them to one object and put to JSONAarry. An exmpale 
	        //has been given below.
      
	        JSONArray friends = new JSONArray();
	        String rs = MysqlDAO.getQ3(Long.parseLong(userId));
	        if (rs==null) {
	        	out.print(friends);
	        	return;
	        }
	        //format -,1,userid_-,1,userid_-,1,userid_
	        String[] infos = rs.split("_");
	        //do for loop to put element to object, and object to array.
	        for (int i = 0; i < infos.length; i++) {
	        	String[] info = infos[i].split(",");
	        	JSONObject obj = new JSONObject();
	        	//friend user id
		        obj.put("fUserId", info[2]);
		        //num cannot be zero
		        obj.put("Num", info[1]);
		        //rel== + , -, *
		        obj.put("rel", info[0]);
		        friends.add(obj);
	        }
	        /*
	        for (int i = 0; i < 50; i++) {
	        	
	        	 //fUserId, Num, rel tag cannot be changed 
	        	 //to other name, and must return a JSONArray
	        	 //variable
	        	 //
	        	JSONObject obj = new JSONObject();
	        	//friend user id
		        obj.put("fUserId", "AA00" + i);
		        //num cannot be zero
		        obj.put("Num", (i+1)*2);
		        //rel== + , -, *
		        obj.put("rel", "*");
		        friends.add(obj);
	        }*/
	        out.print(friends);
		}
		
	}
}
