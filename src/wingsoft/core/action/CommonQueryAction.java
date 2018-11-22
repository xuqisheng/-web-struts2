package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import wingsoft.core.command.CommandAuthorityController;
import wingsoft.core.command.CommandParser;
import wingsoft.core.command.QueryCommandExecutor;
import wingsoft.core.command.QueryCommand;


import com.opensymphony.xwork2.ActionSupport;

public class CommonQueryAction extends ActionSupport{

	private static final long serialVersionUID = 1318904013923899735L;

	static QueryCommandExecutor cmdExecutor = new QueryCommandExecutor();
	static CommandParser cmdParser = new CommandParser();
	static CommandAuthorityController caController = new CommandAuthorityController();
	private InputStream responseText;	//AJAX 请求响应文本流
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}
	public InputStream getResponseText() {
		return responseText;
	}

	public String doQuery()throws Exception{

		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = request.getSession();

		String funcno = request.getParameter("funcno");
		System.out.println("myFuncno:"+funcno);
		QueryCommand qCmd = encapsulateParamToCmd(request);

		cmdExecutor.setCommand(qCmd);

		try{
			String jsonResult = cmdExecutor.execute();

			this.setResponseText(new ByteArrayInputStream(jsonResult.getBytes("utf-8")));

			String sql = cmdExecutor.getSql(qCmd);

			String pagedSql = cmdExecutor.getPagedSql(qCmd);

			session.setAttribute(funcno+"_sql", sql);

			session.setAttribute(funcno+"_paged_sql", pagedSql);

		}catch(Exception e){

			e = new Exception("错误的SQL: " + cmdExecutor.getPagedSql(qCmd));
			throw e;
		}
		return "textPlain";

	}


	@SuppressWarnings("unchecked")
	private QueryCommand encapsulateParamToCmd(HttpServletRequest request) throws UnsupportedEncodingException{



		QueryCommand qCmd=new QueryCommand();

		qCmd.setPrjfields(request.getParameter("prjfields"));

		String joinCond = request.getParameter("joinconditions");
		/*
		if(joinCond!=null)
			joinCond = new String(joinCond.getBytes("ISO-8859-1"), "utf-8").trim();
		*/
		qCmd.setSql(request.getParameter("sql"));

		qCmd.setJoinconditions(joinCond);

		String selCond = request.getParameter("selconditions");

		if(selCond !=null )
			selCond = new String(selCond.getBytes("ISO-8859-1"), "GBK").trim();

		qCmd.setSelconditions(selCond);

		qCmd.setUserconditions(request.getParameter("userconditions"));

		qCmd.setGroupBy(request.getParameter("goupby"));

		String filterStr = request.getParameter("filters");

		if(filterStr!=null){
			String filterconditions =cmdParser.ConvertFiltersStrToConditions(filterStr);

			qCmd.setFilterconditions(filterconditions);
		}

		qCmd.setTablenames(request.getParameter("tablenames"));

		qCmd.setSortname(request.getParameter("sidx"));

		qCmd.setSortord(request.getParameter("sord"));

		qCmd.setOrdStr(request.getParameter("ordStr"));

		qCmd.setRows(Long.parseLong(request.getParameter("rows")));

		qCmd.setPage(Long.parseLong(request.getParameter("page")));

		HashMap uc = (HashMap<String,String>) request.getSession().getAttribute("userContext");

		qCmd.setUserid(uc.get("0-USERINFO.USERID").toString());

		//qCmd = caController.AddConstraint(qCmd, uc);

		return qCmd;

	}

}
