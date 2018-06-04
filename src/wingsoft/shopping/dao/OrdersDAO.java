package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wingsoft.shopping.model.Orders;
import wingsoft.shopping.util.DBManager;

public class OrdersDAO {
	private final String selectUser = "select * from SHP_orders where trim(userid)=?";
	private final String getid = "select seq_shp_orderid.nextval from dual";
	private final String save = "insert into SHP_orders values(?,?,?)";

	public List<Orders> selectUser(String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectUser);
		ps.setString(1, userid);
		ResultSet rs = ps.executeQuery();
		
		List<Orders> os = new ArrayList<Orders>();

		while (rs.next()){
			Orders o = new Orders();
			o.setOrderid(rs.getString(1).trim());
			o.setUserid(rs.getString(2).trim());
			o.setTime(rs.getTimestamp(3));
			os.add(o);
		}
		c.close();
		return os;
	}
	
	public String save(String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps;
		PreparedStatement ps2;
		try {
			ps2 = c.prepareStatement(getid);
			ResultSet rs = ps2.executeQuery();
			String id = null;
			if (rs.next()) {
				id = rs.getString(1).trim();
			}
			ps2.close();
			
			ps = c.prepareStatement(save);
			ps.setString(1, id);
			ps.setString(2, userid);
			ps.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			ps.executeUpdate();
			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		} finally {
			c.close();
		}
	}
}
