package wingsoft.shopping.model;

public class Catepara {
	private String categoryid;
	private String parameterid;
	
	public Catepara(){}

	public Catepara(String categoryid, String parameterid) {
		super();
		this.categoryid = categoryid;
		this.parameterid = parameterid;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categroyid) {
		this.categoryid = categroyid;
	}

	public String getParameterid() {
		return parameterid;
	}

	public void setParameterid(String parameterid) {
		this.parameterid = parameterid;
	}
	
	
}
