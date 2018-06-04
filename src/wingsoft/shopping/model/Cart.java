package wingsoft.shopping.model;

public class Cart {
	private String cartid;
	private String userid;
	private String itemid;
	private int numbers;
	
	public Cart(){}

	public Cart(String cartid, String userid, String itemid,int number) {
		super();
		this.cartid = cartid;
		this.userid = userid;
		this.itemid = itemid;
		this.numbers = number;
	}

	public String getCartid() {
		return cartid;
	}

	public void setCartid(String cartid) {
		this.cartid = cartid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getNumber() {
		return numbers;
	}

	public void setNumber(int number) {
		this.numbers = number;
	}
	
	
}
