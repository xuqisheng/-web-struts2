package wingsoft.core.action;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport implements
		
ServletContextAware, ServletRequestAware {

	private File doc;
	private String fileName;
	private String contentType;
	private ServletContext context;

	private HttpServletRequest request;
	private InputStream responseText;	//AJAX 请求响应文本流
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText; 
	}
	public InputStream getResponseText() {
		return responseText;
	}
	public String execute() throws Exception {
		String targetDirectory = this.context.getRealPath("/file");
		String[] names = ((MultiPartRequestWrapper)this.request).getFileNames("doc");
		File target = new File(targetDirectory, names[0]);
		FileUtils.copyFile(doc, target);
		this.setResponseText(new ByteArrayInputStream("ok".getBytes("utf-8")));
		return "textPlain";
	}

	public void setServletContext(ServletContext context) {
		this.context = context;

	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}