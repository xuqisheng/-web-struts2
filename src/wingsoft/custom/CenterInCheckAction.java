package wingsoft.custom;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class CenterInCheckAction extends BaseAction {
    public String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String centerIn(){
        String params = super.parametersRequest("multiParams");
        String sql =" select cp.product_name, cp.supplier_name,cp.in_num,cp.pack_unit as package_unit, cp.spec as specifications, " +
                " cp.in_price,(cp.in_num)*cp.in_price as total ,cp.check_pic_id as pici , cp.birth_num ,cp.dep,cp.save_time," +
                " cp.produce_nuame,cp.supplier_person,cp.file_pic   " +
                " from check_page cp where cp.CHECK_PIC_ID in  "+CommonJsonDeal.getParameters(Arrays.asList(params.split(";")));
        System.out.println(sql);
        setJson(super.reArray(sql).toString());
        JSONArray array = CommonJsonDeal.updateJsonType(JSONArray.fromObject(json), "supplier_name");
        JSONArray sr = CommonJsonDeal.updateJsonTypeByList(array, "pici");
        json = sr.toString();
        return SUCCESS;
    }

    public String centerInOld(){
      {
          System.out.println("centerInCheck ^_^");
          ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
          Connection conn = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          String multiParams = null;
          HttpServletRequest request = ServletActionContext.getRequest();
          multiParams = CommonOperation.nTrim(request.getParameter("multiParams")); //I20180416D13;I20180416D5
          String paramsStr ="";
          for (String s:multiParams.split(";"))
              paramsStr +="'"+s+"',";
          if(paramsStr != null)
              paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
//          String newSql = "select cp.CHECK_PIC_DTL_ID as ";
          String oldSql = "select sd.id,sd.product_name,sd.specifications,sd.package_unit,sd.in_num,sd.return_num,sd.in_price," +
                  "(sd.in_num+sd.return_num)*sd.in_price total,sd.product_id,sd.pici, " +
                  "(select t.name from supplier t where t.id  = sd.supplier_id) as supplier_name,(select als.apply_ord from app_link als " +
                  " where als.store_record_id =sd.pici) as apply_ord " +
                  " from stock_dtl sd    where sd.pici in ("+paramsStr+")";
          try {
              System.out.println("处理前的sql："+oldSql);
              json = "[";
              conn = pool.getConnection();
              ps = conn.prepareStatement(oldSql);
              rs = ps.executeQuery();
              boolean flag = false;
              while (rs.next()) {
                  json+="{id:'"+CommonOperation.nTrim(rs.getString("id"))+
                          "',product_name:'"+CommonOperation.nTrim(rs.getString("product_name"))+
                          "',specifications:'"+CommonOperation.nTrim((rs.getString("specifications"))).replaceAll("'", "\\\\\'")+
                          "',package_unit:'"+CommonOperation.nTrim((rs.getString("package_unit"))).replaceAll("'", "\\\\\'")+
                          "',in_num:'"+CommonOperation.nTrim(rs.getString("in_num"))+
                          "',in_price:'"+CommonOperation.nTrim(rs.getString("in_price"))+
                          "',total:'"+CommonOperation.nTrim(rs.getString("total"))+
                          "',product_id:'"+CommonOperation.nTrim(rs.getString("product_id"))+
                          "',pici:'"+CommonOperation.nTrim(rs.getString("pici"))+
                          "',supplier_name:'"+CommonOperation.nTrim(rs.getString("supplier_name"))+
                          "'},";
                  flag = true;
              }
              if (flag)
                  json = json.substring(0, json.length() - 1);
              json += "]";
              System.out.println("处理之前的json"+json);
              JSONArray array = CommonJsonDeal.updateJsonType(JSONArray.fromObject(json), "supplier_name");
              JSONArray finalArray = new JSONArray();
              JSONArray sr = CommonJsonDeal.updateJsonTypeByList(array, "pici");
              json = sr.toString();
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
          return SUCCESS;
      }

  }
}
