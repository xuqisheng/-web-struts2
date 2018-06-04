package wingsoft.core.command;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
public class CommandParser {
	@SuppressWarnings("unchecked")
	public UpdateCommand convertStrToCommonCmd(String cmdStr){
		Map classMap = new HashMap();
		classMap.put("check_data", Field.class);
		classMap.put("data0", Field.class);
		classMap.put("data1", Field.class);
		classMap.put("data2", Field.class);
		classMap.put("data3", Field.class);
		classMap.put("data4", Field.class);
		
		UpdateCommand cmd = (UpdateCommand)JSONObject.toBean( JSONObject.fromObject(cmdStr), UpdateCommand.class,classMap );
		return cmd;
	}
	
	/**将json格式的查询过滤字符串转换成数据库能执行的条件字符串。
	 * @param filterStr json格式的条件字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ConvertFiltersStrToConditions(String filterStr){
		
		Map classMap=new HashMap();
		
		classMap.put("rules", QueryCondition.class);
		
		QueryFilters qf=(QueryFilters) JSONObject.toBean( JSONObject.fromObject(filterStr), QueryFilters.class, classMap );
		
		List<QueryCondition> qcList=qf.getRules();
		
		String gOp=qf.getGroupOp();
		
		String condStr="";
		
		for(int i=0;i<qcList.size();i++){
			
			condStr+=" "+qcList.get(i).getConditionString();
			
			if(i!=qcList.size()-1){
				
				condStr+=" "+gOp;
				
			}
			
		}
		
		return condStr;
	}
}
