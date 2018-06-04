package wingsoft.core.statistic;

import java.util.List;

import wingsoft.tool.common.CommonOperation;

public class StatisticResultFormatter {
	public String formatResultToJsonStr(List resultList,int scalar,boolean filterNullVal){

		if(resultList==null||resultList.size()==0){
			return "[]";
			
		}else{
			
			StringBuffer jsonStr = new StringBuffer();
			jsonStr.append("[");
			int size = resultList.size();
			int cnt =size/scalar;
			
			for(int i=0;i<cnt;i++){
				int j = i * scalar;
				int end = j + scalar;
				
				if(filterNullVal&&!this.isNotNull(resultList, j, end)){
					continue;
				}
				
				jsonStr.append( "[");
				Object[] o = (Object[]) resultList.get(j);
				jsonStr.append( "\"" + CommonOperation.nTrimToShow(o[0]) + "\",\"" + CommonOperation.nTrimToShow(o[1]) + "\"" );
				for(;j<end;j++){
					Object[] objs = (Object[]) resultList.get(j);
					for(int k=2;k<objs.length;k++)
						jsonStr.append(",\""+CommonOperation.nTrimToShow(objs[k])+"\"");
					
				}
				
				jsonStr.append( "]" );
				
				if(i!=size-1)
					jsonStr.append(",");
				
			}
			
			jsonStr.append("]");
			
			return jsonStr.toString();
		}
	}
	
	public String formatResultToJsonStr2(List resultList,int scalar,boolean filterNullVal){
		if(resultList==null||resultList.size()==0){
			return "[]";
		}else{
			StringBuffer jsonStr = new StringBuffer();
			jsonStr.append("[");
			int size = resultList.size();
			int cnt =size/scalar;
			for(int i=0;i<cnt;i++){
				int j = i * scalar;
				int end = j + scalar;
				
				if(filterNullVal&&!this.isNotNull(resultList, j, end)){
					continue;
				}
				
				StringBuffer tr = new StringBuffer();
				
				Object[] o = (Object[]) resultList.get(j);
				tr.append("\"<tr value='"+o[0].toString().trim()+"'>");
				tr.append( "<td class='fcell'><span class='ui-icon ui-icon-plus' style='float:left'></span>" + CommonOperation.nTrimToShow(o[1]) + "</td>");
				
				for(;j<end;j++){
					Object[] objs = (Object[]) resultList.get(j);
					for(int k=2;k<objs.length;k++)
						tr.append("<td class='cell'>"+CommonOperation.formatMoneyToShow(objs[k])+"</td>");
					
				}
				
				tr.append("</tr>\"");
				jsonStr.append(tr);
				if(i!=size-1)
					jsonStr.append(",");
			}
			
			jsonStr.append("]");
			
			return jsonStr.toString();
		}
	}
	
	private boolean isNotNull(List resultList,int start,int end){
		
		for(int i= start;i<end;i++){
			Object[] objs = (Object[]) resultList.get(i);
			for(int k=2;k<objs.length;k++)
				if(objs[k] !=null)
					return true;
		}
		return false;
	}
}
