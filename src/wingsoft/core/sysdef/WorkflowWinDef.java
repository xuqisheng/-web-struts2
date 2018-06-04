package wingsoft.core.sysdef;

import java.util.HashMap;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.SQLHelper;

public class WorkflowWinDef extends HashMap<String, String>
{
  public void loadDefs(String defPath)
  {
    StringBuffer buf = CommonOperation.getFileContent(defPath + "WORKFLOWWIN.DEF");

    if (buf != null) {
      String sWfWin = buf.toString();
      if ("".equals(sWfWin))
        return;
      String[] wfwins = sWfWin.split("\\|");
      for (int i = 0; i < wfwins.length; ++i) {
        String onewfwin = wfwins[i];
        String[] wfact_win = onewfwin.split("@");
        put(wfact_win[0], wfact_win[1]);
      }
      SQLHelper.LOGGERinfo("工作流窗体列表被加载");
    }
    else {
      SQLHelper.LOGGERinfo("工作流窗体列表加载失败：无法读取WORKFLOWWIN.DEF文件");
    }
  }
}