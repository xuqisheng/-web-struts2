package wingsoft.core.wfdao;

import java.util.HashMap;
import java.util.List;


public class QueryDefDAO extends WFDefQueryDAO {
	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findQueryDef(String funcno){
		
		String sql="select * from SYS_QUERY_DEF where FUNCNO="+funcno;
		
		return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public List findQueryDetail(String funcno){
		
		String sql="select * from SYS_QUERY_DETAIL where FUNCNO="+funcno+" order by ord";
		
		return this.queryHashDataForm(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List findQueryDetail(String funcno,String uid){
		
		String sql ="select t.* from SYS_QUERY_DETAIL t,"
					 +          "(select funcno,ord from SYS_ROLE_COLS r,SYS_USER_ROLE s "
					 +			 " where r.roleid = s.roleid and s.userid = '"+uid+"' and r.funcno="+funcno+") c "
					 +"where t.ord = c.ord and t.funcno="+funcno+" order by t.ord asc";
		
		return this.queryHashDataForm(sql);
	}
}
