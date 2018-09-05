package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wingsoft.shopping.model.Category;
import wingsoft.shopping.util.DBManager;

public class CategoryDAO {
	private final String selectLevel = "select * from SHP_category where levels =?";
	private final String selectChildren = "select * from SHP_category where trim(parents) =?";
	
	public List<Category> selectLevel(int level) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectLevel);
		ps.setInt(1, level);
		ResultSet rs = ps.executeQuery();
		
		List<Category> cas = new ArrayList<Category>();
		
		while (rs.next()){
			Category ca = new Category();
			ca.setCategoryid(rs.getString(1).trim());
			ca.setCategoryname(rs.getString(2).trim());
			ca.setLevel(rs.getInt(3));
			ca.setParent(rs.getString(4).trim());
			cas.add(ca);
		}
		c.close();
		return cas;
	}
	
	public List<Category> selectChildren(String parentid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectChildren);
		ps.setString(1, parentid);
		ResultSet rs = ps.executeQuery();
		List<Category> cas = new ArrayList<Category>();
		
		while (rs.next()){
			Category ca = new Category();
			ca.setCategoryid(rs.getString(1).trim());
			ca.setCategoryname(rs.getString(2).trim());
			ca.setLevel(rs.getInt(3));
			ca.setParent(rs.getString(4).trim());
			cas.add(ca);
		}
		c.close();
		return cas;
	}
}
