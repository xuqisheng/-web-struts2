package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

public class JqCustomWin {
	@SuppressWarnings("unchecked")
	public String generateCustomWinJson(HashMap<String,Object> customWinDef,List customDetail){
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("[{");
		jsonStr.append("funcno:" + CommonOperation.nTrim(customWinDef.get("FUNCNO")) + "," );
		jsonStr.append("url:'" + CommonOperation.nTrim(customWinDef.get("FUNC_URL")) + "'," );
		jsonStr.append("vars:[" );
		int c = customDetail.size();
		for(int i=0; i<c; i++){
			HashMap<String,Object> hm = (HashMap<String,Object>)customDetail.get(i);
			jsonStr.append("{");
			jsonStr.append("varname:'" + CommonOperation.nTrim( hm.get("VARNAME") ) + "',");
			jsonStr.append("mapname:'" + CommonOperation.nTrim( hm.get("MAPNAME") ) + "',");
			jsonStr.append("type:'" + CommonOperation.nTrim( hm.get("DATATYPE") ) + "'");
			jsonStr.append("}");
			if(i<c-1){
				jsonStr.append(",");
			}
		}
		jsonStr.append("]" );
		jsonStr.append("}]");
		return jsonStr.toString();
	}
}
