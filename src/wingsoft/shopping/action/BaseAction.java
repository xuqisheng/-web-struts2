package wingsoft.shopping.action;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


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
//        System.out.println("returnObject");
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
                    jo.put(CommonOperation.nTrim(rs.getMetaData().getColumnName(i)).toLowerCase(),CommonOperation.nTrim(rs.getString(i)));
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
//        System.out.println("returnArray");
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");  //server using
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
                    jo.put(CommonOperation.nTrim(rs.getMetaData().getColumnName(i)).toLowerCase(),CommonOperation.nTrim(rs.getString(i)));
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
    public String gtStringKey(JSONObject obj,String keyWord){
            return CommonOperation.nTrim(obj.getString(keyWord));
    }

    /**
     * 获取多个关键字
     * @param obj
     * @param keyWord
     * @return
     */
    public ArrayList<String> gtStringKeys(JSONArray obj, String keyWord){
        ArrayList<String> keyList = new ArrayList<String>();
            for(Object oj :obj) {
                JSONObject ja = JSONObject.fromObject(oj);
                keyList.add(CommonOperation.nTrim(gtStringKey(ja, keyWord)));
            }
            return  keyList;

    }

    /**
     * 获取参数鸭
     * @param params
     * @return 返回参数字符串
     */
    public String parametersGetByJson(String params){
        try{
            HttpServletRequest request = ServletActionContext.getRequest();
            JSONObject jo = JSONObject.fromObject(JSONUtil.deserialize(request.getReader()));
            return CommonOperation.nTrim(JSONObject.fromObject(jo).getString(params));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (JSONException jo){
            jo.printStackTrace();
        }
        return "";
    }

    public String parametersRequest(String name){
        HttpServletRequest request = ServletActionContext.getRequest();
        String str = CommonOperation.nTrim(request.getParameter(name));
        return str;
    }
}
