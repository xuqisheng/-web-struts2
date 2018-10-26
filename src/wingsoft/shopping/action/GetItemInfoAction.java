package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import wingsoft.custom.BaseAction;
import wingsoft.shopping.dao.CommentsDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.model.Item;

@SuppressWarnings("serial")
public class GetItemInfoAction extends BaseAction {

	public String execute() throws IOException, SQLException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String itemid = request.getParameter("itemid");

		ItemDAO id = new ItemDAO();
		Item it = id.selectItem(itemid);
		CommentsDAO cd = new CommentsDAO();
		int comment = cd.selectItemid(itemid).size();
		
		String json = it.toString();
		json = json.substring(0,json.length()-1);
		json+=",\"comment\":"+comment+",\"choose\":[";
		boolean flag = false;
		List<Item> ss = id.selectChildren(itemid);
		
		while (!ss.isEmpty()) {
			json+=ss.remove(0).toString()+",";
			flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]}";
		json = json.replaceAll("null","");
		setJsonObject(JSONObject.fromObject(json));
		
		return SUCCESS;
	}
}