package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CommentsDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.model.Item;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetItemInfoAction extends ActionSupport {
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