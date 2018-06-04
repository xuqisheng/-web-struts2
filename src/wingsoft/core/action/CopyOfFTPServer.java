package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import wingsoft.core.wfdao.WFDefQueryDAO;
public class CopyOfFTPServer extends ActionSupport{
	static WFDefQueryDAO wfQueryDao =new WFDefQueryDAO();
	public static String ftpIP = "127.0.0.1";
	public static String ftpUser = "ftpz";
	public static String ftpPwd = "ftpz";
	public static String port = "21";
	
	private static final long serialVersionUID = -7315993163052045399L;
	private InputStream responseText;	
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText; 
	}
	public InputStream getResponseText() {
		return responseText;
	}
	public String getLicense() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getParameter("path");
		if(path == null){
			path = "/";
		}else{
			path = "/"+path;
		}
		
		String rtnXML = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		rtnXML+="<ftpinfo>";
		rtnXML+="<fptIP>"+ftpIP+"</fptIP>";
		rtnXML+="<user>"+ftpUser+"</user>";
		rtnXML+="<passwd>"+ftpPwd+"</passwd>"; 
		rtnXML+="<port>"+port+"</port>";
		rtnXML+="<path>"+path+"</path>";
		rtnXML+="</ftpinfo>";
		this.setResponseText(new ByteArrayInputStream(rtnXML.getBytes("utf-8")));
		
		return "textPlain";
	}
	public String getFileNameSequence() throws UnsupportedEncodingException{
		String sql = "select sys_file_seq.nextval from dual";
		long seq = wfQueryDao.queryCountData(sql);
		this.setResponseText(new ByteArrayInputStream((seq+"").getBytes("utf-8")));
		
		return "textPlain";
	}
	
}
