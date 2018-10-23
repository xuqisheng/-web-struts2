package wingsoft.tool.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 */
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
    public synchronized DataSource createPool()  {
        try {
            context = (Context) new InitialContext().lookup("java:comp/env");
            this.dbUsername="store";
            ds = (DataSource) context.lookup("jdbc/" + this.dbUsername);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
        //测试连接是否有效
//        ds = this.getDatasource();
//        Connection conn = ds.getConnection();
//        conn.commit();
//        conn.close();

    }


    /**
     * @return 返回一个可用的数据库连接对象
     * @throws NamingException,SQLException
     */
    public synchronized Connection getConnection() throws SQLException {
        Connection conn = this.createPool().getConnection();
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
