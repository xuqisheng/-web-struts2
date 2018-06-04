package wingsoft.core.wfdao;

import java.util.HashMap;
import java.util.List;

public class CustomDefDAO extends WFDefQueryDAO {

	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findCustomDef(String funcno){
		
		String sql="select * from SYS_CUSTOM_DEF where FUNCNO="+funcno;
		try{
			return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findCustomDetail(String funcno){
		
		String sql="select * from SYS_CUSTOM_DETAIL where FUNCNO="+funcno;
		try{
			return this.queryHashDataForm(sql);
		}catch(Exception e){
			return null;
		}
	}
}
