package wingsoft.shopping.model;

public class Users {
	private String userid;
	private String nickname;

	public Users(){}

	public Users(String userid,String nickname) {
		super();
		this.userid = userid;
		this.nickname = nickname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
