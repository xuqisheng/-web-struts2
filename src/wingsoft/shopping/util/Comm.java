package wingsoft.shopping.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


//import wingsoft.custom.sendmsg.msginit.MsgInit;

public class Comm {
	public static void Log(String msg){
//		if ("T".equals(MsgInit.ini.getIsPrint())) {
//		System.out.println(msg);
//		Logger log = Logger.getLogger(Comm.class);
//		log.info(msg);
//		}
		
	}
	public static String nTrim(String src) {
		return src==null?"":src.trim();
    }
	public static int TimeBetween(String Abegin,String Aend,String Atype){
		int day1=0;
		int hour1=0;
		int minute1=0;
		int second1=0;
		try {
		 SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd Hh:mm:ss");
		 java.util.Date begin = dfs.parse(Abegin);
		 java.util.Date end = dfs.parse(Aend);
		 long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒

		    day1=(int) (between/(24*3600));
		    hour1=(int) (between%(24*3600)/3600);
		    minute1=(int) (between%3600/60);
		    second1=(int) (between%60/60);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if ("D".equals(Atype)) {
			return day1;
			
		}else if ("H".equals(Atype)) {
			return hour1;
			
		}else if ("M".equals(Atype)) {
			return minute1;
			
		}else if ("S".equals(Atype)) {
			return second1;
			
		}else {
			return 0;
			
		}
		
	}
	
	


	
	
	
}
