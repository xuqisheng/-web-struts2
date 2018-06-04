package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wingsoft.shopping.model.Parameter;
import wingsoft.shopping.util.DBManager;

public class ParameterDAO {
	private final String selectParameter = "select * from SHP_parameter where trim(parameterid)=?";
	
	public Parameter selectParameter(String parameterid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectParameter);
		System.out.println(selectParameter);
		System.out.println(parameterid);
		ps.setString(1, parameterid);
		ResultSet rs = ps.executeQuery();
		
		Parameter pa = new Parameter();
		
		if (rs.next()){
			pa.setParameterid(rs.getString(1).trim());
			pa.setParametername(rs.getString(2).trim());
			pa.setParametertype(rs.getString(3).trim());
		}
		c.close();
		return pa;
	}
}
