package wingsoft.shopping.service.yb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class Test_CLOB {

	
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@192.168.102.5:1521:orcl";
	private static String user = "shop";
	private static String pwd = "zhao";
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public Connection getConn(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeAll(){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("关闭数据库链接");
	}

	// 读取oracle 中的CLOB字段内容
	public void getClob() {
		String sql = "";
		try {
			sql = "select XML_TEXT from xml_log where log_id = 1869";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String xml = "";
			if (rs.next()) {
				xml = clobToString(rs.getClob("XML_TEXT"));
			}
			System.out.println(xml);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将CLOB写入表
	public void writeToClob(String xmlData,String returnValue,String SEQ_ID) {
		String sql = "insert into  XML_LOG(log_id,LOG_DATE,XML_TEXT,RESP_XML_TEXT) values(?,sysdate,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			StringReader reader_xmlData = new StringReader(xmlData);
			StringReader reader_returnValue = new StringReader(returnValue);
			ps.setString(1, SEQ_ID);
			ps.setCharacterStream(2, reader_xmlData, xmlData.length());
			ps.setCharacterStream(3, reader_returnValue, returnValue.length());
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 将CLOB 转成 String
	public String clobToString(Clob clob) {
		String result = "";
		try {
			Reader reader = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String s = br.readLine();
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
			result = sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		Test_CLOB tc = new Test_CLOB();
		tc.getConn();
		tc.getClob();
		tc.closeAll();
	}
}
