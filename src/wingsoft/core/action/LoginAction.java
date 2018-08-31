package wingsoft.core.action;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;
import wingsoft.shopping.util.DBManager;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.common.MyDes;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

//重写的
public class LoginAction extends ActionSupport{
	private InputStream responseText;

	public InputStream getResponseText() {
		return responseText;
	}

	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}

	public String doLogin() throws Exception{
		System.out.println("dologin");
		HttpServletRequest request = ServletActionContext.getRequest();
		String uid = CommonOperation.nTrim(request.getParameter("uid"));
		String pwd = CommonOperation.nTrim(request.getParameter("pwd"));
		if(uid!=null && pwd !=null){
			if(isValidate(uid,pwd)){
				this.setResponseText(new ByteArrayInputStream("ok".getBytes()));
			}
			return "textPlain";
		}
		return "textPlain";
	}

	public boolean isValidate(String uid,String pwd) throws Exception{
		boolean isLegal = false;
		Connection coon =null;
//		ConnectionPool pool = ConnectionPoolManager.getPool("WFServer");
		Connection conn=null;
		PreparedStatement ps=null;
		System.out.println(uid);
		System.out.println(pwd);
		if(!CommonOperation.nTrim(pwd).equals("1")){
			pwd = MyDes.md5Encrypt(pwd)+"PV1";
		}
		System.out.println(uid);
		System.out.println(pwd);
		String sql ="select * from WF_USERINFO u where trim(u.userid)=? and trim(u.password)=?";
		conn =  DBManager.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1,uid);
		ps.setString(2,pwd);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			isLegal = true;
			initUserContext(uid,rs.getString("username"));
		}
//		pool.closeResultSet(rs);
//		pool.closePreparedStatement(ps);
//		pool.returnConnection(conn);
		conn.close();
		return isLegal;
	}

	protected void initUserContext(String uid,String username) throws SQLException{
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("userId",uid);
		session.setAttribute("userContextStr",uid);
		session.setAttribute("userName",username);
}

}
