package wingsoft.core.flash;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;






import com.opensymphony.xwork2.ActionSupport;

public class FlashAction extends ActionSupport{
	
	//type indicates flash type
	private String json_param;
	
	

	private InputStream responseText;	//AJAX 请求响应文本流
	
	private String paging_to_show;
	
	public String getPaging_to_show() {
		return paging_to_show;
	}
	public void setPaging_to_show(String paging_to_show) {
		this.paging_to_show = paging_to_show;
		//System.out.println("set paging to show is " + paging_to_show);
	}
	public String getJson_param() {
		return json_param;
	}
	public void setJson_param(String json_param) {
		this.json_param = json_param;
		//System.out.println("set json_param is " + json_param);
	}
	
	
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}
	public InputStream getResponseText() {
		return responseText;
	}
	
	
	
	
	public String getFlashJson() throws UnsupportedEncodingException
	{
		try{
		FlashModel fm = new FlashModel();
		//HttpServletRequest request = ServletActionContext.getRequest();
		//System.out.println(request.getCharacterEncoding());
		//request.setCharacterEncoding("UTF-8");
		//String c = request.getParameter("json_param");
		
		//String s = Escape.unescape(json_param.getBytes("ISO-8859-1"));
		//String s = new String(json_param.getBytes("ISO-8859-1"));
		//String s = Escape.unescape(URLDecoder.decode(json_param, "ISO-8859-1"));
		
		String s = json_param;
		//String s = Escape.unescapeWithoutPercent(json_param);
		
		
		
		
		
		
		
		
		
		
		//System.out.println("decode then unescape is " + Escape.decodeThenUnescape(s));
		
		
		
		String chartXml = fm.createFlashJsonWrapper(Escape.decodeThenUnescape(s), Integer.parseInt(paging_to_show));
		
		
		
		//handle utf-8 bom
		//when we generate the flash, we need to set bom for utf-8 
		try {
			byte[] bom = new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF};
			byte[] chartXmlByte = chartXml.getBytes("utf-8");
			byte[] b = new byte[bom.length + chartXmlByte.length];
			
			
			for(int i = 0; i < bom.length; i++)
			{
				b[i] = (bom[i]);
			}
			
			for(int i = 0; i < chartXmlByte.length; i++)
			{
				b[i+3] = chartXmlByte[i];
			}
			
						
			setResponseText(new ByteArrayInputStream(b));
			//setResponseText(new ByteArrayInputStream(chartXml.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

}
