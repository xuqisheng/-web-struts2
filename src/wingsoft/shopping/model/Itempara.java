package wingsoft.shopping.model;

public class Itempara {
	private String itemid;
	private String parameterid;
	private String value;
	
	public Itempara(){}

	public Itempara(String itemid, String parameterid, String value) {
		super();
		this.itemid = itemid;
		this.parameterid = parameterid;
		this.value = value;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getParameterid() {
		return parameterid;
	}

	public void setParameterid(String parameterid) {
		this.parameterid = parameterid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
