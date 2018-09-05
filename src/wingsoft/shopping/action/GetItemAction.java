package wingsoft.shopping.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.dao.CommentsDAO;
import wingsoft.shopping.dao.ItemDAO;
import wingsoft.shopping.model.Item;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetItemAction extends BaseAction {

	/**
	 * Method execute
	 * 
	 * @param 'category
	 * @param 'form
	 * @param 'request
	 * @param 'response
	 * @return ActionForward
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public String execute() throws IOException, SQLException {
		System.out.println("GetItemAction started");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String category = request.getParameter("category");
		String parameters = request.getParameter("parameter"); 
		String values = request.getParameter("value");
		String keyword = request.getParameter("keyword");
		String page = request.getParameter("page");
		String Collection = request.getParameter("collection");
		Boolean IsCollection = false;  //是否为查询我的收藏
//		System.out.println("itemaction_keyword="+keyword);
//		System.out.println("itemaction_parameters="+parameters);
//		System.out.println("itemaction_values="+values);

		if (values!=null) {
			values = new String(values);
		}
		if (keyword!=null) {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
		}
		if (parameters==null||parameters.equals("null")) {
			parameters = null;
		}
		if (values==null||values.equals("null")) {
			values = null;
		}
		if (category==null||category.equals("null")) {
			category = "0";
		}
		if (page==null||page.equals("null")) {
			page = "1";
		}
		if (keyword==null||keyword.equals("null")) {
			keyword = "";
		} 
		if ("T".equals(Collection)) {
			IsCollection = true; 
		} 
		
		ItemDAO id = new ItemDAO();
		CommentsDAO cd = new CommentsDAO();
		String json = "[";
		List<Item> is = new ArrayList<Item>();
		is = id.selectCategory(category, parameters, values,keyword, 16, Integer.parseInt(page),IsCollection);
		System.out.println("list的结果 并且 list大小："+is.size());
		boolean flag = false;
		while (!is.isEmpty()) {
			Item i = is.remove(0);
			if (i.getParents()!=null) {
				i.setItemname(id.selectItem(i.getParents()).getItemname()+" - "+i.getItemname());
			}
			json+=i.toString();
			json = json.substring(0,json.length()-1);
			json+=",\"comment\":\""+cd.selectItemid(i.getItemid()).size()+"\"},";//评论
			flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		System.out.println("json="+json);
		json = json.replaceAll("null","");
		this.setJsonArray(JSONArray.fromObject(json));
//		System.out.println(getJsonArray().toString());
		System.out.println("GetItemAction ended");
		return SUCCESS;
	}
}