package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

public class JqTree {
	
	protected String typeMap="";
	@SuppressWarnings("unchecked")
	public String generateTreeJson(HashMap<String,Object> treeDef,List treeLevelDef){
		StringBuffer jsonStr = new StringBuffer();
		typeMap = "";
		String treeLevels = this.generateTreeDetail(treeLevelDef);
		jsonStr.append("[{");
		jsonStr.append("funcno:" + CommonOperation.nTrim(treeDef.get("FUNCNO")) + ",");
		jsonStr.append("typeMap:" + typeMap + ",");
		jsonStr.append("folder_icon:" + CommonOperation.nTrim(treeDef.get("FOLDER_ICON")) + ",");
		jsonStr.append("leaf_icon:\"" + CommonOperation.nTrim(treeDef.get("LEAF_ICON")) + "\",");
		jsonStr.append("exp_speed:\"" + CommonOperation.nTrim(treeDef.get("EXP_SPEED")) + "\",");
		jsonStr.append("exp_unique:" + CommonOperation.nTrim(treeDef.get("EXP_UNIQUE")) + ",");
		jsonStr.append("showpath:" + CommonOperation.nTrim(treeDef.get("SHOWPATH")) + ",");
		jsonStr.append("none:\"" + CommonOperation.nTrim(treeDef.get("NONE")) + "\",");
		jsonStr.append("levels:" + treeLevels+",");
		jsonStr.append("leaf_level:" + (treeLevelDef.size()-1));
		jsonStr.append("}]");
		return jsonStr.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String generateTreeDetail(List treeLevels){
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("[");
		int c = treeLevels.size();
		HashMap<String,Object> level ;
		for(int i=0; i<c; i++){
			level = (HashMap<String, Object>) treeLevels.get(i);
			typeMap +="\""+CommonOperation.nTrim(level.get("KEY_NAME"))+"\":\""+CommonOperation.nTrim(level.get("KEY_TYPE"))+"\""
					+",\""+CommonOperation.nTrim(level.get("VAL_NAME"))+"\":\""+CommonOperation.nTrim(level.get("VAL_TYPE"))+"\"";
			jsonStr.append("{");
			jsonStr.append("key_name:\"" + CommonOperation.nTrim(level.get("KEY_NAME")) + "\",");
			jsonStr.append("val_name:\"" + CommonOperation.nTrim(level.get("VAL_NAME")) + "\",");
			jsonStr.append("bind_data:\"" + CommonOperation.transferLineBreak(CommonOperation.nTrim(level.get("BIND_DATA"))) + "\"");
			jsonStr.append("}");
			if(i<c-1){
				jsonStr.append(",");
				typeMap += ",";
			}
		}
		typeMap = "{"+typeMap+"}";
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
