package wingsoft.tool.printer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import wingsoft.core.command.QueryDAO;

import com.opensymphony.xwork2.ActionSupport;

public class PrintAction extends ActionSupport{
	/**
	 * @author zh
	 */
	private static final long serialVersionUID = 1L;
	private InputStream responseText;	//AJAX 请求响应文本流
	FileInputStream inputStream;  	//输出到客户端的文件流
	//List data;  					//待打印的数据
	
	
	/*public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}*/
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText; 
	}
	public InputStream getResponseText() {
		return responseText;
	}
	public FileInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	//生成客户端传来的列中文名、英文名、类型、功能号
	@SuppressWarnings({ "unchecked" })
	private Setting generatePrintInfo(){
		HttpServletRequest request = ServletActionContext.getRequest (); 
		String json = (String)request.getParameter("set");
		
		Map classMap = new HashMap();
		classMap.put("p_type", String.class);
		classMap.put("title", String.class);
		classMap.put("pagesize", String.class);
		classMap.put("fontsize", String.class);
		classMap.put("title_bold", String.class);
		classMap.put("title_italic", String.class);
		classMap.put("t_border", String.class);
		classMap.put("scope", String.class);
		classMap.put("cols_cn", String.class);
		classMap.put("cols_en", String.class);
		classMap.put("types", String.class);
		classMap.put("funcno", String.class);
		Setting s = (Setting) JSONObject.toBean(JSONObject.fromObject(json),Setting.class,classMap);
		return s;
	}
	
	//从数据库中查出表中的数据
	@SuppressWarnings("unchecked")
	private List<HashMap> getData(String sql){
		QueryDAO qd=new QueryDAO();
		List data = new ArrayList();
		data=qd.queryHashDataForm(sql);
		return data;
	}
	
	//根据客户端选择打印当前页面或是全部数据
	@SuppressWarnings("unchecked")
	private List generatePrintData(String funcno,String scope){
		HttpServletRequest request = ServletActionContext.getRequest ();
		HttpSession session = request.getSession();
		String sql = "";
		if(scope.equals("curr")){
			sql = session.getAttribute(funcno+"_paged_sql").toString();
		}
		else{
			sql = session.getAttribute(funcno+"_sql").toString();
		}
		List data = new ArrayList();
		data=getData(sql);
		return data;
	}
	private String getFileName(int type,String title){
		String fileName="";
		fileName+=title;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd-hh-mm-ss"); 
		String nowdate=sdf.format(new java.util.Date());
		fileName+=nowdate;
		switch(type){
		case 0: fileName+=".pdf";
				break;
		case 1: fileName+=".doc";
				break;
		case 2: fileName+=".xls";
				break;
		default:break;
		}
		return fileName;
	}
	//选择word/pdf/excel打印
	@SuppressWarnings("unchecked")
	private void print(List data,Setting s) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();
//response.setCharacterEncoding("utf-8");
		OutputStream os = response.getOutputStream();//对客户端的响应流，可将生成的文件在浏览器中打开
		
		Printer p = null;
		String reType = "";
		String fileName = "";
		
		if(s.getP_type().equals("PDF")){
			reType = "apllication/pdf";
			p = new PdfPrinter();
			fileName = this.getFileName(0,s.getTitle());
		}else if(s.getP_type().equals("WORD")){
			reType = "apllication/msword";
			p = new WordPrinter();
			fileName = this.getFileName(1,s.getTitle());
		}else{
			reType = "apllication/msexcel";
			p = new ExcelPrinter();
			fileName = this.getFileName(2,s.getTitle());
		}
		response.setHeader("Content-Disposition", "filename="+URLEncoder.encode(fileName, "UTF-8"));
		response.setContentType(reType);
		try {
			p.print(data,s,os);
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		Setting s = generatePrintInfo();
		List data = generatePrintData(s.getFuncno(),s.getScope());
		print(data,s);
		return null;
	}

}
