package wingsoft.core.wfdao;

import java.util.HashMap;

public class ChartDefDAO extends WFDefQueryDAO{
	
	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findFormDef(String funcno){
			
			String sql="select * from SYS_CHART_DEF where FUNCNO="+funcno;
			
			return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
			
	}
}

