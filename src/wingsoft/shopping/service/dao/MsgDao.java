package wingsoft.shopping.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class MsgDao {
	/**获取通知的基本信息**/
	public String GetMsgInfo(){ 
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest(); 
		try {
		
		String Sql = "select t.id,t.keyword,t.name,t.typeid from msg t where t.state='I' order by t.msgdate desc";
	
		 json = "["; 
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{mid:'"+Comm.nTrim(rs.getString("id"))+
						"',keyword:'"+Comm.nTrim(rs.getString("keyword"))+
						"',mname:'"+Comm.nTrim(rs.getString("name"))+ 
						"',typeid:'"+Comm.nTrim(rs.getString("typeid"))+ 
						"'},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		
		
		
		json+="]";
		
		
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
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
			pool.returnConnection(conn);
		}
		return json;
		
	}	
	
	/**获取通知的内容**/
	public String GetMsg(){ 
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest(); 
		String mid = request.getParameter("mid");
		try {
		
		String Sql = "select name,msginfo from msg t where t.id="+mid;
	
		 json = ""; 
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery(); 
		while (rs.next()) {
				json+=Comm.nTrim(rs.getString("name"))+"#@#@"+Comm.nTrim(rs.getString("msginfo")); 
		}
		
		
		
		
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
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
			pool.returnConnection(conn);
		}
		return json;
		
	}	
	
	
}
