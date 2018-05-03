package wingsoft.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class SettlePrintAction extends ActionSupport {
	public String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String dataFromDB() {
		System.out.println("settle ^_^");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String multiParams = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		multiParams = CommonOperation.nTrim(request.getParameter("multiParams"));//I20180403D11;I20180403D9;I20180403D8
		System.out.println(request.getParameter("multiParams"));
//		multiParams = "I20180403D11;I20180403D9;I20180403D8";
		String paramsStr ="";
		for (String s:multiParams.split(";")) 
			paramsStr +="'"+s+"',";
		if(paramsStr != null) 
			paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
		
		try {
			CommonJsonDeal commonJsonDeal = CommonJsonDeal.getInstance();
			String sql = " select sd.id,sd.product_name,sd.specifications,sd.package_unit,sd.in_num,sd.return_num,sd.in_price,(sd.in_num+sd.return_num)*sd.in_price total,sd.product_id,sd.pici"
					+ " from stock_dtl sd " + 
					" where sd.pici in ("+paramsStr+")";
			System.out.println("处理前的sql："+sql);
			
			json = "[";
			conn = pool.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				json+="{id:'"+CommonOperation.nTrim(rs.getString("id"))+
						"',product_name:'"+CommonOperation.nTrim(rs.getString("product_name"))+
						"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
						"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
						"',in_num:'"+CommonOperation.nTrim(rs.getString("in_num"))+
						"',return_num:'"+CommonOperation.nTrim(rs.getString("return_num"))+
						"',in_price:'"+CommonOperation.nTrim(rs.getString("in_price"))+
						"',total:'"+CommonOperation.nTrim(rs.getString("total"))+
						"',product_id:'"+CommonOperation.nTrim(rs.getString("product_id"))+
						"',pici:'"+CommonOperation.nTrim(rs.getString("pici"))+
						"'},";
				flag = true;
			}
			if (flag) 
				json = json.substring(0, json.length() - 1);
			json += "]";
			System.out.println(json);
			json=commonJsonDeal.dealArray(json,"pici").toString();
			json=commonJsonDeal.updateList(json, "pici").toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs!=null)
						rs.close();
					if(ps!=null)
						ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			pool.returnConnection(conn);
		}
		return "settlePrint";
	}
	
	public String allDataFromDB() {
		System.out.println("allDataFromDB ^_^");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String multiParams = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		multiParams = CommonOperation.nTrim(request.getParameter("multiParams"));//I20180416D13;I20180416D5
		String paramsStr ="";
		for (String s:multiParams.split(";")) 
			paramsStr +="'"+s+"',";
		if(paramsStr != null) 
			paramsStr = paramsStr.substring(0, paramsStr.length() - 1);

		String sql = "select sd.id,sd.product_name,sd.specifications,sd.package_unit,sd.in_num,sd.return_num,sd.in_price,(sd.in_num+sd.return_num)*sd.in_price total,sd.product_id,sd.pici," + 
				" (select t.name from supplier t where t.id  = sd.supplier_id) as supplier_name," + 
				"(select als.apply_ord from app_link als  where als.store_record_id =sd.pici) as apply_ord"+
				"  from stock_dtl sd " + 
				"   where sd.pici in "+ 
				"(select al.store_record_id from app_link al where al.apply_ord in ("+paramsStr+"))";
		try {
			CommonJsonDeal commonJsonDeal = CommonJsonDeal.getInstance();
			System.out.println("处理前的sql："+sql);
			json = "[";
			conn = pool.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				json+="{id:'"+CommonOperation.nTrim(rs.getString("id"))+
						"',product_name:'"+CommonOperation.nTrim(rs.getString("product_name"))+
						"',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
						"',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
						"',in_num:'"+CommonOperation.nTrim(rs.getString("in_num"))+
						"',return_num:'"+CommonOperation.nTrim(rs.getString("return_num"))+
						"',in_price:'"+CommonOperation.nTrim(rs.getString("in_price"))+
						"',total:'"+CommonOperation.nTrim(rs.getString("total"))+
						"',product_id:'"+CommonOperation.nTrim(rs.getString("product_id"))+
						"',pici:'"+CommonOperation.nTrim(rs.getString("pici"))+
						"',supplier_name:'"+CommonOperation.nTrim(rs.getString("supplier_name"))+
						"',apply_ord:'"+CommonOperation.nTrim(rs.getString("apply_ord"))+
						"'},";
				flag = true;
			}
			if (flag) 
				json = json.substring(0, json.length() - 1);
			json += "]";
			System.out.println("处理之前的json"+json);
			JSONArray array = commonJsonDeal.updateJsonType(json, "supplier_name");
			JSONArray finalArray = new JSONArray();
			JSONArray sr = commonJsonDeal.updateJsonTypeByList(array, "apply_ord");
			for(Object o:sr) {
				JSONObject ojb  = JSONObject.fromObject(o);
				ojb.put("list", commonJsonDeal.updateJsonTypeByList((JSONArray) ojb.get("list"), "pici"));
				finalArray.add(ojb);
			}
			json = finalArray.toString();
System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
				try {
					if(ps!=null)
						ps.close();
					if(rs!=null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			pool.returnConnection(conn);
		}
		return "allSettle";
	}
	
}
