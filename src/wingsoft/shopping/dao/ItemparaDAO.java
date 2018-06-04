package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import wingsoft.shopping.model.Itempara;
import wingsoft.shopping.util.DBManager;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.common.CommonOperation;
public class ItemparaDAO {
	private final String selectItem = "select SHP_itempara.* from SHP_itempara,SHP_PARAMETER where itemid=rpad(?,30,' ') AND  SHP_itempara.PARAMETERID=SHP_PARAMETER.PARAMETERID and SHP_PARAMETER.ISSHOWPARAM='T' and SHP_itempara.value is not null";
	private final String selectPara = "select * from SHP_itempara where parameterid=rpad(?,30,' ') and SHP_itempara.value is not null";
	private final String findTopForPara1 = "select replace(value,'\"','\\\"') value from (select COUNT(*) c,value from SHP_itempara,(select itemid from SHP_item where ";
	//private final String findTopForPara2 = ") b,(select shp_itempara.itemid from shp_itempara where shp_itempara.value='' ) where b.itemid = SHP_itempara.itemid and trim(PARAMETERID) = ? GROUP BY value) a where rownum<=? order by c desc";
	/**
	 * 吴斌 20170524
	 * **/
	private final String findTopForPara2 = ") b  where shp_itempara.value is not null and b.itemid = SHP_itempara.itemid and trim(PARAMETERID) = ? GROUP BY value) a where rownum<=? order by c desc";

	//private final String findTopForPara2 = ") b,(select shp_itempara.itemid from shp_itempara where shp_itempara.value is not null ) where b.itemid = SHP_itempara.itemid and trim(PARAMETERID) = ? GROUP BY value) a where rownum<=? order by c desc";

	private final String findTopPara1 = "select parameterid from (select COUNT(*) c,parameterid from (select SHP_itempara.parameterid from SHP_itempara,SHP_PARAMETER,(select itemid from SHP_item where ";
	private final String findTopPara2 = ") b where SHP_itempara.itemid = b.itemid and SHP_itempara.PARAMETERID=SHP_PARAMETER.PARAMETERID and SHP_PARAMETER.ISNAVIGATION='T' and  SHP_itempara.value is not null ) GROUP BY parameterid) a where rownum<=? order by c desc";
	private final String findTopPara3 = "select parameterid,c from (select COUNT(*) c,parameterid  from SHP_itempara SHP_itempara,collection c  " +
										" where SHP_itempara.Itemid=c.itemid group by SHP_itempara.Parameterid and c.userid=rpad(?,50,' ')) ";
	private final String findNumPara1 = "select MAX(value) max,MIN(value) min from SHP_itempara,(select itemid from SHP_item where ";
	private final String findNumPara2 = ") b where SHP_itempara.itemid = b.itemid and trim(PARAMETERID) = ?";
		
	public List<Itempara> selectItem(String itemid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectItem);
		ps.setString(1,itemid);
		ResultSet rs = ps.executeQuery();
		
		List<Itempara> ips = new ArrayList<Itempara>();
		String SVal = "";
		while (rs.next()){
			Itempara ip = new Itempara();
			SVal = Comm.nTrim(rs.getString(3));
			if (SVal!=""&&!"".equals(SVal)&&SVal!=null) {
				ip.setItemid(Comm.nTrim(rs.getString(1)));
				ip.setParameterid(Comm.nTrim(rs.getString(2)));
				ip.setValue(SVal);	
			}
			ips.add(ip);
		}
		c.close();
		return ips;
	}
	
	public List<Itempara> selectPara(String paraid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectPara);
		ps.setString(1,paraid);
		ResultSet rs = ps.executeQuery();
		
		List<Itempara> ips = new ArrayList<Itempara>();
		
		while (rs.next()){
			Itempara ip = new Itempara();
			ip.setItemid(rs.getString(1).trim());
			ip.setParameterid(rs.getString(2).trim());
			ip.setValue(rs.getString(3).trim());
			ips.add(ip);
		}
		c.close();
		return ips;
	}
	
	//前n个筛选值
	public List<String> findParaValue(String paraid,String categoryid, String keyword, int top) throws SQLException {
		Connection c = DBManager.getConnection();
		String sql = findTopForPara1+" instr(itemcate,'."+categoryid+".')>0 ";//" itemcate like '%."+categoryid+".%' ";
	   System.out.println("keyword="+keyword);
		if (!"".equals(keyword)&&null!=keyword) {
			sql+=" and (";
			String[] keys = keyword.split(" ");
			for (int i=0;i<keys.length;i++) {
				if (i==0) {
					sql+="instr(itemname,'"+keys[i]+"') >0 ";// "itemname like '%"+keys[i]+"%' ";
				} else {
					sql+=" or instr(itemname,'"+keys[i]+"') >0 ";
				}
				
			}
			
			sql+=") ";
			if (!"".equals(keys)&&null!=keys&&keys.length>1) {
			sql+="union all select itemid from shp_itempara where (";
			for(int i=0;i<keys.length;i++) {
				if(i==0){
					//sql+="value like '%"+keys[i]+"%' ";
					sql+="  instr(value,'"+keys[i]+"') >0   ";
				}else {
					//sql+="value like '%"+keys[i]+"%' ";
					sql+=" or instr(value,'"+keys[i]+"') >0 ";
				}
			}
			sql+=") "; 
			}
		}
		
		sql+=findTopForPara2;
		System.out.println("顶部快捷");
		
		System.out.println("findparavalue:"+sql);
		PreparedStatement ps=c.prepareStatement(sql);
		ps.setString(1,paraid);
		ps.setInt(2, top);
		ResultSet rs = ps.executeQuery();
		
		List<String> ips = new ArrayList<String>();
		
		while (rs.next()){
			ips.add(Comm.nTrim(rs.getString(1)));
		}
		c.close();
		return ips;
	}
	
	//前n位筛选项
	public List<String> findTopPara(String categoryid, String keyword, int top,boolean IsCollection) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		if (IsCollection) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String userid = ""; 
					userid = (String) request.getSession().getAttribute("userId");
			sql = findTopPara3;
			System.out.println("item="+sql);
			ps =c.prepareStatement(sql);
				ps.setString(1, userid);
				rs = ps.executeQuery();
		} 
		else{
		sql = findTopPara1+"itemcate like '%."+categoryid+".%' ";
		if (keyword!=null) {
			sql+=" and (";
			String[] keys = keyword.split(" ");
			for (int i=0;i<keys.length;i++) {
				if (i==0) {
					sql+="itemname like '%"+keys[i]+"%'";
				} else {
					sql+=" or itemname like '%"+keys[i]+"%'";
				}
			}
			sql+=") ";
			if (!"".equals(keys)&&null!=keys&&keys.length>1) {
				sql+="union all select itemid from shp_itempara where (";
				for(int i=0;i<keys.length;i++) {
					if(i==0){
						sql+="value like '%"+keys[i]+"%' ";
					}else {
						sql+="value like '%"+keys[i]+"%' ";
					}
				}
				sql+=") ";
			}
			
		}
		
		
	
		sql+=findTopPara2;
		System.out.println("findtoppara:"+sql);
		ps =c.prepareStatement(sql);
		ps.setInt(1, top);
		rs = ps.executeQuery();
		}
		List<String> ips = new ArrayList<String>();
		
		while (rs.next()){
			ips.add(rs.getString(1).trim());
		}
		c.close();
		return ips;
	}
	
	public List<String> findNumPara(String paraid,String categoryid, String keyword) throws SQLException {
		/*
		Connection c = DBManager.getConnection();
		String sql = findNumPara1+"itemcate like '%."+categoryid+".%' ";
		if (keyword!=null) {
			sql+=" and (";
			String[] keys = keyword.split(" ");
			for (int i=0;i<keys.length;i++) {
				if (i==0) {
					sql+="itemname like '%"+keys[i]+"%'";
				} else {
					sql+=" or itemname like '%"+keys[i]+"%'";
				}
			}
			sql+=") ";
		}
		sql+=findNumPara2;
		System.out.println(sql);
		PreparedStatement ps=c.prepareStatement(sql);
		ps.setString(1,paraid);
		ResultSet rs = ps.executeQuery();
		List<String> ips = new ArrayList<String>();
		
		if (rs.next()){
			int max = rs.getInt(1);
			int min = rs.getInt(2);
			int tmp = (max-min)/4;
			ips.add("0~"+(min+tmp));
			ips.add((min+tmp)+"~"+(min+2*tmp));
			ips.add((min+2*tmp)+"~"+(max-tmp));
			ips.add((max-tmp)+"~"+max);
		}
		c.close();
		return ips;*/
		List<String> ips = new ArrayList<String>();
		ips.add("0~500");
		ips.add("500~1000");
		ips.add("1000~1500");
		ips.add("1500~2000");
		ips.add("2000~99999");
		return ips;
	}
	
	public String GetPara(String itemid){
		Connection c = DBManager.getConnection();
		String sql = "select t.parameterid,t.value from SHP_ITEMPARA t where t.itemid='"+itemid+"'";
		System.out.println("findtoppara:"+sql);
		PreparedStatement ps;
		
		String itp = "";
		try {
			ps = c.prepareStatement(sql);
		
		//ps.setString(1, itemid);
		ResultSet rs = ps.executeQuery();
		
		itp = "{";
		int a = 0;
		while (rs.next()){
			if (a==0) {
				itp = itp + "\""+CommonOperation.nTrim(rs.getString("parameterid").trim())+"\":\"" + CommonOperation.nTrim(rs.getString("value").trim()) + "\"";
			}else {
				itp = itp + ",\""+CommonOperation.nTrim(rs.getString("parameterid").trim())+"\":\"" + CommonOperation.nTrim(rs.getString("value").trim()) + "\"";
				
			}
			a+=1;
		}
		itp = itp  + "}";
		System.out.println("itp="+itp);
		c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itp;
	}

}
