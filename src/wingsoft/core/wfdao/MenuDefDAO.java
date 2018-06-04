package wingsoft.core.wfdao;
import java.util.List;


public class MenuDefDAO extends WFDefQueryDAO{
	
	
	/**获得登录用户所拥有权限的菜单树，封装成一个list hashmap
	 * @param uid	用户登录id
	 * @return
	 */
	public List getUserMenuTree(String uid){
		
		String sql = "SELECT * FROM "
				   + "(SELECT  DISTINCT D.NODEID, D.LEAF, D.PARENT_NODEID, D.NODENAME,D.WINNO,D.LEVELID "
				   + "FROM SYS_MENU_NODE D, "
				   + "(SELECT NODEID FROM SYS_ROLE_MENU A, SYS_USER_ROLE B "
				   + "WHERE  B.USERID = '"+uid+"' AND A.ROLEID=B.ROLEID) C, "
				   + "SYS_MENU_PARENTS E " 
				   + "WHERE E.NODEID=C.NODEID AND D.NODEID=E.PARENT_NODEID) "
				   + "ORDER BY LEVELID";
		
		return this.queryHashDataForm(sql);
		
	}
	public List getAllLeaf(){
		String sql = "select nodeid,nodename from sys_menu_node where leaf = 'Y' order by nodeid,levelid";
		
		return this.queryHashDataForm(sql);
		
	}
	
}
