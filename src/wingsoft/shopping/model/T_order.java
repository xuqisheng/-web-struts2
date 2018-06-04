package wingsoft.shopping.model;

import java.util.Date;
/*
 * 订单
 */
public class T_order {

	  private String orderCode;// 申请单号
	  private String pro_code;//  项目号
	  private String type_id;//  订单类型，99自购…其他
	  private String status_id;// 订单状态
	  private String app_acc ;//  申请人账号
	  private String app_name;//  申请人姓名
	  private String pro_name;// 项目负责人
	  private String supplier_id;// /供应商id
	  private String app_msg;// 老师留言
	  private Double totalPrice;// 总金额
	  private Date date1;// 下单日期
	  private Date date2;// 审核通过日期
	  private Date date3;//  发货日期
	  private Date date4;//  收货日期
	  private Date date5;//   确认付款日期
	  private String delivery_man;// 送货人
	  private String delivery_man_phone;// 送货人联系方式
	  private String receipt;// 发票编号
	  private Date date6;// 期望送货日期	
	  private String getName;// 姓名
	  private String getPhone;// 联系方式
	  private String postalCode ;// 邮编
	  private String address;// 收货地址
	  private String pro_acc;// 负责人账户	
	  private String sendcode ;//  送货单号
	  private Date date7;//  购买日期，自购
	  private Date date8;//  订单受理日期
	  private Integer address_id;// 地址编号，0为物资处代收
	  private String freeze_no;//  冻结号
	  private String yb_no ;// 预约号
	  private String b_code ;//  费用项代码
	  private String shpOrderId ; //网上购物订单编号
	  private String payType ;//  支付方式，XM：选择项目直接支付，OT：提交他人代付, ZG:自购备案
	  private String manner  ;//结算方式，XM项目结算
	  private Date payDate;//支付日期
	  private String isSelf;// 是否属于自购
	  private String self_msg ;//自购原因
	  private String groupCode ;//课题组编号
	  private Integer locationId ;//存放地点,如果是危化品一定要填写
	  private String freeze_msg;//冻结信息
	  private String files ;//附件、如：管制品附件、购买清单	
	  private String pzType;// 出库入库，C:出库，R：入库
	  private String b_name;//费用项
	  private Double postage ;// 邮费
	  private String yb_type ;//预约转账方式，DG:对公转账。DS:对私转账
	  private Integer store;//仓库编号
	public T_order(String orderCode, String pro_code, String type_id,
			String status_id, String app_acc, String app_name, String pro_name,
			String supplier_id, String app_msg, Double totalPrice, Date date1,
			Date date2, Date date3, Date date4, Date date5,
			String delivery_man, String delivery_man_phone, String receipt,
			Date date6, String getName, String getPhone, String postalCode,
			String address, String pro_acc, String sendcode, Date date7,
			Date date8, Integer address_id, String freeze_no, String yb_no,
			String b_code, String shpOrderId, String payType, String manner,
			Date payDate, String isSelf, String self_msg, String groupCode,
			Integer locationId, String freeze_msg, String files, String pzType,
			String b_name, Double postage, String yb_type, Integer store) {
		super();
		this.orderCode = orderCode;
		this.pro_code = pro_code;
		this.type_id = type_id;
		this.status_id = status_id;
		this.app_acc = app_acc;
		this.app_name = app_name;
		this.pro_name = pro_name;
		this.supplier_id = supplier_id;
		this.app_msg = app_msg;
		this.totalPrice = totalPrice;
		this.date1 = date1;
		this.date2 = date2;
		this.date3 = date3;
		this.date4 = date4;
		this.date5 = date5;
		this.delivery_man = delivery_man;
		this.delivery_man_phone = delivery_man_phone;
		this.receipt = receipt;
		this.date6 = date6;
		this.getName = getName;
		this.getPhone = getPhone;
		this.postalCode = postalCode;
		this.address = address;
		this.pro_acc = pro_acc;
		this.sendcode = sendcode;
		this.date7 = date7;
		this.date8 = date8;
		this.address_id = address_id;
		this.freeze_no = freeze_no;
		this.yb_no = yb_no;
		this.b_code = b_code;
		this.shpOrderId = shpOrderId;
		this.payType = payType;
		this.manner = manner;
		this.payDate = payDate;
		this.isSelf = isSelf;
		this.self_msg = self_msg;
		this.groupCode = groupCode;
		this.locationId = locationId;
		this.freeze_msg = freeze_msg;
		this.files = files;
		this.pzType = pzType;
		this.b_name = b_name;
		this.postage = postage;
		this.yb_type = yb_type;
		this.store = store;
	}
	  
	 public T_order() {
		// TODO Auto-generated constructor stub
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getApp_acc() {
		return app_acc;
	}

	public void setApp_acc(String app_acc) {
		this.app_acc = app_acc;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getApp_msg() {
		return app_msg;
	}

	public void setApp_msg(String app_msg) {
		this.app_msg = app_msg;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Date getDate4() {
		return date4;
	}

	public void setDate4(Date date4) {
		this.date4 = date4;
	}

	public Date getDate5() {
		return date5;
	}

	public void setDate5(Date date5) {
		this.date5 = date5;
	}

	public String getDelivery_man() {
		return delivery_man;
	}

	public void setDelivery_man(String delivery_man) {
		this.delivery_man = delivery_man;
	}

	public String getDelivery_man_phone() {
		return delivery_man_phone;
	}

	public void setDelivery_man_phone(String delivery_man_phone) {
		this.delivery_man_phone = delivery_man_phone;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public Date getDate6() {
		return date6;
	}

	public void setDate6(Date date6) {
		this.date6 = date6;
	}

	public String getGetName() {
		return getName;
	}

	public void setGetName(String getName) {
		this.getName = getName;
	}

	public String getGetPhone() {
		return getPhone;
	}

	public void setGetPhone(String getPhone) {
		this.getPhone = getPhone;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPro_acc() {
		return pro_acc;
	}

	public void setPro_acc(String pro_acc) {
		this.pro_acc = pro_acc;
	}

	public String getSendcode() {
		return sendcode;
	}

	public void setSendcode(String sendcode) {
		this.sendcode = sendcode;
	}

	public Date getDate7() {
		return date7;
	}

	public void setDate7(Date date7) {
		this.date7 = date7;
	}

	public Date getDate8() {
		return date8;
	}

	public void setDate8(Date date8) {
		this.date8 = date8;
	}

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}

	public String getFreeze_no() {
		return freeze_no;
	}

	public void setFreeze_no(String freeze_no) {
		this.freeze_no = freeze_no;
	}

	public String getYb_no() {
		return yb_no;
	}

	public void setYb_no(String yb_no) {
		this.yb_no = yb_no;
	}

	public String getB_code() {
		return b_code;
	}

	public void setB_code(String b_code) {
		this.b_code = b_code;
	}

	public String getShpOrderId() {
		return shpOrderId;
	}

	public void setShpOrderId(String shpOrderId) {
		this.shpOrderId = shpOrderId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getManner() {
		return manner;
	}

	public void setManner(String manner) {
		this.manner = manner;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}

	public String getSelf_msg() {
		return self_msg;
	}

	public void setSelf_msg(String self_msg) {
		this.self_msg = self_msg;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getFreeze_msg() {
		return freeze_msg;
	}

	public void setFreeze_msg(String freeze_msg) {
		this.freeze_msg = freeze_msg;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getPzType() {
		return pzType;
	}

	public void setPzType(String pzType) {
		this.pzType = pzType;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public String getYb_type() {
		return yb_type;
	}

	public void setYb_type(String yb_type) {
		this.yb_type = yb_type;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}
	 

	
}
