package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import wingsoft.core.jq.MenuTree;
import wingsoft.core.wfdao.MenuDefDAO;
import wingsoft.tool.common.CommonOperation;

import com.opensymphony.xwork2.ActionSupport;

public class MenuAction extends ActionSupport{
	private static final long serialVersionUID = 573713908432183666L;
	private InputStream responseText;	//AJAX 请求响应文本流
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText; 
	}
	public InputStream getResponseText() {
		return responseText;
	}
	@SuppressWarnings("unchecked")
	public String loadMenu()throws Exception{
		HttpSession session = ServletActionContext.getRequest().getSession();
		HashMap<String,String> userContext = (HashMap<String,String>)session.getAttribute("userContext");
		if(userContext == null){
			this.setResponseText(new ByteArrayInputStream("<a style='color:red'>未登录,非法请求</a>".getBytes("UTF-8")));
			return "textPlain";
		}
		String userContextStr = session.getAttribute("userContextStr").toString();
		String uid = userContext.get("0-USERINFO.USERID");
		MenuDefDAO menudefDao = new MenuDefDAO();
		List<HashMap> treeNodes = menudefDao.getUserMenuTree(uid);
		MenuTree mt = new MenuTree(treeNodes);
		String menuTreeHtml = mt.getMenuTreeHtml();
		String menuJS = "<script type=\"text/javascript\" src=\"core/menu.js\"></script>";
		String userInfo = "<input type=\"hidden\" id=\"userContext\" value=\""+userContextStr+"\"/>";
		this.setResponseText(new ByteArrayInputStream((menuTreeHtml + menuJS + userInfo).getBytes("UTF-8")));
		return "textPlain";
	}
	
	public String getAllLeafNode()throws Exception{
		List leafNodes = new MenuDefDAO().getAllLeaf();
		StringBuffer leavis = new StringBuffer() ;
		leavis.append("[");
		for(int i=0;i<leafNodes.size();i++){
			leavis.append("{");
			HashMap<String,Object> node = (HashMap<String,Object>)leafNodes.get(i);
			leavis.append("nodeid:" + CommonOperation.nTrim(node.get("NODEID"))+",");
			leavis.append("name:\"" + CommonOperation.nTrim(node.get("NODENAME")) +"\"" );
			leavis.append("}");
			if(i<leafNodes.size()-1){
				leavis.append(",");
			}
		}
		leavis.append("]");
		this.setResponseText(new ByteArrayInputStream((leavis.toString()).getBytes("UTF-8")));
		return "textPlain";
	}
}
