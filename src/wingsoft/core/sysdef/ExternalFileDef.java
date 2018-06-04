package wingsoft.core.sysdef;

import java.util.ArrayList;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.SQLHelper;

public class ExternalFileDef extends ArrayList<String>
{
  public void loadDefs(String defPath)
  {
    StringBuffer buf = CommonOperation.getFileContent(defPath + "FILES.DEF");
    System.out.println(buf.length());
    if (buf != null) {
      String sAllFiles = buf.toString();
    	System.out.println(sAllFiles);
      String[] fileNameArray = sAllFiles.split("\\$\\$");
      System.out.println(fileNameArray.toString());
      System.out.println(fileNameArray.length);
      for (int i = 0; i < fileNameArray.length; ++i){
          if ((fileNameArray[i] == null) || ("".equals(fileNameArray[i]))) {
        	System.out.println("数据为空");
		}else{
            System.out.println("存在数据="+fileNameArray[i]);
      	    add(fileNameArray[i].substring(1));
		}
      }
      SQLHelper.LOGGERinfo(fileNameArray.length + "个文件名被加载");
    }
    else {
      SQLHelper.LOGGERinfo("文件名白名单加载失败：无法读取FILES.DEF文件");
    }
  }
}