package wingsoft.core.wfdao;

import java.sql.Connection;
import java.util.List;

import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;
import wingsoft.tool.db.SQLHelper;
public class WFDefQueryDAO {
	protected static ConnectionPool pool = ConnectionPoolManager.getPool("WFServer");
	protected static SQLHelper sqlHelper = new SQLHelper();
	public List queryDataForm(String sql){
			Connection conn = null;
			List resultList=null;
			
			try{
				conn = pool.getConnection();
				System.out.println('1');
				resultList= sqlHelper.executeSqlObjArray(sql,conn);
				
			}catch(Exception e){
				System.out.println("错误的SQL:"+sql);
				//e.printStackTrace();
			}finally{
				pool.returnConnection(conn);
			}
			
			return resultList;
		}
	
	public List queryHashDataForm(String sql){
		
		Connection conn = null;
		List resultList = null;
		
		try{
			conn = pool.getConnection();
			resultList= sqlHelper.executeSqlHashMapArray(sql, conn);
			
		}catch(Exception e){
			System.out.println("错误的SQL:"+sql);
			//e.printStackTrace();
		}finally{
			pool.returnConnection(conn);
		}
		
		return resultList;
		
	}
	
	public long queryCountData(String sql){
		
		Connection conn = null;
		
		long count=0;
		
		try{
			conn = pool.getConnection();
			count = Long.parseLong(sqlHelper.executeScalar(sql, conn).toString());
			
		}catch(Exception e){
			System.out.println("错误的SQL:"+sql);
			e.printStackTrace();
		}finally{
			pool.returnConnection(conn);
		}
		return count;
		
	}
	
	public String queryScalar(String sql){
		
		Connection conn = null;
		
		String result = null;
		
		try{
			conn = pool.getConnection();
			Object s = sqlHelper.executeScalar(sql, conn);
			if(s != null){
				result = s.toString();
			}else{
				result = null;
			}
			
		}catch(Exception e){
			System.out.println("错误的SQL:"+sql);
			//e.printStackTrace();
			return null;
			
		}finally{
			pool.returnConnection(conn);
		}
		
		return result;
	}	
		
}
