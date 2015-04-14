public class Tweet implements Comparable<Tweet> {
	private String tweetid;
	private String userid;
	private String tweettime;
	private User user;


	public Tweet(String tweetid, String userid, String tweettime, long score) {
		this.setTweetid(tweetid);
		this.setUserid(userid);
		this.setTweettime(tweettime);
		this.setUser(new User(userid, score));
	}

	public String getTweetid() {
		return tweetid;
	}

	public void setTweetid(String tweetid) {
		this.tweetid = tweetid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTweettime() {
		return tweettime;
	}

	public void setTweettime(String tweettime) {
		this.tweettime = tweettime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	// Sort the tweets using the user's rank (use Q5) as the primary sort key,
	// the tweet ID (numerically) as a secondary sort key.
	public int compareTo(Tweet that) {
		if (this.user.getScore() > that.user.getScore()) {
			return -1;
		}
		else if (this.user.getScore() <that.user.getScore()) {
			return 1;
		}
		long thistweet =Long.parseLong(this.tweetid);
		long thattweet = Long.parseLong(that.tweetid);
		if (thistweet > thattweet) {
			return 1;
		}
		else if (thistweet <thattweet) {
			return -1;
		}
		return 0;
	}

}
