package wingsoft.core.command;

import java.util.List;

import wingsoft.tool.common.CommonOperation;


/**专门构建生成客户端grid能解析的json数据的类
 * @author ZYZ
 *
 */
public class QueryResultFormatter {

	
	/**将查询结果传换成前台grid能够解析的json字符串
	 * @param page	当前查询结构所在的页号
	 * @param rownum 每一页能呈现的最大记录数
	 * @param records 总记录数
	 * @param resultList 查询结果列表
	 * @param primaryKeyList 该结果集的主键列表
	 * @return json	字符串
	 */
	@SuppressWarnings("unchecked")
	public String formatResultToJsonStr(long page,long rownum,long records,
			List resultList){

		if(resultList==null||resultList.size()==0){
			
			return "{total:0,page:0,records:0,rows:[]}";
			
		}else{
			
			long totalPages=records/rownum+1;
			
			String jsonStr="{total:"+totalPages+",page:"+page+",records:"+records+",rows:[";
			
			for(int i=0;i<resultList.size();i++){
				
				jsonStr+="{id:\""+i+"\",cell:["; 
				
				Object[] objs=(Object[]) resultList.get(i);
				
				for(int j=1;j<objs.length;j++){	//从第1列开始 而不是0列 是因为查询结果第0列为 序号
					
					jsonStr+="\""+CommonOperation.nTrimToShow(objs[j]).replaceAll("\"", "“")+"\"";
					
					if(j!=objs.length-1){
						
						jsonStr+=",";
						
					}
					
				}
				
				jsonStr+="]}";
				
				if(i!=resultList.size()-1){
					
					jsonStr+=",";
					
				}
			}
			
			jsonStr+="]}";
			
			return jsonStr;
		}
	}

	
}
