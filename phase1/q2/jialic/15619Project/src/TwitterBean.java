//tweetid,userid,time,text,score,censoredtext
public class TwitterBean {
	private String tweetid;
	private String userid;
	private String time;
	private String text;
	private int  score;
	private String censoredtext;
	public String getTweetid() {
		return tweetid;
	}
	public void setTweetid(String tweetid) {
		this.tweetid = tweetid;
	}
	public String getCensoredtext() {
		return censoredtext;
	}
	public void setCensoredtext(String censoredtext) {
		this.censoredtext = censoredtext;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	

}
