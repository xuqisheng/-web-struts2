package wingsoft.core.statistic;
import java.util.HashMap;

import wingsoft.core.command.QueryDAO;
public class StatisticCommandExecutor {
	protected static StatisticResultFormatter srf = new StatisticResultFormatter();
	protected static QueryDAO qDao = new QueryDAO();
	
	public String execute(StatisticCommand scmd){
		return srf.formatResultToJsonStr2(qDao.queryDataForm(this.generateSQL(scmd)),
				scmd.getScalar(),true);
	}
	
	public String generateSQL(StatisticCommand scmd){
		
		String A = this.generateTableASQL(scmd);
		
		String B = this.generateTableBSQL(scmd.getCodeMap(), scmd.getSections(),scmd.filterMap);
		
		String SQL = "SELECT CODE0,NAME0,"+this.getAlias(scmd.getFormulaItems())
						+ " FROM "+A+","+B;
		
		String COND = "";
		
		String ORD = "";
		
		String [] sections = scmd.getSections().split(",");
		
		String outerJoin = sections.length>1?"(+)":"";
		
		for(int i=0; i<sections.length; i++){
			COND += "CODE"+i+" = A."+sections[i].split("\\.")[1]+outerJoin;
			ORD +="CODE"+i;
			if(i<sections.length-1){
				COND += " AND ";
				ORD +=",";
			}
		}
		
		return SQL + " WHERE " + COND +" ORDER BY " + ORD;
	}
	
	private String generateTableASQL(StatisticCommand scmd){
		
		String sql = "";
		
		sql = "SELECT " + scmd.getSections() + "," + scmd.getFormulaItems()
		
			 + " FROM " + scmd.getTables() +	" WHERE 1 = 1" ;
		
		if( scmd.getJoinCond()  != null
			&& !scmd.getJoinCond().equals("")
		){
			sql += " AND " + scmd.getJoinCond(); 
		}
	
		if( scmd.getSelCond()  != null
			&& !scmd.getSelCond().equals("")
		){
			sql += " AND " + scmd.getSelCond(); 
		}
		
		if( scmd.getFilter()  != null
				&& !scmd.getFilter().equals("")
			){
				sql += " AND " + scmd.getFilter(); 
			}
		
		sql +=" GROUP BY " + scmd.getSections();
		
		
		return "( " + sql + " ) A";
	}
	
	private String generateTableBSQL(String codeMap,String sections,HashMap<String,String>fMap){
		String codemaps = "";
		String prjs = "";
		String [] secs = sections.split(",");
		
		for(int i=0; i<secs.length;i++){
			String S = "S" + i;
			prjs += S + ".SA_CODE CODE"+i+"," + S + ".SA_NAME NAME"+i;
			String[] tf = secs[i].split("\\.");
			codemaps += "( SELECT * FROM " + codeMap +" WHERE FIELD_NAME = '" + tf[1] + "' AND TABLE_NAME = '"+tf[0]+"'";
			String f = fMap.get(secs[i]);
			if(f!=null)
				codemaps += " AND SA_CODE IN " + f;
			codemaps +=" ) " + S;
			if(i<secs.length-1){
				prjs += ",";
				codemaps += ",";
			}
		}
		
		String sql = "SELECT "+prjs+" FROM " + codemaps ;
		return "( " + sql + " ) B";
	}
	
	private String getAlias( String items ){
		String [] its = items.split(",");
		String alias = "";
		for(int i=0; i<its.length;i++)
			alias += its[i].split(" ")[1] + (i<its.length-1?",":"");
		
		return alias;
	}
}
