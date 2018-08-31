package wingsoft.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.avalon.framework.activity.Suspendable;
import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.model.Item;
import wingsoft.shopping.model.Parameter;
import wingsoft.shopping.util.DBManager;
import wingsoft.tool.common.CommonOperation;

public class ItemDAO {
	//http://www.blogjava.net/conans/articles/219693.html参考rownum
	private final String selectItem = "select s.*," +
			" (select '{'||wm_concat('\"'||trim(sp.parameterid)||'\":\"'||replace(trim(sp.value),'\"','\\\"')||'\"')||'}'  from shp_itempara sp where sp.itemid=s.itemid and sp.value is not null) itempara" +
			" from SHP_item s where s.itemid= rpad(?,30,' ') and s.published = 'true'";
	private final String selectCategory0 = "select distinct SHP_ITEM.itemid,itemname,prize,itemimg,ITEMCATE,itempage,parents,itemimgupload," +
			" (select '{'||wm_concat('\"'||trim(sp.parameterid)||'\":\"'||replace(trim(sp.value),'\"','\\\"')||'\"')||'}'  from shp_itempara sp " +
			" where sp.itemid=SHP_item.itemid and sp.value is not null) itempara,rownum rn" +
			" from SHP_item  SHP_item where itemcate like '%.";


	private final String selectCategory1 = "select distinct SHP_ITEM.itemid,itemname,prize,itemimg,ITEMCATE,itempage,parents,itemimgupload," +
			" (select '{'||wm_concat('\"'||trim(sip.parameterid)||'\":\"'||replace(trim(sip.value),'\"','\\\"')||'\"')||'}'  from shp_itempara sip where sip.itemid=SHP_item.itemid) itempara" +
			",rownum rn " +
			"from SHP_item SHP_item,(select itemid from (select itemid,count(*) c from shp_itempara  where ";
	private final String selectCategory2 = " group by itemid) where c=?) b where (shp_item.parents = b.itemid) and itemcate like '%.";
	private final String selectCategory3 = ".%'";
	private final String selectCategory4 = "  and SHP_item.published = 'true' order by SHP_item.itemid DESC";  // 增加published控制。
	
	private final String selectCategory5 = "select distinct SHP_ITEM.itemid,itemname,prize,itemimg,ITEMCATE,itempage,parents,itemimgupload," +
	" (select '{'||wm_concat('\"'||trim(sp.parameterid)||'\":\"'||replace(trim(sp.value),'\"','\\\"')||'\"')||'}'  from shp_itempara sp " +
	" where sp.itemid=SHP_item.itemid and sp.value is not null) itempara,c.INDATE,rownum rn" +
	" from SHP_item  SHP_item ,collection c where SHP_item.itemid=c.itemid and c.userid=rpad(?,50,' ') ";
	private final String selectAll = "select * from SHP_item where parents is not null and published = 'true' order by itemid DESC";
	private final String selectChildren = "select SHP_item.*, (select '{'||wm_concat('\"'||trim(sp.parameterid)||'\":\"'||replace(trim(sp.value),'\"','\\\"')||'\"')||'}'  from shp_itempara sp " +
			" where sp.itemid=SHP_item.itemid and sp.value is not null) itempara from SHP_item where trim(parents)=? and published = 'true'";
	public Item selectItem(String itemid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectItem);
//		System.out.println("selectItem1="+selectItem);
//		System.out.println(itemid);
		ps.setString(1, CommonOperation.nTrim((itemid)));
		ResultSet rs = ps.executeQuery();
		
		Item i = new Item();
		if (rs.next()){
			//System.out.println("ITEMIMGUPLOAD="+rs.getString("ITEMIMGUPLOAD"));
			i.setItemid(rs.getString("ITEMID").trim());
			i.setItemname(rs.getString("ITEMNAME").trim());
			i.setPrize(rs.getDouble("PRIZE"));
			i.setItemimg(CommonOperation.nTrim(rs.getString("itemimg")));
			i.setItemcate(rs.getString("itemcate").trim());
			i.setItemimgupload(CommonOperation.nTrim(rs.getString("itemimgupload")));
			i.setItempara(CommonOperation.nTrim(rs.getString("itempara")));
			String s = rs.getString(6);
			if (s!=null) {
				s = s.trim();
			}
			i.setItempage(s);
			s = rs.getString(7);
			if (s!=null) {
				s = s.trim();
			}
			i.setParents(s);
		}else{
			System.out.println("未找到数据");
		}
		c.close();
		return i;
	}
	
	public int countCategory(String categoryid, String para,String keyword, String value) throws SQLException {
		Connection c = DBManager.getConnection();

		String sql = "";
		String SearchKey = "INSERT INTO SEARCHKEY(SEARCHKEY, SEARCHDATE, USERID) " +
			" values(trim(?),sysdate,trim(?))";
			HttpServletRequest request = ServletActionContext.getRequest(); 
			String userid = "";
			if (request.getSession().getAttribute("userId")!=null) {
			userid = (String) request.getSession().getAttribute("userId");
			}else{
			System.out.println("未登录");
			
			}

		/*
		if (para!=null) {
			ParameterDAO pd = new ParameterDAO();
			Parameter p = pd.selectParameter(para);
			
			if (p.getParametertype().equals("string")) {
				sql+=" and trim(parameterid) = '" + para + "' and trim(value) = '" + value +"' ";
			} else {
				sql+=" and trim(parameterid) = '" + para + "' and TO_NUMBER(trim(value)) between '" + value.substring(0,value.indexOf("~")) +"' and '"+value.substring(value.indexOf("~")+1)+"' ";
			}
		}*/
		int length = 0;
		System.out.println(length);
		System.out.println(para);
		System.out.println(value);
		if (para!=null&&value!=null) {
			String[] pas = para.split(",");
			String[] vas = value.split(",");
			length = pas.length;
			System.out.println("itemdao-countCategory-length="+length);
			if (length!=0) {
				sql+=selectCategory1;
			} else {
				sql+=selectCategory0;
			}
			
			System.out.println("select = " + sql);
			//处理各类选项
			HashMap<String,String> map = new HashMap<String,String>();
			for (int i=0;i<length;i++) {
				if (map.containsKey(pas[i])) {
					map.put(pas[i], map.get(pas[i])+","+vas[i]);
				} else {
					map.put(pas[i], vas[i]);
				}
			}
			
			//遍历map
			length=0;
			Iterator<Entry<String, String>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				length++;
				Entry entry = (Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				
				ParameterDAO pd = new ParameterDAO();
				Parameter p = pd.selectParameter(key);
				
				if (p.getParametertype().equals("string")) {
					sql+=" (trim(parameterid) = '" + key + "' and (";
					String[] vals = val.split(",");
					for (int i=0;i<vals.length;i++) {
						sql+="trim(value) = '" + vals[i]+"'";
						if (i==vals.length-1) {
							sql+=")) ";
						} else {
							sql+=" or ";
						}
					}
					
				} else {
					sql+=" (trim(parameterid) = '" + key + "' and (";
					String[] vals = val.split(",");
					for (int i=0;i<vals.length;i++) {
						sql+="TO_NUMBER(trim(value)) between '" + vals[i].substring(0,vals[i].indexOf("~")) +"' and '"+vals[i].substring(vals[i].indexOf("~")+1)+"' ";
						if (i==vals.length-1) {
							sql+=")) ";
						} else {
							sql+=" or ";
						}
					}
				}
				if (iter.hasNext()) {
					sql+=" or ";
				}
			}
			
			if (length!=0) {
				sql+=selectCategory2;
			}
		} else {
			sql = selectCategory0;
		}
		
		sql+=categoryid+selectCategory3;
		
		//空格分隔关键词
		if (keyword!=null&&!"".equals(keyword)) {
			sql+=" and (";
			String[] keys = keyword.split(" ");

			for (int i=0;i<keys.length;i++) {
				System.out.println(keys[i]);
				if (keys[i]!=""&&!"".equals(keys[i])) {
			if (i==0) {
				sql+=" instr(itemname,'"+keys[i]+"') >0  ";//  itemname like '%"+keys[i]+"%'";
			//	sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE S.VALUE LIKE  '%"+keys[i]+"%' AND S.ITEMID=SHP_item.ITEMID)";
				sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE instr(S.VALUE,'"+keys[i]+"') >0 AND S.ITEMID=SHP_item.ITEMID)";
			} else {
				sql+=" or instr(itemname,'"+keys[i]+"') >0";
				//sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE S.VALUE LIKE  '%"+keys[i]+"%' AND S.ITEMID=SHP_item.ITEMID)";
				sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE instr(S.VALUE,'"+keys[i]+"') >0 AND S.ITEMID=SHP_item.ITEMID)";
				}
			} 

		 
				PreparedStatement ps=c.prepareStatement(SearchKey);
				ps.setString(1,keys[i]);
				ps.setString(2,userid);
				ps.execute();	 
					 
				
		}
		sql+=") ";
		}
		
		sql += selectCategory4;
		sql = "select COUNT(*) from ("+sql+") where parents is not null";
		System.out.println(sql);
		PreparedStatement ps=c.prepareStatement(sql);
		if (length!=0) {
			ps.setInt(1,length);
		}
		ResultSet rs = ps.executeQuery();
		int is = 0;
		if (rs.next()){
			is = rs.getInt(1);
		}
		c.close();
		return is;
	}
	
	public List<Item> selectCategory(String categoryid, String para, String value, String keyword, int perpage, int page,Boolean IsCollection)  {

		Connection c = DBManager.getConnection();
		System.out.println("连接：：："+c);
		String sql = "";
		
		int length = 0;
		System.out.println("para="+para);
		System.out.println("value="+value);
		PreparedStatement ps = null;
		if (!IsCollection) { 
		 if (para!=null&&value!=null) {
			String[] pas = para.split(",");
			String[] vas = value.split(",");
			length = pas.length;
			if (length!=0) {
				sql+=selectCategory1;
			} else {
				sql+=selectCategory0;
			}
			
			//处理各类选项
			HashMap<String,String> map = new HashMap<String,String>();
			for (int i=0;i<length;i++) {
				if (map.containsKey(pas[i])) {
					map.put(pas[i], map.get(pas[i])+","+vas[i]);
				} else {
					map.put(pas[i], vas[i]);
				}
			}
			
			//遍历map
			length=0;
			Iterator<Entry<String, String>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				length++;
				Entry entry = (Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				System.out.println("key="+key);
				System.out.println(" val="+val);
				
				ParameterDAO pd = new ParameterDAO();
				try {
					Parameter p = pd.selectParameter(key);
					System.out.println("属性="+p.getParametertype());
					if (p.getParametertype().equals("string")) {
						sql+=" (trim(parameterid) = '" + key + "' and (";
						String[] vals = val.split(",");
						for (int i=0;i<vals.length;i++) {
							sql+="trim(value) = '" + vals[i]+"'";
							if (i==vals.length-1) {
								sql+=")) ";
							} else {
								sql+=" or ";
							}
						}

					} else {
						sql+=" (trim(parameterid) = '" + key + "' and (";
						System.out.println("val="+val);
						String[] vals = val.split(",");
						for (int i=0;i<vals.length;i++) {
							System.out.println("vals="+vals[i]);
							sql+="TO_NUMBER(trim(value)) between '" + vals[i].substring(0,vals[i].indexOf("~")) +"' and '"+vals[i].substring(vals[i].indexOf("~")+1)+"' ";
							if (i==vals.length-1) {
								sql+=")) ";
							} else {
								sql+=" or ";
							}
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}

				if (iter.hasNext()) {
					sql+=" or ";
				}
			}
			
			if (length!=0) {
				sql+=selectCategory2;
			}
		} else {
			sql = selectCategory0;
		}
		System.out.println("1="+sql);
		sql+=categoryid+selectCategory3;
		
		//空格分隔关键词
		if (keyword!=null&&!"".equals(keyword)) {
			sql+=" and (";
			String[] keys = keyword.split(" ");
//			for (int i=0;i<keys.length;i++) {
//				if (i==0) {
//					sql+="itemname like '%"+keys[i]+"%'";
//				} else {
//					sql+=" or itemname like '%"+keys[i]+"%'";
//				}
//			}
			/**by吴斌 20170524 增加属性搜索**/
			for (int i=0;i<keys.length;i++) {
				System.out.println(keys[i]);
				if (keys[i]!=""&&!"".equals(keys[i])) {
				if (i==0) {
					//sql+=" itemname like '%"+keys[i]+"%'";
					sql+=" instr(itemname,'"+keys[i]+"')>0 ";
					sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE  instr(S.VALUE,'"+keys[i]+"')>0 AND S.ITEMID=SHP_item.ITEMID)";// S.VALUE LIKE  '%"+keys[i]+"%'
				} else {
					sql+=" or instr(itemname,'"+keys[i]+"')>0";
					sql+=" or exists (select 1 from SHP_ITEMPARA S WHERE instr(S.VALUE,'"+keys[i]+"')>0 AND S.ITEMID=SHP_item.ITEMID)";
				}
					
				}
			}
			sql+=") ";
		}

			sql += selectCategory4;
			sql = "select * from ("+sql+") where parents is not null and rn between " + ((page-1)*perpage+1) + " and "+page*perpage;
			System.out.println("item="+sql);
			try {
				ps=c.prepareStatement(sql);
				if (length!=0) {
					ps.setInt(1,length);
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		else{
			HttpServletRequest request = ServletActionContext.getRequest();
			String userid = ""; 
					userid = (String) request.getSession().getAttribute("userId");
			sql	= selectCategory5;
			sql = "select * from ("+sql+") where parents is not null and rn between " + ((page-1)*perpage+1) + " and "+page*perpage + " order by INDATE desc";
			System.out.println("item="+sql);
			try {
				ps =c.prepareStatement(sql);
				ps.setString(1, userid);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		List<Item> is = new ArrayList<Item>();
		try {
			ResultSet rs = ps.executeQuery();


			ItemparaDAO id = new ItemparaDAO();

			while (rs.next()){
				//TODO: WARNING 这里用1，2，3作为getString的参数不好。应改成字段名。
				Item i = new Item();
				i.setItemid(rs.getString(1).trim());
				i.setItemname(rs.getString(2).trim());
				i.setPrize(rs.getDouble(3));
				i.setItemimg(CommonOperation.nTrim(rs.getString(4)));
				i.setItemimgupload(CommonOperation.nTrim(rs.getString(8)));
				i.setItemcate(rs.getString(5).trim());
				i.setItempara(CommonOperation.nTrim(rs.getString("itempara")));
				String s = rs.getString(6);
				if (s!=null) {
					s = s.trim();
				}
				i.setItempage(s);
				s = rs.getString(7);
				if (s!=null) {
					s = s.trim();
				}
				i.setParents(s);
				is.add(i);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		finally {
			try {
				c.close();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return is;

	}
	
	public List<Item> selectAll() throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectAll);
		ResultSet rs = ps.executeQuery();
		
		List<Item> is = new ArrayList<Item>();
		
		while (rs.next()){
			Item i = new Item();
			i.setItemid(rs.getString(1).trim());
			i.setItemname(rs.getString(2).trim());
			i.setPrize(rs.getDouble(3));
			i.setItemimg(CommonOperation.nTrim(rs.getString(4)));
			i.setItemimgupload(CommonOperation.nTrim(rs.getString(8)));
			i.setItemcate(rs.getString(5).trim());
			String s = rs.getString(6);
			if (s!=null) {
				s = s.trim();
			}
			i.setItempage(s);
			s = rs.getString(7);
			if (s!=null) {
				s = s.trim();
			}
			i.setParents(s);
			is.add(i);
		}
		c.close();
		return is;
	}
	
	public List<Item> selectChildren(String itemid) throws SQLException {
		Connection c = DBManager.getConnection();
		PreparedStatement ps=c.prepareStatement(selectChildren);
		ps.setString(1, itemid);
		ResultSet rs = ps.executeQuery();
		
		List<Item> is = new ArrayList<Item>();
		while (rs.next()){
			Item i = new Item();
			i.setItemid(rs.getString(1).trim());
			i.setItemname(rs.getString(2).trim());
			i.setPrize(rs.getDouble(3));
			i.setItemimg(CommonOperation.nTrim(rs.getString(4)));
			i.setItemimgupload(CommonOperation.nTrim(rs.getString(8)));
			i.setItemcate(rs.getString(5).trim());
			i.setItempara(rs.getString("itempara").trim());
			String s = rs.getString(6);
			if (s!=null) {
				s = s.trim();
			}
			i.setItempage(s);
			s = rs.getString(7);
			if (s!=null) {
				s = s.trim();
			}
			i.setParents(s);
			is.add(i);
		}
		c.close();
		return is;
	}
	
	
}
