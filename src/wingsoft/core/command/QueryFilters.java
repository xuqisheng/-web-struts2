package wingsoft.core.command;

import java.util.List;

/**该类是为了方便将前台传过来的查询过滤参数filters转换成Java对
 * 象的一个类， 其参数名与json字符串中的字段一一对应有关filter
 * 格式的定义请参看文档。
 * @author ZYZ
 *
 */
public class QueryFilters {
	private String groupOp;
	@SuppressWarnings("unchecked")
	private List rules;
	public QueryFilters(){
		
	}
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	@SuppressWarnings("unchecked")
	public void setRules(List rules) {
		this.rules = rules;
	}
	@SuppressWarnings("unchecked")
	public List getRules() {
		return rules;
	}
	
	
}
