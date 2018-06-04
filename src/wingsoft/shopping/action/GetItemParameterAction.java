package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.ItemparaDAO;
import wingsoft.shopping.dao.ParameterDAO;
import wingsoft.shopping.model.Itempara;
import wingsoft.shopping.model.Parameter;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetItemParameterAction extends ActionSupport {
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
		
		ItemparaDAO id = new ItemparaDAO();
		List<Itempara> is = id.selectItem(itemid);
		ParameterDAO pd = new ParameterDAO();
		
		String json = "[";
		boolean flag = false;
		while (!is.isEmpty()) {
			Itempara i = is.remove(0);
			Parameter p = pd.selectParameter(i.getParameterid());
			json+="{\"name\":\""+p.getParametername()+"\",\"value\":\""+i.getValue()+"\"},";
			flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("html/text");
		PrintWriter out=null;
		System.out.println("parameterAction="+json);
		out=response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		
		return null;
	}
}