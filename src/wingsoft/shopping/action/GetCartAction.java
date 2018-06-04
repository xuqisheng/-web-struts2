package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CartDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.model.Cart;
import wingsoft.shopping.model.Item;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetCartAction extends ActionSupport {
	/*
	 * Generated Methods
	 */
	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
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

//			if (i.getParents()!=null&&i.getParents()!="null") {
//				pi = id.selectItem(i.getParents());
//				//pi.setItemname(id.selectItem(i.getParents()).getItemname()+" - "+i.getItemname());
//			}
//
//			if (pi==null) {
//				json+="{\"cartid\":\""+c.getCartid()+"\",\"itemid\":\""+i.getItemid()+"\",\"itemname\":\""+i.getItemname()+
//						"\",\"itemimg\":\""+i.getItemimg()+"\",\"subitemid\":\"\",\"subitemname\":\"\",\"subitemprize\":\""+i.getPrize()+"\",\"number\":\""+c.getNumber()+"\"},";
//			} else {
//				json+="{\"cartid\":\""+c.getCartid()+"\",\"itemid\":\""+i.getItemid()+"\",\"itemname\":\""+pi.getItemname()+
//						"\",\"itemimg\":\""+i.getItemimg()+"\",\"subitemid\":\""+i.getItemid()+"\",\"subitemname\":\""+
//						i.getItemname()+"\",\"subitemprize\":\""+i.getPrize()+"\",\"number\":\""+c.getNumber()+"\"},";
//			}
			flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("html/text");
		PrintWriter out=null;
	
		out=response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
		return null;
	}
}