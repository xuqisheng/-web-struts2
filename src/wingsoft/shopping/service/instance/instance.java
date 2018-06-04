package wingsoft.shopping.service.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wingsoft.shopping.model.Item;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class instance {
	public static String CreateInstance(String ORDERCODE,String Userid,String UserName,String ABST,Connection conn){ 
		System.out.println(conn); 
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String Sql = ""; 
		String NODE_GEID = "";
		String WF_NAME = "";
		String UNI_KEY = "";
		String NODE_NAME = "";
		String NODE_ID = "";
		String TONODE_ID = "";
		String ROLE_ID = "";
		String ROLEUSER = "";
		String res = "";
		
		try { 
			/**首先获取起点NODE_GEID**/
			
			Sql = " select   T.*  from NEWWF_NODE_DETAIL T WHERE T.WF_NAME='T_ORDER' and NODE_NAME='起点活动'";
			System.out.println(Sql);
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery(); 
			while (rs.next()) {
				NODE_GEID = rs.getString("NODE_GEID");
				NODE_ID = rs.getString("NODE_ID");
				ROLE_ID = rs.getString("ROLE_ID");
				WF_NAME = rs.getString("WF_NAME");
				NODE_NAME = rs.getString("NODE_NAME");
				
			}
			/**根据流程起点，获取分支条件**/
			Sql = " select n.condition,to_node_id from NEWWF_EDGE_DETAIL n where n.from_node_id='"+NODE_GEID+"' and wf_name='T_ORDER'";
			System.out.println(Sql);
			String condition = "";
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery(); 
			int vc = 0;
			while (rs.next()) {
				condition = rs.getString("condition");
				System.out.println("condition="+condition);
				/**分支条件处理  2018-04-18**/
				condition = condition.replace("&lt;", "<");
				condition = condition.replace("&gt;", ">");
				System.out.println("condition="+condition);
				/**end**/
				/**循环判断分支条件,如果满足，获取下一节点的node_id**/
				Sql = "select count(1) c from t_order o where o.ordercode='"+ORDERCODE+"' and  "+condition.replace("&#39;", "'");
				System.out.println(Sql);
				ps = conn.prepareStatement(Sql);
				rs1 = ps.executeQuery(); 
				if (rs1.next()) {
					vc = rs1.getInt("c");
				
				if (vc==1) {
					TONODE_ID = rs.getString("TO_NODE_ID");
					break;
				}
				}
			}
			
			if ("".equals(TONODE_ID)) {
				res = "获取分支信息失败"; 
			} else{
			/**获取到下一流程节点NODE_ID后，按照条件选中下一步审核人**/	
				Sql = " select   T.*  from NEWWF_NODE_DETAIL T WHERE T.WF_NAME='T_ORDER'  and node_geid='"+TONODE_ID+"'";
				System.out.println(Sql);
				ROLEUSER = "";
				ps = conn.prepareStatement(Sql);
				rs = ps.executeQuery(); 
				if (rs.next()) {
					ROLEUSER = rs.getString("roleuser"); 
				}
				/**按照获取到自定义流程函数，获取下一步审核人**/
				if (!"".equals(ROLEUSER)) {
					try{
					String callString = ROLEUSER.replace("&#39;", "'").replace("!BASETABLE_KEY!", ORDERCODE).replace(":@", " ");
					Sql = " select "+callString+" roleuser from dual";
					ROLEUSER = "";
					ps = conn.prepareStatement(Sql);
					rs = ps.executeQuery(); 
					if (rs.next()) {
						ROLEUSER = rs.getString("roleuser");
						System.out.println("ROLEUSER="+ROLEUSER);
					}
					
					}catch(Exception e){
						e.printStackTrace();
						ROLEUSER = "err";
					}
				}
				/**获取项目负责人作为下一步审核人  2018-03-28 白雪**/
				/*Sql = "select pro_code from t_order o where o.ordercode='"+ORDERCODE+"'";
				System.out.println(Sql);
				ROLEUSER = "";
				ps = conn.prepareStatement(Sql);
				rs = ps.executeQuery(); 
				if (rs.next()) {
					String pro_code = rs.getString("pro_code"); 
					Sql = "select t.proacc from v_user_procode t where t.procode = '"+pro_code+"' and t.useracc ="+Userid;
					System.out.println(Sql);
					ps = conn.prepareStatement(Sql);
					rs = ps.executeQuery();
					if(rs.next()){
						ROLEUSER = rs.getString("proacc");
					}
					System.out.println("项目负责人："+ROLEUSER);
				}*/
				
				/**end**/
			if ("err".equals(ROLEUSER)||"".equals(ROLEUSER)||ROLEUSER=="") {
				res = "获取下一步审核人失败";
			}else{	
			Sql = "select sys_wfinst_seq.nextval UNI_KEY from dual";

			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery(); 
			while (rs.next()) {
				UNI_KEY = rs.getString("UNI_KEY"); 
			}
			
			
			
			Sql = "INSERT INTO  NEWWF_INSTANCE" +
					"(BUSINESS_ID,PROJECT_ID,WF_NAME,UNI_KEY,BASETABLE_KEY,DISPLAY_NAME,NODE_ID," +
					"OP_COMMENT,OPERATOR,OP_NAME,OP_DATE,FROM_NODE_ID,START_NODE_ID,CREATOR,CREATE_DATE) " +
					" values('SHOP','WF_NYD','"+WF_NAME+"','"+UNI_KEY+"','"+ORDERCODE+"','"+WF_NAME+"','"+TONODE_ID+
					"','申请人提交申请',trim('"+Userid+"'),trim('"+UserName+"'),sysdate,'*START*',trim('"+NODE_ID+"'),trim('"+UserName+"'),SYSDATE)"; 
			System.out.println("NEWWF_INSTANCE="+Sql);
			ps = conn.prepareStatement(Sql);
			ps.execute(); 
			if (!"".equals(ROLEUSER)) {
				//查询用户权限
				Sql = "select t.role_id from O_USER_ROLE t where t.user_id='"+ROLEUSER+"'";
				ps = conn.prepareStatement(Sql);
				rs = ps.executeQuery();
				while (rs.next()) { 

					Sql = "INSERT INTO  NEWWF_INSTANCE_USER" +
						"(BUSINESS_ID,PROJECT_ID,UNI_KEY,NODE_ID,ROLE_ID,USER_ID," +
						"COLLAB_ORD,COLLAB_COMMENT,COLLAB_YESNO) " +
						" values('SHOP','WF_NYD','"+UNI_KEY+"','"+TONODE_ID+"',trim('"+rs.getString("role_id")+"'),trim('"+ROLEUSER+
						"'),'none','','')"; 
						System.out.println("NEWWF_INSTANCE_USER="+Sql);
						ps = conn.prepareStatement(Sql);
						ps.execute(); 
					
				}
				
			}
			
			
			Sql = "INSERT INTO  NEWWF_INSTANCE_HISTORY" +
					"(BUSINESS_ID,PROJECT_ID,WF_NAME,UNI_KEY,BASETABLE_KEY,DISPLAY_NAME,ABST,NODE_ID," +
					"OP_COMMENT,OPERATOR,OP_NAME,OP_DATE,DONE_DATE,FROM_NODE_ID,START_NODE_ID,CREATOR,CREATE_DATE) " +
					" values('SHOP','NYD','"+WF_NAME+"','"+UNI_KEY+"','"+ORDERCODE+"','"+NODE_NAME+"',trim('"+ABST+"'),'"+NODE_GEID+
					"','申请人提交申请',trim('"+Userid+"'),trim('"+UserName+"'),sysdate,sysdate,'*START*','"+NODE_ID+"',trim('"+UserName+"'),SYSDATE)"; 
			System.out.println("NEWWF_INSTANCE_HISTORY="+Sql);
			ps = conn.prepareStatement(Sql);
			ps.execute(); 
			res = "ok";
			}
			}
			} catch (Exception e) {
				e.printStackTrace();
				res = e.getMessage();
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					if (ps != null)
						ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		
		
		
		return res;
		
		
	}
}
