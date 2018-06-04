package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;


public class JqGEditor {
	
	protected String prjfields = "";
	protected String typeMap="";
	@SuppressWarnings("unchecked")
	public String generateGEditorJson(HashMap<String,Object> geDef,List geFields){
		prjfields = "";
		typeMap = "";
		StringBuffer jsonStr=new StringBuffer();
		String colNames=getColNames(geFields);
		String colModel=generateColModel(geFields);
		
		jsonStr.append("[{");
		
		jsonStr.append("funcno:\"" + CommonOperation.nTrim(geDef.get("FUNCNO"))+"\",");
		jsonStr.append("prjfields:\"" + prjfields + "\",");
		
		jsonStr.append("typeMap:"+typeMap+",");
		jsonStr.append("tablenames:\"" + CommonOperation.nTrim(geDef.get("TABLENAMES"))+"\",");
		jsonStr.append("joinconditions:\"" + CommonOperation.nTrim(geDef.get("JOINCONDITIONS"))+"\",");
		jsonStr.append("panelSearch:'" + CommonOperation.nTrim(geDef.get("PANELSEARCH"))+"',");
		jsonStr.append("rowNum:" + CommonOperation.nTrim(geDef.get("ROWNUMS"))+",");
		jsonStr.append("height:" + CommonOperation.nTrim(geDef.get("HEIGHT"))+",");
		jsonStr.append("width:" + CommonOperation.nTrim(geDef.get("WIDTH"))+",");
		jsonStr.append("sortname:\"" + CommonOperation.nTrim(geDef.get("SORTNAME"))+"\",");
		jsonStr.append("sortorder:\"" + CommonOperation.nTrim(geDef.get("SORTORDER"))+"\",");
		jsonStr.append("rownumbers:" + CommonOperation.nTrim(geDef.get("ROWNUMBERS"))+",");
		jsonStr.append("viewrecords:" + CommonOperation.nTrim(geDef.get("VIEWRECORDS"))+",");
		jsonStr.append("pginput:" + CommonOperation.nTrim(geDef.get("PGINPUT"))+",");
		jsonStr.append("hoverrows:" + CommonOperation.nTrim(geDef.get("HOVERROWS"))+",");
		jsonStr.append("refreshable:" + CommonOperation.nTrim(geDef.get("REFRESHABLE"))+",");
		jsonStr.append("reloadable:" + CommonOperation.nTrim(geDef.get("RELOADABLE")) + ",");
		jsonStr.append("searchable:" + CommonOperation.nTrim(geDef.get("SEARCHABLE"))+",");
		jsonStr.append("toolbar:[" + CommonOperation.nTrim(geDef.get("TOOLBAR"))+",\"top\"],");
		jsonStr.append("toolbarlayout:\"" + CommonOperation.nTrim(geDef.get("TOOLBARLAYOUT")) + "\",");
		jsonStr.append("selcol:" + CommonOperation.nTrim(geDef.get("SELCOL"))+",");
		jsonStr.append("sortable:" + CommonOperation.nTrim(geDef.get("SORTABLE"))+",");
		jsonStr.append("print:" + CommonOperation.nTrim(geDef.get("PRINT"))+",");
		jsonStr.append("stripe:" + CommonOperation.nTrim(geDef.get("STRIPE"))+",");
		jsonStr.append("editable:" + CommonOperation.nTrim(geDef.get("EDITABLE"))+",");
		jsonStr.append("delable:" + CommonOperation.nTrim(geDef.get("DELABLE"))+",");
		jsonStr.append("addable:" + CommonOperation.nTrim(geDef.get("ADDABLE"))+",");
		
		jsonStr.append("update_check:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("UPDATE_CHECK")))+"\",") ;
		jsonStr.append("update_proc:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("UPDATE_PROC")))+"\",") ;
		jsonStr.append("delete_check:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("DELETE_CHECK")))+"\",");
		jsonStr.append("delete_proc:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("DELETE_PROC")))+"\",");
		jsonStr.append("insert_check:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("INSERT_CHECK")))+"\",");
		jsonStr.append("insert_proc:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("INSERT_PROC")))+"\",");
		jsonStr.append("editCond:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(geDef.get("EDITCOND")))+"\",");
		jsonStr.append("multiselect:" + CommonOperation.nTrim(geDef.get("MULTISELECT"))+",");
		jsonStr.append("multifields:\"" + CommonOperation.nTrim(geDef.get("MULTIFIELDS"))+"\",");
		jsonStr.append("datatype:\"json\",");
		jsonStr.append("mtype:\"POST\",");
		jsonStr.append("colNames:"+colNames+",");
		jsonStr.append("colModel:"+colModel);
		jsonStr.append("}]");
		
		return jsonStr.toString();
	}
	
	/**获取各个字段在列中显示的名称（区别于数据库表中整正的字段名称）
	 * @param funcFields  功能字段定义的hash列表
	 * @return 形如[\"显示名1\",\"显示名2\",...]
	 */
	@SuppressWarnings("unchecked")
	public String getColNames(List geFields){
		
		StringBuffer colNames=new StringBuffer();
		
		colNames.append("[");
		
		int num=geFields.size();
		
		HashMap fieldset=null;
		
		for(int i=0;i<num;i++){
			
			fieldset=(HashMap<String,Object>)geFields.get(i);
			
			colNames.append("\""+CommonOperation.nTrim(fieldset.get("SHOWNAME"))+"\"");
			
			if(i!=num-1){
				
				colNames.append(",");
				
			}
		}
		
		colNames.append("]");
		
		return colNames.toString();
	}
	
	/**跟据所给的功能字段定义，生成符合前台页面能解析的字段模式字符串
	 * @param funcFields 功能字段定义的hash列表
	 * @return 符合格式的字符串
	 */
	@SuppressWarnings("unchecked")
	public String generateColModel(List geFields){
		
		StringBuffer colModelStr=new StringBuffer();
		
		colModelStr.append("[");
		
		int num=geFields.size();
		
		HashMap fieldset=null;
		
		for(int i=0;i<num;i++){
			
			fieldset=(HashMap<String,Object>)geFields.get(i);
			
			colModelStr.append("{");
			String exp = CommonOperation.nTrim(fieldset.get("EXP"));
			//字段名 等于表达式 或者别名.字段名
			String fname = exp.length()>0?exp:CommonOperation.nTrim(fieldset.get("ALIAS"))+"."
							  + CommonOperation.nTrim(fieldset.get("FIELDNAME"));
			
			typeMap +="\""+fname+"\":\""+CommonOperation.nTrim(fieldset.get("DATA_TYPE"))+"\"";
			prjfields += fname;
			
			colModelStr.append("name:\"" + fname + "\",");
			colModelStr.append("index:\"" +fname+"\",");
			colModelStr.append("datatype:\"" + CommonOperation.nTrim(fieldset.get("DATA_TYPE")) + "\",");
			colModelStr.append("searchable:" + CommonOperation.nTrim(fieldset.get("SEARCHABLE")) + ",");
			colModelStr.append("width:" + CommonOperation.nTrim(fieldset.get("WIDTH"))+",");
			colModelStr.append("align:\"" + CommonOperation.nTrim(fieldset.get("ALIGN"))+"\",");
			colModelStr.append("resizable:" + CommonOperation.nTrim(fieldset.get("RESIZABLE"))+",");
			colModelStr.append("sortable:" + CommonOperation.nTrim(fieldset.get("SORTABLE"))+",");
			colModelStr.append("formatter:\"" + CommonOperation.nTrim(fieldset.get("FORMATTER"))+"\",");
			colModelStr.append("editable:" + CommonOperation.nTrim(fieldset.get("EDITABLE"))+",");
			String edittype = CommonOperation.nTrim(fieldset.get("EDITTYPE"));
			if(!edittype.equals("text")){
				colModelStr.append("edittype:\"" + edittype+"\",");
				colModelStr.append("editoptions:{value:\"" + CommonOperation.nTrim(fieldset.get("EDITOPTIONS"))+"\"},");
			}else{
				colModelStr.append("editrules:"+CommonOperation.nTrim(fieldset.get("EDITRULES"))+",");
			}
			colModelStr.append("isshow:" + CommonOperation.nTrim(fieldset.get("ISSHOW")));
			colModelStr.append("}");
			
			if(i!=num-1){
				
				colModelStr.append(",");
				prjfields +=",";
				typeMap+=",";
			}
		}
		
		colModelStr.append("]");
		typeMap = "{" + typeMap + "}";
		return colModelStr.toString();
	}
	
}
