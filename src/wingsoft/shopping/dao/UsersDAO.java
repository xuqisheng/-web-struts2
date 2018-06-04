package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wingsoft.shopping.util.DBManager;

public class UsersDAO {
	private final String selectUser = "select * from SHP_users where trim(userid)=?";
	private final String saveUser = "insert into SHP_users values(?,?)";

	
	public String selectUser(String userid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectUser);
		ps.setString(1, userid);
		ResultSet rs = ps.executeQuery();
		
		String s = "";

		if (rs.next()){
			s = rs.getString(2).trim();
		}
		c.close();
		return s;
	}
	
	public void saveUser(String userid, String nickname) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(saveUser);
		ps.setString(1, userid);
		ps.setString(2, nickname);
		ps.executeUpdate();
		
		c.close();
	}
}
