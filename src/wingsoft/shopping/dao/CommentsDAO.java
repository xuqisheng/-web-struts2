package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wingsoft.shopping.model.Comments;
import wingsoft.shopping.util.DBManager;

public class CommentsDAO {
private final String selectItem = "select commentid,time,COMMENTTXT,userid,SHP_COMMENTS.ITEMID,ANONYMOUS from shp_comments,(select itemid from shp_item where trim(itemid) = ? or trim(parents) = ?) t where t.itemid = SHP_COMMENTS.itemid order by time desc";
	
	public List<Comments> selectItemid(String itemid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectItem);
		ps.setString(1, itemid);
		ps.setString(2, itemid);
		ResultSet rs = ps.executeQuery();
		
		List<Comments> cos = new ArrayList<Comments>();
		
		while (rs.next()){
			Comments co = new Comments();
			co.setCommentid(rs.getString(1).trim());
			co.setTime(rs.getTimestamp(2));
			co.setCommenttxt(rs.getString(3).trim());
			co.setUserid(rs.getString(4).trim());
			co.setItemid(rs.getString(5).trim());
			co.setAnonymous(rs.getInt(6));
			cos.add(co);
		}
		c.close();
		return cos;
	}
}
