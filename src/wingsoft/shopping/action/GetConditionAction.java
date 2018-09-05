package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.ItemparaDAO;
import wingsoft.shopping.dao.ParameterDAO;
import wingsoft.shopping.model.Parameter;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetConditionAction extends BaseAction {
	/*
	 * Generated Methods
	 */
	/**
	 * Method execute
	 * 
	 * @return ActionForward
	 * @throws IOException 
	 * @throws SQLException
	 */
	public String execute() throws IOException, SQLException {
		System.out.println("getConditionAction is starting");
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");
		String Collection = request.getParameter("collection");
		Boolean IsCollection = false;  //是否为查询我的收藏
		if (keyword != null) {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		}
		if (category == null || category.equals("null")) {
			category = "0";
		}
		if (keyword == null || keyword.equals("null")) {
			keyword = "";
		}
		if ("T".equals(Collection)) {
			IsCollection = true;
		}
		ParameterDAO pd = new ParameterDAO();
		ItemparaDAO ip = new ItemparaDAO();
		List<String> ps = ip.findTopPara(category, keyword, 5, IsCollection);

		String json = "[";

		//for (int i=0;i<3;i++) {
		for (int i = 0; i < ps.size(); i++) {
			Parameter p = pd.selectParameter(ps.get(i));
			json += "{\"id\":\"" + p.getParameterid() + "\",\"display\":\"";

			json += p.getParametername() + "\",\"options\":[";

			if (p.getParametertype().endsWith("string")) {
				List<String> value = ip.findParaValue(p.getParameterid(), category, keyword, 10);
				boolean flag = false;
				while (!value.isEmpty()) {
					json += "\"" + value.remove(0) + "\",";
					flag = true;
				}

				if (flag) {
					json = json.substring(0, json.length() - 1);
				}
				json += "]";
			} else {
				List<String> value = ip.findNumPara(p.getParameterid(), category, keyword);
				boolean flag = false;
				while (!value.isEmpty()) {
					json += "\"" + value.remove(0) + "\",";
					flag = true;
				}
				if (flag) {
					json = json.substring(0, json.length() - 1);
				}
				json += "]";
			}
			json += "},";
		}
		json = json.substring(0, json.length() - 1);
		json += "]";
		JSONArray jo = JSONArray.fromObject(json);
		setJsonArray(jo);
		System.out.println("getConditionAction is ended");
		return SUCCESS;
	}


}