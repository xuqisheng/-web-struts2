package wingsoft.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class CostTable extends ActionSupport {
	private static final long serialVersionUID = -3372684080595425861L;
	public String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	
	public String insert() {
		System.out.println("getCostTableByID");
		System.out.println(new Date());
		HttpServletRequest request = ServletActionContext.getRequest();
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps0 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs0 = null;
		ResultSet rs1 = null;
		try {
			conn = pool.getConnection();
			Object obj = JSONUtil.deserialize(request.getReader());
			JSONObject jObj = JSONObject.fromObject(obj);
			System.out.println(jObj.toString());
			String profit= CommonOperation.nTrim(jObj.getString("profit"));
			String height = CommonOperation.nTrim(jObj.getString("height"));//HEIGHT number
			String countPrice =  CommonOperation.nTrim(jObj.getString("countPrice"));//sql foodtotal numb
			String totalCost = CommonOperation.nTrim(jObj.getString("totalCost"));//sql totalCost numb
			String direct_cost =CommonOperation.nTrim(jObj.getString("direct_cost"));// direct_cost numb
			String utilities = CommonOperation.nTrim(jObj.getString("utilities"));//utilities numb
			String joint_cost = CommonOperation.nTrim(jObj.getString("joint_cost"));//joint_cost numb
			String interest_rate = CommonOperation.nTrim(jObj.getString("interest_rate"));//毛利 numb
			String re_total= CommonOperation.nTrim(jObj.getString("re_total"));// totalmoney nub
			String re_number= CommonOperation.nTrim(jObj.getString("re_number"));//num
			String re_price = CommonOperation.nTrim(jObj.getString("re_price"));//price
			String class_id = CommonOperation.nTrim(jObj.getString("class_id"));//class_id char
			String rate =interest_rate.substring(0, interest_rate.length()-1);
			String taste = CommonOperation.nTrim(jObj.getString("taste"));//TASTE char 
			String name = CommonOperation.nTrim(jObj.getString("name"));//get name char 
			//String tableID = CommonOperation.nTrim(jObj.getString("tableID"));//sql id char 
			String oil_price = CommonOperation.nTrim(jObj.getString("oil_price"));
			String oil_numb = CommonOperation.nTrim(jObj.getString("oil_numb"));
			String other = CommonOperation.nTrim(jObj.getString("other"));
			double d_data =Double.parseDouble(rate)/100;
			//list
			
			JSONArray listO = JSONArray.fromObject(jObj.get("typeList"));
			JSONObject oil= new JSONObject();
			JSONObject otherO= new JSONObject();
			oil.put("costName", "油");
			oil.put("costNumber", oil_numb);
			oil.put("unitPrice", oil_price);
//			oil.put("total", Integer.parseInt(oil_numb)*Double.parseDouble(oil_price));
			oil.put("others", "1");
			otherO.put("costName", "调料");
			otherO.put("costNumber", "1");
			otherO.put("unitPrice", other);
//			otherO.put("total",other);
			otherO.put("others", "1");
			listO.add(oil);
			listO.add(otherO);
			///////////////json处理
			
			String tableID = "";
			int table_id = 0;
			String sql0 = "select max(id) as max_id from cost";
			
			ps0 = conn.prepareStatement(sql0);
			rs0 = ps0.executeQuery();
			boolean success1_flag  = false;
			if(rs0.next()) {
				String str = CommonOperation.nTrim(rs0.getString("max_id"));
				if(!str.equals("")) {
					table_id = Integer.parseInt(str)+1;
				}
				else {
					str = "0";
					table_id = Integer.parseInt(str)+1;
				}
				success1_flag=true;
			}
				
			tableID = String.valueOf(table_id);
			
			if(success1_flag==true) {
				String sql = "insert into cost "
						+ " (id,name,foodtotal,total_cost,direct_cost,joint_cost,utilities,height,taste,"
						+ " interest_rate,num,price,totalmoney,profit,class_id)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, tableID);
System.out.println(tableID);
				ps.setString(2, name);
				ps.setString(3, countPrice);//foodtotal
				ps.setString(4, totalCost);
				ps.setString(5, direct_cost);
				ps.setString(6, joint_cost);
				ps.setString(7, utilities);
				ps.setString(8, height);
				ps.setString(9, taste);
				ps.setString(10, String.valueOf(d_data));
				ps.setString(11, re_number);
				ps.setString(12, re_price);
				ps.setString(13, re_total);
				ps.setString(14, profit);
				ps.setString(15, class_id);
				ps.executeUpdate();
				for(Object jo:listO) {
					JSONObject jsonObj=JSONObject.fromObject(jo);
					if(!jsonObj.getString("costNumber").equals("null")&&!jsonObj.getString("unitPrice").equals("null")&&!jsonObj.getString("costName").equals("null")) {
						String costName = jsonObj.getString("costName");
						String costNumber = jsonObj.getString("costNumber");
						String costPrice = jsonObj.getString("unitPrice");
						double total_d=Double.parseDouble(costNumber)*Double.parseDouble(costPrice);
						String sql1 = "select max(cd.ord) ord from cost_dtl cd";
						ps1 = conn.prepareStatement(sql1);
						rs1 =ps1.executeQuery();
						int numb = 0;
						if(rs1.next()) {
							numb = rs1.getInt("ord");
						}
						String sql2="insert into cost_dtl(cost_id,type,name,num,price,totalprice,ord)values ("
								+"?,?,?,?,?,?,?)";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, tableID);
						ps2.setString(2, jsonObj.getString("others"));
						ps2.setString(3, costName);
						ps2.setString(4, jsonObj.getString("costNumber"));
						ps2.setString(5, jsonObj.getString("unitPrice"));
						ps2.setString(6, String.valueOf(total_d));
						ps2.setInt(7, numb+1);
						ps2.executeUpdate();
System.out.println(sql2);
					}
				}	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HttpServletResponse response = ServletActionContext.getResponse();  
			response.setStatus(500);
		}finally {
			try {
				if (rs1 != null)
					rs1.close();
				if (rs0 != null)
					rs0.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(ps0!=null)
					ps0.close();
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return "insert";
	}

	//get
	public String fromDBData() {
		System.out.println("getTable");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cost_id = CommonOperation.nTrim(request.getParameter("tableID"));
		String sql1 = " select id,class_id,name,foodtotal,total_cost,direct_cost,joint_cost,utilities,height,taste,interest_rate,num,price,totalmoney,profit" + 
				" from cost where id ="+ cost_id;
		String sql2 = "select ord,type, name, num, price, totalprice from cost_dtl " + 
				" where cost_id ="+ cost_id+ 
				" order by ord";
		JSONObject jsonObj = new JSONObject();
		System.out.println(sql1);
		System.out.println(sql2);
		try {
			conn = pool.getConnection();			
			ps2 = conn.prepareStatement(sql2);
			ps1=conn.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				jsonObj.put("tableID", CommonOperation.nTrim(rs1.getString("id")));
				jsonObj.put("class_id", CommonOperation.nTrim(rs1.getString("class_id")));
				jsonObj.put("name", CommonOperation.nTrim(rs1.getString("name")));
				jsonObj.put("countPrice", CommonOperation.nTrim(rs1.getString("foodtotal")));
				jsonObj.put("total_cost", CommonOperation.nTrim(rs1.getString("total_cost")));
				jsonObj.put("direct_cost", CommonOperation.nTrim(rs1.getString("direct_cost")));
				jsonObj.put("joint_cost", CommonOperation.nTrim(rs1.getString("joint_cost")));
				jsonObj.put("utilities", CommonOperation.nTrim(rs1.getString("utilities")));
				jsonObj.put("height", CommonOperation.nTrim(rs1.getString("height")));
				jsonObj.put("taste", CommonOperation.nTrim(rs1.getString("taste")));
				jsonObj.put("interest_rate", CommonOperation.nTrim(rs1.getString("interest_rate")));
				jsonObj.put("re_total", CommonOperation.nTrim(rs1.getString("totalmoney")));
				jsonObj.put("profit", CommonOperation.nTrim(rs1.getString("profit")));
				jsonObj.put("re_number", CommonOperation.nTrim(rs1.getString("num")));
				jsonObj.put("re_price", CommonOperation.nTrim(rs1.getString("PRICE")));
			}
			
			rs2 = ps2.executeQuery();
			JSONArray jaList = new JSONArray();
			while(rs2.next()) {
				JSONObject detailObj = new JSONObject();
				detailObj.put("type",CommonOperation.nTrim(rs2.getString("type")).equals("0")?"主辅料":"其他");
				detailObj.put("order", CommonOperation.nTrim(rs2.getString("ord")));
				detailObj.put("name", CommonOperation.nTrim(rs2.getString("name")));
				detailObj.put("num", CommonOperation.nTrim(rs2.getString("num")));
				detailObj.put("price", CommonOperation.nTrim(rs2.getString("price")));
				detailObj.put("totalprice",CommonOperation.nTrim(rs2.getString("totalprice")));
				jaList.add(detailObj);
			}
			jsonObj.put("typeList",jaList);
			json=jsonObj.toString();
System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs1 != null)
					rs1.close();
				if (rs2 != null)
					rs2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return "fromDBData";
	}
	
	public String update() {
		System.out.println("update");
		System.out.println(new Date());
		HttpServletRequest request = ServletActionContext.getRequest();
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		PreparedStatement ps_add = null;
		try {
			conn = pool.getConnection();
			Object obj = JSONUtil.deserialize(request.getReader());
			JSONObject jObj = JSONObject.fromObject(obj);
			String profit= CommonOperation.nTrim(jObj.getString("profit"));
			String height = CommonOperation.nTrim(jObj.getString("height"));//HEIGHT number
			String countPrice =  CommonOperation.nTrim(jObj.getString("countPrice"));//sql foodtotal numb
			String totalCost = CommonOperation.nTrim(jObj.getString("totalCost"));//sql totalCost numb
			String direct_cost =CommonOperation.nTrim(jObj.getString("direct_cost"));// direct_cost numb
			String utilities = CommonOperation.nTrim(jObj.getString("utilities"));//utilities numb
			String joint_cost = CommonOperation.nTrim(jObj.getString("joint_cost"));//joint_cost numb
			String interest_rate = CommonOperation.nTrim(jObj.getString("interest_rate"));//毛利 numb
			String re_total= CommonOperation.nTrim(jObj.getString("re_total"));// totalmoney nub
			String re_number= CommonOperation.nTrim(jObj.getString("re_number"));//num
			String re_price = CommonOperation.nTrim(jObj.getString("re_price"));//price
			String class_id = CommonOperation.nTrim(jObj.getString("class_id"));//class_id char
			String rate =interest_rate.substring(0, interest_rate.length()-1);
			String taste = CommonOperation.nTrim(jObj.getString("taste"));//TASTE char 
			String name = CommonOperation.nTrim(jObj.getString("name"));//get name char 
			String tableID = CommonOperation.nTrim(jObj.getString("tableID"));//sql id char 
			String oil_price = CommonOperation.nTrim(jObj.getString("oil_price"));
			String oil_numb = CommonOperation.nTrim(jObj.getString("oil_numb"));
			String other = CommonOperation.nTrim(jObj.getString("other"));
			String oil_order = CommonOperation.nTrim(jObj.getString("oil_order"));//oreder number
			String o_order  =CommonOperation.nTrim(jObj.getString("o_order"));
System.out.println(jObj.toString());
			double d_data =Double.parseDouble(rate)/100;
			//list
			
			JSONArray listO = JSONArray.fromObject(jObj.get("typeList"));
System.out.println(listO.toString());
			JSONObject oil= new JSONObject();
			JSONObject otherO= new JSONObject();
			oil.put("costName", "油");
			oil.put("costNumber", oil_numb);
			oil.put("unitPrice", oil_price);
			oil.put("others", "1");
			oil.put("order", oil_order);
			otherO.put("costName", "调料");
			otherO.put("costNumber", "1");
			otherO.put("unitPrice", other);
			otherO.put("others", "1");
			otherO.put("order", o_order);
			listO.add(oil);
			listO.add(otherO);
			///////////////json处理
			
			String sql = "update cost "
					+ " set name=?,foodtotal=?,total_cost=?,direct_cost=?,joint_cost=?,utilities=?,height=?,taste=?,"
					+ " interest_rate=?,num=?,price=?,totalmoney=?,profit=? "
					+ " where id ="+tableID+" and class_id ="+class_id;
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, countPrice);//foodtotal
			ps.setString(3, totalCost);
			ps.setString(4, direct_cost);
			ps.setString(5, joint_cost);
			ps.setString(6, utilities);
			ps.setString(7, height);
			ps.setString(8, taste);
			ps.setString(9, String.valueOf(d_data));
			ps.setString(10, re_number);
			ps.setString(11, re_price);
			ps.setString(12, re_total);
			ps.setString(13, profit);
			ps.executeUpdate();
			System.out.println(listO.toString());
			//遍历详单
			for(Object jo:listO) {
				JSONObject jsonObj=JSONObject.fromObject(jo);
				if(!jsonObj.getString("costNumber").equals("null")&&!jsonObj.getString("unitPrice").equals("null")&&!jsonObj.getString("costName").equals("")) {
					String order = CommonOperation.nTrim(jsonObj.getString("order"));
					String costName = CommonOperation.nTrim(jsonObj.getString("costName"));
					String costNumber = CommonOperation.nTrim(jsonObj.getString("costNumber"));
					String costPrice = CommonOperation.nTrim(jsonObj.getString("unitPrice"));
					double total_d=Double.parseDouble(costNumber)*Double.parseDouble(costPrice);
					//add
					if (order.equals("-1")) {
						String sql1 = "select max(cd.ord) ord from cost_dtl cd";
						ps1 = conn.prepareStatement(sql1);
						rs1 =ps1.executeQuery();
						if(rs1.next()) {
							order = String.valueOf(rs1.getInt("ord")+1);
						}
						String add_dtl = "insert into cost_dtl (cost_id,type,name,num,price,totalprice,ord)values (?,?,?,?,?,?,?)";
						ps_add = conn.prepareStatement(add_dtl);
						ps_add.setString(1, tableID);
						ps_add.setString(2, CommonOperation.nTrim(jsonObj.getString("others")));
						ps_add.setString(3, costName);
						ps_add.setString(4, costNumber);
						ps_add.setString(5, costPrice);
						ps_add.setString(6, String.valueOf(total_d));
						ps_add.setString(7, order);
						ps_add.execute();
						continue;
					}
//System.out.println("order值："+order);
					String sql2="update cost_dtl set type=?,name=?,num=?,price=?,totalprice=? "
								+" where cost_id ="+tableID+"and ord= "+order;
//System.out.println(sql2);
					ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, CommonOperation.nTrim(jsonObj.getString("others")));
					ps2.setString(2, costName);
					ps2.setString(3, costNumber);
					ps2.setString(4, costPrice);
					ps2.setString(5, String.valueOf(total_d));
					ps2.executeUpdate();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HttpServletResponse response = ServletActionContext.getResponse();  
			response.setStatus(500);
		}finally {
			try {
				if (rs1 != null)
					rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
				if(ps_add!=null)
					ps_add.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return "update";
	}
	
}
