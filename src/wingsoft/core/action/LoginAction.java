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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import wingsoft.core.wfdao.UserAuthorityDAO;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.common.ImageGenerator;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 2280012109022069288L;
	static Logger LOGGER = Logger.getLogger(LoginAction.class);
	static UserAuthorityDAO ua = new UserAuthorityDAO();
	private InputStream responseText;	//AJAX 请求响应文本流
	private InputStream picStream;
	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}
	public InputStream getResponseText() {
		return responseText;
	}
	public void setPicStream(InputStream picStream) {
		this.picStream = picStream;
	}
	public InputStream getPicStream() {
		return picStream;
	}
	public String getCheckCodeImg()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		ImageGenerator imageGenerator = new ImageGenerator();
        BufferedImage image = imageGenerator.createImage();
        String randString = imageGenerator.getRandString();
        request.getSession().setAttribute("checkcode", randString);
        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        ImageIO.write(image, "JPG",bos);
        this.setPicStream(new ByteArrayInputStream(bos.toByteArray()));
        return "img";
	}


	public String doLogin() throws Exception{
		System.out.println("doLogindoLogindoLogindoLogindoLogindoLogin");
		HttpServletRequest request = ServletActionContext.getRequest();
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		String chkcode = request.getParameter("chkcode");
		String bdate = CommonOperation.nTrim(request.getParameter("bdate"));
		String ip = request.getRemoteAddr();
		String checkcode = request.getSession().getAttribute("checkcode").toString();
		if((uid == null) || (pwd == null) ||(chkcode == null)|| (uid == "") || (pwd == "")||(chkcode == "")){
			this.setResponseText(new ByteArrayInputStream("null".getBytes()));
			return "textPlain";
		}
		if(!chkcode.toUpperCase().equals(checkcode)){
			this.setResponseText(new ByteArrayInputStream("checkerror".getBytes()));
			return "textPlain";
		}
		try{
			if(isValidate(uid,pwd,bdate)){
				registUserLogin(uid,ip);
				this.setResponseText(new ByteArrayInputStream("ok".getBytes()));
				LOGGER.info(uid+" 登录系统---------------"+new Date().toString());
			}else{
				this.setResponseText(new ByteArrayInputStream("illegal".getBytes()));
			}

		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}

		return "textPlain";
	}

	public String isValidate()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		ConnectionPool pool = ConnectionPoolManager.getPool("WFServer");
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="select MODBDATE from SYS_USERINFO u where trim(u.USERID) = ? and trim(u.PASSWORD) = ?";

		try{
			conn = pool.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,uid);
			ps.setString(2,pwd);

			ResultSet u = ps.executeQuery();
			if(u.next()){
				String id = CommonOperation.nTrim(u.getString(1));
				if(id.equals("Y")){
					this.setResponseText(new ByteArrayInputStream("super".getBytes()));
				}else{
					this.setResponseText(new ByteArrayInputStream("normal".getBytes()));
				}
			}else{
				this.setResponseText(new ByteArrayInputStream("false".getBytes()));;
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ps.close();
			pool.returnConnection(conn);
		}

		return "textPlain";
	}

	protected boolean isValidate(String uid,String pwd,String bdate)throws Exception{

		boolean isLegal=false;
		ConnectionPool pool = ConnectionPoolManager.getPool("WFServer");
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="select * from WF_USERINFO u where trim(u.USERID) = ? and trim(u.PASSWORD) = ?";
		System.out.println("login "+sql);
		try{
			conn = pool.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,uid);
			ps.setString(2,pwd);

			ResultSet userInfo = ps.executeQuery();
			if(userInfo.next()){
				initUserContext(uid,userInfo,bdate);
				isLegal = true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ps.close();
			pool.returnConnection(conn);
		}

		return isLegal;
	}

	protected void registUserLogin(String uid,String ip) throws Exception{
		ConnectionPool pool = ConnectionPoolManager.getPool("WFServer");
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="insert into SYS_USER_LOG (USERID,LOGINTIME,LOGINIP) values(?,?,?)";

		try{
			conn = pool.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,uid);
			ps.setDate(2,new java.sql.Date(new Date().getTime()));
			ps.setString(3,ip);
			ps.executeUpdate();
			conn.commit();

		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw e;

		}finally{
			ps.close();
			pool.returnConnection(conn);
		}
	}

	/**初始化用户环境变量（上下文）,并将变量保存在session中
	 * _@param userInfo	用户基本信息
	 * _@throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected void initUserContext(String uid,ResultSet userInfo,String bdate) throws SQLException{

		//声明存储用户上下文的哈希列表
		HashMap<String,String> userContext=new HashMap<String,String>();
		//存放到客户端的用户上下文字符串。
		String userContextStr = "[{";

		//将用户基本信息填写到上下文中
		ResultSetMetaData rsmd=userInfo.getMetaData();
		int c=rsmd.getColumnCount();
		int i;
		String colName;
		String value;
		for(i = 1; i<=c; i++){
			colName = rsmd.getColumnName(i);
			value = CommonOperation.nTrim(userInfo.getString(i));
			userContext.put("0-USERINFO."+colName, value);
			userContextStr += "_@0-USERINFO."+colName + "_@:_@" + value + "_@,";
		}



		List urc = ua.findUserRoleTableCond(uid);
		for(i=0; i<urc.size();i++){
			HashMap hm = (HashMap<String,Object>) urc.get(i);
			userContextStr += "_@0-" + CommonOperation.nTrim(hm.get("ALIAS"))+"."+"COND_@:_@"+CommonOperation.nTrim(hm.get("FILTERS"))+"_@,";
		}

		//将用户登录的时间信息保存到上下文中
		Date today=new Date();
		String loginTime = CommonOperation.formatDate(today, "yyyy-MM-dd HH:mm:ss");
		String y = loginTime.substring(0,4);
		String m = loginTime.substring(5,7);
		String d = loginTime.substring(8,10);
		String tday = loginTime.substring(0,10);
		userContextStr += "_@0-CURRENTYEAR_@:_@" + y + "_@,"
						+ "_@0-CURRENTMONTH_@:_@" + m + "_@,"
						+ "_@0-CURRENTDAY_@:_@" + d + "_@,"
						+ "_@0-CURRENTYM_@:_@" + y + m + "_@,"
						+ "_@0-CURRENTYMD_@:_@" + y + m + d +"_@,"
						+ "_@0-TODAY_@:_@" + tday +"_@,"
						+ "_@0-LOGINTIME_@:_@" + loginTime + "_@,"
						+ "_@0-BYEAR_@:_@"+bdate.substring(0,4)+"_@,"
						+ "_@0-BDATE_@:_@"+bdate+"_@";
		userContextStr +="}]";
		//将初始化好的上下文存入session
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("userContext");
		session.removeAttribute("userContextStr");
		session.setAttribute("userContext", userContext);
		session.setAttribute("userContextStr", userContextStr);
	}
}

