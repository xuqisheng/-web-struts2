package wingsoft.custom;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaresCateAction extends ActionSupport {

    public JSONArray jsonArray;
    public JSONArray typeJsonArray;

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
        PreparedStatement psStore  =null;
        PreparedStatement ps = null;
        PreparedStatement psType = null;
        ResultSet rs = null;
        ResultSet rsStore = null;
        ResultSet rsType = null;
        Connection conn =null ;
        String sqlStore = "select * from store s where s.levels in (0,1)";
        String sqlType = "select t.id, t.name  from pro_category t where parents is not null";
        String sql = "select t.product_id ,t.in_num,t.in_price,t.store_id , " +
                "(select c.category  from product c where id = t.product_id) as cate  " +
                "from stock_dtl t " +
                "where substr(id,0,1) = 'I'  ";
        try {
            conn = pool.getConnection();
            psType = conn.prepareStatement(sqlType);
            rsType = psType.executeQuery();
            JSONArray jsonArrayType = new JSONArray();//类别get
            while(rsType.next()){
                JSONObject typeObj = new JSONObject();
                typeObj.put("id",CommonOperation.nTrim(rsType.getString("id")));
                typeObj.put("name",CommonOperation.nTrim(rsType.getString("name")));
                jsonArrayType.add(typeObj);
            }
            JSONArray arrayNumPrice = new JSONArray();
            ps = conn.prepareStatement(sql);
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
            System.out.println(jsonArray);
            typeJsonArray = jsonArrayType;
        }catch (NamingException na){
            na.printStackTrace();
        }catch (SQLException sle){
            sle.printStackTrace();
        }finally {
            pool.closePreparedStatement(ps);
            pool.closePreparedStatement(psStore);
            pool.closePreparedStatement(psType);
            pool.closeResultSet(rs);
            pool.closeResultSet(rsStore);
            pool.closeResultSet(rsType);
            pool.returnConnection(conn);
        }
        //and td.createdate between to_date ('"+year+"','yyyy-mm-dd') and to_date('"+month+"','yyyy-mm-dd')
        return SUCCESS;
    }

}
