package wingsoft.custom;

import net.sf.json.JSONArray;

/**
 * 中心仓库日常查询->入库明细查询
 */
public class CenterWaresDetails extends BaseAction{
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
        String sql =" SELECT sd.createdate, supplier.name, pc.pcname, product.type, sd.product_name, " +
                " sd.specifications, sd.package_unit, sd.in_num, sd.in_price, supplier.url, sd.pici, sd.id, product.remark " +
                " FROM stock_dtl sd, supplier supplier, pro_category pc, product product " +
                " WHERE  (sd.supplier_id = supplier.id) " +
                " AND (sd.product_id = product.id) " +
                " AND (product.category = pc.id) " +
                " AND (substr(sd.id,0,1) = 'I') " +
                " ORDER BY sd.createdate DESC, sd.pici DESC, sd.id ASC ";
        JSONArray frankArray = super.reArray(sql);
        JSONArray array  = CommonJsonDeal.updateJsonType(frankArray,"pcname");
        setJsonArray(array);
        return "noCollect";
    }

    //汇总查询
    public String collectSelect(){
        return "collect";
    }
}
