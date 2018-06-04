package wingsoft.core.wfdao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

import wingsoft.tool.db.SQLHelper;

public class PrintDefDAO extends WFDefQueryDAO{
	private final static Logger LOGGER = Logger.getLogger(SQLHelper.class);
	@SuppressWarnings("unchecked")
	public  HashMap<String,Object> findPrintDef(String funcno){	
		String sql="select PRINT_HTML,USED_VARS from SYS_PRINT_DEF where FUNCNO="+funcno;
		return (HashMap<String,Object>) this.queryHashDataForm(sql).get(0);
		}
	
	@SuppressWarnings("unchecked")
	public List queryHashDataForm(String sql){
		
		Connection conn = null;
		List resultList = null;
		
		try{
			conn = pool.getConnection();
			resultList= this.executeSqlHashMapArray(sql, conn);
			
		}catch(Exception e){
			System.out.println("错误的SQL:"+sql);
			//e.printStackTrace();
		}finally{
			pool.returnConnection(conn);
		}
		
		return resultList;
		
	}
	
	@SuppressWarnings("unchecked")
	public List executeSqlHashMapArray(final String sql, final Connection conn) throws Exception {
		LOGGER.info("执行前的SQL：" + sql);
		List rltList = null;
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		
		
		ResultSet rs = null;
		
		rs = pstmt.executeQuery();
		rltList = this.encapsulateRsToHashMapArr(rs);
		rs.close();
		pstmt.close();
		return rltList;
	}
	
	@SuppressWarnings("unchecked")
	protected List encapsulateRsToHashMapArr(final ResultSet rs) throws SQLException, IOException {

		final List returnData = new ArrayList();
		final ResultSetMetaData rsm = rs.getMetaData();
		final int columnCountNum = rsm.getColumnCount();
		while (rs.next()) {
			HashMap oneData = new HashMap();
			for (int i = 1; i <= columnCountNum; i++) {
				if(i==1){
					Reader reader = rs.getCharacterStream(1); 
					BufferedReader bufReader = new BufferedReader(reader); 
					StringBuffer strBuf = new StringBuffer(); 
					String line; 
					while ((line = bufReader.readLine()) != null) { 
					strBuf.append(line); 
					} 
					bufReader.close(); 
					oneData.put(rsm.getColumnName(i), strBuf.toString());
				}else{
					oneData.put(rsm.getColumnName(i), rs.getObject(i));
				}
				
			}
			returnData.add(oneData);
		}
		return returnData;

	}
	public void getPic(OutputStream os,String funcNo,String order) throws IOException{
		Connection conn = null;
		String sql = "select image from sys_print_img_def where funcno='"+funcNo+"' and ord='"+order+"'";
		try{
			conn = pool.getConnection();
			LOGGER.info("执行前的SQL：" + sql);
			PreparedStatement ps = null;
            ResultSet rs = null;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
            	Blob b = rs.getBlob("image");
           	 	byte[] images = b.getBytes(1, (int)b.length());
           	 	os.write(images);
           }
		}catch(Exception e){
			System.out.println("错误的SQL:"+sql);
		}finally{
			pool.returnConnection(conn);
			os.flush();
			os.close();
		}
	}
}
