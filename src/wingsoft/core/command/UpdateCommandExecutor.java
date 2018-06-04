package wingsoft.core.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.Logger;

import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class UpdateCommandExecutor {
	protected OrclFuncCaller funcCaller = new OrclFuncCaller();
	protected OrclProcCaller procCaller = new OrclProcCaller();
	protected QueryDAO queryDao = new QueryDAO();
	protected Logger LOGGER = Logger.getLogger(UpdateCommandExecutor.class);
	public String execute(UpdateCommand cmd){
		String checkRlt = this.doCheck(cmd);
		if(checkRlt.equals("pass")){
			
			return this.callProcs(cmd);
		}else{
			return checkRlt;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String doCheck(UpdateCommand cmd){
		if(cmd.getCheck_func().equals("escape")){
			return "pass";
		}
		
		if(cmd.getCheck_func().charAt(0) == '@'){
			String check_sql = cmd.getCheck_func().substring(1);
			try {
				long c = queryDao.queryCountData(check_sql);
				if(c==0){
					return "pass";
				}else{
					return "unpass:该记录已存在";
				}
			} catch (Exception e) {
				LOGGER.error("错误的SQL:"+check_sql);
				return "error:错误的SQL>>>"+check_sql;
			}
		}
		
		return funcCaller.call(cmd.getCheck_func(), cmd.getCheck_data());
	}

	public String callProcs(UpdateCommand cmd){
		String errorList = "";
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		Connection conn = null;
		try{
			conn = pool.getConnection();
			conn.setAutoCommit(false);
		
			String exeRlt;
			
			if(cmd.getProc0()!=null && !cmd.getProc0().equals("")){
				exeRlt = this.callProc(cmd.getProc0(), cmd.getData0(), conn);
				if(!exeRlt.startsWith("success")){
					errorList = exeRlt + "@";
				}
			}
			if(cmd.getProc1()!=null && !cmd.getProc1().equals("")){
				exeRlt = this.callProc(cmd.getProc1(), cmd.getData1(), conn);
				if(!exeRlt.startsWith("success")){
					errorList += exeRlt + "@";
				}
			}
			if(cmd.getProc2()!=null && !cmd.getProc2().equals("")){
				exeRlt = this.callProc(cmd.getProc2(), cmd.getData2(), conn);
				if(!exeRlt.startsWith("success")){
					errorList += exeRlt + "@";
				}
			}
			if(cmd.getProc3()!=null && !cmd.getProc3().equals("")){
				exeRlt = this.callProc(cmd.getProc3(), cmd.getData3(), conn);
				if(!exeRlt.startsWith("success")){
					errorList += exeRlt + "@";
				}
			}
			if(cmd.getProc4()!=null && !cmd.getProc4().equals("")){
				exeRlt = this.callProc(cmd.getProc4(), cmd.getData4(), conn);
				if(!exeRlt.startsWith("success")){
					errorList += exeRlt;
				}
			}
			if(errorList.equals("")){
				conn.commit();
				pool.returnConnection(conn);
				return "success";
			}else{
				conn.rollback();
				pool.returnConnection(conn);
				return errorList;
			}
			
		}catch(Exception e){
			pool.returnConnection(conn);
			return "error";
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String callProc(String proc, List data, Connection conn){
		if(proc.charAt(0)=='@'){
			String updateSql = proc.substring(1);
			Statement st = null;
			try {
				LOGGER.info("执行前的SQL："+updateSql);
				st = conn.createStatement();
				long c = st.executeUpdate(updateSql);
				if(c>0){
					return "success";
				}else{
					return "error:未更新到记录，请刷新后再做尝试";
				}
			} catch (Exception e) {
				LOGGER.error("错误的SQL:"+updateSql);
				return "error:错误的SQL>>>"+updateSql;
			}
		}else{
			try {
				return procCaller.call(proc, data, conn);
			} catch (Exception e) {
				procCaller.outPutParams(data);
				e.printStackTrace();
				return "error:调用"+proc+"过程时发生异常，请检查参数是否设置合理";
			}
		}
	}
}
