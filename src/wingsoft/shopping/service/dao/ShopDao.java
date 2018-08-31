package wingsoft.shopping.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import wingsoft.shopping.model.Item;
import wingsoft.shopping.service.instance.instance;
import wingsoft.shopping.service.yb.ConnectWWS;
import wingsoft.shopping.service.yb.New_WWS;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;
/**南医大，有审核流程**/
public class ShopDao {
	public Boolean IsLogin(){
		System.out.println("检查用户是否登录");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
			return true;
		}else{
			System.out.println("未登录");
			return false;
			
		}
		
		
		
	}
	public String GetOrders(){
		System.out.println("获取所有订单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		String jsonDtl = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		try {
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		
		String Sql = "select t.ordercode,"+
					      " t.date1 indate,"+
					      " s.name sname,"+
					      " os.ostatus,"+
					      " t.status_id,"+
					      " t.totalprice,"+
					      " decode(t.paytype, 'XM', '自持项目', 'OT', '他人代付') ||'('"+
					      " || decode(t.manner, 'XM', '项目结算')||')' payinfo,"+
					      " t.getname"+
					 " from T_ORDER t, supplier s, order_status os"+
					" where to_char(t.supplier_id) = to_char(s.id(+))"+
					 "  and t.status_id = os.id(+) and t.app_acc='"+userid+"'";
		System.out.println(Sql);
		
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		//ps.setString(1, userid);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
			Sql = "select t.common_name,t.specifications,t.brand,t.measurement_unit,t.price,(t.price*t.by_number) tprice,by_number,p.image from " +
					" t_Order_Product t,product p " +
					" where t.product_id=p.id(+) and  t.ordercode=rpad(?,20,' ')";
			System.out.println(Sql);
			ps = conn.prepareStatement(Sql);
			ps.setString(1, Comm.nTrim(rs.getString("ordercode")));
		    rsdtl = ps.executeQuery();
			flag = false;
			 jsonDtl = "[";
		    while(rsdtl.next()){
		    	jsonDtl+="{common_name:'"+Comm.nTrim(rsdtl.getString("common_name"))+
							"',specifications:'"+Comm.nTrim(rsdtl.getString("specifications"))+
							"',brand:'"+Comm.nTrim(rsdtl.getString("brand"))+
							"',measurement_unit:'"+Comm.nTrim(rsdtl.getString("measurement_unit"))+
							"',price:'"+Comm.nTrim(rsdtl.getString("price"))+
							"',tprice:'"+Comm.nTrim(rsdtl.getString("tprice"))+
							"',by_number:'"+Comm.nTrim(rsdtl.getString("by_number"))+
							"',image:'"+Comm.nTrim(rsdtl.getString("image"))+
							"'},";
				flag = true;
		    }
		    if (flag) {
		    	jsonDtl = jsonDtl.substring(0,jsonDtl.length()-1);
			}
			 jsonDtl += "]";
			flag = false;
				json+="{ordercode:'"+Comm.nTrim(rs.getString("ordercode"))+
						"',indate:'"+Comm.nTrim(rs.getString("indate"))+
						"',sname:'"+Comm.nTrim(rs.getString("sname"))+
						"',ostatus:'"+Comm.nTrim(rs.getString("ostatus"))+
						"',status_id:'"+Comm.nTrim(rs.getString("status_id"))+
						"',payinfo:'"+Comm.nTrim(rs.getString("payinfo"))+
						"',getname:'"+Comm.nTrim(rs.getString("getname"))+
						"',totalprice:'"+Comm.nTrim(rs.getString("totalprice"))+
						"',dtl:"+jsonDtl+
						"},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		
		
		
		json+="]";
		
		
		System.out.println(json);
		}else{
			System.out.println("未登录");
			
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
		return json;
		
	}	
	
	public String GetOrdersByORDERID(){
		System.out.println("根据SHP_ORDER的ORDERID获取子订单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String orderid = request.getParameter("orderid");
		System.out.println("orderid="+orderid);
		String userid = "";
		try {
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		
		String Sql = "select o.ordercode,"+
					      " o.date1 indate,"+  
					      " (o.totalprice+o.POSTAGE) tamt,"+
					      " o.paytype,o.pro_acc,o.pro_name,decode(o.paytype, 'XM', '项目支付['||trim(o.PRO_CODE)||']', 'OT', '他人代付['||trim(o.PRO_ACC)||'-'||trim(o.PRO_NAME))  payinfo," +
					      " o.postage," +
					      " s.name sname"+
					 " from SHP_ORDERS so,t_order o ,supplier s" +
					 " where so.orderid=o.shporderid " +
					 " and o.supplier_id=s.id "+
					 " and so.orderid=rpad(?,30,' ') and so.userid=rpad(?,30,' ')";
		System.out.println(Sql);
		
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		ps.setString(1, orderid);
		ps.setString(2, userid);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
			
			flag = false;
				json+="{ordercode:'"+Comm.nTrim(rs.getString("ordercode"))+
						"',indate:'"+Comm.nTrim(rs.getString("indate"))+
						"',sname:'"+Comm.nTrim(rs.getString("sname"))+  
						"',payinfo:'"+Comm.nTrim(rs.getString("payinfo"))+ 
						"',tamt:'"+Comm.nTrim(rs.getString("tamt"))+ 
						"',postage:'"+Comm.nTrim(rs.getString("postage"))+ 
						"',paytype:'"+Comm.nTrim(rs.getString("paytype"))+ 
						"',pro_acc:'"+Comm.nTrim(rs.getString("pro_acc"))+ 
						"',pro_name:'"+Comm.nTrim(rs.getString("pro_name"))+ 
						"'},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		
		
		
		json+="]";
		
		
		System.out.println(json);
		}else{
			System.out.println("未登录");
			
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
		return json;
		
	}	
	
	
	
	public Boolean SetAddressDef(){
		System.out.println("设置默认地址");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String aid = request.getParameter("aid");
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		String Sql = "";
		try {
			Sql = "update ADDRESS t  set t.isdef='F' WHERE T.USER_ACC='"+userid+"' AND T.IS_DELETED='0' ";
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		ps.execute();
		Sql = "update ADDRESS t  set t.isdef='T' WHERE T.USER_ACC='"+userid+"' AND T.IS_DELETED='0' AND ID=?";
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		ps.setInt(1, Integer.parseInt(aid));
		ps.execute();
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
		return true;
		
		
	}
	
	/**获取求购 、供应商反馈**/
	public String WantReq(){
		System.out.println("获取求购供应商反馈");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest(); 
		String wid = request.getParameter("wid");
		try {
		
		String Sql = "select w.remark,w.price,w.tele,s.name sname,o.name uname " +
					 " from WANTFOR_RES w,supplier s ,o_user o where w.sid=s.id " +
					 " and w.userid=o.account " +
					 " and w.wid=?";
	
		 json = "["; 
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			ps.setInt(1, Integer.parseInt(wid));
			rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{remark:'"+Comm.nTrim(rs.getString("remark"))+
						"',price:'"+Comm.nTrim(rs.getString("price"))+
						"',tele:'"+Comm.nTrim(rs.getString("tele"))+
						"',sname:'"+Comm.nTrim(rs.getString("sname"))+
						"',uname:'"+Comm.nTrim(rs.getString("uname"))+ 
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
	/**获取供应商**/
	public String GetSuppliers(){
		System.out.println("获取供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String key = request.getParameter("key");
		try {
		
		String Sql = "select s.id,s.name,s.contact,s.telephone,s.qq,s.email,s.url,s.address,s.businesslicense,s.taxlicense from supplier  s where 1=1 ";
		String sql1 = " order by s.name";
		 json = "[";
		if (key==""&&"".equals(key)) {
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql+sql1);
			rs = ps.executeQuery();
		}else{
			Sql = Sql + " and s.name like '%?%'" + sql1;
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			ps.setString(1, key);
			rs = ps.executeQuery();
		}
		boolean flag = false;
		while (rs.next()) {
				json+="{id:'"+Comm.nTrim(rs.getString("id"))+
						"',name:'"+Comm.nTrim(rs.getString("name"))+
						"',contact:'"+Comm.nTrim(rs.getString("contact"))+
						"',telephone:'"+Comm.nTrim(rs.getString("telephone"))+
						"',qq:'"+Comm.nTrim(rs.getString("qq"))+
						"',email:'"+Comm.nTrim(rs.getString("email"))+
						"',url:'"+Comm.nTrim(rs.getString("url"))+
						"',address:'"+Comm.nTrim(rs.getString("address"))+
						"',businesslicense:'"+Comm.nTrim(rs.getString("businesslicense"))+
						"',taxlicense:'"+Comm.nTrim(rs.getString("taxlicense"))+
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
	
	/**删除自购材料**/
	public String Delproduct_s(){
		System.out.println("获取供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		// this is always
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String cart = request.getParameter("cart");
		try {
		
		String Sql = "delete from PRODUCT_S where id=?  ";
	
		
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			ps.setInt(1, Integer.parseInt(cart));
			 ps.execute();
		
		
		
		
		
		json+="ok";
		
		
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

	
	/**保存自购材料信息**/
	public String Collection(){
		System.out.println("保存自购材料信息");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String itemid = request.getParameter("itemid");// "[{a:'1',b:'2'},{a:'11',b:'22'}]"; 
		System.out.println("itemid="+itemid);
		
		String userid = "";
		
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
			
		String Sql = "merge into COLLECTION c "+
						" using (select trim(?) ITEMID,trim(?) userid from dual) d"+
						" on (trim(c.itemid)=trim(d.itemid) and trim(c.userid)=trim(d.userid))"+
						" when not matched then"+
						" insert(itemid,indate,userid) values(d.itemid,sysdate,d.userid)";
		
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			ps.setString(1, itemid);
			ps.setString(2, userid);
			ps.execute();
			json = "ok";
			}else{
				json = "请先登录";
				System.out.println("未登录");
				
			}
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			json = "保存异常";
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
	
	/**保存自购材料信息**/
	public String SaveProducts(){
		System.out.println("保存自购材料信息");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String products = request.getParameter("products");// "[{a:'1',b:'2'},{a:'11',b:'22'}]"; 
		System.out.println("products="+products);
		
		String userid = "";
		
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
			
		String Sql = "insert into product_s(id,specifications, brand,measurement_unit, " +
				"price, buy_num, common_name,  " +
				" cas_name, status, userid,scode)" +
				" values( seq_product_id.nextval," +
				" trim(?)," +
				" trim(?)," +
				" trim(?)," +
				" trim(?)," +
				" trim(?)," +
				" trim(?)," +
				" trim(?)," +
				" '0'," +
				" trim(?)," +
				" trim(?))";
		String u_sql = "update  product_s set " +
					"specifications=trim(?), " +
					"brand=trim(?)," +
					"measurement_unit=trim(?), " +
					"price=trim(?)," +
					" buy_num=trim(?)," +
					" common_name=trim(?),  " +
					" cas_name=trim(?)," +
					"scode=trim(?) where id=?"+
					" and userid=rpad(?,50,' ')";
			System.out.println(Sql);
			conn = pool.getConnection();	
			JSONArray arry = JSONArray.fromObject(products);
		    for (int i = 0; i < arry.size(); i++)
	        {	
		    	JSONObject jsonObject = arry.getJSONObject(i);
		    	System.out.println("pid="+jsonObject.get("pid").toString());
		    	if ("".equals(jsonObject.get("pid").toString())||
		    			""==jsonObject.get("pid").toString()||
		    			"undefined".equals(jsonObject.get("pid").toString())) {
					ps = conn.prepareStatement(Sql);
				
				ps.setString(1, jsonObject.get("specifications").toString());
				ps.setString(2, jsonObject.get("brand").toString());
				ps.setString(3, jsonObject.get("unit").toString());
				ps.setInt(4, Integer.parseInt(jsonObject.get("price").toString()));
				ps.setInt(5, Integer.parseInt(jsonObject.get("buy_num").toString()));
				ps.setString(6, jsonObject.get("common_name").toString());
				ps.setString(7, jsonObject.get("cas_name").toString());
				ps.setString(8, userid);
				ps.setString(9, jsonObject.get("scode").toString());	
				  ps.execute();
				}else{

					System.out.println("u_sql="+u_sql);
					ps = conn.prepareStatement(u_sql);
					
				ps.setString(1, jsonObject.get("specifications").toString());
				ps.setString(2, jsonObject.get("brand").toString());
				ps.setString(3, jsonObject.get("unit").toString());
				ps.setDouble(4, Double.parseDouble(jsonObject.get("price").toString()));
				ps.setDouble(5, Double.parseDouble(jsonObject.get("buy_num").toString()));
				ps.setString(6, jsonObject.get("common_name").toString());
				ps.setString(7, jsonObject.get("cas_name").toString());
				ps.setString(8, jsonObject.get("scode").toString());
				ps.setInt(9, Integer.parseInt(jsonObject.get("pid").toString()));
				ps.setString(10, userid);
				System.out.println(jsonObject.get("pid").toString());	
				System.out.println(userid);	
				  ps.execute();
				}
	        }

		
		
		json = "ok";
		
			}else{
				json = "请先登录";
				System.out.println("未登录");
				
			}
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			json = "保存异常";
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
	
	/**自行采购备案**/
	public String SaveOrder_S(){
		System.out.println("自行采购备案");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;
			String userid = "";
			double Tamt = Double.valueOf(request.getParameter("Tamt"));//自购总金额
			String Supplierid = request.getParameter("sid");//供应商编号，如果供应商编号不存在，就任务是新的供应商
			String SupplierName = request.getParameter("sname");//供应商名称
			String BuyDate = request.getParameter("buydate");//购买日期
			
			String tickets = request.getParameter("tickets");//发票
			String AppMsg = request.getParameter("AppMsg");//自购原因
			
			String pro_code = request.getParameter("pro_code");//支付项目
			String b_code = request.getParameter("b_code");//费用项代码
			String b_name = request.getParameter("b_name");//费用项
			String yb_type =  request.getParameter("yb_type");//预约转账方式。DG:对公转账。DS:对私转账
			
			String bankacc = request.getParameter("bankacc");
			String bankaccname = request.getParameter("bankaccname");
			String bankname = request.getParameter("bankname");
			String remark = request.getParameter("remark");//备注
			String postage = request.getParameter("postage");
			String username = "";

			String state = "";
			
			String projacc = ""; 
			String projNAME = "";
			String proname ="";
			
			String json = "";
			
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			String sqlDtl = "";
			String sqlDtl1 = "";
			Integer l = 0;
			json = "ok";
			
			int OrderSeq = 0;
			String year = "";
			String mm = "";
			String dd = "";
			String OrderId = "";
			
			int tprice = 0;
			String SHPordid = "";
			try {
				conn = pool.getConnection();
				if (request.getSession().getAttribute("userId")!=null) {
					userid = (String) request.getSession().getAttribute("userId");
						sql = "select SEQ_ORDER_ID.nextval orderid,to_char(sysdate,'yyyy') y,to_char(sysdate,'mm') m,to_char(sysdate,'dd') d   from dual";	
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							OrderSeq = rs.getInt("orderid");
							year = rs.getString("y");
							mm = rs.getString("m");
							dd = rs.getString("d");
							
						}
						OrderId = year+mm+dd+"S"+String.format("%06d", OrderSeq);
						sqlDtl =  " SELECT SEQ_CART_PRODUCT_ID.nextval,'"+OrderId+"',ps.id,'3', ps.common_name,ps.chemical_name," +
									" ps.english_name,ps.cas_name,'"+userid+"',ps.buy_num,ps.specifications,ps.brand,'"+Supplierid+"',ps.measurement_unit," +
									" ps.price,1,ps.price oprice" +
									" FROM product_s ps" +
									" WHERE  (ps.status = '0')" +
									" AND (ps.userid = rpad(?,50,' ') and ordercode is null)";
						sqlDtl1 =  "select  ps.buy_num,"+
									" ps.price " +
									" FROM product_s ps" +
									" WHERE  (ps.status = '0')" +
									" AND (ps.userid = rpad(?,50,' ') and ordercode is null)";
						sql = "select nvl(SUM(buy_num*price),0) p from ("+sqlDtl1+")";
						System.out.println("2="+sql);
						ps = conn.prepareStatement(sql);
						ps.setString(1, userid);
						rs = ps.executeQuery();
			
						if (rs.next()) {
							tprice = rs.getInt("p");
						}	
						if (tprice!=Tamt||tprice==0) {
							json = "结算金额异常,请刷新重新结算";
						}
				}else{
					System.out.println("未登录");
					json = "请先登录";
					
				}
				
				
				
				
				if ("ok".equals(json)) {
				
				/**查看供应商是否为新添加**/
				if ("qt".equals(Supplierid)) {
					sql = "select seq_supplier_id.nextval sid from dual";
					ps = conn.prepareStatement(sql);
					rs=ps.executeQuery();
					if (rs.next()) {
						Supplierid = rs.getString("sid");
					}
					sql = "insert into SUPPLIER_S(id,NAME,ACCOUNT,ACCNAME,ACCBANK,USERID)" +
							" values('"+Supplierid+"','"+SupplierName+"','"+bankacc+"','"+bankaccname+"','"+bankname+"','"+userid+"')";
					System.out.println("supplier="+sql);
					ps = conn.prepareStatement(sql);
					ps.execute();
				} else{
					sql = "update SUPPLIER_S set ACCOUNT=?,ACCNAME=?,ACCBANK=? where trim(id)=trim(?)";
						System.out.println("supplier="+sql);
						ps = conn.prepareStatement(sql);
						ps.setString(1, bankacc);
						ps.setString(2, bankaccname);
						ps.setString(3, bankname);
						ps.setString(4, Supplierid);
						ps.execute();
				} 
					sql = "insert into SUPPLIER_BANK(ACCOUNT,ACCNAME,ACCBANK,ORDERCODE,PAYAMT,SID) " +
							"values(?,?,?,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, bankacc);
					ps.setString(2, bankaccname);
					ps.setString(3, bankname);
					ps.setString(4, OrderId);
					ps.setInt(5, tprice);
					ps.setString(6, Supplierid);
					ps.executeQuery();
				/**
				 * 先查看结算订单总金额
				 * **/
				sql = "";
				
				sql = "INSERT INTO T_ORDER_PRODUCT(id,ordercode,Product_Id,Status_Id,"+
		                  "Common_Name,Chemical_Name,English_Name,Cas_Name,User_Acc,By_Number,"+
		                  "Specifications,Brand,Supplier_Id,Measurement_Unit,Price,PRIVILEGE,O_PRICE)"+
		                  	sqlDtl;
				System.out.println("1="+sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, userid);
				ps.execute();
				

				sql = "";
				sql = "select trim(T.nextstate) nextstate from FLOW_INFO t where t.ftype='H' and T.ORD=0 and famt<="+tprice+" and tamt>"+tprice;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
						 
				if (rs.next()) {
					state = rs.getString("nextstate");
					System.out.println("***订单状态："+state+"***");
				}
				sql = "";
				sql = "INSERT INTO FLOW_TASK(ROLEID,USERID,ISCHECK,ORDERCODE,ORD,FAMT,TAMT,NEXTSTATE,BACKSTATE,NOWSTATE,contxt) " +
						"SELECT F.ROLEID,'',ISCHECK,'"+OrderId+"',F.ORD,F.FAMT,F.TAMT,f.NEXTSTATE,f.BACKSTATE,f.NOWSTATE,f.contxt FROM FLOW_INFO F " +
								" WHERE F.FTYPE='H' and famt<="+tprice+" and tamt>"+tprice;
				System.out.println("审核流程初始化"+sql);
				ps = conn.prepareStatement(sql);
				ps.execute();
				
				
				
				sql = "";
				sql = "select t.NAME,t.proacc,t.proname from V_USER_PROCODE t where t.procode='"+pro_code+"' and t.useracc='"+userid+"'";
				System.out.println("3="+sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				
				if (rs.next()) {
					projacc = rs.getString("proacc");
					projNAME = rs.getString("proname");
					proname = rs.getString("NAME");
				}	
				
				sql = "select t.name from T_USER t where t.account='"+userid+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				
				if (rs.next()) {
					username = rs.getString("name");
				}	
				
				sql = "";
				sql = "update product_s set status='1',ordercode='"+OrderId+"' where (status = '0')" +
									" AND (userid = rpad(?,50,' ') and ordercode is null)";

			    ps = conn.prepareStatement(sql);
				ps.setString(1, userid);
			    ps.execute();     
				 
				sql = "";
					sql = "insert into t_order(ORDERCODE,type_id,status_id,"+
		            " supplier_id,app_msg,totalprice,date1 ,APP_ACC,APP_NAME,RECEIPT,DATE7,ISSELF,SELF_MSG,B_CODE,B_NAME,PRO_CODE," +
		            " pro_name,pro_acc,paytype,postage,yb_type)"+
		            " values('"+OrderId+"',"+
		           "'99','"+state+"',"+
		           "trim('"+Supplierid+"'),trim('"+remark+"'),"+
		           +tprice+",sysdate ,'"+userid+"','"+username+"','"+tickets+"',to_date('"+BuyDate+"','yyyy-mm-dd'),'T','"+AppMsg+"'," +
		           		"'"+b_code+"','"+b_name+"','"+pro_code+"','"+projNAME+"','"+projacc+"','ZG','"+postage+"','"+yb_type+"')";
			
				
				System.out.println("4="+sql);
			    ps = conn.prepareStatement(sql);
			    ps.execute();   
			/*    if ("DG".equals(yb_type)||"DS".equals(yb_type)) {
			    	  if ("DG".equals(yb_type)) {
						    sql = "";
						    sql = "insert into USED_BANK(ACCOUNT,ACCNAME,ACCBANK,USERID,SID,YB_TYPE)" +
						    		" values('"+bankacc+"','"+bankaccname+"','"+bankname+"',trim('"+userid+"'),trim('"+Supplierid+"'),'DG') ";
							
						}else if ("DS".equals(yb_type)) {
						    sql = "";
						    sql = "insert into USED_BANK(ACCOUNT,ACCNAME,ACCBANK,USERID,YB_TYPE)" +
						    		" values('"+bankacc+"','"+bankaccname+"','"+bankname+"',trim('"+userid+"'),'DS') ";
						}

					    ps = conn.prepareStatement(sql);
					    ps.execute();   
				}*/
			  
			    
			    
			    /**
			     * 订单生成后，发送冻结请求
			     * 
			     * **/
				}
				json =  json +':' + OrderId;
				//Res = "success";
			} catch (Exception e) {
				e.printStackTrace();
				json = "数据异常";
				//Res = "保存异常="+e.getMessage();
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
	
	
	/**获取自购未提交材料**/
	public String Getproduct_s(){
		System.out.println("获取供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		String userid = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
		String	Sql =  " SELECT  ps.id, ps.common_name," +
					" ps.cas_name,ps.buy_num,ps.specifications,ps.brand,ps.supplier_id,ps.measurement_unit,ps.scode," +
					" ps.price" +
					" FROM product_s ps" +
					" WHERE  (ps.status = '0')" +
					" AND (ps.userid = '"+userid+"')";
		  json = "["; 
			System.out.println(Sql); 
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			System.out.println(userid);
			//ps.setString(1, userid);
			rs = ps.executeQuery(); 
		boolean flag = false;
		while (rs.next()) {
				json+="{id:'"+Comm.nTrim(rs.getString("id"))+
						"',common_name:'"+Comm.nTrim(rs.getString("common_name"))+
						"',cas_name:'"+Comm.nTrim(rs.getString("cas_name"))+
						"',buy_num:'"+Comm.nTrim(rs.getString("buy_num"))+
						"',specifications:'"+Comm.nTrim(rs.getString("specifications"))+
						"',brand:'"+Comm.nTrim(rs.getString("brand"))+
						"',price:'"+Comm.nTrim(rs.getString("price"))+
						"',scode:'"+Comm.nTrim(rs.getString("scode"))+
						"',supplier_id:'"+Comm.nTrim(rs.getString("supplier_id"))+
						"',measurement_unit:'"+Comm.nTrim(rs.getString("measurement_unit"))+
						"',},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		
		json+="]";
			}
		else{
			json = "请先登录";
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
	
	/**获取历史自购供应商**/
	public String SupplierS(){
		System.out.println("获取自购供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		String userid = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
		String	Sql =  " select id,name,account,accname,accbank from SUPPLIER_S t where t.userid=rpad(?,50,' ')";
		  json = "["; 
			System.out.println(Sql); 
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			System.out.println(userid);
			ps.setString(1, userid);
			rs = ps.executeQuery(); 
		boolean flag = false;
		while (rs.next()) {
				json+="{id:'"+Comm.nTrim(rs.getString("id"))+
						"',name:'"+Comm.nTrim(rs.getString("name"))+
						"',account:'"+Comm.nTrim(rs.getString("account"))+
						"',accname:'"+Comm.nTrim(rs.getString("accname"))+
						"',accbank:'"+Comm.nTrim(rs.getString("accbank"))+
						"',},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		
		json+="]";
			}
		else{
			json = "请先登录";
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
	
	public String hot_search(){
		System.out.println("hot_search");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		 
		String Sql = "select h.searchkey from hot_search h where rownum<5 order by h.searchnum";
		System.out.println(Sql);
		try { 
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql); 
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{searchkey:'"+Comm.nTrim(rs.getString("searchkey"))+"'},";
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
	public String CATEGORY(){
		System.out.println("获取分类");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		
		String json = "";
		
		String Sql = "select A.categoryid, A.categoryname,a.levels,a.parents " +
					"from (select b.categoryid,b.categoryname,b.levels,b.parents from SHP_CATEGORY B where 1 = 1) A " +
					" connect by A.parents = prior A.categoryid  start with A.LEVELS = 1  ";
		System.out.println(Sql);
		try {
		List<Item> is = new ArrayList<Item>();
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
			
				json+="{categoryid:'"+Comm.nTrim(rs.getString("categoryid"))+"',categoryname:'"+Comm.nTrim(rs.getString("categoryname"))+
						"',levels:'"+Comm.nTrim(rs.getString("levels"))+"',parents:'"+Comm.nTrim(rs.getString("parents"))+"'},";
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
	
	public String GET_OT_PAY(){
		System.out.println("获取当前用户之前代付人员信息");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		String Sql = "select trim(t.ot_uni_no) ot_uni_no,trim(t.ot_name) ot_name from OT_PAY t where t.userid='"+userid+"'";
		System.out.println(Sql);
		try {
		List<Item> is = new ArrayList<Item>();
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{ot_uni_no:'"+Comm.nTrim(rs.getString("ot_uni_no"))+"',ot_name:'"+Comm.nTrim(rs.getString("ot_name"))+
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
	
	
	public String GetTGROUP(){
		System.out.println("获取所属课题组");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		String Sql = "select t.groupcode,t.groupname,u.show from TGROUP t ,usergroup u where   t.state='1' and  u.groupcode=t.groupcode and u.userid='"+userid+"'";
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
				json+="{groupcode:'"+Comm.nTrim(rs.getString("groupcode"))+"',groupname:'"+Comm.nTrim(rs.getString("groupname"))+
						"',show:'"+Comm.nTrim(rs.getString("show"))+"'},";
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
	
	/**获取今日推荐**/
	public String GetRECOMMEND(){
		System.out.println("获取推荐供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		try {
		
		String Sql = "select t.imgs,t.url from RECOMMEND_TODAY t where t.isdef='T' AND " +
				" SYSDATE BETWEEN to_date(to_char(T.STARTDATE,'yyyy-mm-dd')||' 00:00:00','yyyy-mm-dd hh24:mi:ss') " +
				" AND to_date(to_char(T.Enddate,'yyyy-mm-dd')||' 23:59:00','yyyy-mm-dd hh24:mi:ss')";
		 json = "[";
	
			System.out.println(Sql);
			conn = pool.getConnection();	
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{imgs:'"+Comm.nTrim(rs.getString("imgs"))+
						"',url:'"+Comm.nTrim(rs.getString("url"))+
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
	
	/**关闭求购**/
	
	
	public String Close_WantFor(){
		HttpServletRequest request = ServletActionContext.getRequest();
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;
			String wid = request.getParameter("wid"); 
			
			String json = "";
			
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			String userid = "";
			json = "ok";
			
			try {
				if (request.getSession().getAttribute("userId")!=null) {
					userid = (String) request.getSession().getAttribute("userId");

					conn = pool.getConnection(); 
					sql = "update WANTFOR w set w.STATE='D' where w.wid=? and w.USERID=rpad(?,50,' ')";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(wid));
					ps.setString(2, userid);
					ps.execute();
					 
				}else{
					System.out.println("未登录");
					json = "请先登录";
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				json = "保存异常="+e.getMessage();
			} finally {
				try {
					conn.setAutoCommit(true);
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	
	
/**关闭求购**/
	
	
	public String ignore(){
		HttpServletRequest request = ServletActionContext.getRequest();
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;
			String rid = request.getParameter("rid"); 
			
			String json = "";
			
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			String userid = "";
			json = "ok";
			
			try {
				if (request.getSession().getAttribute("userId")!=null) {
					userid = (String) request.getSession().getAttribute("userId");

					conn = pool.getConnection(); 
					sql = "update WANTFOR_RES w set w.STATE='F' where w.RID=?  ";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(rid)); 
					ps.execute();
					 
				}else{
					System.out.println("未登录");
					json = "请先登录";
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				json = "保存异常="+e.getMessage();
			} finally {
				try {
					conn.setAutoCommit(true);
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	
	/**保存订单**/
	public String SaveOrder(){
		HttpServletRequest request = ServletActionContext.getRequest();
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;
			String Res = "";
			String userid = "";
			String PayType = request.getParameter("paytype");//paytype :XM 项目支付，OT：他人代付
			double Tamt = Double.valueOf(request.getParameter("Tamt"));
			
			//String pro_codes = request.getParameter("pro_code");//获取到多个支付项目 [{pro_code:'123',pro_amt:100},{pro_code:'123',pro_amt:200}]
			String pro_json = request.getParameter("pro_json");//多个支付项目
			String prom_json = request.getParameter("prom_json");//对应的项目金额
			
			System.out.println("pro_json="+pro_json);
			System.out.println("prom_json="+prom_json);
			String pro_code = request.getParameter("pro_code");
			String s_json = request.getParameter("s_json");//供应商信息/*[{sid:'123',tamt:'100',Getdate:'yyyy-mm-dd'}]*/
			System.out.println(s_json);
			String AppMsg = request.getParameter("appmsg");
			String GetName = request.getParameter("GetName");
			String GetPhone = request.getParameter("GetPhone");
			String GetAddress = request.getParameter("GetAddress");
			//String b_code = request.getParameter("b_code");
			//String b_name = request.getParameter("b_name");
			String b_code = "";
			String b_name = "";
			String files = request.getParameter("files");
			String projacc = "";
			String othercode = request.getParameter("othercode");//代付人账户
			String othername = request.getParameter("othername");//代付人账户
			String proappjacc = request.getParameter("GetDate");//前台提交负责人账户
			String projNAME = "";
			String proname ="";
			String TYPE_ID = "";
			//String B_code = request.getParameter("GetDate");
			String GROUPCODE = request.getParameter("groupcode");//课题组编号，普通材料可为空,如果为管制品，必填
			String LOCATIONID = request.getParameter("locationid");//管制品    课题组指定 存放地点编号
			String username = "";
			/*SaveProducts格式："1","2","3"*/
			String cartids = request.getParameter("cartids");
			HttpServletResponse response = ServletActionContext.getResponse();
			String json = "";
			
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			String sqlDtl = "";
			String sqlDtl1 = "";
			Integer l = 0;
			json = "ok";
			boolean CkR = true; 
			
			int OrderSeq = 0;
			String year = "";
			String mm = "";
			String dd = "";
			String OrderId = "";
			String OrderIdS = "";
			double tprice = 0;
			double settle_price = 0;/*结算总金额*/
			double stprice = 0;
			double orderprice = 0;/*订单金额：材料金额加邮费*/
			double postage = 0;
			String SHPordid = "";
			String sid = "";
			int store = 0;
			String Getdate = "";
			String state = "";
			boolean iswhp = false;
			String  ftype = "";
			String FreezeInfo = "";
			String wws_userid = "";
			String wws_pwd = "";
		    Document doc = null;
		    String procode = "";
		    Double pro_amt = 0.0;
			try {
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				System.out.println("a"+GetName);
				if (request.getSession().getAttribute("userId")!=null) {
					userid = (String) request.getSession().getAttribute("userId");
					
					if (GetName == null || GetName.length() <= 0 || GetName == "null") {
						json = "请填写收货地址";{}
					}else {
						/*根据提交信息，循环处理,[{sid:'123',tamt:'100',Getdate:'yyyy-mm-dd'}]*/
						/**检查提交材料是否属于危化品，如果属于危化品，必须有所属课题组，送货地点必须为课题组注册存放地点**/

						Integer whpnum = 0;
						Integer tnum = 0;
						Integer user_c = 0;
						
						sql = "";
						sql = "select count(1) c from shp_cart sc ,shp_item si " +
								" where sc.itemid=si.itemid and si.itemcate like '%.22.%' and sc.cartid in ("+cartids+") and sc.userid='"+userid+"'";
						System.out.println("管制品数量="+sql);
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						
						
						if (rs.next()) {
							whpnum = rs.getInt("c");
						}	
						if(whpnum>1){
							json = "管制品一次只允许购买一种";
							CkR = false;
						}
						 

						if (CkR) {

							if (PayType=="OT"||"OT".equals(PayType)) {
								
							sql = "";
							sql = "select count(1) c from T_USER t where t.account='"+othercode+"' and t.name='"+othername+"'"; 
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							
							
							if (rs.next()) {
								user_c = rs.getInt("c");
							}	
							if(user_c==0){
								json = "代付人账户姓名不一致";
								CkR = false;
							}
							}
						}
						
						
						/*检查是否有管制品跟普通材料同时采购*/
						if (CkR) {
							sql = "";
							sql = "select count(1) c from shp_cart sc ,shp_item si " +
									" where sc.itemid=si.itemid and si.itemcate not like '%.22.%' and  sc.cartid in ("+cartids+") and sc.userid='"+userid+"'";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							 
							if (rs.next()) {
								tnum = rs.getInt("c");
							}	
							if(whpnum>0&&tnum>0){
								json = "普通材料跟管制品不允许同时购买";
								CkR = false;
							}
								
						}
						/*检查选中课题组是否为当前用户所在课题组,选中地址*/
						if (CkR) {
							if (whpnum==1) {
								iswhp = true;
								
								if (GROUPCODE=="0"||"0".equals(GROUPCODE)) {
									json = "购买管制品,请选择课题组";
									CkR = false;
								}else if (LOCATIONID=="0"||"0".equals(LOCATIONID)) {
									json = "购买管制品,请选择课题组注册送货地址";
									CkR = false;
								}
							}else{
								/**如果为普通材料，获取平台采购定义状态**/
								iswhp = false;
								 
							}
							/**GROUPCODE ！=0 说明选择了课题组**/
							if(GROUPCODE!="0"&&!"0".equals(GROUPCODE)){
								tnum = 0;
								sql = "";
								sql = "select count(1) c from USERGROUP t where t.userid= '"+userid+"' and t.groupcode='"+GROUPCODE+"'";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
							 
								if (rs.next()) {
									tnum = rs.getInt("c");
								}	
								if(tnum==0){
									/* 2018-04-24  关闭课题组功能
									json = "选择课题组已关闭,或非当前用户所在课题组";
									CkR = false;*/
								}else{
									if(LOCATIONID!="0"&&!"0".equals(LOCATIONID)){
										tnum = 0;
										sql = "";
										sql = "select count(1) c from LOCATION t where t.groupcode='"+GROUPCODE+"'  and t.locationid='"+LOCATIONID+"'" ;//and t.userid='"+userid+"'
					 					ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
									 
										if (rs.next()) {
											tnum = rs.getInt("c");
										}	
										if(tnum==0){
											json = "选择收货地址非当前课题组指定地址";
											CkR = false;
										}
								}
							}
						}
						
						
						if (CkR) {
						
							sql = "";
							sql = "select SEQ_SHP_ORDERID.nextval shpid from dual";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							
							
							if (rs.next()) {
								SHPordid = rs.getString("shpid");
							}	

							sql = "";
							sql = "select t.NAME,t.proacc,t.proname from V_USER_PROCODE t where t.procode='"+pro_code+"' and t.useracc='"+userid+"'";
							System.out.println("3="+sql);
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							
						
							if (rs.next()) {
								projacc = rs.getString("proacc");
								projNAME = rs.getString("proname");
								proname = rs.getString("NAME");
							}	
						
							sql = "select t.name from T_USER t where t.account='"+userid+"'";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							
							
							if (rs.next()) {
								username = rs.getString("name");
							}	
						
						
							JSONArray arry = JSONArray.fromObject(s_json);
						    for (int i = 0; i < arry.size(); i++)
					        {	
						    	JSONObject jsonObject = arry.getJSONObject(i);
						    	sid = jsonObject.get("sid").toString();
						    	store = Integer.parseInt(jsonObject.get("store").toString());
						    	tprice = 0;
						    	stprice = Double.parseDouble(jsonObject.get("tamt").toString());
						    	postage =  Double.parseDouble(jsonObject.get("postage").toString());
						    	Getdate = jsonObject.get("Getdate").toString();
	
								b_code = jsonObject.get("b_code").toString();
								b_name = jsonObject.get("b_name").toString();
						    	
								
								
						    	/*先获取订单号*/
								sql = "select SEQ_ORDER_ID.nextval orderid,to_char(sysdate,'yyyy') y,to_char(sysdate,'mm') m,to_char(sysdate,'dd') d   from dual";	
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									OrderSeq = rs.getInt("orderid");
									year = rs.getString("y");
									mm = rs.getString("m");
									dd = rs.getString("d");
									
								}
								OrderId = year+mm+dd+"P"+String.format("%06d", OrderSeq);
								OrderIdS +=  "'"+OrderId+"',";
						    	
						    	
						    	sqlDtl = "";
						    	sqlDtl1 = "";
						    	sqlDtl =  "select SEQ_CART_PRODUCT_ID.nextval,'"+OrderId+"',cp.id,'1',"+
								            "cp.common_name,cp.chemical_name,cp.english_name,"+
								            "cp.cas_name,'"+userid+"',s.numbers,"+
								            "cp.specifications,cp.brand,cp.supplier_id,cp.measurement_unit,"+
								            "cp.price,CP.PRIVILEGE, cp.price from  PRODUCT cp,SHP_CART S"+
								            " where CP.ID=S.ITEMID AND S.ORDERID is null and s.cartid in ("+cartids+") " +
								            " and cp.supplier_id='"+sid+"' and s.userid='"+userid+"'";
								sqlDtl1 =  "select  '"+OrderId+"',cp.id,'1',"+
											"cp.common_name,cp.chemical_name,cp.english_name,"+
											"cp.cas_name,'"+userid+"',s.numbers,"+
											"cp.specifications,cp.brand,cp.supplier_id,cp.measurement_unit,"+
											"cp.price,CP.PRIVILEGE from  PRODUCT cp,SHP_CART S"+
											" where CP.ID=S.ITEMID AND S.ORDERID is null and s.cartid in ("+cartids+")"+
								            " and cp.supplier_id='"+sid+"' and s.userid='"+userid+"'";
								sql = "select nvl(SUM(numbers*price),0) p from ("+sqlDtl1+")";
											System.out.println("2="+sql);
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											tprice = 0;
											if (rs.next()) {
												tprice = rs.getDouble("p");
											}	
											if (tprice!=stprice||tprice==0) {
												json = "结算金额异常,请刷新重新结算";
												CkR = false;
												break;
											}
											settle_price = settle_price + tprice + postage;
											/*if (iswhp) {
									if (PayType=="XM"||"XM".equals(PayType)) {
										ftype = "W";
									}else{
										ftype = "E";
										
									}	
									
								}else{
									if (PayType=="XM"||"XM".equals(PayType)) {
										ftype = "A";
									}else{
										ftype = "D";
										
									}	
								}
								orderprice =  tprice + postage;
									sql = "";
									sql = "select trim(T.nextstate) nextstate from FLOW_INFO t where t.ftype='"+ftype+"' and T.ORD=0 and famt<="+orderprice+" and tamt>"+orderprice;
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
											 
									if (rs.next()) {
										state = rs.getString("nextstate");
									}
									sql = "";
									sql = "INSERT INTO FLOW_TASK(ROLEID,USERID,ISCHECK,ORDERCODE,ORD,FAMT,TAMT,NEXTSTATE,BACKSTATE,NOWSTATE,contxt) " +
											"SELECT F.ROLEID,'',ISCHECK,'"+OrderId+"',F.ORD,F.FAMT,F.TAMT,f.NEXTSTATE,f.BACKSTATE,f.NOWSTATE,f.contxt FROM FLOW_INFO F " +
													" WHERE F.FTYPE='"+ftype+"' and famt<="+orderprice+" and tamt>"+orderprice;
									System.out.println("审核流程初始化"+sql);
									ps = conn.prepareStatement(sql);
									ps.execute();*/
						    	
								/**
								 * 先查看结算订单总金额
								 * **/
								sql = "";
								
								sql = "INSERT INTO T_ORDER_PRODUCT(id,ordercode,Product_Id,Status_Id,"+
						                  "Common_Name,Chemical_Name,English_Name,Cas_Name,User_Acc,By_Number,"+
						                  "Specifications,Brand,Supplier_Id,Measurement_Unit,Price,PRIVILEGE,O_PRICE)"+
						                  	sqlDtl;
								System.out.println("1="+sql);
								ps = conn.prepareStatement(sql);
								ps.execute();
								
								
								System.out.println("PayType="+PayType);
								sql = "";
								if (PayType=="XM"||"XM".equals(PayType)) {
									sql = "insert into t_order(ORDERCODE,"
											+ "PRO_CODE,"
											+ "type_id,status_id,app_acc,app_name,"+
						            " pro_name,pro_acc,supplier_id,"
						            + "app_msg,totalprice,date1,date6,getname,getphone,"+
						            " address,SHPORDERID,paytype,MANNER,groupcode,locationid,b_code,files,b_name,postage,store)"+
						            " values('"+OrderId+"',"+
						          " trim('"+pro_code+"'）,"+
						           "'1','1',trim('"+userid+"'),trim('"+username+"'),"
						           		+ "trim('"+projNAME+"'),"+ "trim('"+projacc+"'),"+
						           "trim('"+sid+"'),trim('"+AppMsg+"'),"+
						           +tprice+",sysdate,to_date(trim('"+Getdate+"'),'yyyy-mm-dd'),"+
						           "trim('"+GetName+"'),trim('"+GetPhone+"'),trim('"+GetAddress+"'),'"+SHPordid+"','"+PayType+"','XM','"
						           +GROUPCODE+"','"+LOCATIONID+"','"+b_code+"' ,'"+files+"','"+b_name+"',"+postage+","+store+")";
									
									/*获取供应商注册信息*/ 
									ps = conn.prepareStatement("select t.wws_userid,t.wws_pwd,T.name from SUPPLIER t where t.id = rpad(?,20,' ')");
									ps.setString(1, sid);
									rs = ps.executeQuery();
									if (rs.next()) { 
										 wws_userid = Comm.nTrim(rs.getString("wws_userid"));
										 wws_pwd = Comm.nTrim(rs.getString("wws_pwd"));
										 if (wws_userid=="") {
												json = "供应商未在财务处注册,请先进行注册["+Comm.nTrim(rs.getString("name"))+"]";
												CkR = false;
												break; 
										}
									}else{ 
										json = "未找到该供应商,["+sid+"]";
										CkR = false;
										break;
									}
									
									/**插入支付信息关系表   白雪**/
									System.out.println("***插入支付信息关系表***");
									JSONArray arry_pro_json = JSONArray.fromObject(pro_json);
									JSONArray arry_prom_json = JSONArray.fromObject(prom_json);
									
									for (int j = 0; j < arry_pro_json.size(); j++) {
										JSONObject arry_pro_json_oj = arry_pro_json.getJSONObject(j);
										JSONObject arry_prom_json_oj = arry_prom_json.getJSONObject(j);
										procode = arry_pro_json_oj.get("procode").toString();
										pro_amt =  Double.parseDouble(arry_prom_json_oj.get("pro_money").toString());
										String sqls= "insert into order_project (ORDER_ID,ORD,PRO_CODE,AMT)"
												+ "	 values('"+OrderId+"',"+j+",'"+pro_code+"',"+pro_amt+")";
										System.out.println("插入支付信息关系表="+sqls);
										ps = conn.prepareStatement(sqls);
										ps.execute(); 
										
										
										/**冻结信息**/
										if (j != 0) postage = 0;
										FreezeInfo += "<order>"	+
														        "<prov_code>"+wws_userid+"</prov_code>"+
														        "<order_no>"+OrderId+"_"+j+"</order_no>"+	
														        "<uni_prj_code>"+procode+"</uni_prj_code>"+
														        "<frozen_user>"+userid+"</frozen_user>"+
														        "<b_code>"+b_code+"</b_code>"+
														        "<frozen_amt>"+(postage+pro_amt)+"</frozen_amt>"+
													   " </order >"; 
									}
									/**end**/
									
									
									/**冻结信息**/
									/*FreezeInfo += "<order>"	+
													        "<prov_code>"+wws_userid+"</prov_code>"+
													        "<order_no>"+OrderId+"</order_no>"+	
													        "<uni_prj_code>"+pro_code+"</uni_prj_code>"+
													        "<frozen_user>"+userid+"</frozen_user>"+
													        "<b_code>"+b_code+"</b_code>"+
													        "<frozen_amt>"+(postage+tprice)+"</frozen_amt>"+
												   " </order >"; */
									
									
									
								}else if(PayType=="OT"||"OT".equals(PayType)){
									sql = "insert into t_order(ORDERCODE,type_id,status_id,app_acc,app_name,"+
						            " pro_acc,pro_name,supplier_id,app_msg,totalprice,date1,date6,getname,getphone,"+
						            " address,SHPORDERID,paytype,MANNER,groupcode,locationid,files,postage,store)"+
						            " values('"+OrderId+"',"+
						           "'1','0',trim('"+userid+"'),trim('"+username+"'),trim('"+othercode+"'),trim('"+othername+"'),"+
						           "trim('"+sid+"'),trim('"+AppMsg+"'),"+
						           +tprice+",sysdate,to_date(trim('"+Getdate+"'),'yyyy-mm-dd'),"+
						           "trim('"+GetName+"'),trim('"+GetPhone+"'),trim('"+GetAddress+"'),'"+SHPordid+"','"+PayType+"','XM' ,'"
						           +GROUPCODE+"','"+LOCATIONID+"' ,'"+files+"',"+postage+","+store+")";
									/**记录他人代付信息**/
									String otsql = "merge into  OT_PAY o using (select trim(?) USERID,trim(?) OT_UNI_NO,trim(?) OT_NAME from dual) c " +
											" on (trim(o.userid)=trim(c.userid) and trim(o.OT_UNI_NO)=trim(c.OT_UNI_NO) ) " +
											" when  matched then " +
											" update set OT_NAME=c.OT_NAME " +
											" when not matched then " +
											" insert (USERID,OT_UNI_NO,OT_NAME) values(c.USERID,c.OT_UNI_NO,c.OT_NAME)";
	
									    ps = conn.prepareStatement(otsql);
									    ps.setString(1, userid); 
									    ps.setString(2, othercode);
									    ps.setString(3, othername);
									    ps.execute();
									    
								} 
								
								System.out.println("4="+sql);
							    ps = conn.prepareStatement(sql);
							    ps.execute();
							    /**test**/
							    sql = "select count(1) c from t_order o where o.ordercode='"+OrderId+"' ";
								System.out.println(sql);
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery(); 
							    while (rs.next()) {
									System.out.println(rs.getString("c"));
									
								}
							    
							    instance ins = new instance();
							    
							    
							    //创建流程
								String insres = ins.CreateInstance(OrderId, userid, username, AppMsg,conn);
								if (!"ok".equals(insres)) {
									CkR = false;
									json = insres;
									break;
								}
					        }
					       
					    if (CkR) {
					       OrderIdS = OrderIdS.substring(0,OrderIdS.length()-1);
					    
					    	sql = "";
					        sql = "insert into  SHP_ORDERS(Orderid,USERID,TIME) " +
					    		"values('"+SHPordid+"','"+userid+"',to_char(sysdate,'yyyy-mm-dd hh:mi:ss'))";
						    System.out.println("5="+sql);
						    ps = conn.prepareStatement(sql);
						    ps.execute();     
						    

							sql = "";
						    sql = "update SHP_CART set orderid='"+ SHPordid +"' where userid = '"+userid+"' and cartid in ("+cartids+") and orderid is null";
						    System.out.println("6="+sql);
						    ps = conn.prepareStatement(sql);
						    ps.execute();  
						    json = json+":"+SHPordid;	
						    /**
						     * 订单生成后，发送冻结请求
						     * **/
						    /**如果是项目支付,进行冻结**/
						    if(PayType=="XM"||"XM".equals(PayType)){
							    New_WWS NW = new New_WWS();	
							    String SEQ_ID = "";
							    sql = "";
							    sql = "select WWS_SEQ_ID.nextval SEQ_ID from dual";
							    ps = conn.prepareStatement(sql);
							    rs = ps.executeQuery();  
							    if (rs.next()) {
							    	SEQ_ID = rs.getString("SEQ_ID");
								}
							    /**FreezeInfo：[{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'}{}]**/
							    /**财务接口 结算**/
							    FreezeInfo =  "<orders>"+ FreezeInfo + "</orders>";
							    System.out.println("****调用冻结接口1321***");
							    String RetMsg = NW.OrderFrozee(FreezeInfo, SEQ_ID);
							    System.out.println("冻结返回RetMsg="+RetMsg);
							    
							    /**模拟冻结 05-16**/
							    /*RetMsg= "<?xml version=\"1.0\"  encoding=\"utf-8\"?><return>ok</return>"; 
							    System.out.println("(模拟)冻结返回RetMsg="+RetMsg);*/
							    
							    doc = DocumentHelper.parseText(RetMsg);
								Element rootElt = doc.getRootElement(); // 获取根节点 
								System.out.println(rootElt.getData());
								
								if (!"error".equals(rootElt.getName())) {
									if("ok".equals(rootElt.getData())){
									   sql = "";
									    sql = "update  t_order set FREEZE_NO='"+SEQ_ID+"',FREEZE_MSG='"+RetMsg+"' where ordercode in ("+OrderIdS+")";
									    ps = conn.prepareStatement(sql);
									    ps.execute();
									    conn.commit();
									}
									else{
										conn.rollback();
										json = "冻结失败,"+RetMsg; 
									}
								}else{
									conn.rollback();
									json = "冻结失败"+rootElt.elementText("type")+"-"+rootElt.elementText("msg"); 
								}
								
								
								
							}else{
								conn.commit();
								
							}
						}
						}	
					}
					}
				}else{
					System.out.println("未登录");
					json = "请先登录";
					
				}
				
				
				Res = json;
			} catch (Exception e) {
				e.printStackTrace();
				json = "数据出来异常";
				Res = "保存异常="+e.getMessage();
			} finally {
				try {
					conn.setAutoCommit(true);
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
	
	/**获取待结算信息**/
	public String GetSettleCart(){
		System.out.println("获取待结算订单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String Sql = ""; 
		String Sql_supplier = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		String json = "";
		String jsondtl = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		String cartids = request.getParameter("cartids");
		
		cartids = cartids.substring(1,cartids.length()-1);
		Sql_supplier = " select p.name sname,p.id sid,p.qq,sum(s.numbers*cp.price) tprice,sum(s.numbers) nums,small,postage,ss.STORE ," +
						"(select '['||wm_concat('{b_code:'''||trim(sb.b_code)||''',usage:'''||trim(u.usage)||'''}')||']' from SUPPLIER_BCODE sb,usage u where sb.b_code=u.b_code and sb.sid=p.id) bcodes"+
						" from  " +
						" PRODUCT cp,SHP_CART S,supplier p,SUPP_STORE ss "+
					    " where CP.ID=S.ITEMID " +
					    " AND S.USERID='"+userid+"' " +
					    " and s.cartid in ("+cartids+") " +
					    " and s.orderid is  null" +
					    " and p.id=ss.sid(+)" +
					    " and cp.supplier_id=trim(p.id) " +
					    " group by p.name ,p.id ,p.qq ,small,postage,ss.STORE";
		Sql =    "select cp.common_name,s.numbers,cp.brand,cp.specifications,cp.price,cp.image," +
				" p.name sname,cp.supplier_id from " +
				" PRODUCT cp,SHP_CART S,supplier p"+
				    " where CP.ID=S.ITEMID AND S.USERID='"+userid+"' and s.cartid in ("+cartids+") " +
				    " and trim(cp.supplier_id)=trim(p.id) " +
				    " and s.orderid is  null" +
				    " and cp.supplier_id=?";
		System.out.println(Sql);
		try {
		List<Item> is = new ArrayList<Item>();

		conn = pool.getConnection();	
		System.out.println("Sql_supplier="+Sql_supplier);
		ps = conn.prepareStatement(Sql_supplier);
		rs = ps.executeQuery();
		json = "[";
		boolean flag = false;
		while (rs.next()) {
		
			jsondtl = "[";
				conn = pool.getConnection();	
				ps = conn.prepareStatement(Sql);
				System.out.println("sid="+rs.getString("sid"));
				ps.setString(1, Comm.nTrim(rs.getString("sid")));
				rsdtl = ps.executeQuery();
				while (rsdtl.next()) {
					jsondtl+="{name:'"+Comm.nTrim(rsdtl.getString("common_name"))+
								"',number:'"+Comm.nTrim(rsdtl.getString("numbers"))+
								"',price:'"+Comm.nTrim(rsdtl.getString("price"))+
								"',brand:'"+Comm.nTrim(rsdtl.getString("brand"))+
								"',sname:'"+Comm.nTrim(rsdtl.getString("sname"))+
								"',sid:'"+Comm.nTrim(rsdtl.getString("supplier_id"))+
								"',image:'"+Comm.nTrim(rsdtl.getString("image"))+
								"',specifications:'"+Comm.nTrim(rsdtl.getString("specifications"))+
								"'},";
						flag = true;
				}
				if (flag) {
					jsondtl = jsondtl.substring(0,jsondtl.length()-1);
				}
				flag = false;
				jsondtl+="]";
				System.out.println("jsondtl="+jsondtl);
				json+="{sname:'"+Comm.nTrim(rs.getString("sname"))+
						"',sid:'"+Comm.nTrim(rs.getString("sid"))+
						"',qq:'"+Comm.nTrim(rs.getString("qq"))+
						"',small:'"+Comm.nTrim(rs.getString("small"))+
						"',store:'"+Comm.nTrim(rs.getString("store"))+
						"',postage:'"+Comm.nTrim(rs.getString("postage"))+
						"',tprice:'"+Comm.nTrim(rs.getString("tprice"))+
						"',nums:'"+Comm.nTrim(rs.getString("nums"))+
						"',bcodes:"+Comm.nTrim(rs.getString("bcodes"))+ 
						",dtl:"+jsondtl+
						"},";

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
	
	
	/**获取管制品申请单**/
	public String GetREQUISITION(){
		System.out.println("获取管制品申请单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		try {
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		
			String groupcode = request.getParameter("groupcode");
			
		
		String Sql = "select wr.rcode,wr.rname,wr.files,wr.itemid,wr.baseunit,wr.num,wr.appnum,wr.buynum " +
					 " from WHP_REQUISITION wr where wr.state='T'  and wr.num>(wr.appnum+wr.buynum) " +
					 " and  wr.groupcode='"+groupcode+"' and wr.userid='"+userid+"'";
		System.out.println(Sql);
		
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{rcode:'"+Comm.nTrim(rs.getString("rcode"))+
						"',rname:'"+Comm.nTrim(rs.getString("rname"))+
						"',itemid:'"+Comm.nTrim(rs.getString("itemid"))+
						"',baseunit:'"+Comm.nTrim(rs.getString("baseunit"))+
						"',num:'"+Comm.nTrim(rs.getString("num"))+
						"',appnum:'"+Comm.nTrim(rs.getString("appnum"))+
						"',buynum:'"+Comm.nTrim(rs.getString("buynum"))+
						"',files:'"+Comm.nTrim(rs.getString("files"))+"'},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		
		
		System.out.println(json);
		}else{
			System.out.println("未登录");
		
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
		return json;
		
	}
	
	
	/**获取同售供应商**/
	public String GetSamSale(){
		System.out.println("获取同售供应商");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest(); 
		try { 
		
			String itemid = request.getParameter("itemid");
			
		
		String Sql = "select t.itemid,t.cycle,s.name,s.qq,s.telephone,s.small,s.postage,t.SERVICESCORE se," +
				"t.PRODUCTSCORE ps,t.SENDSCORE sd,si.prize from " +
				" WITH_THE_SALE_OF t ,supplier s,shp_item si" + 
				" where t.id = (select w.id from WITH_THE_SALE_OF w where w.itemid=rpad(?,30,' ')) " +
				" and t.sid=s.id  and t.itemid=si.itemid order by si.prize";
		System.out.println(Sql);
		
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		ps.setString(1, itemid);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{itemid:'"+Comm.nTrim(rs.getString("itemid"))+
						"',name:'"+Comm.nTrim(rs.getString("name"))+
						"',qq:'"+Comm.nTrim(rs.getString("qq"))+
						"',telephone:'"+Comm.nTrim(rs.getString("telephone"))+
						"',small:'"+Comm.nTrim(rs.getString("small"))+
						"',postage:'"+Comm.nTrim(rs.getString("postage"))+
						"',prize:'"+Comm.nTrim(rs.getString("prize"))+
						"',cycle:'"+Comm.nTrim(rs.getString("cycle"))+
						"',se:'"+Comm.nTrim(rs.getString("se"))+
						"',ps:'"+Comm.nTrim(rs.getString("ps"))+ 
						"',sd:'"+Comm.nTrim(rs.getString("sd"))+"'},";
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
	
	
	
	/**获取求购信息**/
	public String GetWantFor(){
		System.out.println("获取管制品申请单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		try {
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		 
			String Sql = "select t.wid,t.name,t.brand,t.model,t.num,t.remark,t.tel,t.indate," +
			"(select count(1) from WANTFOR_RES wr where wr.wid=t.wid and wr.STATE='A') c from WANTFOR t " +
			" where t.STATE='A' and t.userid='"+userid+"' order by t.wid desc";
		System.out.println(Sql);
		
		 json = "[";
		
		conn = pool.getConnection();	
		ps = conn.prepareStatement(Sql);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) {
				json+="{wid:'"+Comm.nTrim(rs.getString("wid"))+
						"',wname:'"+Comm.nTrim(rs.getString("name"))+
						"',brand:'"+Comm.nTrim(rs.getString("brand"))+
						"',model:'"+Comm.nTrim(rs.getString("model"))+
						"',num:'"+Comm.nTrim(rs.getString("num"))+
						"',remark:'"+Comm.nTrim(rs.getString("remark"))+
						"',tel:'"+Comm.nTrim(rs.getString("tel"))+
						"',c:'"+Comm.nTrim(rs.getString("c"))+
						"',indate:'"+Comm.nTrim(rs.getString("indate"))+"'},";
				flag = true;
		}
		if (flag) {
			json = json.substring(0,json.length()-1);
		}
		json+="]";
		
		
		System.out.println(json);
		}else{
			System.out.println("未登录");
		
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
		return json;
		
	}
	
	/**快速购买**/
	public String QuickBuy(){
		System.out.println("快速购买");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		ResultSet rsdo = null; 
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String type = request.getParameter("type");
		/**如果type=P  单个商品购买，
		 * 如果type=O  整单快速购买**/
		String pid = request.getParameter("pid");
		String bnum = request.getParameter("bnum");
		
		String ordercode= request.getParameter("ordercode"); 
		 
		
		String userid = "";
		String Sql = "";
		String cartid = "";
		String cartids = "";
		
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
			if ("P".equals(type)) {
				Sql = "select SEQ_SHP_CARTID.nextval cartid from dual";
				System.out.println(Sql);
				conn = pool.getConnection();	 
				ps = conn.prepareStatement(Sql); 
				rs = ps.executeQuery();
				if (rs.next()) {
					cartid = rs.getString("cartid");
				}
				
				Sql = "merge into SHP_CART s1" +
					" using (select '"+pid+"' itemid,'"+userid+"' userid from dual) s2" +
					" on (s1.orderid is null and s1.userid=s2.userid and s1.itemid=s2.itemid)"+
					" when matched then" +
					" update set NUMBERS="+bnum+
					" ,cartid='"+cartid+"'"+
					" when not matched then" + 
				    " insert(cartid,userid,ITEMID,NUMBERS) " +
					" values('"+cartid+"','"+userid+"','"+pid+"',"+bnum+" )";
	 
					System.out.println(Sql);
					conn = pool.getConnection();	 
					ps = conn.prepareStatement(Sql); 
					ps.execute();
					cartids = cartid;
			}else{
				Sql = "select o.product_id,o.by_number from t_order_product o where o.ordercode='"+ordercode+"'";
				System.out.println(Sql);
				String itemid = "";
				String itemnum = "" ;
				conn = pool.getConnection();	 
				ps = conn.prepareStatement(Sql); 
				rsdo = ps.executeQuery();
				int i = 0;
			    while (rsdo.next()) {
			    	itemid = rsdo.getString("product_id");
			    	itemnum = rsdo.getString("by_number");
				
					Sql = "select SEQ_SHP_CARTID.nextval cartid from dual";
					System.out.println(Sql);
					conn = pool.getConnection();	 
					ps = conn.prepareStatement(Sql); 
					rsdo = ps.executeQuery();
					if (rsdo.next()) {
						cartid = rsdo.getString("cartid");
					}

					Sql = "merge into SHP_CART s1" +
						" using (select '"+itemid+"' itemid,'"+userid+"' userid from dual) s2" +
						" on (s1.orderid is null and s1.userid=s2.userid and s1.itemid=s2.itemid)"+
						" when matched then" +
						" update set NUMBERS="+itemnum+
						" ,cartid='"+cartid+"'"+
						" when not matched then" + 
					    " insert(cartid,userid,ITEMID,NUMBERS) " +
						" values('"+cartid+"','"+userid+"','"+itemid+"',"+itemnum+" )";
						System.out.println(Sql);
						conn = pool.getConnection();	 
						ps = conn.prepareStatement(Sql); 
						ps.execute();
						if (i==0) {
							cartids =  cartid;
						}else {
							cartids = cartids + ','+ cartid;
						}
						i+=1;
				}
				
			}

			//response.sendRedirect("shopping/settle/settle.jsp?cartids='"+cartids+"'");
		   
	    	json = "ok:"+cartids;
		
			}else{
				json = "请先登录";
				System.out.println("未登录");
				
			}
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			json = "提交异常";
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
	
	
	
	
	/**保存自购材料信息**/
	public String WantFor(){
		System.out.println("保存求购单");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		String json = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String pname = request.getParameter("pname");
		String brand = request.getParameter("brand");
		String model= request.getParameter("model");
		String num = request.getParameter("num");
		String remark = request.getParameter("remark");
		String tel = request.getParameter("tel");
		String fromdate = request.getParameter("fromdate");
		String enddate = request.getParameter("enddate");
		
		String userid = "";
		
		try {
			if (request.getSession().getAttribute("userId")!=null) {
				userid = (String) request.getSession().getAttribute("userId");
			
		String Sql = "insert into wantfor(wid,name,brand,model,num,remark,tel,userid,indate," +
				//"fromdate," +
				//"enddate," +
				"state) " +
				"values(SEQ_WANTFOR_ID.nextval,TRIM(?),TRIM(?),TRIM(?),TRIM(?),TRIM(?),TRIM(?),TRIM(?),sysdate," +
			//	"to_date(TRIM(?),'yyyy-mm-dd')," +
			//	"to_date(TRIM(?),'yyyy-mm-dd')," +
				"'A')";
	 
			System.out.println(Sql);
			conn = pool.getConnection();	 
			ps = conn.prepareStatement(Sql);

		
				ps.setString(1, pname);
				ps.setString(2, brand);
				ps.setString(3, model);
				ps.setInt(4, Integer.parseInt(num));
				ps.setString(5, remark);
				ps.setString(6, tel);
				ps.setString(7, userid);
				//ps.setString(8, fromdate);
				//ps.setString(9, enddate);	
				  ps.execute();
				
		   
		json = "ok";
		
			}else{
				json = "请先登录";
				System.out.println("未登录"); 
			}
		System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			json = "提交异常";
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
	
	

	public String GetCart(){
		System.out.println("获取购物车");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsdtl = null;
		String json = "";
		String jsonDtl = "";
		
		String Sql = "";
		String Sqlsupplier = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userid = "";
		if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
		}else{
			System.out.println("未登录");
			
		}
		//Sql =    "select cp.common_name,s.numbers,cp.brand,cp.price,cp.image,(select trim(sp.name)　from supplier sp where sp.id=cp.supplier_id ) sname,cp.supplier_id from " +
		//		" PRODUCT cp,SHP_CART S"+
		//		    " where CP.ID=S.ITEMID AND S.USERID='"+userid+"' and s.orderid is  null";
		Sqlsupplier = "select * from supplier s where s.id in " +
				"(select rpad(si.value,20,' ') from shp_itempara si,shp_cart sc where " +
				"si.parameterid='15' and si.itemid=sc.itemid and sc.userid=rpad(?,30,' ') and sc.orderid is null)";
		Sql =  "select si.itemname,si.itemimgupload,sc.numbers,si.itemcate,si.itemid,sc.cartid,si.prize,sc.cartid " + 
				" from shp_cart sc,shp_item si where sc.itemid=si.itemid and sc.userid=rpad(?,30,' ') and sc.orderid is null " +
				" and sc.itemid in (select rpad(trim(si.itemid),30,' ') from  shp_itempara si where si.parameterid='15' and trim(si.value)=?)";
		System.out.println(Sql);
		try {
		List<Item> is = new ArrayList<Item>();
		 json = "["; 
		conn = pool.getConnection(); 
		System.out.println("Sqlsupplier="+Sqlsupplier);
		ps = conn.prepareStatement(Sqlsupplier);
		ps.setString(1, userid);
		rs = ps.executeQuery();
		boolean flag = false;
		while (rs.next()) { 
			ps = conn.prepareStatement(Sql);
			ps.setString(1, userid);
			ps.setString(2, Comm.nTrim(rs.getString("id")));
		    rsdtl = ps.executeQuery();
			flag = false;
			 jsonDtl = "[";
		    while(rsdtl.next()){
		    	jsonDtl+="{itemname:'"+Comm.nTrim(rsdtl.getString("itemname")).replace("'", "\"")+ 
							"',prize:'"+Comm.nTrim(rsdtl.getString("prize"))+
							"',numbers:'"+Comm.nTrim(rsdtl.getString("numbers"))+ 
							"',image:'"+Comm.nTrim(rsdtl.getString("itemimgupload"))+
							"',itemcate:'"+Comm.nTrim(rsdtl.getString("itemcate"))+
							"',itemid:'"+Comm.nTrim(rsdtl.getString("itemid"))+
							"',cartid:'"+Comm.nTrim(rsdtl.getString("cartid"))+
							"'},";
				flag = true;
		    }
		    if (flag) {
		    	jsonDtl = jsonDtl.substring(0,jsonDtl.length()-1);
			}
			 jsonDtl += "]";
			flag = false;
				json+="{sname:'"+Comm.nTrim(rs.getString("name"))+
						"',sid:'"+Comm.nTrim(rs.getString("id"))+
						"',postage:'"+Comm.nTrim(rs.getString("postage"))+
						"',small:'"+Comm.nTrim(rs.getString("small"))+
						"',dtl:"+jsonDtl+
						"},";
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
	
	public static void main(String[] args) {
		String js = "[{a:'1',b:'2'},{a:'11',b:'22'}]";
		JSONArray arry = JSONArray.fromObject(js);
	    for (int i = 0; i < arry.size(); i++)
        {	
	    	JSONObject jsonObject = arry.getJSONObject(i);
	    	System.out.println(jsonObject.toString());
	    	System.out.println(jsonObject.get("a"));
	    	System.out.println(jsonObject.get("b"));
        }
	}
}




/*
1、并发   300  
2、密码3次 锁15分钟
3、session 30
4、操作日志
*/
