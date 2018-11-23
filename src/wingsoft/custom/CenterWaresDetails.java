package wingsoft.custom;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * 中心仓库日常查询->入库明细查询
 */
public class CenterWaresDetails extends BaseAction{

    @Override
    public JSONObject getJsonObject() {
        return super.getJsonObject();
    }

    @Override
    public void setJsonObject(JSONObject jsonObject) {
        super.setJsonObject(jsonObject);
    }

    @Override
    public JSONArray getJsonArray() {
        return super.getJsonArray();
    }

    @Override
    public void setJsonArray(JSONArray jsonArray) {
        super.setJsonArray(jsonArray);
    }

    //不汇总查询
    public String noCollectSelect(){
        String MultiRows = super.parametersGetByJson("MultiRows");
        String para_str = CommonJsonDeal.getParameters(MultiRows);
        System.out.println(para_str);
        String sql =" SELECT sd.createdate, supplier.name, pc.pcname, product.type, sd.product_name, " +
                " sd.specifications, sd.package_unit, sd.in_num, sd.in_price, supplier.url, sd.pici, sd.id," +
                " product.remark ,sd.product_id " +
                " FROM stock_dtl sd, supplier supplier, pro_category pc, product product " +
                " WHERE  (sd.supplier_id = supplier.id) " +
                " AND (sd.product_id = product.id) " +
                " AND (product.category = pc.id) " +
                " AND (substr(sd.id,0,1) = 'I') " +
                "and sd.id in " + para_str+
                " ORDER BY sd.createdate DESC, sd.pici DESC, sd.id ASC ";
        System.out.println(sql);
        JSONArray frankArray = super.reArray(sql);
        JSONArray array  = CommonJsonDeal.updateJsonType(frankArray,"pcname");
        setJsonArray(array);
        return "noCollect";
    }

    //汇总查询
    public String collectSelect(){
        HttpServletRequest request = ServletActionContext.getRequest();
        // 获取session中所有的键值
        HttpSession session =   request.getSession();
        String sql =(String) session.getAttribute("288_sql");
        JSONArray frankArray = super.reArray(sql);
        JSONArray array  = CommonJsonDeal.updateJsonType(frankArray,"pcname");
        setJsonArray(array);
        return "collect";
    }

    public String outCollect(){
        System.out.println("出库汇总查询");
        HttpServletRequest request = ServletActionContext.getRequest();
        // 获取session中所有的键值
        HttpSession session   =   request.getSession();
        String sql =(String)session.getAttribute("365_sql");
        String sqlType = (String)session.getAttribute("329_sql");
        JSONArray typeArray = super.reArray(sqlType);
        JSONArray frankArray = super.reArray(sql);
        JSONArray array  = CommonJsonDeal.updateJsonType(frankArray,"name");
        JSONObject result = new JSONObject();
        result.put("jsonArray",array);
        result.put("typeArray",typeArray);
        setJsonObject(result);
        return "collect";
    }
//frame_centerWaresDetailsCollectOut
    public String outNoCollect(){
        System.out.println("出库非汇总查询");
        String MultiRows = super.parametersGetByJson("MultiRows");
        String para_str = CommonJsonDeal.getParameters(MultiRows);
        System.out.println(para_str);
        String sql ="  SELECT sd.createdate, supplier.name, sd.product_name,  sr.*, " +
                " sd.specifications, sd.package_unit, sd.out_num, sd.out_price, sd.pici, sd.id,sd.product_id,sd.class_id, " +
                " (select name from customer where id  =sr.customer_id) as custom_name " +
                " FROM stock_dtl sd, supplier supplier ,store_record sr " +
                " WHERE  (sd.supplier_id = supplier.id) " +
                " and sd.id in "+para_str +
                " and (sd.pici = sr.id )" +
                " ORDER BY sd.createdate DESC, sd.pici DESC, sd.id ASC ";
        System.out.println(sql);

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session   =   request.getSession();
        String sqlType = (String)session.getAttribute("329_sql");
        JSONArray frankArray = super.reArray(sql);
        JSONArray typeArray = super.reArray(sqlType);

        JSONArray array  = CommonJsonDeal.updateJsonType(frankArray,"custom_name");
        JSONObject result = new JSONObject();
        result.put("jsonArray",array);
        result.put("typeArray",typeArray);
        setJsonObject(result);
        return "noCollect";
    }
}
