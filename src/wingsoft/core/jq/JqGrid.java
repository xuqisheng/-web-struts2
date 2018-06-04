package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;


public class JqGrid {
	/**该函数根据从数据库查出来的页面定义配置列表生成
	 * 前台页面解析需要的格式的Json对象字符串,
	 * 配置表各定义的含义请查看数据库文档。
	 * @param queryDef	定义页面配置的hash列表
	 * @param funcFieldsConfig 定义各字段模式的列表
	 * @return json字符串
	 */
	protected String prjfields = "";
	protected String typeMap="";
	@SuppressWarnings("unchecked")
	public String generateGridJson(HashMap<String,Object> queryDef,List queryFields){
		prjfields = "";
		typeMap = "";
		StringBuffer jsonStr=new StringBuffer();
		String colNames=getColNames(queryFields);
		String colModel=generateColModel(queryFields);
		
		jsonStr.append("[{");
		
		jsonStr.append("funcno:\"" + CommonOperation.nTrim(queryDef.get("FUNCNO"))+"\",");
		jsonStr.append("prjfields:\"" + prjfields + "\",");
		jsonStr.append("typeMap:"+typeMap+",");
		jsonStr.append("sql:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(queryDef.get("COMPLEX_SQL"))+"\","));
		
		jsonStr.append("tablenames:\"" + CommonOperation.nTrim(queryDef.get("TABLENAMES"))+"\",");
		jsonStr.append("joinconditions:\"" + CommonOperation.nTrim(queryDef.get("JOINCONDITIONS"))+"\",");
		jsonStr.append("rowNum:" + CommonOperation.nTrim(queryDef.get("ROWNUMS"))+",");
		jsonStr.append("height:" + CommonOperation.nTrim(queryDef.get("HEIGHT"))+",");
		jsonStr.append("width:" + CommonOperation.nTrim(queryDef.get("WIDTH"))+",");
		jsonStr.append("sortname:\"" + CommonOperation.nTrim(queryDef.get("SORTNAME"))+"\",");
		jsonStr.append("sortorder:\"" + CommonOperation.nTrim(queryDef.get("SORTORDER"))+"\",");
		jsonStr.append("rownumbers:" + CommonOperation.nTrim(queryDef.get("ROWNUMBERS"))+",");
		jsonStr.append("viewrecords:" + CommonOperation.nTrim(queryDef.get("VIEWRECORDS"))+",");
		jsonStr.append("pginput:" + CommonOperation.nTrim(queryDef.get("PGINPUT"))+",");
		jsonStr.append("hoverrows:" + CommonOperation.nTrim(queryDef.get("HOVERROWS"))+",");
		jsonStr.append("refreshable:" + CommonOperation.nTrim(queryDef.get("REFRESHABLE"))+",");
		jsonStr.append("reloadable:" + CommonOperation.nTrim(queryDef.get("RELOADABLE")) + ",");
		jsonStr.append("searchable:" + CommonOperation.nTrim(queryDef.get("SEARCHABLE"))+",");
		jsonStr.append("panelSearch:'" + CommonOperation.nTrim(queryDef.get("PANELSEARCH")) + "',");
		jsonStr.append("toolbar:[" + CommonOperation.nTrim(queryDef.get("TOOLBAR"))+",\"top\"],");
		jsonStr.append("toolbarlayout:\"" + CommonOperation.nTrim(queryDef.get("TOOLBARLAYOUT")) + "\",");
		jsonStr.append("selcol:" + CommonOperation.nTrim(queryDef.get("SELCOL"))+",");
		jsonStr.append("sortable:" + CommonOperation.nTrim(queryDef.get("SORTABLE"))+",");
		jsonStr.append("print:" + CommonOperation.nTrim(queryDef.get("PRINT"))+",");
		jsonStr.append("stripe:" + CommonOperation.nTrim(queryDef.get("STRIPE"))+",");
		jsonStr.append("multiselect:" + CommonOperation.nTrim(queryDef.get("MULTISELECT"))+",");
		jsonStr.append("multifields:\"" + CommonOperation.nTrim(queryDef.get("MULTIFIELDS"))+"\",");
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
	public String getColNames(List queryFields){
		
		StringBuffer colNames=new StringBuffer();
		
		colNames.append("[");
		
		int num=queryFields.size();
		
		HashMap fieldset=null;
		
		for(int i=0;i<num;i++){
			
			fieldset=(HashMap<String,Object>)queryFields.get(i);
			
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
	public String generateColModel(List queryFields){
		
		StringBuffer colModelStr=new StringBuffer();
		
		colModelStr.append("[");
		
		int num=queryFields.size();
		
		HashMap fieldset=null;
		
		for(int i=0;i<num;i++){
			
			fieldset=(HashMap<String,Object>)queryFields.get(i);
			
			colModelStr.append("{");
			
			String exp = CommonOperation.nTrim(fieldset.get("EXP"));
			
			//字段名 等于表达式 或者别名.字段名
			String fname = exp.length()>0?exp:CommonOperation.nTrim(fieldset.get("ALIAS"))+"."
							  + CommonOperation.nTrim(fieldset.get("FIELDNAME"));
			
			typeMap +="\""+fname+"\":\""+CommonOperation.nTrim(fieldset.get("DATA_TYPE"))+"\"";
			prjfields += fname;
			
			colModelStr.append("name:\"" + fname + "\",");
			colModelStr.append("index:\"" +fname + "\",");
			colModelStr.append("datatype:\"" + CommonOperation.nTrim(fieldset.get("DATA_TYPE")) + "\",");
			colModelStr.append("width:" + CommonOperation.nTrim(fieldset.get("WIDTH"))+",");
			colModelStr.append("align:\"" + CommonOperation.nTrim(fieldset.get("ALIGN"))+"\",");
			colModelStr.append("resizable:" + CommonOperation.nTrim(fieldset.get("RESIZABLE"))+",");
			colModelStr.append("sortable:" + CommonOperation.nTrim(fieldset.get("SORTABLE"))+",");
			colModelStr.append("formatter:\"" + CommonOperation.nTrim(fieldset.get("FORMATTER"))+"\",");
			colModelStr.append("searchable:" + CommonOperation.nTrim(fieldset.get("SEARCHABLE")) + ",");
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
