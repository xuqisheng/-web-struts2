package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CartDAO;
import wingsoft.shopping.dao.OrdersDAO;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddOrderAction extends ActionSupport {
	/*
	 * Generated Methods
	 */

	/**
	 *
	 * @return
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
		OrdersDAO od = new OrdersDAO();
		String id = od.save(userid);
		cd.addOrder(userid, id);
		
		response.sendRedirect("cart.jsp");
		return null;
	}
}