package wingsoft.shopping.model;

import java.util.Date;

public class Orders {
	private String orderid;
	private String userid;
	private Date time;
	
	public Orders(){}

	public Orders(String orderid, String userid, Date time) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.time = time;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
