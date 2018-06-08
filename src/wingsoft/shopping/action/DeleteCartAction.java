package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CartDAO;

import com.opensymphony.xwork2.ActionSupport;
import wingsoft.shopping.util.DBManager;

@SuppressWarnings("serial")
public class DeleteCartAction extends ActionSupport {
	/*
	 * Generated Methods
	 */
	/**
	 * Method execute
	 * 
	 * @param ""mapping
	 * @param "form
	 * @param "request
	 * @param "response
	 * @return ActionForward
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public String execute() throws IOException, SQLException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String cartid = request.getParameter("cartid");
		String userid = "";//暂定
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}
		
		CartDAO cd = new CartDAO();
		cd.delete(cartid,userid);
		
		response.sendRedirect("cart.jsp");
		return null;
	}

	public void deleteAll() throws  Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try{
			String userid = "";//暂定
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
			}
			String deleteAll = "delete from SHP_cart where userid=rpad(?,30,' ')";
			ps  = c.prepareStatement(deleteAll);//o
			ps.setString(1,userid);
			int index = ps.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			c.close();
		}
		response.sendRedirect("cart.jsp");
	}
}