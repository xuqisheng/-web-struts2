package wingsoft.tool.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import wingsoft.core.start.WebInitServlet;

public class SQLHelper
{
  private static final Logger LOGGER = Logger.getLogger(WebInitServlet.class);

  public List executeSqlObjArray(String sql, Connection conn)
    throws Exception
  {
    LOGGERdebug("执行前的SQL(executeSqlObjArray):" + sql, null, null);

    List rltList = null;
    Statement stmt = null;
    ResultSet rs = null;
    stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);
    rltList = encapsulateRsToObjArr(rs);
    rs.close();
    stmt.close();
    return rltList;
  }

  public List<HashMap<String, Object>> executeSqlHashMapArray(String sql, Connection conn) throws Exception {
    LOGGERdebug("执行前的SQL(executeSqlHashMapArray):" + sql, null, null);

    List rltList = null;
    Statement stmt = null;
    ResultSet rs = null;
    stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);
    rltList = encapsulateRsToHashMapArr(rs);
    rs.close();
    stmt.close();
    return rltList;
  }

  public void executeSql(String sql, Connection conn) throws Exception {
    LOGGERdebug("执行前的SQL(executeSql):" + sql, null, null);

    Statement stmt = null;
    stmt = conn.createStatement();
    stmt.execute(sql);
    stmt.close();
  }

  public Object executeScalar(String sql, Connection conn)
    throws Exception
  {
    LOGGERdebug("执行前的SQL(executeScalar):" + sql, null, null);

    Object scalar = null;
    Statement stmt = null;
    ResultSet rs = null;
    stmt = conn.createStatement();
    try {
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsm = rs.getMetaData();
      if (rs.next()) {
        String type = rsm.getColumnTypeName(1);
        if ("CLOB".equals(type)) {
          Clob clob = rs.getClob(1);
          if (clob == null) {
            scalar = "";
          } else {
            BufferedReader in = new BufferedReader(clob.getCharacterStream());
            StringBuffer str = new StringBuffer();
            String oneLine = "";
            while ((oneLine = in.readLine()) != null)
              str.append(oneLine);

            in.close();
            scalar = str.toString();
          }
        }
        else {
          scalar = rs.getObject(1); }
      }
      rs.close();
    }
    finally {
      try {
        stmt.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return scalar;
  }

  protected List encapsulateRsToObjArr(ResultSet rs) throws SQLException, IOException
  {
    List returnData = new ArrayList();
    ResultSetMetaData rsm = rs.getMetaData();
    int columnCountNum = rsm.getColumnCount();
    while (rs.next())
    {
      Object[] objs = new Object[columnCountNum];
      for (int i = 1; i <= columnCountNum; ++i) {
        String type = rsm.getColumnTypeName(i);
        if ("CLOB".equals(type)) {
          Clob clob = rs.getClob(i);
          if (clob == null) {
            objs[(i - 1)] = "";
          }
          else {
            BufferedReader in = new BufferedReader(clob.getCharacterStream());
            StringBuffer str = new StringBuffer();
            String oneLine = "";
            while ((oneLine = in.readLine()) != null)
              str.append(oneLine);

            in.close();
            objs[(i - 1)] = str.toString();
          }
        }
        else {
          objs[(i - 1)] = rs.getObject(i);
        }
      }
      returnData.add(objs);
    }

    return returnData;
  }

  public static void LOGGERinfo(String info) {
    if (("Y".equals(WebInitServlet.printLog)) || ("Y".equals(WebInitServlet.isDebugging)))
      LOGGER.info("WFINFO>>" + info);
  }

  public static void LOGGERdebug(String info, HttpSession session, String projid)
  {
    if ("Y".equals(WebInitServlet.isDebugging)) {
      String userID = "NOSESSN";
      String sessionID = "NOSESSN";
      if (session != null) {
        if (session.getAttribute("userId") != null)
          userID = session.getAttribute("userId").toString();
        else
          userID = "NOTLOGGEDIN";
        sessionID = session.getId();
      }
      LOGGER.info("WFDEBUG>>" + (("json".equals(WebInitServlet.startMode)) ? WebInitServlet.busiID : "*") + ">>" + ((projid == null) ? "*" : projid) + ">>" + userID + ">>" + sessionID + ">>" + info);
    }
  }

  public static void LOGGERerror(String info, HttpSession session, String projid)
  {
    String userID = "NOSESSN";
    String sessionID = "NOSESSN";
    if (session != null) {
      if (session.getAttribute("userId") != null)
        userID = session.getAttribute("userId").toString();
      else
        userID = "NOTLOGGEDIN";
      sessionID = session.getId();
    }
    LOGGER.error("WFERROR>>" + (("json".equals(WebInitServlet.startMode)) ? WebInitServlet.busiID : "*") + ">>" + projid + ">>" + userID + ">>" + sessionID + ">>" + info);
  }

  protected List<HashMap<String, Object>> encapsulateRsToHashMapArr(ResultSet rs)
    throws SQLException, IOException
  {
    List returnData = new ArrayList();
    ResultSetMetaData rsm = rs.getMetaData();
    int columnCountNum = rsm.getColumnCount();
    while (rs.next()) {
      HashMap oneData = new HashMap();
      for (int i = 1; i <= columnCountNum; ++i) {
        String type = rsm.getColumnTypeName(i);
        String colName = rsm.getColumnName(i).toUpperCase();
        if ("CLOB".equals(type))
        {
          if (rs.getClob(i) == null) {
            oneData.put(colName, "");
          }
          else {
            BufferedReader in = new BufferedReader(rs.getClob(i).getCharacterStream());
            StringBuffer jscontent = new StringBuffer();
            String oneLine = "";
            while ((oneLine = in.readLine()) != null)
              jscontent.append(oneLine);

            in.close();
            oneData.put(colName, jscontent.toString()); }
        }
        else if (type.endsWith("LONG")) {
          oneData.put(colName, rs.getObject(i));
        }
        else
          oneData.put(colName, rs.getObject(i));

      }

      returnData.add(oneData);
    }
    return returnData;
  }
}