package wingsoft.core.command;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import wingsoft.tool.common.CommonOperation;


public class CommandContextParser {
	static Logger LOGGER = Logger.getLogger(CommandContextParser.class);
	public String bindData(String value,HashMap<String,String> userContext){
		
		int index = value.length()-1;
		if(value.startsWith("@")){
			value = value.substring(1,index)+".nextval";
			return value;
		}else if(!value.startsWith("#")){
			return value;//非上下文 无需绑定
		}
		
		value = value.substring(1, index).toUpperCase();
		if(value == "NOW"){
			Date d=new Date();
			return CommonOperation.formatDate(d, "yyyy-MM-dd HH:mm:ss");
		}
		String val = userContext.get(value);
		if(val == null){
			LOGGER.error("未绑定到上下文变量："+value);
			return "";
		}
		return val;
	}
	public String parser(String str,HashMap<String,String> userContext){

		String[] strBuf = str.split("#");
		String rltstr = "";
		for(int i=1; i<strBuf.length;i=i+2){
			strBuf[i] = this.bindData("#"+strBuf[i]+"#",userContext);
		}
		for(int i=0; i<strBuf.length; i++){
			rltstr += strBuf[i];
		}
		
		return rltstr;
	}
}
