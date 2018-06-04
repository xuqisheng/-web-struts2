package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import wingsoft.core.command.*;

import com.opensymphony.xwork2.ActionSupport;
public class CommonUpdateAction extends ActionSupport{

	private static final long serialVersionUID = -8544607504975379538L;

	static QueryDAO cbQueryDao = new QueryDAO();
	static CommandParser cmdParser = new CommandParser();
	static UpdateCommandExecutor cmdExecutor = new UpdateCommandExecutor();
	private InputStream responseText;	//AJAX 请求响应文本流
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}
	public InputStream getResponseText() {
		return responseText;
	}	

	public String responseButtonEvent() throws Exception{
		   
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cmdStr = request.getParameter("cmd");
		
		UpdateCommand cmd = cmdParser.convertStrToCommonCmd(cmdStr);
		
		String exeResult = cmdExecutor.execute(cmd);
	
		this.setResponseText(new ByteArrayInputStream(exeResult.getBytes("utf-8")));
		 
		return "textPlain";
	} 
}
