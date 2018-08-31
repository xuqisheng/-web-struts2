package wingsoft.shopping.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CommentsDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.dao.UsersDAO;
import wingsoft.shopping.model.Comments;
import wingsoft.shopping.model.Item;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetItemCommentAction extends BaseAction {

	/*
	 * Generated Methods
	 */
	/**
	 * @return ActionForward
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public String execute() throws IOException, SQLException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String item = request.getParameter("itemid");
		
		CommentsDAO cd = new CommentsDAO();
		ItemDAO id = new ItemDAO();
		String json = "[";
		List<Comments> cs = cd.selectItemid(item);
		UsersDAO ud = new UsersDAO();
		
		boolean flag = false;
		while (!cs.isEmpty()) {
			Comments c = cs.remove(0);
			String u = ud.selectUser(c.getUserid());
			if (c.getAnonymous()==1) {
				u = u.charAt(0)+"***"+u.charAt(u.length()-1);
			}
			Item i =id.selectItem(c.getItemid());
			json+="{\"name\":\""+u+"\",\"time\":\""+c.getTime()+"\",\"type\":\""+i.getItemname()+"\",\"comment\":\""+c.getCommenttxt()+"\"},";
			flag = true;
		}
		
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		setJsonArray(JSONArray.fromObject(json));

		return SUCCESS;
	}
}