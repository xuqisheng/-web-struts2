package wingsoft.core.command;

import java.util.List;


public class QueryCommandExecutor {
	private QueryCommand qCmd = null;
	protected QueryDAO queryDao = new QueryDAO();
	protected QueryResultFormatter resultBuilder = new QueryResultFormatter();
	public void setCommand(QueryCommand qCmd){
		this.qCmd = qCmd;
	}
	
	public String execute()throws Exception{
		if(qCmd == null){
			throw new Exception();
		}else{
			return this.execute(qCmd);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String execute(QueryCommand qCmd)throws Exception{
		if(qCmd == null){
			return null;
		}
		String sql = this.getPagedSql(qCmd);
		String countsql = this.getCountSql(qCmd);
		List resultList = queryDao.queryDataForm(sql);
		long records = queryDao.queryCountData(countsql);
		long page = qCmd.getPage();
		long rownum = qCmd.getRows();
		String jsonResult = resultBuilder.formatResultToJsonStr(page, rownum, records, resultList);
		return jsonResult;
	}

	public String getSql(QueryCommand cmd){
		String ordStr = cmd.getOrdStr();
		if(ordStr==null||ordStr.equals("")){
			ordStr = cmd.getSortname() + " " + cmd.getSortord();
			if(cmd.getSortname()==null ||cmd.getSortname().equals("")){
				ordStr = "rownum asc";
			}
		}
		String sql = "select row_number() over(order by "
				+ ordStr + ") rn,"
				+ cmd.getPrjfields() + " from " + cmd.getTablenames();
		
		String conditions = this.getAllConditions(cmd);
		
		if( (conditions != null) && !conditions.equals("") ){
			sql += " where " + conditions; 
		}
		
		if(cmd.getGroupBy() !=null){
			sql += " group by " + cmd.getGroupBy() ;
		}
		
		return sql;
	}
	
	public String getAllConditions(QueryCommand qCmd){
		
		boolean tag = false;			
		String allconditions = "";
		
		if((qCmd.getJoinconditions() != null) && !qCmd.getJoinconditions().equals("") ) {		//连接条件
			
			allconditions += " ( " + qCmd.getJoinconditions() + " ) ";
			tag = true;
		}
		
		if((qCmd.getSelconditions() != null) && !qCmd.getSelconditions() .equals("") ) {		//选择条件
			if(tag){
				allconditions += " and ( "+ qCmd.getSelconditions() + " ) ";
			}else{
				allconditions += " ( "+ qCmd.getSelconditions() + " ) ";
			}
			tag = true;
		}
		
		if((qCmd.getUserconditions() != null) && !qCmd.getUserconditions() .equals("")){	//用户行权限选择条件
			if(tag){
				allconditions += " and ( "+ qCmd.getUserconditions() + " ) ";
			}else{
				allconditions += " ( "+ qCmd.getUserconditions() + " ) ";
			}
			tag = true;
		}
		
		if((qCmd.getFilterconditions() != null) && !qCmd.getFilterconditions() .equals("") ){	//查询过滤选择条件
			if(tag){
				allconditions += " and ( "+ qCmd.getFilterconditions() + " ) ";
			}else{
				allconditions += " ( "+ qCmd.getFilterconditions() + " ) ";
			}
			tag = true;
		}
		return allconditions;
	}
	
	public String getPagedSql(QueryCommand qCmd){
		long page = qCmd.getPage();
		long rows = qCmd.getRows();
		long start = ( page - 1 ) * rows + 1;
		long end = page * rows;
		String unpagedSql = qCmd.getSql();
		if(unpagedSql == null || unpagedSql.equals("")){
			unpagedSql = this.getSql(qCmd);
		}
		String sql = "select *" 
					 + " from ( " + unpagedSql + " ) "
					 + " where rn between " + start + " and " + end;
		return sql;
	}
	
	public String getCountSql(QueryCommand qCmd){
		String sql = "select count(*) "+" from " + qCmd.getTablenames() + " ";
		String conditions = this.getAllConditions(qCmd);
		if( !conditions.equals("") ){
			sql += " where " + conditions;
		}
		return sql;
	}
}
