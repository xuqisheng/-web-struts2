package wingsoft.core.wfdao;

import java.util.List;

public class WindowDefDAO extends WFDefQueryDAO{
	
	
@SuppressWarnings("unchecked")
public List findWindowDef(String winno){
		//根据主窗口查出其所拥有的副窗口集合
		String sql1 = "select attach_wins from SYS_WINDOW_DEF where winno = "+winno;
		String attachWins = this.queryScalar(sql1);
		String wins = "";
		if(attachWins != null && !attachWins.trim().equals("")){
			wins = "in ("+attachWins.trim()+","+winno+")";
		}else{
			wins = "= " + winno;
		}
		String sql2 ="select * from SYS_WINDOW_DEF where winno  "+wins;
		
		return this.queryHashDataForm(sql2);
	}
}
