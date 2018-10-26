package wingsoft.shopping.service.print;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wingsoft.custom.BaseAction;
import java.util.List;

/**
 * 南医大结算单
 * 打印功能
 */
public class OrderPrintAction extends BaseAction {

    public String mainContent()  {
            JSONObject obj = new JSONObject();
            System.out.println("OrderPrintAction_mainContent");
            String orderApplyNumber =super.parametersGetByJson("apply_no");// 参数啊 201有两个结算明细
            System.out.println("参数鸭:"+orderApplyNumber);
            if(orderApplyNumber==""&&orderApplyNumber.equals(""))
                orderApplyNumber = "182";
            String orderMain ="SELECT ast.apply_no, ast.userid, ast.sid, ast.samt, ast.indate, ast.remark, ast.username FROM app_settle ast " +
                    "WHERE  ast.apply_no = '"+orderApplyNumber+"'";
            String orderDetailsFather = "SELECT asd.ordercode, asd.amt, asd.uni_prj_code, asd.b_code, asd.apply_no FROM app_settle_dtl asd " +
                    "WHERE asd.APPLY_NO = '"+orderApplyNumber+"' ORDER BY asd.ORD ASC";
            String orderInvoice = "SELECT asi.invoice_no, asi.invoice_amt, asi.invoice_name,asi.invoice_doc , asi.isapp, asi.userid FROM app_settle_invoice asi " +
                    "WHERE (asi.apply_no = '"+orderApplyNumber+"') ORDER BY asi.indate ASC";
            JSONObject checkListF = super.reObject(orderMain); //结算单
            JSONArray orderDetailsF = super.reArray(orderDetailsFather); //订单明细
            JSONArray orerInvoice = super.reArray(orderInvoice);
            List<String> orderCodeList = super.gtStringKeys(orderDetailsF,"ordercode");
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
                orderCode.put("detailsList",detailsList); //订单明细
                orderCode.put("commentsList",commentsList); //审核意见
                orderCodeCate.put(ordercode,orderCode);
                orderArray.add(orderCodeCate);
            }
            obj.put("checkListF",checkListF);
            obj.put("orderDetailsF",orderDetailsF);
            obj.put("orerInvoice",orerInvoice);
            obj.put("orderArray",orderArray);
            setJsonObject(obj);
            System.out.println(obj);
            return  OBJECT;
    }
}
