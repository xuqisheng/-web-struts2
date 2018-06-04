package wingsoft.shopping.model;

public class Item {
	private String itemid;
	private String itemname;
	private double prize;
	private String itemimg;
	private String itemcate;
	private String itempage;
	private String parents;
	private String itemimgupload;
	private String itempara;
	
	
	public Item(){}

	public Item(String itemid, String itemname, double prize, String itemimg,String itemcate,String itempage,String parents, String itemimgupload,String itempara) {
		super();
		this.itemid = itemid;
		this.itemname = itemname;
		this.prize = prize;
		this.itemimg = itemimg;
		this.itemcate = itemcate;
		this.itempage = itempage;
		this.itemimgupload = itemimgupload;
		this.parents = parents;
		this.itempara = itempara;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public String getItemimg() {
		return itemimg;
	}

	public void setItemimg(String itemimg) {
		this.itemimg = itemimg;
	}

	public String getItemimgupload() {
		return itemimgupload;
	}

	public void setItemimgupload(String itemimgupload) {
		this.itemimgupload = itemimgupload;
	}

	public String getItemcate() {
		return itemcate;
	}

	public void setItemcate(String itemcate) {
		this.itemcate = itemcate;
	}
	
	public String getItempage() {
		return itempage;
	}

	public void setItempage(String itempage) {
		this.itempage = itempage;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getItempara() {
		return itempara;
	}

	public void setItempara(String itempara) {
		this.itempara = itempara;
	}

	@Override
	public String toString() {
		return "{\"itemid\":\"" + itemid + "\",\"itemname\":\"" + itemname + "\","
				+ "\"prize\":\"" + prize + "\","
				+ "\"itemimg\":\"" + itemimg + "\","
				+ "\"itemimgupload\":\"" + itemimgupload + "\","
				+ "\"itemcate\":\"" + itemcate + "\","
				+ "\"itempage\":\"" + itempage + "\","
				+ "\"parents\":\"" + parents + "\","
				+ "\"itempara\":" + itempara + "}";
	}
	
	
}
