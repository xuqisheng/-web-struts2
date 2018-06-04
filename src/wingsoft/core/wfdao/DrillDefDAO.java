package wingsoft.core.wfdao;

import java.util.HashMap;
import java.util.List;

public class DrillDefDAO  extends WFDefQueryDAO {

	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findDrillDef(String funcno){
		
		String sql = "select * from SYS_DRILLTABLE_DEF where FUNCNO="+funcno;
		
		return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public List findDrillSections(String funcno){
		
		String sql = "select * from SYS_DRILLTABLE_SECTION where FUNCNO="+funcno+" order by SEC_ORD";
		
		return this.queryHashDataForm(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List findDrillCalItems(String funcno){
		
		String sql = "select * from SYS_DRILL_CAL_ITEM where FUNCNO = "+ funcno + " order by ITEM_ORD";
		
		return  this.queryHashDataForm(sql);
		
	}

}
