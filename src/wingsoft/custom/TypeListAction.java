package wingsoft.custom;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
