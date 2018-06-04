package wingsoft.shopping.model;

import java.util.Date;

public class Comments {
	private String commentid;
	private Date time;
	private String commenttxt;
	private String userid;
	private String itemid;
	private int anonymous;
	
	public Comments(){}

	public Comments(String commentid, Date time, String commenttxt,
			String userid, String itemid, int anonymous) {
		super();
		this.commentid = commentid;
		this.time = time;
		this.commenttxt = commenttxt;
		this.userid = userid;
		this.itemid = itemid;
		this.anonymous = anonymous;
	}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCommenttxt() {
		return commenttxt;
	}

	public void setCommenttxt(String commenttxt) {
		this.commenttxt = commenttxt;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	@Override
	public String toString() {
		return "{\"commentid\":\"" + commentid + "\",\"time\":\"" + time
				+ "\",\"commenttxt\":\"" + commenttxt + "\",\"userid\":\"" + userid
				+ "\",\"itemid\":\"" + itemid + "\",\"anonymous\":\"" + anonymous
				+ "\"}";
	}
	
	
}
