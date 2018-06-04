package wingsoft.core.command;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.log4j.Logger;

import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;


public class OrclCaller {
	
	static Logger LOGGER = Logger.getLogger(OrclCaller.class);
	
	/**调用oracle函数或过程
	 * @param callStr oracle 过程或函数调用字符串
	 * @param params	参数列表，一定是按照过程参数列表顺序排列好的。
	 * @return 执行结果的消息字符串
	 */
	public String call(String callStr,List<Field> params){
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		CallableStatement cst = null;
		Connection conn = null;
		String resultStr = "";
		LOGGER.info("调用过程函数："+callStr);
		
		try{
			conn = pool.getConnection();
			cst = conn.prepareCall(callStr);
			cst = setParams(cst,params);
			cst.execute();
			resultStr = cst.getString(1);	//此处默认将第一个参数作为过程调用后的返回参数
			cst.close();
			pool.returnConnection(conn);
			return CommonOperation.nTrim(resultStr);
			
		}catch(Exception e){
			try {
				
				cst.close();
				conn.rollback();
				pool.returnConnection(conn);
				this.outPutParams(params);
				LOGGER.error(e.getMessage());
				return "error";//"+callStr+"函数或过程执行发生异常，请检查调用的过程函数是否存在，或者参数设置是否合理";
				
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage());
				return "error:"+callStr+"函数或过程执行发生异常，请检查参数设置是否合理(oracle链接未能正常关闭)";
			}
		}
	}
	/**调用oracle函数或过程 需要手动提交
	 * @param callStr oracle 过程或函数调用字符串
	 * @param params	参数列表，一定是按照过程参数列表顺序排列好的。
	 * @param conn	执行的数据库连接
	 * @return 执行结果的消息字符串
	 */
	public String call(String callStr,List<Field> params,Connection conn)throws Exception{
		
		LOGGER.info("调用过程函数："+callStr);
		String resultStr = "";
		CallableStatement cst = conn.prepareCall(callStr);
		cst = setParams(cst,params);
		cst.execute();
		resultStr = cst.getString(1);	//此处默认将第一个参数作为过程调用后的返回参数
		cst.close();
		return CommonOperation.nTrim(resultStr);
		
	}
	private CallableStatement setParams(CallableStatement cst,List<Field> params) throws Exception{
		
		int c = params.size()+1;
		
		//将第一个参数设置为返回值，类型为char
		cst.registerOutParameter(1, Types.VARCHAR);
		
		for(int i = 2;i<=c;i++){
			String type = params.get(i-2).getType();
			String value = params.get(i-2).getValue();
			if(value==null ||value.equals("")){
				if(type.equals("NUMBER") || type.equals("INTEGER") || type.equals("FLOAT")){
					value = "0";
				}
			}
			try{
				if(type.equals("INTEGER")){
					int val = Integer.parseInt(value);
					cst.setInt(i,val);
					
				} else if(type.equals("FLOAT")){
					float val = Float.parseFloat(value);
					cst.setFloat(i, val);
					
				}else if(type.equals("DOUBLE")){
					double val = Double.parseDouble(value);
					cst.setDouble(i, val);
					
				}
				else if(type.equals("DATE")){
					java.util.Date d = CommonOperation.parseFullDate(value, "yyyy-MM-dd HH:mm:ss");
					Date val = null;
					if(d!=null){
						val = new Date(d.getTime());
					}
					cst.setDate(i, val);
					
				} else if(type.equals("NUMBER")){
					double val = Double.parseDouble(value);
					cst.setBigDecimal(i, BigDecimal.valueOf(val));
					
				} else{//默认字符串类型
					cst.setString(i, value);
				}
			}catch (Exception e){
				throw e;
			}
		}
		
		return cst;
		
	}
	public void outPutParams(List<Field> params){
		
		StringBuffer msg = new StringBuffer("调用前的参数列表   参数名: 值  --  类型\n");
		for(int i=0; i<params.size(); i++){
			Field f = params.get(i);
			msg.append("\t"+f.getName()+":  "+f.getValue()+"  --  "+f.getType()+"\n");
		}
		LOGGER.info(msg.toString());
	}
}
