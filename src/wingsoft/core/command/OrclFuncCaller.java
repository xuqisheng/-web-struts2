package wingsoft.core.command;


import java.sql.Connection;
import java.util.List;


/**oracle function 调用类
 * 指定函数或过程名，并赋予参数列表
 * 负责链接调用，返回相关消息
 * @author Z
 *
 */
public class OrclFuncCaller extends OrclCaller{

	public String funcStr(String funcName,int c){
		String qMark = "";
		int i;
		for( i= 0; i<c-1; i++){
			qMark += "?,";
		}
		qMark += "?";
		return "{? = call " + funcName + "("+qMark+")}";
	}
	
	
	/**调用oracle函数
	 * @param funcName oracle 函数名
	 * @param params	参数列表，一定是按照函数参数列表顺序排列好的。
	 * @return 执行结果的消息字符串
	 */
	public String call(String funcName,List<Field> params){
		
		int c = params.size();
		String callStr = this.funcStr(funcName,c);
	
		return super.call(callStr, params);
	}
	
	/**调用oracle函数或过程 需要手动提交
	 * @param funcName oracle 过程或函数调用字符串
	 * @param params	参数列表，一定是按照过程参数列表顺序排列好的。
	 * @param conn	执行的数据库连接
	 * @return 执行结果的消息字符串
	 */
	public String call(String funcName,List<Field> params,Connection conn) throws Exception{
		
		int c = params.size();
		String callStr = this.funcStr(funcName,c);
		return super.call(callStr,params,conn);
		
	}
	
	
}
