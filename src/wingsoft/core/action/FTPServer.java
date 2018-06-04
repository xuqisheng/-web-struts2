package wingsoft.core.action;

import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import wingsoft.core.start.WebInitServlet;
import wingsoft.core.wfdao.WFDefQueryDAO;
import wingsoft.tool.common.MyDes;

public class FTPServer extends ActionSupport
{
  static WFDefQueryDAO wfQueryDao = new WFDefQueryDAO();
  public static String ftpIP = "";
  public static String ftpUser = "";
  public static String ftpPwd = "";
  public static String port = "";
  private static final long serialVersionUID = -7315993163052045399L;
  private InputStream responseText;

  public void setResponseText(InputStream responseText)
  {
    this.responseText = responseText; }

  public InputStream getResponseText() {
    return this.responseText;
  }
  
  public String getFileNameSequence() throws UnsupportedEncodingException{
		String sql = "select sys_file_seq.nextval from dual";
		long seq = wfQueryDao.queryCountData(sql);
		System.out.println(seq);
		this.setResponseText(new ByteArrayInputStream((seq+"").getBytes("utf-8")));
		
		return "textPlain";
	}
  
  public String getLicense() throws UnsupportedEncodingException
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    String path = request.getParameter("path");
    if (path == null)
      path = "/";
    else {
      path = "/" + path;
    }

    String rtnXML = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
    rtnXML = rtnXML + "<ftpinfo>";
    rtnXML = rtnXML + "<fptIP>" + ftpIP + "</fptIP>";
    rtnXML = rtnXML + "<user>" + ftpUser + "</user>";
    rtnXML = rtnXML + "<passwd>" + ftpPwd + "</passwd>";
    rtnXML = rtnXML + "<port>" + port + "</port>";
    rtnXML = rtnXML + "<path>" + path + "</path>";
    rtnXML = rtnXML + "</ftpinfo>";
    try {
      rtnXML = new MyDes("FtpEncode").encrypt(rtnXML);
    }
    catch (Exception localException) {
      rtnXML = "";
    }
    setResponseText(new ByteArrayInputStream(rtnXML.getBytes(WebInitServlet.charset)));

    return "textPlain";
  }
}