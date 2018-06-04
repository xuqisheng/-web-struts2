package wingsoft.core.flash;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import wingsoft.core.command.QueryDAO;

import net.sf.json.JSONObject;

//classname changed
//import wingsoft.core.command.CommonBusinessQueryDAO;

import com.opensymphony.xwork2.ActionSupport;

public class CountPagingAction extends ActionSupport {
	
	private String json_param;
	private InputStream responseText;	
	public String getJson_param() {
		return json_param;
	}
	public void setJson_param(String json_param) {
		this.json_param = json_param;
	}
	
	
	public InputStream getResponseText() {
		return responseText;
	}
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}
	
	
	
	public String countPaging()
	{
		QueryDAO cb = new QueryDAO();
		
		
		JSONObject jo = JSONObject.fromObject(Escape.decodeThenUnescape(json_param));
		String sql = "" + jo.get("sql");
		long categoryPerPage = Long.parseLong("" + jo.get("categoryPerPage"));
		
		
		//filter sql by adding where
		String filter = "" + jo.get("filter");
		if(jo.get("filter") != null)
		{
			sql = SQLParserFull.addWhere(sql, SQLParserFull.AND, filter);
			//sql = sql + " where " + filter;
			
		}
		
		//query database to get number of data rows
		String sqlPagingTotal = "SELECT COUNT(*) FROM (" +
														sql +
														")";
				
		long sqlPagingTotalLong;
		try {
			sqlPagingTotalLong = cb.queryCountData(sqlPagingTotal);
			
			String count;
			long mod = sqlPagingTotalLong % categoryPerPage;
			if(mod == 0)
			{
				count = "" + (sqlPagingTotalLong / categoryPerPage);
			}
			else
			{
				count  = "" + (sqlPagingTotalLong / categoryPerPage + 1);
			}
			System.out.println("CountPagingAction createFlashJsonWrapper: sqlPagingTotal is " + sqlPagingTotalLong);
			
			//give response to the client
			setResponseText(new ByteArrayInputStream(count.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

}
