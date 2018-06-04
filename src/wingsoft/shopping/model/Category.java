package wingsoft.shopping.model;

public class Category {
	private String categoryid;
	private String categoryname;
	private int levels;
	private String parents;
	
	public Category(){}
	
	public Category(String categroyid, String categrpyname, int level,
			String parent) {
		super();
		this.categoryid = categroyid;
		this.categoryname = categrpyname;
		this.levels = level;
		this.parents = parent;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categroyid) {
		this.categoryid = categroyid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categrpyname) {
		this.categoryname = categrpyname;
	}

	public int getLevel() {
		return levels;
	}

	public void setLevel(int level) {
		this.levels = level;
	}

	public String getParent() {
		return parents;
	}

	public void setParent(String parent) {
		this.parents = parent;
	}

	@Override
	public String toString() {
		return "{\"categoryid\":\"" + categoryid + "\",\"categoryname\":\""
				+ categoryname + "\",\"levels\":\"" + levels + "\",\"parents\":\""
				+ parents + "\"}";
	}

	
	

}
