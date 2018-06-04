package wingsoft.core.jq;

import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;


public class JqWindow {

	@SuppressWarnings("unchecked")
	public String generateWindowsJson(String mainWinno,List windowsDefList){
		
		StringBuffer wins = new StringBuffer();
		StringBuffer funcMap = new StringBuffer();
		StringBuffer actMap = new StringBuffer();
		
		HashMap<String,Object> w = null;
		int c = windowsDefList.size();
		for(int i=0;i<c;i++){
			
			w=(HashMap<String,Object>)windowsDefList.get(i);
			wins.append("{");
			wins.append("winno:" + CommonOperation.nTrim(w.get("WINNO")) + ",");
			wins.append("win_type:" + CommonOperation.nTrim(w.get("WIN_TYPE")) + ",");
			
			wins.append("funcno:" + CommonOperation.nTrim(w.get("FUNCNO")) + ",");
			wins.append("func_type:\"" + CommonOperation.nTrim(w.get("FUNC_TYPE")) + "\",");
			wins.append("isshow:" + CommonOperation.nTrim(w.get("ISSHOW")) + ",");
			wins.append("frame:" + CommonOperation.nTrim(w.get("FRAME")) + ",");
			wins.append("init_title:\"" + CommonOperation.nTrim(w.get("INIT_TITLE")) + "\",");
			wins.append("title:\"" + CommonOperation.nTrim(w.get("TITLE")) + "\",");
			wins.append("c_url:\"" + CommonOperation.nTrim(w.get("C_URL")) + "\",");
			wins.append("height:" + CommonOperation.nTrim(w.get("HEIGHT")) + ",");
			wins.append("width:" + CommonOperation.nTrim(w.get("WIDTH")) + ",");
			wins.append("resizable:" + CommonOperation.nTrim(w.get("RESIZABLE")) + ",");
			wins.append("draggable:" + CommonOperation.nTrim(w.get("DRAGGABLE")) + ",");
			wins.append("maximizable:" + CommonOperation.nTrim(w.get("MAXIMIZABLE")) + ",");
			wins.append("x:" + CommonOperation.nTrim(w.get("X")) + ",");
			wins.append("y:" + CommonOperation.nTrim(w.get("Y")) + "," );
			
			wins.append("func_btns:" + CommonOperation.transferLineBreak(CommonOperation.nTrim(w.get("FUNC_BTNS"))));
			wins.append("}");
			
			funcMap.append(CommonOperation.nTrim(w.get("WINNO"))+":{func:"+CommonOperation.nTrim(w.get("FUNCNO"))
									+",type:\""+CommonOperation.nTrim(w.get("FUNC_TYPE"))
									+"\",url:\""+CommonOperation.nTrim(w.get("C_URL"))
									+"\",title:\""+CommonOperation.nTrim(w.get("TITLE"))+"\"}");
			actMap.append(CommonOperation.transferLineBreak("\""+CommonOperation.nTrim(w.get("FUNCNO"))+"\":\""+CommonOperation.nTrim(w.get("DEFAULT_ACTS"))+"\""));
			if(i!=c-1){
				wins.append(",");
				funcMap.append(",");
				actMap.append(",");
			} 
		}
		
		String pageJson = "{mainWin:"+mainWinno+",wins:["+wins+"],funcMap:{"+funcMap+"},actMap:{"+actMap+"}}";
		
		return pageJson;
	}
	
}
