package wingsoft.custom;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaresCateAction extends ActionSupport {

    public JSONArray jsonArray;
    public JSONArray typeJsonArray;
    public JSONObject name;

    public JSONObject getName() {
        return name;
    }

    public void setName(JSONObject name) {
        this.name = name;
    }

    public JSONArray getTypeJsonArray() {
        return typeJsonArray;
    }

    public void setTypeJsonArray(JSONArray typeJsonArray) {
        this.typeJsonArray = typeJsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    //get开头的方法，会提交两次，this????
    public String waresCateData(){
        System.out.println("WaresCateAction_waresCateData");
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
        HttpServletRequest request = ServletActionContext.getRequest();
        PreparedStatement ps = null;
        PreparedStatement ps2=null;
        PreparedStatement psType = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rsType = null;
        Connection conn =null ;
        String sqlType = "select t.id, t.name  from pro_category t where parents is not null";
        try {
            conn = pool.getConnection();
            psType = conn.prepareStatement(sqlType);
            rsType = psType.executeQuery();
            JSONArray jsonArrayType = new JSONArray();//类别get
            Object obj = JSONUtil.deserialize(request.getReader());
//            System.out.println(obj);
            while(rsType.next()){
                JSONObject typeObj = new JSONObject();
                typeObj.put("id",CommonOperation.nTrim(rsType.getString("id")));
                typeObj.put("name",CommonOperation.nTrim(rsType.getString("name")));
                jsonArrayType.add(typeObj);
            }
            String startTime =  JSONObject.fromObject(obj).getString("startTime");
            String endTime = JSONObject.fromObject(obj).getString("endTime");
            String class_id = JSONObject.fromObject(obj).getString("class_id");
            String sql = "select t.product_id ,t.in_num,t.in_price,t.store_id , " +
                    "(select c.category  from product c where id = t.product_id) as cate " +
                    "from stock_dtl t " +
                    "where substr(id,0,1) = 'I'  "+
                    " and t.store_id = "+class_id+
                    " and t.createdate between to_date ('"+startTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') ";
            String sql2 = "select tp.name as storeName from customer tp where tp.id = "+class_id;
            System.out.println(sql2);
            ps2 = conn.prepareStatement(sql2);
            rs2 = ps2.executeQuery();
            JSONObject nameJ = new JSONObject();
            if(rs2.next()){
                nameJ.put("storeName",rs2.getString(CommonOperation.nTrim("storeName")));
            }
            JSONArray arrayNumPrice = new JSONArray();
            ps = conn.prepareStatement(sql);
System.out.println(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                JSONObject jo = new JSONObject();
                jo.put("product_id",CommonOperation.nTrim(rs.getString("product_id")));
                jo.put("in_num",CommonOperation.nTrim(rs.getString("in_num")));
                jo.put("in_price",CommonOperation.nTrim(rs.getString("in_price")));
                jo.put("store_id",CommonOperation.nTrim(rs.getString("store_id")));
                jo.put("cate",CommonOperation.nTrim(rs.getString("cate")));
                arrayNumPrice.add(jo);
            }
            CommonJsonDeal cjd = CommonJsonDeal.getInstance();
            arrayNumPrice  = cjd.updateJsonType(arrayNumPrice.toString(),"cate");
            jsonArray = arrayNumPrice;
            typeJsonArray = jsonArrayType;
            name =nameJ;
        }catch (SQLException sle){
            sle.printStackTrace();
        }catch (IOException eo){
            eo.printStackTrace();
        }catch (JSONException jsonE){
            jsonE.printStackTrace();
        }
        finally {
            pool.closePreparedStatement(ps);
            pool.closePreparedStatement(ps2);
            pool.closePreparedStatement(psType);
            pool.closeResultSet(rs);
            pool.closeResultSet(rsType);
            pool.closeResultSet(rs2);
            pool.returnConnection(conn);
        }
        return SUCCESS;
    }

}
