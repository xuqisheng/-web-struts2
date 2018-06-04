package wingsoft.core.statistic;
import java.util.HashMap;
import java.util.List;

import wingsoft.core.command.QueryDAO;
import wingsoft.tool.common.CommonOperation;
public class CodeMap {
	protected static QueryDAO qDao = new QueryDAO();
	
	public String getCodeMaps(String sections,String filter,String ord){
		String [] secs = sections.split(",");
		HashMap<String,String> fmap = new HashMap<String,String>();
		if(filter!=null && !filter.equals("")){
			String [] conds = filter.split("and");
			for(int i=0;i<conds.length;i++){
				String[] c = conds[i].split("in");
				fmap.put(c[0].trim(), c[1].trim());
			}
		}
		StringBuffer codemaps = new StringBuffer();
		codemaps.append("[");
		
		for(int i=0;i<secs.length;i++){
			codemaps.append(
					this.getCodeMap(secs[i],fmap, ord)
					+	(i<secs.length-1?",":"")
			);
		}
		codemaps.append("]");
		return codemaps.toString();
	}
	
	public String getCodeMap(String sectionName,HashMap<String,String> fmap,String ord){
		String sql = "SELECT SA_NAME FROM XCODEMAP WHERE TABLE_NAME = '"+sectionName.split("\\.")[0]+"' AND FIELD_NAME = '" +sectionName.split("\\.")[1] + "'";
		String f = fmap.get(sectionName);
		if(f!=null)
			sql += " AND SA_CODE IN"+f;
		sql += " ORDER BY SA_CODE " + ord;
		List cmList = qDao.queryDataForm(sql);
		StringBuffer cMap = new StringBuffer();
		cMap.append("[");
		int size = cmList.size()-1;
		int i = 0;
		for(; i<size;i++){
			Object [] objs = (Object[]) cmList.get(i);
			cMap.append("\""+CommonOperation.nTrimToShow(objs[0])+"\",");
		}
		Object [] objs = (Object[]) cmList.get(i);
		cMap.append("\""+CommonOperation.nTrimToShow(objs[0])+"\"");
		cMap.append("]");
		return cMap.toString();
	}
}