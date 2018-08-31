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

public class OrderPrintAction extends BaseAction {

    public String mainContent(){
        {
            System.out.println("OrderPrintAction_mainContent");
            String orderApplyNumber = "183";// 参数啊
            String orderMain ="SELECT ast.apply_no, ast.userid, ast.sid, ast.samt, ast.indate, ast.remark, ast.username FROM app_settle ast " +
                    "WHERE  ast.apply_no = '"+orderApplyNumber+"'";
            String orderDetailsFather = "SELECT asd.ordercode, asd.amt, asd.uni_prj_code, asd.b_code, asd.apply_no FROM app_settle_dtl asd " +
                    "WHERE asd.APPLY_NO = '"+orderApplyNumber+"' ORDER BY asd.ORD ASC";
            String orderInvoice = "SELECT asi.invoice_no, asi.invoice_amt, asi.invoice_name, asi.isapp, asi.userid FROM app_settle_invoice asi " +
                    "WHERE (asi.apply_no = '"+orderApplyNumber+"') ORDER BY asi.indate ASC";

            JSONObject checkListF = super.reObject(orderMain);
            JSONArray  orderDetailsF = super.reArray(orderDetailsFather);
            JSONArray orerInvoice = super.reArray(orderInvoice);
            System.out.println(checkListF);
            System.out.println(orderDetailsF);
            System.out.println(orerInvoice);
            String ordercode = super.gtStringKey(orderDetailsF,"ordercode");
            System.out.println("ordercode:"+ordercode);
            for(Object j : orderDetailsF){
                String childDetails = "SELECT tor.common_name, tor.chemical_name, tor.english_name, tor.cas_name, tor.specifications, tor.measurement_unit, tor.by_number, tor.price ,(tor.by_number*tor.price) as allprice FROM t_order_product tor " +
                        "where tor.ordercode = '"+ordercode+"' " +
                        "ORDER BY tor.ordercode ASC";
                String childCheckComments= "SELECT oc.check_name, oc.check_result, oc.check_msg, oc.check_date FROM order_check oc " +
                        "where  (oc.ordercode = '"+ordercode+"') ORDER BY oc.check_date DESC";
                JSONArray detailsList = super.reArray(childDetails);
                JSONArray commentsList = super.reArray(childCheckComments);
                System.out.println("detailsList:"+detailsList.toString());
                System.out.println("commentsList:"+commentsList.toString());
            }

            return  SUCCESS;
        }
    }

}
