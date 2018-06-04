package wingsoft.core.statistic;

import java.util.HashMap;

public class StatisticCommand {
	
	private String sections;						//当前统计切面集合
	private String formulaItems;					//计算项
	private String tables;							//目标统计表
	private String joinCond;						//链接条件  
	private String selCond;							// 选择条件
	public HashMap<String,String> filterMap = new HashMap<String,String>();
	private String filter;								//过滤条件
	private String codeMap = "XCODEMAP";			//切面值码对照表
	private String order = "ASC";					//查询生降序
	private int scalar = 1; 							//统计后结果集的维阶
	
	public String getFormulaItems() {
		return formulaItems;
	}
	public void setFormulaItems(String formulaItems) {
		this.formulaItems = formulaItems;
	}
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}
	public String getJoinCond() {
		return joinCond;
	}
	public void setJoinCond(String joinCond) {
		this.joinCond = joinCond;
	}
	public String getSelCond() {
		return selCond;
	}
	public void setSelCond(String selCond) {
		this.selCond = selCond;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
		if(filter!=null&& !filter.equals("")){
			String [] conds = filter.split(" and ");
			for(int i=0;i<conds.length;i++){
				String[] c = conds[i].split(" in ");
				filterMap.put(c[0].trim(), c[1].trim());
			}
				
		}
	}
	public String getCodeMap() {
		return codeMap;
	}
	public void setCodeMap(String codeMap) {
		if(codeMap == null || codeMap.equals("") )
			return;
		this.codeMap = codeMap;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		if(order != null)
			this.order = order;
	}
	public void setSections(String sections) {
		this.sections = sections;
	}
	public String getSections() {
		return sections;
	}
	public void setScalar(int scalar) {
		this.scalar = scalar;
	}
	public void setScalar(String scalar) {
		
		this.scalar = Integer.parseInt(scalar);
	}
	public int getScalar() {
		return scalar;
	}
	
}
