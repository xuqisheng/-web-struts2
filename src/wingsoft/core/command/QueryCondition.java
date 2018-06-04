package wingsoft.core.command;

/**该类是为了方便将前台传过来的查询条件转换成Java对象的一个类，
 * 其参数名与json字符串中的字段一一对应
 * @author ZYZ
 *
 */
public class QueryCondition {
	private String field;	//字段名
	private String op;		//比较符号
	private String data;	//数据值
	/**构造查询条件函数
	 * @param field	查询字段
	 * @param op	比较符号
	 * @param data	数据值
	 */
	public QueryCondition(){
		
	}
	public QueryCondition(String field,String op,String data){
		this.field=field;
		this.op=op;
		this.data=data;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getConditionString(){
		
		return covertToConditionStr(this.field,this.op,this.data);
	}
	
	/**条件对应转换关系
	 * ['等于', '不等', '小于', '小于等于','大于','大于等于', '开始于','不开始于','属于','不属于','结束于','不结束于','包含','不包含'],
		 "eq",  "ne",   "lt",     "le",    "gt",  "ge",      "bw",     "bn",    "in",	"ni",	"ew",		"en",	"cn",	"nc"
	 */
	private String covertToConditionStr(String field,String operator,String data){
		
		String condStr ="to_char("+field+")";
		if( data!=null)
			data = data.replace('\'', '%');
		if(operator.equals("eq")){
			return condStr+"='"+data+"'";
		}else if(operator.equals("ne")){
			return condStr+"<>'"+data+"'";
		}else if(operator.equals("lt")){
			return condStr+"<'"+data+"'";
		}else if(operator.equals("le")){
			return condStr+"<='"+data+"'";
		}else if(operator.equals("gt")){
			return condStr+">'"+data+"'";
		}else if(operator.equals("ge")){
			return condStr+">='"+data+"'";
		}else if(operator.equals("bw")){
			return condStr+" like '"+data+"%'";
		}else if(operator.equals("bn")){
			return condStr+" not like '"+data+"%'";
		}else if(operator.equals("in")){
			return condStr+" like '%"+data+"%'";
		}else if(operator.equals("ni")){
			return condStr+" not like '%"+data+"%'";
		}else if(operator.equals("ew")){
			return condStr+" like '%"+data+"'";
		}else if(operator.equals("en")){
			return condStr+" not like '%"+data+"'";
		}else if(operator.equals("cn")){
			return condStr+" like '%"+data+"%'";
		}else if(operator.equals("nc")){
			return condStr+"not like '%"+data+"%'";
		}
		
		return null;
	}
}
