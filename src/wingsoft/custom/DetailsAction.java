package wingsoft.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class DetailsAction extends ActionSupport{
	private static final long serialVersionUID = 3404623771668905007L;
	public String json;
	public String typeJson;
	public String storeNameJson ;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTypeJson() {
		return typeJson;
	}

	public void setTypeJson(String typeJson) {
		this.typeJson = typeJson;
	}

	public String getStoreNameJson() {
		return storeNameJson;
	}

	public void setStoreNameJson(String storeNameJson) {
		this.storeNameJson = storeNameJson;
	}

	public String dataFromDB() {
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement psStoreNames= null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rsStoreNames = null;
//暂时	
		HttpServletRequest request = ServletActionContext.getRequest();
		//String cost_id = CommonOperation.nTrim(request.getParameter("tableID"));
		String year = CommonOperation.nTrim(request.getParameter("year"));//this year means STARTS TIME
		String month = CommonOperation.nTrim(request.getParameter("month"));
		//System.out.println(year+"  "+month);

		String sql1 = "select td.id as pici ,t.id,t.class_id,t.straight_toclass as isClass, " +
				"(select tc.storename from store tc where tc.storeid= t.class_id) storename, " + 
				"(select c.pro_cate  as cateId from product c where id =td.product_id) as pro_cate, " + 
				"(select c.category  as cateId from product c where id =td.product_id) as cate, "+
				"td.out_num,td.out_price, " + 
				"sum(td.out_num*td.out_price) as acount  " + //c.pro_cate
				"from store_record t,stock_dtl td " + 
				"where t.store_id='1' " + 
				"and t.type='1' " + 
				"and t.status not in ('3','4','5') " + 
				"and t.id = td.pici(+) " +
				"and td.createdate between to_date ('"+year+"','yyyy-mm-dd') and to_date('"+month+"','yyyy-mm-dd') "+
				"group by t.id,td.id,t.class_id,t.straight_toclass ,t.class_id,td.out_num,td.out_price,td.product_id " +
				"order by td.id ";
		String sql2 = "select t.id, t.name,t.parents from pro_category t"; 
		String sqlStore = "select t.* from store t";
		try {
			conn = pool.getConnection();
			ps1=conn.prepareStatement(sql1);
			rs1 = ps1.executeQuery();
			JSONArray rs1JsonArray = new JSONArray();
			while (rs1.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("pici", CommonOperation.nTrim(rs1.getString("pici")));
				jsonObj.put("id",CommonOperation.nTrim(rs1.getString("id")));
				jsonObj.put("class_id",CommonOperation.nTrim(rs1.getString("class_id")));
				jsonObj.put("isClass", CommonOperation.nTrim(rs1.getString("isclass")));
				jsonObj.put("storeName", CommonOperation.nTrim(rs1.getString("storename")));
				jsonObj.put("acount", CommonOperation.nTrim(rs1.getString("acount")));
				jsonObj.put("cate", CommonOperation.nTrim(rs1.getString("cate")));

				if(CommonOperation.nTrim(rs1.getString("pro_cate")).split(",").length!=0){
					jsonObj.put("pro_cate", CommonOperation.nTrim(CommonOperation.nTrim(rs1.getString("pro_cate")).split(",")[1]));
				}else{
					jsonObj.put("pro_cate","00");
				}
				rs1JsonArray.add(jsonObj);
			}
System.out.println("rs1JsonArray"+rs1JsonArray);
		ps2 = conn.prepareStatement(sql2);
		rs2 = ps2.executeQuery();
		//this "typeOfJson" contains all of category
		JSONArray typeOfJson = new JSONArray();
		while (rs2.next()) {
			JSONObject child = new JSONObject();
			child.put("id", CommonOperation.nTrim(rs2.getString("id")));
			child.put("name",CommonOperation.nTrim(rs2.getString("name")));
			child.put("parents", CommonOperation.nTrim(rs2.getString("parents")));
			typeOfJson.add(child);
		}	
		typeOfJson = CommonJsonDeal.childTreeCount(typeOfJson.toString(), "parents");
System.out.println("typeOfJson:"+typeOfJson);//分类啊
		JSONArray tempStockJsonArray =CommonJsonDeal.updateJsonType(rs1JsonArray, "storeName");
		JSONArray resultList = new JSONArray();
		for(Object jArray:tempStockJsonArray) {
			JSONObject jObject = JSONObject.fromObject(jArray);
			JSONArray jli = CommonJsonDeal.updateJsonType(JSONArray.fromObject(jObject.get("list")), "pro_cate"); //子list
			JSONArray li_result = new JSONArray();
			for(Object jli_c : jli) {//遍历子分类
				JSONObject jli_childObj = JSONObject.fromObject(jli_c);
				JSONArray li_childList = CommonJsonDeal.updateJsonType(JSONArray.fromObject(jli_childObj.get("list")), "cate");
				jli_childObj.put("list", li_childList);
				li_result.add(jli_childObj);
			}
			jli = li_result;
			jObject.put("list",jli);
			resultList.add(jObject);
		}

		psStoreNames=conn.prepareStatement(sqlStore);
		rsStoreNames = psStoreNames.executeQuery();
		JSONArray storeNamesArray = new JSONArray();
		while(rsStoreNames.next()){
			JSONObject store_jo = new JSONObject();
			store_jo.put("storename",CommonOperation.nTrim(rsStoreNames.getString("storename")));
			storeNamesArray.add(store_jo);
		}

		storeNameJson=storeNamesArray.toString();
		json=resultList.toString();
		typeJson = typeOfJson.toString();
//		System.out.println("json:"+json);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs1 != null)
					rs1.close();
				if (rs2 != null)
					rs2.close();
				if (rsStoreNames!=null)
					rsStoreNames.close();
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
				if(psStoreNames!=null)
					psStoreNames.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.returnConnection(conn);
		}
		return "printDBA";
	}
}
