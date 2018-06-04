package wingsoft.shopping.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.opensymphony.xwork2.ActionSupport;

import wingsoft.tool.common.Md5Tools;

import wingsoft.shopping.model.Item;
import wingsoft.shopping.model.Itempara;
import wingsoft.shopping.service.dao.ShopDao;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class ShopAction extends ActionSupport{
	private String json = "";
	
	
	
	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}

	

   public String test1(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
	   json = "ok"; 
		try {
			request.getRequestDispatcher("cashier.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
   }	
	
	/**
	 * 获取订单信息
	 * **/
	public String GetOrders(){
		ShopDao sd = new ShopDao();
		json = sd.GetOrders();
		return "success";
		
	}	
	
	
	/**收藏商品**/
	public String Collection(){
		ShopDao sd = new ShopDao();
		json = sd.Collection();
		return "success";
		
	}
	
	/**
	 * 获取根据SHP_ORDERS的orderid获取关联所有订单
	 * **/
	public String GetOrdersByORDERID(){
		ShopDao sd = new ShopDao();
		json = sd.GetOrdersByORDERID();
		return "success";
		
	}
	/**获取供应商信息**/
	public String GetSuppliers(){
		ShopDao sd = new ShopDao();
		json = sd.GetSuppliers();
		return "success";
		
	}
	
	/**保存自购材料**/
	public String SaveProducts(){
		System.out.println("a");
		ShopDao sd = new ShopDao();
		json = sd.SaveProducts();
		return "success";
		
	}
	/**获取之前自购材料**/
	public String Getproduct_s(){
		ShopDao sd = new ShopDao();
		json = sd.Getproduct_s();
		return "success";
		
	}
	
	/**删除已保存自购材料**/
	public String Delproduct_s(){
		ShopDao sd = new ShopDao();
		json = sd.Delproduct_s();
		return "success";
		
	}
	/**获取热门搜索**/

	public String hot_search(){
		ShopDao sd = new ShopDao();
		json = sd.hot_search();
		return "success";
		
	}
	
	/**获取商品分类**/
	public String CATEGORY(){
		ShopDao sd = new ShopDao();
		json = sd.CATEGORY();
		return "success";
		
	}
	/**求购信息反馈**/
	public String WantReq(){
		ShopDao sd = new ShopDao();
		json = sd.WantReq();
		return "success";
		
	}
	
	
	/**获取当前用户历史代付人员**/
	public String GET_OT_PAY(){
		ShopDao sd = new ShopDao();
		json = sd.GET_OT_PAY();
		return "success";
		
	}
	
	
	
	/**获取历史自购供应商**/
	public String SupplierS(){
		ShopDao sd = new ShopDao();
		json = sd.SupplierS();
		return "success";
		
	}
	
	/**保存自购订单**/
	public String SaveOrder_S(){
		ShopDao sd = new ShopDao();
		json = sd.SaveOrder_S();
		return "success";
		
	}
	
	public String WantFor(){
		ShopDao sd = new ShopDao();
		json = sd.WantFor();
		return "success"; 
		
	}
	
	/**保存订单，提交订单**/
	public String SaveOrder(){
		ShopDao sd = new ShopDao();
		json = sd.SaveOrder();
		return "success";
		
		}
	
	/**快速购买**/
	public String QuickBuy(){
		ShopDao sd = new ShopDao();
		json = sd.QuickBuy();
		return "success";
		
		}
	
	
	/**获取用户所属课题组**/
	public String GetTGROUP(){
		ShopDao sd = new ShopDao();
		json = sd.GetTGROUP();
		return "success";
		
	}
	
	/**获取广告**/
	public String GetRECOMMEND(){
		ShopDao sd = new ShopDao();
		json = sd.GetRECOMMEND();
		return "success";
		
	}
	
	/**获取管制品申请单**/
	public String GetREQUISITION(){
		ShopDao sd = new ShopDao();
		json = sd.GetREQUISITION();
		return "success";
		
		}
	
	
	/**获取求购信息**/
	public String GetWantFor(){

		ShopDao sd = new ShopDao();
		json = sd.GetWantFor();
		return "success";
		
	}
	
	/**获取同售供应商**/
	public String GetSamSale(){

		ShopDao sd = new ShopDao();
		json = sd.GetSamSale();
		return "success";
		
	}
	
	/**关闭求购**/
	public String Close_WantFor(){

		ShopDao sd = new ShopDao();
		json = sd.Close_WantFor();
		return "success";
		
	}
	/**忽略供应商求购反馈**/
	public String ignore(){

		ShopDao sd = new ShopDao();
		json = sd.Close_WantFor();
		return "success";
		
	}
	
	
	
	
	public String IsLogin(){
		System.out.println("检查用户是否登录");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
			json = "ok";
		}else{
			System.out.println("未登录");
			json = "err";
			
		}
		
		
		return "success";
		
	}

	public String CheckUser(String Userid, String Pwd){
		
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		String Res = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		Integer l = 0;
		try {
			conn = pool.getConnection();
			sql = "select count(*) b from T_USER where  account=? and password=? ";	
			ps = conn.prepareStatement(sql);
			ps.setString(1, Userid);
			ps.setString(2, Pwd);
			rs = ps.executeQuery();
			if (rs.next()) {
				l = rs.getInt("b");
			}
			if (l==0) {
				Res = "用户名密码错误";
			}
			else{
				Res = "OK";
			}
			

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
		return Res;
	}

	
	public String GetAddres(){
		System.out.println("获取收获地址");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		/**如果groupcode 存在，，就取课题组的注册地址**/
		String groupcode = request.getParameter("groupcode");
		String Sql = "select '0' locationid ,a.name,a.phone,a.address,a.isdef from address a where a.user_acc='"+userid+"' order by isdef desc";
		System.out.println(Sql);
		try {
		List<Item> is = new ArrayList<Item>();
		 json = "[";
		if (groupcode==""||"".equals(groupcode)||groupcode=="0"||"0".equals(groupcode)) {
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			//ps.setString(1, userid);
			rs = ps.executeQuery();
			
		}else{
			Sql = "select locationid,name,tele phone,trim(building)||trim(msg) address,DECODE(ROWNUM,1,'T','F')  isdef from LOCATION t where t.groupcode= '"+groupcode+"' and t.state=1";
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			//ps.setString(1, userid);
			rs = ps.executeQuery();
		
		}
		boolean flag = false;
		while (rs.next()) {
				json+="{locationid:'"+Comm.nTrim(rs.getString("locationid"))+"',name:'"+Comm.nTrim(rs.getString("name"))+"',phone:'"+Comm.nTrim(rs.getString("phone"))+
						"',address:'"+Comm.nTrim(rs.getString("address"))+"',isdef:'"+Comm.nTrim(rs.getString("isdef"))+"'},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		
		
		System.out.println("地址="+json);
		
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("html/text");
//		PrintWriter out=null;
//	
//		out=response.getWriter();
//		out.print(json);
//		out.flush();
//		out.close();
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
		return "success";
		
	}

	




/**获取选中结算材料**/
public String GetSettleCart(){

	ShopDao sd = new ShopDao();
	json = sd.GetSettleCart();
	return "success";
	
}


public String GetCart(){

	ShopDao sd = new ShopDao();
	json = sd.GetCart();
	return "success";
	
}

public String GetProInfo(){
	System.out.println("获取项目信息");
	ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
	System.out.println(pool);
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String Sql = "";
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	String userid = "";
	if (request.getSession().getAttribute("userId")!=null) {
		userid = (String) request.getSession().getAttribute("userId");
	}else{
		System.out.println("未登录");
		
	}
	Sql =    "select t.useracc,t.procode,t.proname,t.NAME,T.haveprice from V_USER_PROCODE t "+
			    " where   T.USERACC='"+userid+"'";
	System.out.println(Sql);
	try {
	List<Item> is = new ArrayList<Item>();
	 json = "[";
	
	conn = pool.getConnection();	
	ps = conn.prepareStatement(Sql);
	//ps.setString(1, userid);
	rs = ps.executeQuery();
	boolean flag = false;
	while (rs.next()) {
			json+="{useracc:'"+Comm.nTrim(rs.getString("useracc"))+
					"',procode:'"+Comm.nTrim(rs.getString("procode"))+
					"',proname:'"+Comm.nTrim(rs.getString("proname"))+
					"',name:'"+Comm.nTrim(rs.getString("NAME"))+
					"',haveprice:'"+Comm.nTrim(rs.getString("haveprice"))+
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
	return "success";
	
}



public String SaveAddress(){
	System.out.println("新增收获地址");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
	System.out.println(pool);
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String GetName = request.getParameter("newuser");
	String GetAddress = request.getParameter("newaddress");
	String GetPhone = request.getParameter("newphone");
	String Sql = "";
	String userid = "";
	if (request.getSession().getAttribute("userId")!=null) {
		userid = (String) request.getSession().getAttribute("userId");
	}else{
		System.out.println("未登录");
		
	}
	Sql =    "insert into ADDRESS(ID,USER_ACC,NAME,PHONE,ADDRESS)"+
			" values(SEQ_ADDRESS_ID.nextval,?,?,?,?)";
	System.out.println(Sql);
	try {
	
	conn = pool.getConnection();	
	ps = conn.prepareStatement(Sql);
	ps.setString(1, userid);
	ps.setString(2, GetName);
	ps.setString(3, GetPhone);
	ps.setString(4, GetAddress);
	ps.execute();
	json = "ok";
	
	
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
	return "success";
	
}


public String AddressDef(){
	System.out.println("修改地址为默认");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
	System.out.println(pool);
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String AddressId = request.getParameter("addressid");
	String Sql = "";
	String userid = "";
	if (request.getSession().getAttribute("userId")!=null) {
		userid = (String) request.getSession().getAttribute("userId");
	}else{
		System.out.println("未登录");
		
	}
	Sql =    "update ADDRESS set isdef=true where  ID=? and USER_ACC=?";
	System.out.println(Sql);
	try {
	
	conn = pool.getConnection();	
	ps = conn.prepareStatement(Sql);
	ps.setString(1, AddressId);
	ps.setString(2, userid);
	ps.execute();
	json = "ok";
	
	
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
	return "success";
	
}



public String supplier(){
	System.out.println("获取供应商信息");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
	System.out.println(pool);
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String supplierid = request.getParameter("supplierid");
	String Sql = "";
	 
	Sql =    "select * from SUPPLIER where id=rpad(?,128,' ')";
	System.out.println(Sql);
	try {
	
	conn = pool.getConnection();	
	ps = conn.prepareStatement(Sql);
	ps.setString(1, supplierid);
	rs = ps.executeQuery();
	json = "[";
	boolean flag = false;
	while (rs.next()) {
			json+="{name:'"+Comm.nTrim(rs.getString("name"))+
					"',telephone:'"+Comm.nTrim(rs.getString("telephone"))+
					"',address:'"+Comm.nTrim(rs.getString("address"))+
					"',contact:'"+Comm.nTrim(rs.getString("contact"))+
					"',qq:'"+Comm.nTrim(rs.getString("qq"))+
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
	return "success";
	
}




	public static void main(String[] args) throws DocumentException {
//		 SAXReader reader = new SAXReader();
//		  Document document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"utf-8\"?><error><type>2</type><msg>1</msg></error>");
//		  Element root = document.getRootElement();
//
//		  Iterator it = root.elementIterator("error");
//		  System.out.println(it.next());
//		  while (it.hasNext()) {
//		   Element element = (Element) it.next();
//
//		 
//		   System.out.println("id: " + element.attributeValue("type"));
//
//
//		   System.out.println();
//		  }
		
//		Itempara i = new Itempara();
//		i.setValue("a");
//		i.setItemid("a");
//		System.out.println(i.toString());
	
		String a = "1234";
		//System.out.println(a.substring(1));
		System.out.println(a.substring(0,a.length()-1));
		String b= "a";
		if ("a"=="a"&&"a".equals("b")) {
			System.out.println("a");
		}
		}
	

	
  
	
}
