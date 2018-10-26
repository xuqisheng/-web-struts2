package wingsoft.shopping.service.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.custom.BaseAction;
import wingsoft.shopping.service.dao.MsgDao;

public class MsgAction extends BaseAction {
	/**
	 * 获取公告、相关规定、促销基本信息
	 * **/
	public String GetMsgInfo(){
		MsgDao sd = new MsgDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetMsgInfo()));
		setJsonObject(jo);
		return OBJECT;
		
	}	
	
	/**
	 * 获取公告、相关规定、促销 内容
	 * **/
	public String GetMsg(){
		MsgDao sd = new MsgDao();
		setJson(sd.GetMsg());
		return "success";

	}
}
