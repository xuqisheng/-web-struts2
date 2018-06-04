package wingsoft.core.wfdao;

import java.util.HashMap;
import java.util.List;


public class FormDefDAO extends WFDefQueryDAO {

	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findFormDef(String funcno){
		
		String sql="select * from SYS_FORM_DEF where FUNCNO="+funcno;
		try{
			return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List findFormDetail(String funcno){
		
		String sql="select * from SYS_FORM_DETAIL where FUNCNO="+funcno+" order by ord";
		try{
			return this.queryHashDataForm(sql);
		}catch(Exception e){
			return null;
		}
	}
}
