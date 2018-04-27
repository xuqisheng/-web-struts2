package wingsoft.custom;

public class Details {
	private String purchase_id;   
	private String id;
	private String product_id;    //采购详细和check详细都有
	private String specifications;//采购详细和check详细都有
	private String package_unit;//采购详细和check详细都有
	private String purchase_num; //采购详细
	private String remarks; //备注
	private String out_num;/**入库数量*/ //只有check详细里有
	private String out_price;/** 入库价格*/ //只有check详细里有

	public String getPurchase_id() {
		return purchase_id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOut_num() {
		return out_num;
	}


	public void setOut_num(String out_num) {
		this.out_num = out_num;
	}


	public String getOut_price() {
		return out_price;
	}


	public void setOut_price(String out_price) {
		this.out_price = out_price;
	}


	public void setPurchase_id(String purchase_id) {
		this.purchase_id = purchase_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getPackage_unit() {
		return package_unit;
	}

	public void setPackage_unit(String package_unit) {
		this.package_unit = package_unit;
	}

	public String getPurchase_num() {
		return purchase_num;
	}

	public void setPurchase_num(String purchase_num) {
		this.purchase_num = purchase_num;
	}

	public Details() {
		super();
	}

}
