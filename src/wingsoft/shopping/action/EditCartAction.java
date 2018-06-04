package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
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
public class EditCartAction extends ActionSupport {
	/*
	 * Generated Methods
	 */
	/**
	 * Method execute
	 * 
	 * @param "mapping
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
		String userid = "";//暂定
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}

		String itemid = request.getParameter("cartid");
		String number = request.getParameter("number");
		
		CartDAO cd = new CartDAO();
		cd.changeNum(itemid,Integer.valueOf(number),userid);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("html/text");
		PrintWriter out=null;
	
		out=response.getWriter();
		out.print("[\""+itemid+"\",\""+number+"\"]");
		out.flush();
		out.close();
		
		return null;
	}


}