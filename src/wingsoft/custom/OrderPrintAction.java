package wingsoft.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class OrderPrintAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	public String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String printDoOrderF() {
		System.out.println("确认采购单子");
		System.out.println(new Date());
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ids = null;
		json = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		ids = CommonOperation.nTrim(request.getParameter("MultiRows"));
//		ids = "P20180320D1;P20180320D2";
		String para_str ="";
		for (String s:ids.split(";")) 
		{
			para_str +="'"+s+"',";
		}
		if(para_str != null) 
		{
			para_str = para_str.substring(0, para_str.length() - 1);
		}
		
		try {
String Sql =
" select (select storename from store t where storeid=od.class_id) as class_name, "+ 
" ol.purchase_id,od.id,od.product_name,od.specifications,od.package_unit,od.purchase_num,od.remarks ,od.price ," +
		"(select  st.storename from store st where st.storeid = (select p.mid_store_id from purchase p where p.id in ol.purchase_id )) as midle_store "+
" from order_dtl od,order_link ol  " + 
" where od.id = ol.order_dtl_id "+ 
" and ol.purchase_id in ("+para_str+") "+
" order by  ol.purchase_id ,od.ord ,class_name ";
System.out.println(Sql);
			json = "[";
			conn = pool.getConnection();
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				json+="{class_id:'"+CommonOperation.nTrim((rs.getString("class_name"))).replaceAll("'", "\\\\\'")+
						"',purchase_id:'"+CommonOperation.nTrim((rs.getString("purchase_id"))).replaceAll("'", "\\\\\'")+
						"',id:'"+CommonOperation.nTrim((rs.getString("id"))).replaceAll("'", "\\\\\'")+
						"',product_name:'"+CommonOperation.nTrim((rs.getString("product_name"))).replaceAll("'", "\\\\\'")+
						"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
						"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
						"',purchase_num:'"+CommonOperation.nTrim((rs.getString("purchase_num"))).replaceAll("'", "\\\\\'")+
						"',remarks:'"+CommonOperation.nTrim(rs.getString("remarks")).replaceAll("'", "\\\\\'")+
						"',price:'"+CommonOperation.nTrim(rs.getString("price"))+
						"',midle_store:'"+CommonOperation.nTrim(rs.getString("midle_store"))+
						"'},";
				flag = true;
			}
			if (flag) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]";
			System.out.println(json);//先对这个json预处理 
			///json的处理
			JSONArray jsArray=JSONArray.fromObject(json);
			List<Clazz> listClazz=new ArrayList<Clazz>();
			for(Object ob:jsArray) 
			{
				Clazz clazz= new Clazz();
				JSONObject jsonObj=JSONObject.fromObject(ob);
				clazz.setClass_id(jsonObj.getString("midle_store"));
				List<Details> list = new ArrayList<Details>();
				Details details = new Details();
				details.setPurchase_id(jsonObj.getString("purchase_id"));
				details.setRemarks(jsonObj.getString("remarks"));
				details.setId(jsonObj.getString("id"));
				details.setPackage_unit(jsonObj.getString("package_unit"));
				details.setProduct_id(jsonObj.getString("product_name"));
				details.setSpecifications(jsonObj.getString("specifications"));
				details.setPurchase_num(jsonObj.getString("purchase_num"));
				details.setPrice(jsonObj.getString("price"));
				//details.setMidle_store(jsonObj.getString("midle_store"));
				list.add(details);
				clazz.setDetails(list);
				listClazz.add(clazz);
			}
//			JSONObject obj = new JSONObject();//结果对象
			HashSet<String> keys = new HashSet<String>();
			for(Clazz caz:listClazz) 
				keys.add(caz.getClass_id());
			JSONArray result_js =new  JSONArray();
			for(String s:keys) 
			{//null , 10
				List<Details> details_list=new ArrayList<Details>();
				JSONObject jo = new JSONObject();
				for(Clazz czz:listClazz) 
				{
					if(czz.getClass_id().equals(s)) 
					{
						details_list.addAll(czz.getDetails());
						jo.put("class_id",s);
						jo.put("details",details_list);
					}
				}
				result_js.add(jo);
			}
			json=result_js.toString();
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
		return "orderPrint";
	}
	public String checkList_in(){
		{
			System.out.println("验收明细…………");
			System.out.println(new Date());
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String ids = null;
			json = "";
			HttpServletRequest request = ServletActionContext.getRequest();

			//ids = "O20180314D1;O20180315D1";//获取的测试参数、、
			ids = CommonOperation.nTrim(request.getParameter("MultiRows"));
			String para_str ="";
			for (String s:ids.split(";"))
			{
				para_str +="'"+s+"',";
			}
//		System.out.println(para_str);
			if(para_str != null)
			{
				para_str = para_str.substring(0, para_str.length() - 1);
			}
			try {

				String Sql ="select cst.name cst_name, sd.id,sd.product_name, sd.specifications, "
						+ "sd.package_unit,sd.in_num,sd.in_price " +
						" from stock_dtl sd ,customer cst,store_record ssd " +
						" where sd.pici in ("+para_str+") " +
						" and ssd.id in sd.pici " +
						" and ssd.class_id = cst.id" +
						" order by sd.ords asc ";
				System.out.println(Sql);
				json = "[";
				conn = pool.getConnection();
				ps = conn.prepareStatement(Sql);
				rs = ps.executeQuery();
				boolean flag = false;
				while (rs.next()) {
					json+="{id:'"+CommonOperation.nTrim((rs.getString("id"))).replaceAll("'", "\\\\\'")+
							"',product_name:'"+CommonOperation.nTrim((rs.getString("product_name"))).replaceAll("'", "\\\\\'")+
							"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
							"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
							"',out_num:'"+CommonOperation.nTrim((rs.getString("in_num"))).replaceAll("'", "\\\\\'")+
							"',cst_name:'"+CommonOperation.nTrim(rs.getString("cst_name")).replaceAll("'", "\\\\\'")+
							"',out_price:'"+CommonOperation.nTrim((rs.getString("in_price"))).replaceAll("'", "\\\\\'")+
							"'},";
					flag = true;
				}
				if (flag) {
					json = json.substring(0, json.length() - 1);
				}
				json += "]";

//			///json的处理
				JSONArray jsArray=JSONArray.fromObject(json);
				List<Clazz> listClazz=new ArrayList<Clazz>();

				for(String id:ids.split(";")) {
					Clazz clazz= new Clazz();
					clazz.setClass_id(id);
					List<Details> list = new ArrayList<Details>();
					for(Object ob:jsArray) {
						JSONObject jsonObj=JSONObject.fromObject(ob);
						//clazz.setClass_id(jsonObj.getString(""));
						if (jsonObj.getString("id").contains(id))
						{
							clazz.setClass_name(jsonObj.getString("cst_name"));
							Details details = new Details();
							details.setId(jsonObj.getString("id"));//get
							details.setPackage_unit(jsonObj.getString("package_unit"));//get
							details.setProduct_id(jsonObj.getString("product_name"));//get
							details.setSpecifications(jsonObj.getString("specifications"));//get
							details.setOut_num(jsonObj.getString("out_num"));
							details.setOut_price(jsonObj.getString("out_price"));
							list.add(details);
						}
					}
					clazz.setDetails(list);
					listClazz.add(clazz);
				}
				JSONArray result_js =new  JSONArray();
				JSONObject obj = new JSONObject();
				for (Clazz c:listClazz)
				{
					obj.put("class_id", c.getClass_id());
					obj.put("class_name", c.getClass_name());
					obj.put("details", c.getDetails());
					result_js.add(obj);
				}
				json = result_js.toString();
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
			return "check";
		}
	}

	public String checkList_out()
	{
		System.out.println("验收明细…………");
		System.out.println(new Date());
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ids = null;
		json = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//ids = "O20180314D1;O20180315D1";//获取的测试参数、、
		ids = CommonOperation.nTrim(request.getParameter("MultiRows"));
		String para_str ="";
		for (String s:ids.split(";")) 
		{
			para_str +="'"+s+"',";
		}
//		System.out.println(para_str);
		if(para_str != null) 
		{
			para_str = para_str.substring(0, para_str.length() - 1);
		}
		try {

String Sql ="select cst.name cst_name, sd.id,sd.product_name, sd.specifications, "
		+ "sd.package_unit,sd.out_num,sd.out_price " +
		" from stock_dtl sd ,customer cst,store_record ssd " + 
		" where sd.pici in ("+para_str+") " + 
		" and ssd.id in sd.pici " + 
		" and ssd.class_id = cst.id" + 
		" order by sd.ords asc ";
System.out.println(Sql);
			json = "[";
			conn = pool.getConnection();
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				json+="{id:'"+CommonOperation.nTrim((rs.getString("id"))).replaceAll("'", "\\\\\'")+
						"',product_name:'"+CommonOperation.nTrim((rs.getString("product_name"))).replaceAll("'", "\\\\\'")+
						"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
						"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
						"',out_num:'"+CommonOperation.nTrim((rs.getString("out_num"))).replaceAll("'", "\\\\\'")+
						"',cst_name:'"+CommonOperation.nTrim(rs.getString("cst_name")).replaceAll("'", "\\\\\'")+
						"',out_price:'"+CommonOperation.nTrim((rs.getString("out_price"))).replaceAll("'", "\\\\\'")+
						"'},";
				flag = true;
			}
			if (flag) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]";
			
//			///json的处理
			JSONArray jsArray=JSONArray.fromObject(json);
			List<Clazz> listClazz=new ArrayList<Clazz>();
			
			for(String id:ids.split(";")) {
				Clazz clazz= new Clazz();
				clazz.setClass_id(id);
				List<Details> list = new ArrayList<Details>();
			for(Object ob:jsArray) {
				JSONObject jsonObj=JSONObject.fromObject(ob);
				//clazz.setClass_id(jsonObj.getString(""));
				if (jsonObj.getString("id").contains(id)) 
				{
					clazz.setClass_name(jsonObj.getString("cst_name"));
					Details details = new Details();
					details.setId(jsonObj.getString("id"));//get 
					details.setPackage_unit(jsonObj.getString("package_unit"));//get 
					details.setProduct_id(jsonObj.getString("product_name"));//get 
					details.setSpecifications(jsonObj.getString("specifications"));//get 
					details.setOut_num(jsonObj.getString("out_num"));
					details.setOut_price(jsonObj.getString("out_price"));
					list.add(details);
				}
			}
			clazz.setDetails(list);
			listClazz.add(clazz);
			}
			JSONArray result_js =new  JSONArray();
			JSONObject obj = new JSONObject();
			for (Clazz c:listClazz) 
			{
				obj.put("class_id", c.getClass_id());
				obj.put("class_name", c.getClass_name());
				obj.put("details", c.getDetails());
				result_js.add(obj);
			}
			json = result_js.toString();
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
		return "check";
	}
	public String test() 
	{
		System.out.println("this is a test Order");
		return "";
	}
	
	public String printAllOrder() 
	{
		System.out.println("orderPrintF");
		System.out.println(new Date());
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ids = null;
		json = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		//ids = "F20180319D11";
		ids = CommonOperation.nTrim(request.getParameter("MultiRows"));
		String para_str ="";
		for (String s:ids.split(";")) 
		{
			para_str +="'"+s+"',";
		}
		//System.out.println(para_str);
		if(para_str != null) 
		{
			para_str = para_str.substring(0, para_str.length() - 1);
		}
		
		try {
String Sql =
" select (select storename from store t where storeid=od.class_id) as class_name, "+ 
" (select name from supplier where id =od.provide_id) as provide_name,"+
" ol.purchase_id,od.id,od.product_name,od.specifications,od.package_unit,od.purchase_num,od.remarks,od.price, od.provide_id ," +
		"(select  st.storename from store st where st.storeid = (select p.mid_store_id from purchase p where p.id in ol.purchase_id )) as midle_store "+
" from order_dtl od,order_link ol  " + 
" where od.id = ol.order_dtl_id "+ 
" and ol.purchase_id in ("+para_str+") "+ 
" order by  ol.purchase_id ,od.ord ,class_name ";
System.out.println(Sql);
			json = "[";
			conn = pool.getConnection();
			ps = conn.prepareStatement(Sql);
			rs = ps.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				json+="{class_id:'"+CommonOperation.nTrim((rs.getString("class_name"))).replaceAll("'", "\\\\\'")+
						"',purchase_id:'"+CommonOperation.nTrim((rs.getString("purchase_id"))).replaceAll("'", "\\\\\'")+
						"',id:'"+CommonOperation.nTrim((rs.getString("id"))).replaceAll("'", "\\\\\'")+
						"',remarks:'"+CommonOperation.nTrim(rs.getString("remarks")).replaceAll("'", "\\\\\'")+
						"',provide_name:'"+CommonOperation.nTrim(rs.getString("provide_name")).replaceAll("'", "\\\\\'")+
						"',product_name:'"+CommonOperation.nTrim((rs.getString("product_name"))).replaceAll("'", "\\\\\'")+
						"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
						"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
						"',purchase_num:'"+CommonOperation.nTrim((rs.getString("purchase_num"))).replaceAll("'", "\\\\\'")+
						"',price:'"+CommonOperation.nTrim((rs.getString("price"))).replaceAll("'", "\\\\\'")+
						"',midle_store:'"+CommonOperation.nTrim(rs.getString("midle_store"))+
						"'},";
				flag = true;
			}
			if (flag) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]";
			System.out.println("最初的json："+json);
			///json的处理
			// clazz_id  clazz_list
			JSONArray jsArray=JSONArray.fromObject(json);
			HashSet<String> class_id = new HashSet<String>();
			HashSet<String> provide_name = new HashSet<String>();
			JSONArray details_list = new JSONArray();
			for (Object ob:jsArray) 
			{
				JSONObject details = new JSONObject();
				JSONObject jsonObj=JSONObject.fromObject(ob);
				details.put("id",jsonObj.get("id"));
				details.put("purchase_id",jsonObj.get("purchase_id"));
				details.put("product_name",jsonObj.get("product_name"));
				details.put("remarks", jsonObj.get("remarks"));
				details.put("specifications",jsonObj.get("specifications"));
				details.put("package_unit", jsonObj.get("package_unit"));
				details.put("purchase_num", jsonObj.get("purchase_num"));
				details.put("provide_name", jsonObj.get("provide_name"));
				details.put("class_id", jsonObj.get("midle_store"));
				details.put("price",jsonObj.get("price"));
				provide_name.add(jsonObj.getString("provide_name"));
				class_id.add(details.getString("class_id"));
				details_list.add(details);
			}
			List<JSONObject> provide_list = new ArrayList<JSONObject>();
			for(String provideName:provide_name) {
				JSONObject provide = new JSONObject();
				List<JSONObject> provide_lis = new ArrayList<JSONObject>();
				for(Object o:details_list) {
					JSONObject jsonObj=JSONObject.fromObject(o);
					if(provideName.equals(jsonObj.get("provide_name"))) {
						provide_lis.add(jsonObj);
					}
				}
				provide.put("provide_name", provideName);
				List<JSONObject> clazzList = new ArrayList<JSONObject>();
				for(String s:class_id) {
					JSONObject clazz = new JSONObject();
					List<JSONObject> clazz_list =new ArrayList<JSONObject>();
					for(JSONObject jb:provide_lis) {
						if(jb.getString("class_id").equals(s)) {
							clazz_list.add(jb);
						}
					}
					clazz.put("clazz_id", s);
					clazz.put("clazz_name",clazz_list);
					clazzList.add(clazz);
				}
				provide.put("provide_list",clazzList);
				//provide.put("provide_list", provide_lis);
				provide_list.add(provide);
			}	
			json = provide_list.toString();
//			System.out.println(provide_list);
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
		return "allOrder";
	}
	
	
}
