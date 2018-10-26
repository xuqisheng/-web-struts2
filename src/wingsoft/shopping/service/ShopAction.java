package wingsoft.shopping.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import wingsoft.custom.BaseAction;
import wingsoft.shopping.model.Item;
import wingsoft.shopping.service.dao.ShopDao;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class ShopAction extends BaseAction {


	
	/**
	 * 获取订单信息
	 * **/
	public String GetOrders(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetOrders()));
		setJsonObject(jo);
		return OBJECT;
		
	}	
	
	
	/**收藏商品**/
	public String Collection(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.Collection());
		setJsonObject(jo);
		return OBJECT;
		
	}
	
	/**
	 * 获取根据SHP_ORDERS的orderid获取关联所有订单
	 * **/
	public String GetOrdersByORDERID(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetOrdersByORDERID()));
		setJsonObject(jo);
		return OBJECT;
	}
	/**获取供应商信息**/
	public String GetSuppliers(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetSuppliers()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**保存自购材料**/
	public String SaveProducts(){
		System.out.println("a");
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.SaveProducts());
		setJsonObject(jo);
		return OBJECT;
		
	}
	/**获取之前自购材料**/
	public String Getproduct_s(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.Getproduct_s()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**删除已保存自购材料**/
	public String Delproduct_s(){
		ShopDao sd = new ShopDao();
		//
		JSONObject jo = new JSONObject();
		jo.put("json",sd.SaveProducts());
		setJsonObject(jo);
		return OBJECT;
		
	}
	/**获取热门搜索**/

	public String hot_search(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.hot_search()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**获取商品分类**/
	public String CATEGORY(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.CATEGORY()));
		setJsonObject(jo);
		return OBJECT;
	}
	/**求购信息反馈**/
	public String WantReq(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.WantReq()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	
	/**获取当前用户历史代付人员**/
	public String GET_OT_PAY(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GET_OT_PAY()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	
	
	/**获取历史自购供应商**/
	public String SupplierS(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.SupplierS()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**保存自购订单**/
	public String SaveOrder_S(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.SaveOrder_S());
		setJsonObject(jo);
		return OBJECT;
		
	}
	
	public String WantFor(){
		ShopDao sd = new ShopDao();
		//
		JSONObject jo = new JSONObject();
		jo.put("json",sd.WantFor());
		setJsonObject(jo);
		return OBJECT;
		
	}
	
	/**保存订单，提交订单**/
	public String SaveOrder(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.SaveOrder());
		setJsonObject(jo);
		return OBJECT;
		
		}
	
	/**快速购买**/
	public String QuickBuy(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.QuickBuy());
		setJsonObject(jo);
		return OBJECT;
		
		}
	
	
	/**获取用户所属课题组**/
	public String GetTGROUP(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetTGROUP()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**获取广告**/
	public String GetRECOMMEND(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetRECOMMEND()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**获取管制品申请单**/
	public String GetREQUISITION(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetREQUISITION()));
		setJsonObject(jo);
		return OBJECT;
		}
	
	
	/**获取求购信息**/
	public String GetWantFor(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetWantFor()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**获取同售供应商**/
	public String GetSamSale(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(sd.GetSamSale()));
		setJsonObject(jo);
		return OBJECT;
	}
	
	/**关闭求购**/
	public String Close_WantFor(){
		ShopDao sd = new ShopDao();
		//
		JSONObject jo = new JSONObject();
		jo.put("json",sd.Close_WantFor());
		setJsonObject(jo);
		return OBJECT;
		
	}
	/**忽略供应商求购反馈**/
	public String ignore(){
		ShopDao sd = new ShopDao();
		JSONObject jo = new JSONObject();
		jo.put("json",sd.Close_WantFor());
		setJsonObject(jo);
		return OBJECT;
		
	}
	
	
	
	
	public String IsLogin(){
		System.out.println("检查用户是否登录");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
			JSONObject jo = new JSONObject();
			jo.put("json","ok");
			setJsonObject(jo);
		}else{
			System.out.println("未登录");
			JSONObject jo = new JSONObject();
			jo.put("json","err");
			setJsonObject(jo);
		}
		
		return OBJECT;
		
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
		JSONObject jo = new JSONObject();
		jo.put("json",Res);
		setJsonObject(jo);
		return OBJECT;

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
		String json = "[";
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

			JSONObject jo = new JSONObject();
			jo.put("json",JSONArray.fromObject(json));
			setJsonObject(jo);
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
		return OBJECT;

	}

	



////////////////////403标记
/**获取选中结算材料**/
public String GetSettleCart(){

	ShopDao sd = new ShopDao();
	JSONObject jo = new JSONObject();
	jo.put("json",JSONArray.fromObject(sd.GetSettleCart()));
	setJsonObject(jo);
	return OBJECT;
	
}


public String GetCart(){
	ShopDao sd = new ShopDao();
	JSONObject jo = new JSONObject();
	jo.put("json",JSONArray.fromObject(sd.GetCart()));
	setJsonObject(jo);
	return OBJECT;
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
	String json = "[";
	
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
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(json));
		setJsonObject(jo);
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
	return OBJECT;
	
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
	String json = "ok";

		JSONObject jo = new JSONObject();
		jo.put("json",json);
		setJsonObject(jo);
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
	return OBJECT;
	
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
	String json = "ok";
	
	
	System.out.println(json);
		JSONObject jo = new JSONObject();
		jo.put("json",json);
		setJsonObject(jo);
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
	return OBJECT;
	
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
	String json = "[";
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
		JSONObject jo = new JSONObject();
		jo.put("json",JSONArray.fromObject(json));
		setJsonObject(jo);
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
	return OBJECT;
	
}

}
