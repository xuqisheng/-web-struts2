package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

public class JqDrillTable {
	public String generateDrillJson(HashMap<String,Object> drilldef,List sections, List items){
		
		 StringBuffer jsonStr = new StringBuffer();
		 jsonStr.append("[{");
		 jsonStr.append("funcno:" + CommonOperation.nTrim(drilldef.get("FUNCNO")) +",");
		 jsonStr.append("codeMap:'" + CommonOperation.nTrim(drilldef.get("CODE_MAP")) +"',");
		 jsonStr.append("tableNames:'" + CommonOperation.nTrim(drilldef.get("TABLE_NAMES")) +"',");
		 jsonStr.append("joinCond:'" + CommonOperation.nTrim(drilldef.get("JOIN_COND")) +"',");
		 jsonStr.append("filter:'',");
		 jsonStr.append("treeWidth:" + CommonOperation.nTrim(drilldef.get("TREE_WIDTH")) +",");
		 jsonStr.append("cellWidth:" + CommonOperation.nTrim(drilldef.get("CELL_WIDTH")) +",");
		 jsonStr.append("indent:" + CommonOperation.nTrim(drilldef.get("INDENT")) +",");
		 jsonStr.append("hover:" + CommonOperation.nTrim(drilldef.get("HOVER")) +",");
		 jsonStr.append("numbers:" + CommonOperation.nTrim(drilldef.get("NUMBERS")) +",");
		 jsonStr.append("prefix:\"" + CommonOperation.nTrim(drilldef.get("PREFIX")) +"\",");
		 jsonStr.append("thousands:\"" + CommonOperation.nTrim(drilldef.get("THOUSANDS")) +"\",");
		 jsonStr.append("secChangable:" + CommonOperation.nTrim(drilldef.get("SEC_CHANGABLE")) +",");
		 jsonStr.append("sections:" +this.generateSections(sections)+",");
		 jsonStr.append("calItems:"+this.generateCalItems(items));
		 jsonStr.append("}]");
		return jsonStr.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String generateSections(List secs){
		StringBuffer sections = new StringBuffer();
		
		sections.append("[");
		for(int i=0; i<secs.size(); i++){
			HashMap<String,Object> sec = (HashMap<String, Object>) secs.get(i);
			sections.append("{");
			sections.append("secField:\""+CommonOperation.nTrim(sec.get("SEC_FIELD"))+"\",");
			sections.append("secName:\""+CommonOperation.nTrim(sec.get("SEC_NAME"))+"\",");
			sections.append("secMode:\""+CommonOperation.nTrim(sec.get("SEC_MODE"))+"\",");
			sections.append("enabled:"+CommonOperation.nTrim(sec.get("ENABLED")));
			sections.append("}");
			sections.append(i<secs.size()-1?",":"");
		}
		
		sections.append("]");
		return sections.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String generateCalItems(List items){
		
		StringBuffer calItems = new StringBuffer();
		calItems.append("[");
		
		for(int i=0; i<items.size(); i++){
			HashMap<String,Object> item = (HashMap<String, Object>) items.get(i);
			calItems.append("{");
			calItems.append("itemName:\""+CommonOperation.nTrim(item.get("ITEM_NAME"))+"\",");
			calItems.append("formula:\""+CommonOperation.nTrim(item.get("FORMULA"))+"\",");
			calItems.append("unit:\""+CommonOperation.nTrim(item.get("UNIT"))+"\",");
			calItems.append("enabled:"+CommonOperation.nTrim(item.get("ENABLED"))+"");
			calItems.append("}");
			calItems.append(i<items.size()-1?",":"");
		}
		
		calItems.append("]");
		
		return calItems.toString();
	}
}
