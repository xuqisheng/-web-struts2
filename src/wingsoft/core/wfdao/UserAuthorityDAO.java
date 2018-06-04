package wingsoft.core.wfdao;

import java.util.List;
import wingsoft.core.start.WebInitServlet;
import wingsoft.tool.common.CommonOperation;

public class UserAuthorityDAO extends WFDefQueryDAO
{
  public String findUserTableFilters(String userid, String tablename)
  {
    String sql = "select filters from  " + WebInitServlet.wfServerName + ".sys_user_role ur, " + WebInitServlet.wfServerName + ".sys_role_table rt where ur.roleid = rt.roleid and" + 
      " ur.userid = '" + userid + "' and rt.table_name = '" + tablename + "' and rownum = 1";

    return queryScalar(sql); }

  public String findUserConditions(String userid, String tablenames) {
    String[] tbNames = tablenames.split(",");
    String userConditions = "";
    for (int i = 0; i < tbNames.length; ++i) {
      String filter = findUserTableFilters(userid, tbNames[i]);
      if (filter != null)
        userConditions = userConditions + CommonOperation.nTrim(filter) + ((i != tbNames.length - 1) ? " and " : "");
    }

    return userConditions;
  }

  public List findUserRoleTableCond(String userid) {
    String sql = "select * from  " + WebInitServlet.wfServerName + ".sys_role_table where roleid in (select roleid from  " + WebInitServlet.wfServerName + ".sys_user_role where userid='" + userid + "')";
    return queryHashDataForm(sql); }

  public List findRoleTableCond(String roleid) {
    String sql = "select * from  " + WebInitServlet.wfServerName + ".sys_role_table where roleid = '" + roleid + "'";
    return queryHashDataForm(sql);
  }
}