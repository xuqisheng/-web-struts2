package wingsoft.core.command;


import java.util.List;
import java.sql.Connection;
public class OrclProcCaller extends OrclCaller{
	
	public String procStr(String procName,int c){
		String qMark = "";
		int i;
		for( i= 0; i<c-1; i++){
			qMark += "?,";
		}
		qMark += "?";
		return "{call " + procName + "("+qMark+")}";
	}
	
	/**调用oracle过程
	 * @param funcName oracle 过程名
	 * @param params	参数列表，一定是按照过程参数列表顺序排列好的。
	 * @return 执行结果的消息字符串
	 */
	public String call(String procName,List<Field> params){
		
		int c = params.size()+1;
		String procStr = this.procStr(procName, c);
		return super.call(procStr, params);
		
	}
	
	/**调用oracle函数或过程 需要手动提交
	 * @param procName oracle 过程或函数名
	 * @param params	参数列表，一定是按照过程参数列表顺序排列好的。
	 * @param conn	执行的数据库连接
	 * @return 执行结果的消息字符串
	 */
	public String call(String procName,List<Field> params,Connection conn) throws Exception{
		
		int c = params.size()+1;
		String procStr = this.procStr(procName, c);
		return super.call(procStr,params,conn);
		
	}
	
}
