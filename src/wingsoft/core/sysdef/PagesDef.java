package wingsoft.core.sysdef;

import java.io.File;
import java.util.HashMap;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.SQLHelper;

public class PagesDef extends HashMap<String, String>
{
  public void loadDefs(String defPath)
  {
    File fPath = new File(defPath);
    File[] fileList = fPath.listFiles();
    String filePrefix = null; String fileName = null;
    int loadCnt = 0;
    for (int i = 0; i < fileList.length; ++i) {
      fileName = fileList[i].getName().trim();
      if (fileName.endsWith(".DEF")) {
        ++loadCnt;
        filePrefix = fileName.split(".DEF")[0];
        put(filePrefix, CommonOperation.getFileContent(defPath + fileName).toString());
      }
    }
   // SQLHelper.LOGGERinfo(loadCnt + "个页面定义文件被加载");

    String funcPath = defPath + "funcdef" + File.separator;
    File fFuncPath = new File(funcPath);
    File[] funcfileList = fFuncPath.listFiles();
    loadCnt = 0;
    for (int i = 0; i < funcfileList.length; ++i) {
      fileName = funcfileList[i].getName().trim();
      if (fileName.endsWith(".DEF")) {
        ++loadCnt;
        filePrefix = fileName.split(".DEF")[0];
        put("F-" + filePrefix, CommonOperation.getFileContent(funcPath + fileName).toString());
      }
    }
    //SQLHelper.LOGGERinfo(loadCnt + "个功能定义文件被加载"); 
    }

  public String getPageDef(String projid, String mainwinno) {
    String result = "[{}]";
    String json = (String)get(projid + "_" + mainwinno);
    if (json != null)
      result = json;
    return result;
  }
}