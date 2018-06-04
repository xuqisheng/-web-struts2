/**
 * ��ݿ�������
 */
package wingsoft.tool.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * l����ݿ�Ĺ�����
 * @author liaojunmin
 */
public class DBTool {
	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	
	//��0���û���Ϣ(Ĭ��l���û�)
	private static String dbUrl= "";
	private static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String userName = "";
    private static String userPswd = "";
    
    static {
        try {
            Class.forName(driverName);
            System.out.println("\t[OracleDriverClass] load success @Jun-Min Liao");
        } catch (Exception e) {
            System.out.println("\t[OracleDriverClass] load fail!");
        }
    }
    
    /**
	 * ������
	 */
	public DBTool() {
	}
	
	/**
	 * ��һ�㷽ʽȡ�ö�DbUser��l��
	 * @return Connection
	 */
	private static Connection getConnectionDbUserByNormal() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl,userName,userPswd);            
        } catch (Exception e) {
            System.out.println("getConnectionDbUserByNormal()-DbUser Normal connection error");
            return null;
        } 
//        Context context = null;
//        try {
//			context = new InitialContext();
//			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/Oracle");
//			conn = ds.getConnection();
//		} catch (NamingException e) {
//			System.out.println("getConnectionDbUserByNormal()-DbUser Normal connection error");
//			return null;
//		} catch (SQLException e) {
//			System.out.println("getConnectionDbUserByNormal()-DbUser Normal connection error");
//			return null;
//		}
        return conn;
    }
	
	private static Connection getConnectionDbCwbs(String jdbcUrl,String userName,String userPswd) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl,userName,userPswd);            
        } catch (Exception e) {
            System.out.println("getConnectionDbCwbs()-DbUser Normal connection error");
            return null;
        }       
        return conn;
    }
	
	
	public static Connection getConnectionCwbs(String jdbcUrl,String userName,String userPswd){
		return getConnectionDbCwbs(jdbcUrl, userName, userPswd);
	}
	
	
	/**
	 * ��ȡ����ݿ��l��.
	 * @param catalog 
	 * 0=��ʽ(DbUser);
	 * 1=����
	 * @return Connection
	 */
	public static Connection getConnection(int catalog) {
		if(catalog==0)
			return getConnectionDbUserByNormal();
		if(catalog==1)
			return getConnectionDbUserByNormal();//����
		else
			return null;
    }
	/**
	 * ��ȡ����ݿ��Ĭ��l�� ����ʽ��l��DbUser�û�
	 * @return Connection
	 */
	public static Connection getConnection() {
		return getConnection(0);
    }
	/*----------------------------------------------------------*/
	/**
	 * ���� JDBC THIN URL
	 */
	public static void setJdbcUrlThin() {
		DBTool.jdbcUrl = "jdbc:oracle:thin:@"+DBTool.dbUrl;
	}
	/**
	 * ���� ��ݿ�URL,��ͬʱ���Զ����� JDBC THIN URL
	 * @param dbUrl the dbUrl to set
	 */
	public static void setDbUrl(String dbUrl){
		DBTool.dbUrl = dbUrl;
		DBTool.setJdbcUrlThin();
	}
	/**
	 * ���� �û���
	 * @param userName the userName to set
	 */
	public static void setUserName(String userName) {
		DBTool.userName = userName;
	}
	/**
	 * ���� �û�����
	 * @param userPswd the userPswd to set
	 */
	public static void setUserPswd(String userPswd) {
		DBTool.userPswd = userPswd;
	}
}
