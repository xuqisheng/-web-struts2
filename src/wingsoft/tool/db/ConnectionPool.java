package wingsoft.tool.db;

import org.apache.tomcat.jdbc.pool.PoolProperties;
//import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ConnectionPool {
    private String dbUsername = ""; // 数据库用户名
    private Context context = null;//
    private DataSource ds = null;//数据源,即数据池

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     * 构造函数
     */
    public ConnectionPool(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    /**
     * 初始化 数据连接源
     *
     * @throws Exception
     */
    public synchronized void createPool() throws Exception {

        context = (Context) new InitialContext().lookup("java:comp/env");
//
        ds = (DataSource) context.lookup("jdbc/" + this.dbUsername);

        //测试连接是否有效
//        ds = this.getDatasource();
        Connection conn = ds.getConnection();
        conn.commit();
        conn.close();

    }

    /*
    private static PoolProperties getPropertiesOne() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:oracle:thin:@http://192.168.102.5:1521/orcl");
        p.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        p.setUsername("shoptest_nyd");
        p.setPassword("zhao");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        return  p;
//        DataSource datasource = new DataSource();
//        datasource.setPoolProperties(p);
//        return datasource;
    }
*/
    private static PoolProperties getPropertiesTwo() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:oracle:thin:@http://192.168.102.5:1521/orcl");
        p.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        p.setUsername("wf_store");
        p.setPassword("zhao");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        return  p;
//        DataSource datasource = new DataSource();
//        datasource.setPoolProperties(p);
//        return datasource;
    }


    /**
     * @return 返回一个可用的数据库连接对象
     * @throws NamingException,SQLException
     */
    public synchronized Connection getConnection() throws SQLException, NamingException {

        Connection conn = ds.getConnection();

        return conn;

    }

    /**
     * @param "需返回链接"
     */
    public void returnConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public void closePreparedStatement(PreparedStatement ps){
        try{
            if(ps!=null)
                ps.close();
        }catch (SQLException sql){
            sql.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet rs){
        try{
            if(rs!=null)
                rs.close();
        }catch (SQLException sql){
            sql.printStackTrace();
        }
    }
}
