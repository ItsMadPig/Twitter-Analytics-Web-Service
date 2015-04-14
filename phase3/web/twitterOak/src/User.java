
public class User {
	private String userid;
	private long score;
	User(String userid, long score) {
		this.userid = userid;
		this.score =score;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}

}
