package wingsoft.shopping.action;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaseAction extends ActionSupport {
    public static String ARRAY ="array";
    public static String OBJECT = "object";
    public JSONObject jsonObject;
    public JSONArray jsonArray;
    public String json;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
       this.json = json;
    }

    /**
     * 从数据库中获取一条记录
     * @param sql
     * @return
     */
    public JSONObject reObject(String sql){
        System.out.println("returnObject");
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn =null ;
        JSONObject jo = new JSONObject();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int number = rs.getMetaData().getColumnCount();
            if(rs.next()){
                for(int i= 1; i<=number;i++){
                    jo.put(Comm.nTrim(rs.getMetaData().getColumnName(i)).toLowerCase(),Comm.nTrim(rs.getString(i)));
                }
            }
        } catch (Exception  e){
            e.printStackTrace();
        }

        finally {
            pool.closePreparedStatement(ps);
            pool.closeResultSet(rs);
            pool.returnConnection(conn);
        }
        return  jo;
    }

    /**
     * 数据库中获取N条记录封装
     * @param sql
     * @return
     */
    public JSONArray reArray(String sql){
        System.out.println("returnArray");
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn =null ;
        JSONArray jar = new JSONArray();
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int number = rs.getMetaData().getColumnCount();
            while(rs.next()){
                JSONObject jo = new JSONObject();
                for(int i= 1; i<=number;i++){
                    jo.put(Comm.nTrim(rs.getMetaData().getColumnName(i)).toLowerCase(),Comm.nTrim(rs.getString(i)));
                }
                jar.add(jo);
            }
        } catch (Exception  e){
            e.printStackTrace();
        }

        finally {
            pool.closePreparedStatement(ps);
            pool.closeResultSet(rs);
            pool.returnConnection(conn);
        }
        return  jar;
    }

    /**
     * 获取一个关键字
     * @param obj
     * @param keyWord
     * @return
     */
    public String gtStringKey(Object obj,String keyWord){
        if(obj instanceof JSONObject){
            JSONObject childObj = JSONObject.fromObject(obj);
            return Comm.nTrim(childObj.getString(keyWord));
        }
        else{
            return null;
        }
    }

    /**
     * 获取多个关键字
     * @param obj
     * @param keyWord
     * @return
     */
    public List<String> gtStringKeys(Object obj, String keyWord){
        ArrayList<String> keyList = new ArrayList<String>();
        if(obj instanceof JSONArray) {
            JSONArray childObj = JSONArray.fromObject(obj);
            for(Object oj :childObj){
                JSONObject ja = JSONObject.fromObject(oj);
                keyList.add(Comm.nTrim(gtStringKey(ja,keyWord)));
            }
            return  keyList;
        }
        return null;
    }

}
