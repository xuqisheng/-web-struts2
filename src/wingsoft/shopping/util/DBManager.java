package wingsoft.shopping.util;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import wingsoft.core.start.WebInitServlet;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;


public class DBManager {
	private static String connectionString;
	private static String dbUsername;
	private static String dbPassword;
	private static String pid = "";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"MM/dd/yyyy");
	static {
		try {
			connectionString = "jdbc:oracle:thin:@http://192.168.102.5:1521:orcl";
			dbUsername = "testshop_nyd";
			dbPassword = "zhao";
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Calendar now() {
		return Calendar.getInstance();
	}

	public static Connection getConnection() {
		ConnectionPool pool;
		if ("json".equals(WebInitServlet.startMode)) {
			pool = ConnectionPoolManager.getPool(pid);
		}
		else {
			pool = ConnectionPoolManager.getPool("CMServer");
		}
		Connection c = null;
		try {
			c = pool.getConnection();
		} catch (Exception e) {
			System.err.println("GET CONNECTION ERROR!!!!");
			e.printStackTrace();
		}
		return c;
	}
	
	public static void setPid(String pid) {
		DBManager.pid = pid;
	}
	public static String getPid() {
		return DBManager.pid;
	}

	//防字符串注入
	public static String filterContent(String content) {
		String flt ="'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,"; 
		String filter[] = flt.split("|"); 
		for(int i=0; i<filter.length;i++) {
			content.replace(filter[i], ""); 
		}

		return content; 
	}
}
