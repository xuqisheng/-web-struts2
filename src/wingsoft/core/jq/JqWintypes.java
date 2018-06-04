package wingsoft.core.jq;

import wingsoft.core.start.WebInitServlet;
import wingsoft.core.wfdao.WFDefQueryDAO;

public class JqWintypes
{
  public String getWinTypes()
  {
    WFDefQueryDAO qry = new WFDefQueryDAO();
    String sql = "select wm_concat(distinct trim(func_type)) from " + WebInitServlet.wfServerName + ".sys_window_def";
    String sWinTypes = qry.queryScalar(sql);
    String[] arTypes = sWinTypes.split(",");
    StringBuffer sbResult = new StringBuffer();
    sbResult.append("[");
    for (int i = 0; i < arTypes.length; ++i) {
      if (arTypes[i].equals("F")) {
        sql = "select wm_concat(distinct trim(inputtype)) from " + WebInitServlet.wfServerName + ".sys_form_detail";
        sbResult.append("{\"type\":\"F\",\"detail\":\"" + qry.queryScalar(sql) + "\"}");
      } else if (arTypes[i].equals("Q")) {
        sql = "select count(*) from " + WebInitServlet.wfServerName + ".sys_query_detail where chart_col is not null and chart_col<>'none'";
        sbResult.append("{\"type\":\"Q\",\"detail\":\"" + ((qry.queryScalar(sql).equals("0")) ? "\"}" : "chart\"}"));
      } else {
        sbResult.append("{\"type\":\"" + arTypes[i] + "\",\"detail\":\"\"}");
      }
      if (i != arTypes.length - 1)
        sbResult.append(",");
    }
    sbResult.append("]");
    return sbResult.toString();
  }
}