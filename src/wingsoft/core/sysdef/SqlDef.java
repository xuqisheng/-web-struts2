package wingsoft.core.sysdef;

import java.util.HashMap;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.SQLHelper;

public class SqlDef extends HashMap<String, String>
{
  public void loadDefs(String defPath)
  {
    StringBuffer buf = CommonOperation.getFileContent(defPath + "SQL.DEF");
    if (buf != null) {
      String sAllSql = buf.toString();
      String[] sqlArray = sAllSql.split("\\$\\$");
      for (int i = 0; i < sqlArray.length; ++i) {
        String oneSql = sqlArray[i];
        if ("".equals(CommonOperation.nTrim(oneSql)))
          continue;
        String sqlID = oneSql.split("\\|")[0];
        String sqlText = oneSql.substring(oneSql.indexOf("|") + 1);
        put(sqlID, sqlText);
      }
      //SQLHelper.LOGGERinfo(sqlArray.length + "条SQL被加载");
    }
    else {
     // SQLHelper.LOGGERinfo("SQL加载失败：无法读取SQL.DEF文件");
    }
  }
}