package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wingsoft.shopping.model.Cart;
import wingsoft.shopping.util.DBManager;

public class CartDAO {
	private final String selectUser = "select * from SHP_cart where userid=rpad(?,30,' ') and orderid is null";
	private final String selectSame = "select * from SHP_cart where userid=rpad(?,30,' ') and itemid=rpad(?,30,' ') and orderid is null";
	private final String deleteAll = "delete from SHP_cart where userid=rpad(?,30,' ') and orderid is null";
	/**吴斌，修改为merge**/
	//private final String save = "insert into SHP_cart(cartid,userid,itemid,numbers) values(seq_shp_cartid.nextval,rpad(?,30,' '),rpad(?,30,' '),?)";
	private final String save = "MERGE INTO SHP_CART sc "+
								"USING (SELECT rpad(?,30,' ') AS userid,rpad(?,30,' ') AS itemid,? as numbers FROM dual) d2 "+
								"ON ( sc.userid=d2.userid and sc.itemid=d2.itemid and sc.orderid is null) "+
								"WHEN MATCHED THEN "+
								"    UPDATE SET sc.numbers=sc.numbers+d2.numbers "+
								"WHEN NOT MATCHED THEN   "+
								"    insert  (cartid,userid,itemid,numbers) values(seq_shp_cartid.nextval,d2.userid,d2.itemid,d2.numbers)";
	
	private final String delete = "delete from SHP_cart where cartid = rpad(?,30,' ') and userid = rpad(?,30,' ')";
	private final String changenum = "update SHP_cart set numbers = ? where cartid = rpad(?,30,' ') and userid = rpad(?,30,' ')";
	private final String addorder = "update SHP_cart set orderid = rpad(?,30,' ') where userid = rpad(?,30,' ') and orderid is null";

	public List<Cart> selectUser(String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectUser);
		ps.setString(1, userid);
		ResultSet rs = ps.executeQuery();
		
		List<Cart> cas = new ArrayList<Cart>();

		while (rs.next()){
			Cart ca = new Cart();
			ca.setCartid(rs.getString(1).trim());
			ca.setUserid(rs.getString(2).trim());
			ca.setItemid(rs.getString(3).trim());
			ca.setNumber(rs.getInt(4));
			cas.add(ca);
		}
		c.close();
		return cas;
	}
	
	public Cart findSame(Cart cart) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(selectSame);
			ps.setString(1, cart.getUserid());
			ps.setString(2, cart.getItemid());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Cart ca = new Cart();
				ca.setCartid(rs.getString(1).trim());
				ca.setUserid(rs.getString(2).trim());
				ca.setItemid(rs.getString(3).trim());
				ca.setNumber(rs.getInt(4));
				c.close();
				return ca;
			} else {
				c.close();
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			c.close();
		}
		
	}
	
	public boolean save(Cart cart) throws SQLException {
		//Cart same = findSame(cart);
		//if (same!=null) {
		//	return changeNum(same.getCartid(),same.getNumber()+cart.getNumber(),same.getUserid());
		//} else {
			Connection c = DBManager.getConnection();
			PreparedStatement ps;
			try {
				System.out.println(save);
				ps = c.prepareStatement(save);
				System.out.println(cart.getItemid());
				ps.setString(1, cart.getUserid());
				ps.setString(2, cart.getItemid());
				ps.setInt(3, cart.getNumber());
				ps.executeUpdate();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				c.close();
			}
		//}
	}

	public boolean deleteALl(String userid) throws  SQLException{
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try{
			ps  = c.prepareStatement(deleteAll);
			ps.setString(1,userid);
			int index = ps.executeUpdate();
			if(index==1)
				return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			c.close();
		}
		return false;
	}

	public boolean delete(String cartid,String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(delete);
			ps.setString(1, cartid);
			ps.setString(2, userid);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			c.close();
		}
		
	}
	
	public boolean changeNum(String cartid,int number,String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(changenum);
			ps.setInt(1, number);
			ps.setString(2, cartid);
			ps.setString(3, userid);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			c.close();
		}
		
	}
	
	public boolean addOrder(String userid, String orderid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(addorder);
			ps.setString(1, orderid);
			ps.setString(2, userid);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			c.close();
		}


	}
}
