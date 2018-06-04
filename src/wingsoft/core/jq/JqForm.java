package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;


import wingsoft.tool.common.CommonOperation;


public class JqForm {
	protected String typeMap="";
	protected String funcMap="";
	public String generateFormJson(HashMap<String,Object> formDef,List formDetail){
		
		StringBuffer jsonStr=new StringBuffer();
		typeMap = "";
		funcMap = "";
		jsonStr.append("[{");
		
		jsonStr.append("funcno:\"" +CommonOperation.nTrim(formDef.get("FUNCNO"))+"\",");
		jsonStr.append("title:\""+CommonOperation.nTrim(formDef.get("TITLE"))+"\",");
		jsonStr.append("bindSql:\""+CommonOperation.transferLineBreak(CommonOperation.nTrim(formDef.get("BINDSQL")))+"\",");
		jsonStr.append("keys:\""+CommonOperation.nTrim(formDef.get("KEYS"))+"\",");
		jsonStr.append("tablenames:\""+CommonOperation.nTrim(formDef.get("TABLENAMES"))+"\",");
		jsonStr.append("width:"+CommonOperation.nTrim(formDef.get("WIDTH"))+",");
		jsonStr.append("height:"+CommonOperation.nTrim(formDef.get("HEIGHT"))+",");
		jsonStr.append("toolbar:"+CommonOperation.nTrim(formDef.get("TOOLBAR"))+",");
		//html中含有大量需要转义的字符JSCHECKMODE
		jsonStr.append("formhtml:\""+CommonOperation.transfer(CommonOperation.nTrim(formDef.get("FORMHTML")))+"\",");
		jsonStr.append("formFields:"+CommonOperation.nTrim(generateFormDetail(formDetail))+",");
		jsonStr.append("typeMap:"+typeMap+",");
		jsonStr.append("funcMap:"+funcMap);
		jsonStr.append("}]");
		
		return jsonStr.toString();
	}
	
	@SuppressWarnings({ "unchecked" })
	public String generateFormDetail(List formDetail){
		StringBuffer jsonStr=new StringBuffer();
		HashMap<String,Object> field=null;
		int c=formDetail.size();
		
		jsonStr.append("[");
		for(int i=0;i<c;i++){
			field=(HashMap<String,Object>)formDetail.get(i);
			
			jsonStr.append("{");
			String tabname = CommonOperation.nTrim(field.get("ALIAS"));
			
			String fname = CommonOperation.nTrim(field.get("FIELDNAME"));
			typeMap +="\""+tabname+"."+fname+"\":\""+CommonOperation.nTrim(field.get("DATA_TYPE"))+"\"";
			funcMap +="\""+tabname+"."+fname+"\":\""+CommonOperation.nTrim(field.get("FUNC"))+"\"";
			
			jsonStr.append("id:\""+tabname+"-"+fname+"\",");
			jsonStr.append("fieldname:\""+tabname+"."+fname+"\",");
			jsonStr.append("label:\""+CommonOperation.nTrim(field.get("LABEL"))+"\",");
			jsonStr.append("datatype:\""+CommonOperation.nTrim(field.get("DATA_TYPE"))+"\",");
			jsonStr.append("initdata:\""+CommonOperation.nTrim(field.get("INITDATA"))+"\",");
			jsonStr.append("inputtype:\""+CommonOperation.nTrim(field.get("INPUTTYPE"))+"\",");
			jsonStr.append("binding_data:\""+CommonOperation.transferLineBreak(CommonOperation.nTrim(field.get("BINDING_DATA")))+"\",");
			jsonStr.append("width:\""+CommonOperation.nTrim(field.get("WIDTH"))+"\",");
			jsonStr.append("height:\""+CommonOperation.nTrim(field.get("HEIGHT"))+"\",");
			jsonStr.append("nullable:"+CommonOperation.nTrim(field.get("NULLABLE"))+",");
			jsonStr.append("readonly:"+CommonOperation.nTrim(field.get("READONLY"))+",");
			jsonStr.append("maxval:\""+CommonOperation.nTrim(field.get("MAXVAL"))+"\",");
			jsonStr.append("minval:\""+CommonOperation.nTrim(field.get("MINVAL"))+"\",");
			jsonStr.append("maxlen:\""+CommonOperation.nTrim(field.get("MAXLEN"))+"\",");
			jsonStr.append("minlen:\""+CommonOperation.nTrim(field.get("MINLEN"))+"\",");
			jsonStr.append("format:\""+CommonOperation.nTrim(field.get("FORMAT"))+"\",");
			jsonStr.append("align:\""+CommonOperation.nTrim(field.get("ALIGN"))+"\",");
			jsonStr.append("trig:\""+CommonOperation.nTrim(field.get("TRIG"))+"\"");
			jsonStr.append("}");
			
			if(i!=c-1){
				jsonStr.append(",");
				typeMap+=",";
				funcMap+=",";
			}
		}
		jsonStr.append("]");
		typeMap = "{"+typeMap+"}";
		funcMap = "{"+funcMap+"}";
		return jsonStr.toString();
	}
	
}
