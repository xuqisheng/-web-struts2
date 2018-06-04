package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import wingsoft.core.statistic.CodeMap;
import wingsoft.core.statistic.StatisticCommand;
import wingsoft.core.statistic.StatisticCommandExecutor;

import com.opensymphony.xwork2.ActionSupport;

public class StatisticAction extends ActionSupport{
	private static final long serialVersionUID = 8478769528702619781L;
	static CodeMap codeMap = new CodeMap();
	static StatisticCommandExecutor scExecutor= new StatisticCommandExecutor();
	private InputStream responseText;	
	
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText; 
	}
	public InputStream getResponseText() {
		return responseText;
	}

	public String doStatistic()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		StatisticCommand sCmd = new StatisticCommand();
		sCmd.setCodeMap(request.getParameter("codeMap"));
		sCmd.setSections(request.getParameter("sections"));
		sCmd.setFormulaItems(request.getParameter("items"));
		sCmd.setTables(request.getParameter("tables"));
		sCmd.setJoinCond(request.getParameter("joinCond"));
		sCmd.setSelCond(request.getParameter("selCond"));
		sCmd.setFilter(request.getParameter("filter"));
		sCmd.setOrder(request.getParameter("order"));
		sCmd.setScalar(request.getParameter("scalar"));
		this.setResponseText(new ByteArrayInputStream(scExecutor.execute(sCmd).getBytes("utf-8")));
		
		return "textPlain";
	}
	
	public String getCodeMaps() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String sections = request.getParameter("sections");
		String filter = request.getParameter("filter");
		String ord = request.getParameter("order");
		this.setResponseText(new ByteArrayInputStream(codeMap.getCodeMaps(sections,filter, ord).getBytes("utf-8")));
		return "textPlain";
	}
	
	public String getSQL() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		StatisticCommand sCmd = new StatisticCommand();
		sCmd.setCodeMap(request.getParameter("codeMap"));
		sCmd.setSections(request.getParameter("sections"));
		sCmd.setFormulaItems(request.getParameter("items"));
		sCmd.setTables(request.getParameter("tables"));
		sCmd.setJoinCond(request.getParameter("joinCond"));
		sCmd.setSelCond(request.getParameter("selCond"));
		sCmd.setFilter(request.getParameter("filter"));
		sCmd.setOrder(request.getParameter("order"));
		//sCmd.setScalar(request.getParameter("scalar"));
		this.setResponseText(new ByteArrayInputStream(scExecutor.generateSQL(sCmd).getBytes("utf-8")));
		
		return "textPlain";
	}
}
