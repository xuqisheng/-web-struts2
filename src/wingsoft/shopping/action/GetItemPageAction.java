package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import wingsoft.custom.BaseAction;
import wingsoft.shopping.dao.ItemDAO;

@SuppressWarnings("serial")
public class GetItemPageAction extends BaseAction {
	/**
	 * Method execute
	 * 
	 * @return ActionForward
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public String execute() throws IOException, SQLException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String category = request.getParameter("category");
		String parameters = request.getParameter("parameter"); 
		String values = request.getParameter("value");
		String page = request.getParameter("page");
		String baseurl = request.getParameter("baseurl");
		String keyword = request.getParameter("keyword");
		String Wjson = "";
		if (keyword!=null) {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		}
		if (values!=null) {
			values = new String(values.getBytes("iso-8859-1"),"utf-8");
		}
		if (baseurl!=null) {
			baseurl = new String(baseurl.getBytes("iso-8859-1"),"utf-8");
		}
		if (parameters==null||parameters.equals("null")) {
			parameters = null;
		}
		if (values==null||values.equals("null")) {
			values = null;
		}
		if (category==null||category.equals("null")) {
			category = "0";
		}
		if (page==null||page.equals("null")) {
			page = "1";
		}
		if (baseurl==null||baseurl.equals("null")) {
			baseurl="search.html?";
		}
		
		ItemDAO id = new ItemDAO();		
		int is = id.countCategory(category, parameters, keyword, values)/16+1;
		
		if (is<Integer.parseInt(page)) {
			page = String.valueOf(is);
		}
		if (Integer.parseInt(page)<1) {
			page = "1";
		}
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("html/text");
		Wjson = "{\"currentpage\":"+page+",\"totalpage\":"+is+",\"baseurl\":\""+baseurl+"\"}";
		setJsonObject(JSONObject.fromObject(Wjson));
		return SUCCESS;
	}
}