package wingsoft.shopping.service.print;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import wingsoft.custom.CommonJsonDeal;
import wingsoft.shopping.action.BaseAction;
import wingsoft.shopping.util.Comm;
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
import java.util.List;

public class OrderPrintAction extends BaseAction {

    public String mainContent(){
        {
            System.out.println("OrderPrintAction_mainContent");
            HttpServletRequest request = ServletActionContext.getRequest();
            String orderApplyNumber =Comm.nTrim(request.getParameter("apply_no"));// 参数啊 201有两个结算明细
            if(orderApplyNumber==""&&orderApplyNumber.equals(""))
                orderApplyNumber = "182";
            String orderMain ="SELECT ast.apply_no, ast.userid, ast.sid, ast.samt, ast.indate, ast.remark, ast.username FROM app_settle ast " +
                    "WHERE  ast.apply_no = '"+orderApplyNumber+"'";
            String orderDetailsFather = "SELECT asd.ordercode, asd.amt, asd.uni_prj_code, asd.b_code, asd.apply_no FROM app_settle_dtl asd " +
                    "WHERE asd.APPLY_NO = '"+orderApplyNumber+"' ORDER BY asd.ORD ASC";
            String orderInvoice = "SELECT asi.invoice_no, asi.invoice_amt, asi.invoice_name, asi.isapp, asi.userid FROM app_settle_invoice asi " +
                    "WHERE (asi.apply_no = '"+orderApplyNumber+"') ORDER BY asi.indate ASC";

            JSONObject checkListF = super.reObject(orderMain); //结算单
            JSONArray orderDetailsF = super.reArray(orderDetailsFather); //订单明细
            JSONArray orerInvoice = super.reArray(orderInvoice);
            System.out.println(checkListF);
            System.out.println("订单明细分类:"+orderDetailsF);//订单明细分类
            System.out.println(orerInvoice);
            List<String> orderCodeList = super.gtStringKeys(orderDetailsF,"ordercode");
            System.out.println("ordercode:"+orderCodeList);
            JSONArray orderArray = new JSONArray();
            for(String ordercode: orderCodeList){
                JSONObject orderCodeCate = new JSONObject();
                JSONObject orderCode = new JSONObject();
                String childDetails = "SELECT tor.common_name, tor.chemical_name, tor.english_name, tor.cas_name, tor.specifications, tor.measurement_unit, tor.by_number, tor.price ,(tor.by_number*tor.price) as allprice FROM t_order_product tor " +
                        "where tor.ordercode = '"+ordercode+"' " +
                        "ORDER BY tor.ordercode ASC";
                String childCheckComments= "SELECT oc.check_name, oc.check_result, oc.check_msg, oc.check_date FROM order_check oc " +
                        "where  (oc.ordercode = '"+ordercode+"') ORDER BY oc.check_date DESC";
                JSONArray detailsList = super.reArray(childDetails);
                JSONArray commentsList = super.reArray(childCheckComments);
                orderCode.put("detailsList",detailsList);//订单明细
                orderCode.put("commentsList",commentsList);//审核意见
                orderCodeCate.put(ordercode,orderCode);
                orderArray.add(orderCodeCate);
            }
            System.out.println("orderArray:"+orderArray);
            return  SUCCESS;
        }
    }

}
