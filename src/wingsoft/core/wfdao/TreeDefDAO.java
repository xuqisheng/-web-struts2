package wingsoft.core.wfdao;

import java.util.HashMap;
import java.util.List;

public class TreeDefDAO extends WFDefQueryDAO{
	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findTreeDef(String funcno){
		
		String sql="select * from SYS_TREE_DEF where FUNCNO="+funcno;
		
		return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public List findTreeDetail(String funcno){
		
		String sql="select * from SYS_TREE_DETAIL where FUNCNO="+funcno+" order by lvl asc";
		
		return this.queryHashDataForm(sql);
	}
}
