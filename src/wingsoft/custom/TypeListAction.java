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
import java.util.ArrayList;
import java.util.List;

public class TypeListAction extends ActionSupport {
    public String json;
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }

    public String theData(){
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
        System.out.println(pool);
        Connection conn = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        HttpServletRequest request = ServletActionContext.getRequest();
        String sql = "select t.id, t.name,t.parents from pro_category t";
        try {
            CommonJsonDeal commonJsonDeal = CommonJsonDeal.getInstance();
            conn = pool.getConnection();
            ps1 = conn.prepareStatement(sql);
            rs1 = ps1.executeQuery();
            JSONArray typeOfJson = new JSONArray();
            while(rs1.next()){
                JSONObject child = new JSONObject();
                child.put("id", CommonOperation.nTrim(rs1.getString("id")));
                child.put("name",CommonOperation.nTrim(rs1.getString("name")));
                child.put("parents", CommonOperation.nTrim(rs1.getString("parents")));
                typeOfJson.add(child);
            }
            json = commonJsonDeal.childTreeCount(typeOfJson.toString(),"parents").toString();
            System.out.println(json);
        }catch (SQLException se){
            se.printStackTrace();
        }catch (NamingException ne){
            ne.printStackTrace();
        }finally {
            try{
                if(rs1==null)
                    rs1.close();
                if(ps1==null)
                    ps1.close();
            }catch (SQLException eqle){
                eqle.printStackTrace();
            }
            pool.returnConnection(conn);
        }
        return "printDBA";
    }


    public String selectType(){
        System.out.println("selectType Methods");
        HttpServletRequest request = ServletActionContext.getRequest();
        Connection conn = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");

        try {
            Object obj = JSONUtil.deserialize(request.getReader());
            JSONObject jsonObject = JSONObject.fromObject(obj);
            List<String> StringList =null;
            StringList=(List<String>) jsonObject.get("selectList");
            CommonJsonDeal commonJsonDeal = CommonJsonDeal.getInstance();

            String sql = "SELECT pc.pcname, product.name, product.type, product.specifications, product.base_unit,  sn.out_amt,  product.id " +
                    "FROM stock_num sn, pro_category pc, product product " +
                    "WHERE (product.id = sn.product_id(+)) " +
                    "AND (product.category = pc.id) " +
                    "and (sn.store_id = 1 or sn.store_id is null) " +
                    "and pc.pcname in "+commonJsonDeal.getParameters(StringList) +
                    "and sn.out_amt !=0 "+
                    "ORDER BY product.category ASC, product.name ASC, product.specifications ASC";
            System.out.println(sql);
            conn = pool.getConnection();
            ps1 = conn.prepareStatement(sql);
            rs1 = ps1.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while(rs1.next()){
                JSONObject child = new JSONObject();
                child.put("pcname", CommonOperation.nTrim(rs1.getString("pcname")));
                child.put("name",CommonOperation.nTrim(rs1.getString("name")));
                child.put("type", CommonOperation.nTrim(rs1.getString("type")));
                child.put("specifications",CommonOperation.nTrim(rs1.getString("specifications")));
                child.put("base_unit",CommonOperation.nTrim(rs1.getString("base_unit")));
                child.put("out_amt",CommonOperation.nTrim(rs1.getString("out_amt")));
                child.put("id",CommonOperation.nTrim(rs1.getString("id")));
                jsonArray.add(child);
            }
            json = jsonArray.toString();
            System.out.println(json);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (JSONException jsonE){
            jsonE.printStackTrace();
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(rs1==null)
                    rs1.close();
                if(ps1==null)
                    ps1.close();
            }catch (SQLException sqlE){
                sqlE.printStackTrace();
            }
            pool.returnConnection(conn);
        }
        return "getType";
    }
}

