package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CartDAO;
import wingsoft.shopping.model.Cart;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddCartAction extends ActionSupport {
	/*
	 * Generated Methods
	 * 
	 */ 
	
	private String json = "";
	
	
	
	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}
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
		//String itemid = request.getParameter("itemid");
		String subitem = request.getParameter("subid");
		String number = request.getParameter("num");
		String userid = "";//暂定
		if (request.getSession().getAttribute("userId")!=null) {
		System.out.println("当前用户已登录");	
			userid = (String) request.getSession().getAttribute("userId");
		
		
		
		Cart c = new Cart();
		c.setItemid(subitem);
		//c.setSubitemid(subitem);
		c.setNumber(Integer.valueOf(number));
		c.setUserid(userid);
		
		CartDAO cd = new CartDAO();
		if (cd.save(c)) {
			json = "ok"; 
		}else{
			json = "添加购物车异常";
		}
		
		//response.sendRedirect("cart.jsp");
		}else{
			json = "未登录";		
		}
		return "success";
	}
}