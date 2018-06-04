package wingsoft.shopping.service.action;

import wingsoft.shopping.service.dao.MsgDao;
import wingsoft.shopping.service.dao.ShopDao;

public class MsgAction {
	private String json = "";
	
	
	
	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}
	
	
	/**
	 * 获取公告、相关规定、促销基本信息
	 * **/
	public String GetMsgInfo(){
		MsgDao sd = new MsgDao();
		json = sd.GetMsgInfo();
		return "success";
		
	}	
	
	/**
	 * 获取公告、相关规定、促销 内容
	 * **/
	public String GetMsg(){
		MsgDao sd = new MsgDao();
		json = sd.GetMsg();
		return "success";
		
	}
}
