package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CategoryDAO;
import wingsoft.shopping.model.Category;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetCategoryAction extends BaseAction {

	public String execute() throws IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();

		CategoryDAO cd = new CategoryDAO();
		String json = "[";
		for (int i=1;i<=3;i++) {
			json+="[";
			List<Category> cas = cd.selectLevel(i);
			boolean flag = false;
			while (!cas.isEmpty()) {
				json+=cas.remove(0).toString()+",";
				flag = true;
			}
			if (flag) {
				json = json.substring(0,json.length()-1);
			}
			json+="],";
		}
		json = json.substring(0,json.length()-1);
		json+="]";
		JSONArray jo = JSONArray.fromObject(json);
		setJsonArray(jo);
		return SUCCESS;
	}
}