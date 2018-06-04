package wingsoft.core.command;

public class QueryCommand {
	/**
	 * select [prjfields] 					--投影字段
	 * 		from [tablenames] 			--候选表集合
	 * 		where ([joinconditions])		--链接条件
	 * 		and ([selconditions]) 		--初始数据选择过滤条件
	 * 		and ([userconditions])		--用户行权限过滤条件
	 * 		and ([subconditions])		--主从表过滤条件
	 * 		and ([filterconditions])		--查询过滤条件
	 * 		group by ([groupBy])		--分组
	 * 		ord by [sortname] [sortord]||[ordStr]
	 * **/
	private String sql;
	private String prjfields;
	private String tablenames;
	private String joinconditions;
	private String selconditions;
	private String subconditions;
	private String userconditions;
	private String filterconditions;
	private String sortname;
	private String sortord;
	private String groupBy;
	private String ordStr;
	private long rows;		
	private long page;
	private String userid;
	public String getPrjfields() {
		return prjfields;
	}
	public void setPrjfields(String prjfields) {
		this.prjfields = prjfields;
	}
	public String getTablenames() {
		return tablenames;
	}
	public void setTablenames(String tablenames) {
		this.tablenames = tablenames;
	}
	public String getJoinconditions() {
		return joinconditions;
	}
	public void setJoinconditions(String joinconditions) {
		this.joinconditions = joinconditions;
	}
	public String getSelconditions() {
		return selconditions;
	}
	public void setSelconditions(String selconditions) {
		this.selconditions = selconditions;
	}
	public String getUserconditions() {
		return userconditions;
	}
	public void setUserconditions(String userconditions) {
		this.userconditions = userconditions;
	}
	public String getFilterconditions() {
		return filterconditions;
	}
	public void setFilterconditions(String filterconditions) {
		
		this.filterconditions = filterconditions;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortord() {
		return sortord;
	}
	public void setSortord(String sortord) {
		this.sortord = sortord;
	}
	public long getRows() {
		return rows;
	}
	public void setRows(long rows) {
		this.rows = rows;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}

	public void setSubconditions(String subconditions) {
		this.subconditions = subconditions;
	}
	public String getSubconditions() {
		return subconditions;
	}
	public void setOrdStr(String ordStr) {
		this.ordStr = ordStr;
	}
	public String getOrdStr() {
		return ordStr;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSql() {
		return sql;
	}

}
