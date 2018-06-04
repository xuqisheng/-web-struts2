package wingsoft.core.sysdef;

import java.util.HashMap;
import java.util.List;
import wingsoft.core.start.WebInitServlet;
import wingsoft.core.wfdao.UserAuthorityDAO;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.SQLHelper;

public class RoleTableDef extends HashMap<String, String>
{
  public void loadDefs(String defPath, String roleid)
  {
    if ("json".equals(WebInitServlet.startMode)) {
      StringBuffer buf = CommonOperation.getFileContent(defPath + "role.DEF");
      if (buf != null) {
        String sAllRoleCond = buf.toString();
        if ("".equals(sAllRoleCond))
          return;
        String[] roleCondArray = sAllRoleCond.split("\\$\\$");
        int loadCnt = 0;
        for (int i = 0; i < roleCondArray.length; ++i) {
          String cond = roleCondArray[i];
          if ("".equals(CommonOperation.nTrim(cond)))
            continue;
          String sRole = cond.split("@")[0];
          if (sRole.equals(roleid)) {
            cond = cond.split("@")[1];
            String sAlias = cond.split("\\:\\:")[0];
            String sCond = cond.substring(cond.indexOf("|") + 1);
            put(sAlias, sCond);
            ++loadCnt;
          }
        }
        SQLHelper.LOGGERinfo("角色" + roleid + "有" + loadCnt + "个角色行条件被加载");
      }
      else {
        SQLHelper.LOGGERinfo("角色行条件加载失败：无法读取role.DEF文件");
      }
    } else {
      UserAuthorityDAO ua = new UserAuthorityDAO();
      List roleTableCondList = ua.findRoleTableCond(roleid);
      int loadCnt = 0;
      for (int i = 0; i < roleTableCondList.size(); ++i) {
        HashMap rtc = (HashMap)roleTableCondList.get(i);
        if (roleid.equals(rtc.get("ROLEID"))) {
          String sCond = (String)rtc.get("FILTERS");
          String sAlias = (String)rtc.get("ALIAS");
          put(sAlias, sCond);
          ++loadCnt;
        }
      }
      SQLHelper.LOGGERinfo("角色" + roleid + "有" + loadCnt + "个角色行条件被加载");
    }
  }

  public void loadDefs(String defPath, List<String> roleids) {
    StringBuffer buf = CommonOperation.getFileContent(defPath + "role.DEF");
    if (buf != null) {
      String sAllRoleCond = buf.toString();
      String[] roleCondArray = sAllRoleCond.split("\\$\\$");
      int loadCnt = 0;
      for (int i = 0; i < roleCondArray.length; ++i) {
        String cond = roleCondArray[i];
        if ("".equals(CommonOperation.nTrim(cond)))
          continue;
        String sRole = cond.split("@")[0];
        if (roleids.contains(sRole)) {
          cond = cond.split("@")[1];
          String sAlias = cond.split("\\:\\:")[0];
          String sCond = cond.substring(cond.indexOf("|") + 1);
          put(sAlias, sCond);
          ++loadCnt;
        }
      }
      SQLHelper.LOGGERinfo("角色" + roleids + "有" + loadCnt + "个角色行条件被加载");
    }
    else {
      SQLHelper.LOGGERinfo("角色行条件加载失败：无法读取role.DEF文件");
    }
  }
}