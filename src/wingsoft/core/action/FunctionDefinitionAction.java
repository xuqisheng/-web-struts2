package wingsoft.core.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import wingsoft.core.jq.*;
import wingsoft.core.wfdao.*;



public class FunctionDefinitionAction extends ActionSupport {

	static WindowDefDAO winDefDao = new WindowDefDAO();
	static QueryDefDAO queryDefDao = new QueryDefDAO();
	static FormDefDAO formDefDao = new FormDefDAO();
	static ChartDefDAO chartDefDao = new ChartDefDAO();
	static TreeDefDAO treeDefDao = new TreeDefDAO();
	static DrillDefDAO drillDefDao = new DrillDefDAO();
	static GEditorDefDAO geDefDao = new GEditorDefDAO();
	static PrintDefDAO printDefDao = new PrintDefDAO();
	static CustomDefDAO customDefDao = new CustomDefDAO();
	
	private static final long serialVersionUID = -1937241368479813451L;
	private InputStream responseText; // AJAX 请求响应文本流

	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}

	public InputStream getResponseText() {
		return responseText;
	}

	@SuppressWarnings("unchecked")
	public String loadDefinition() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String uid = ((HashMap<String, String>) (request.getSession().getAttribute("userContext"))).get("USERID");

		String funcNo = request.getParameter("funcNo");
		char funcType = request.getParameter("type").toString().charAt(0);

		String defJsonStr = "";

		switch (funcType) { // 请求的功能定义类型
		case 'W': // 功能窗口
			defJsonStr = this.getWindowsDef(funcNo, uid);
			break;
		case 'Q': // 通用查询
			defJsonStr = this.getQueryGridDef(funcNo, uid);
			break;
		case 'F': // 通用表单
			defJsonStr = this.getFormDef(funcNo, uid);
			break;
		case 'D': // 通用图表
			defJsonStr = this.getChartDef(funcNo, uid);
			break;
		case 'T': // 通用树
			defJsonStr = this.getTreeDef(funcNo, uid);
			break;
		case 'E'://通用数据钻取表
			defJsonStr = this.getDrillDef(funcNo, uid);
			break;
		case 'G'://表格编辑器
			defJsonStr = this.getGEditorDef(funcNo, uid);
			break;
		case 'P'://通用打印
			defJsonStr = this.getPrintDef(funcNo, uid);
			break;
		case 'C':
			defJsonStr = this.getCustomWinDef(funcNo, uid);
			break;
		default:
			defJsonStr = "";
			break;
		}

		this.setResponseText(new ByteArrayInputStream(defJsonStr
				.getBytes("utf-8")));

		return "textPlain";
	}

	@SuppressWarnings("unchecked")
	protected String getWindowsDef(String funcNo, String uid) {
		
		JqWindow jqWindow = new JqWindow();
		
		List winsDef = winDefDao.findWindowDef(funcNo);

		String jsonStr = jqWindow.generateWindowsJson(funcNo, winsDef);

		return jsonStr;
	}

	@SuppressWarnings("unchecked")
	protected String getQueryGridDef(String funcNo, String uid) {
		
		JqGrid jqQueryGrid = new JqGrid();
		
		HashMap<String, Object> queryDef = queryDefDao.findQueryDef(funcNo);

		List queryFields = queryDefDao.findQueryDetail(funcNo);

		String jsonStr = jqQueryGrid.generateGridJson(queryDef, queryFields);

		return jsonStr;
	}
	

	@SuppressWarnings("unchecked")
	protected String getGEditorDef(String funcNo, String uid) {
		
		JqGEditor jqGEditor = new JqGEditor();
		
		HashMap<String, Object> gDef = geDefDao.findGEditorDef(funcNo);

		List gFields = geDefDao.findGEditorDetail(funcNo);

		String jsonStr = jqGEditor.generateGEditorJson(gDef, gFields);

		return jsonStr;
	}
	
	@SuppressWarnings("unchecked")
	protected String getFormDef(String funcNo, String uid) throws Exception {
		
		JqForm jqForm = new JqForm();
		
		HashMap formDef = formDefDao.findFormDef(funcNo);

		List formDetail = formDefDao.findFormDetail(funcNo);

		String formJSONStr = jqForm.generateFormJson(formDef, formDetail);

		return formJSONStr;
	}
	
	@SuppressWarnings("unchecked")
	protected String getTreeDef(String funcNo, String uid) throws Exception {
		
		JqTree jqTree = new JqTree();
		
		HashMap treeDef = treeDefDao.findTreeDef(funcNo);

		List treeDetail = treeDefDao.findTreeDetail(funcNo);

		String treeJSONStr = jqTree.generateTreeJson(treeDef, treeDetail);

		return treeJSONStr;
	}
	
	protected String getChartDef(String funcNo, String uid) {

		JqChart jqChart = new JqChart();
		
		HashMap<String, Object> chartDef = chartDefDao.findFormDef(funcNo);

		String jsonStr = jqChart.generateChartJson(chartDef);

		return jsonStr;
	}
	
	@SuppressWarnings("unchecked")
	protected String getDrillDef(String funcNo, String uid) throws Exception {
		
		JqDrillTable jqDrill = new JqDrillTable();
		
		HashMap drillDef = drillDefDao.findDrillDef(funcNo);

		List drillsections = drillDefDao.findDrillSections(funcNo);
		
		List calItems = drillDefDao.findDrillCalItems(funcNo);
		
		String drillTableJson = jqDrill.generateDrillJson(drillDef, drillsections, calItems);

		return drillTableJson;
	}
	
	@SuppressWarnings("unchecked")
	protected String getPrintDef(String funcNo, String uid)throws Exception {
		JqPrint jqPrint = new JqPrint();
		HashMap printDef = printDefDao.findPrintDef(funcNo);
		String printJson = jqPrint.generatePrintJson(funcNo,printDef);
		return printJson;
	}
	
	@SuppressWarnings("unchecked")
	protected String getCustomWinDef(String funcNo, String uid)throws Exception {
		List customDetail = customDefDao.findCustomDetail(funcNo);
		
		HashMap<String, Object> customDef = customDefDao.findCustomDef(funcNo);
		JqCustomWin jqCw = new JqCustomWin();
		
		String customJson = jqCw.generateCustomWinJson(customDef , customDetail);;
		return customJson;
	}
}
