package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CartDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.model.Cart;
import wingsoft.shopping.model.Item;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetCartAction extends BaseAction {
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
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";//暂定
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}
		
		CartDAO cd = new CartDAO();
		ItemDAO id = new ItemDAO();
		List<Cart> cs = cd.selectUser(userid);
		
		String json = "[";
		
		boolean flag = false;
		while (!cs.isEmpty()) {
			Cart c = cs.remove(0);
			Item i = id.selectItem(c.getItemid());
//			Item pi = null;
			if (i.getParents()!=null) {
				i.setItemname(id.selectItem(i.getParents()).getItemname()+" - "+i.getItemname());
			}
			json+=i.toString();
			json = json.substring(0,json.length()-1);
			json+=",\"cartid\":\""+c.getCartid()+"\",\"number\":\""+c.getNumber()+"\"},";

			flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		JSONArray jo = JSONArray.fromObject(json);
		setJsonArray(jo);
		return SUCCESS;
	}
}