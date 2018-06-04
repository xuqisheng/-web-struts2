package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wingsoft.shopping.util.DBManager;

public class CateparaDAO {
private final String selectCategory = "select * from SHP_catepara where trim(categoryid)=?";
	
	public List<String> selectCategory(String categoryid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectCategory);
		ps.setString(1, categoryid);
		ResultSet rs = ps.executeQuery();
		
		List<String> ss = new ArrayList<String>();
		
		while (rs.next()){
			ss.add(rs.getString(2).trim());
		}
		c.close();
		return ss;
	}
}
