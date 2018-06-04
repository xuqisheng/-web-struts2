package wingsoft.core.start;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import wingsoft.core.jq.JqCustomWin;
import wingsoft.core.jq.JqWintypes;
import wingsoft.core.sysdef.PagesDef;
import wingsoft.core.sysdef.RoleTableDef;
import wingsoft.core.sysdef.SqlDef;
import wingsoft.core.sysdef.WorkflowWinDef;
import wingsoft.core.wfdao.CustomDefDAO;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.common.StringChallenger;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;
import wingsoft.tool.db.SQLHelper;

public class WebInitServlet extends HttpServlet
{
  private static final long serialVersionUID = -1723786075000061571L;
  public static String SERVER_URL = null;
  public static String DeploymentPath = null;
  public static String DefinitionPath = null;
  public static Properties properties = new Properties();
  public static Properties versionsProp = new Properties();
  public static String startMode = "";
  public static String busiLicense = "";
  public static String busiID = "";
  public static String busiName = "";
  public static String prjLicense = "";
  public static String prjId = "";
  public static String prjName = "";
  public static String wfServerName = "";
  public static String charset = "";
  public static String printLog = "";
  public static String isDebugging = "";
  public static String cmServerName = "";
  public static String URMSG = "";
  public static String multiWin = "";
  public static String menuVertical = "";
  public static String menuLeft = "";
  public static String allMenu = "";
  public static String useRuleSQL = "";
  public static String realRootPath = "";
  public static String appWinTypes = "";
  public static String fileSystemType = "";
  public static PagesDef pagesDef = new PagesDef();
  public static SqlDef sqlDef = new SqlDef();
  public static RoleTableDef roleDef = new RoleTableDef();
  public static HashMap<String, String> projDBConnMap = new HashMap();
  public static WorkflowWinDef wfwin = new WorkflowWinDef();
  public static StringChallenger capturerChallenge = new StringChallenger();
  static Logger LOGGER = Logger.getLogger(WebInitServlet.class);

  public static String getProjDBName(String projid)
  {
    return ((String)projDBConnMap.get(projid));
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    DataInputStream in = null;
    try
    {
      DeploymentPath = config.getServletContext().getRealPath("/");
//      System.out.println(DeploymentPath);
      if (!(DeploymentPath.endsWith(File.separator)))
        DeploymentPath += File.separator;
      DefinitionPath = DeploymentPath + "WEB-INF" + 
        File.separator + "DEFS" + File.separator;

      Properties propLog4j = new Properties();
      String log4jPropFile = DeploymentPath + "WEB-INF" + 
        File.separator + "classes" + File.separator + "log4j.properties";
      in = new DataInputStream(new BufferedInputStream(new FileInputStream(log4jPropFile)));
      propLog4j.load(in);
      PropertyConfigurator.configure(propLog4j);

      String versionFile = DeploymentPath + 
        "WEB-INF" + 
        File.separator + "version.properties";
      in = new DataInputStream(new BufferedInputStream(new FileInputStream(versionFile)));
      versionsProp.load(in);
      String sysversion = versionsProp.getProperty("sysversion");
      String verdate = versionsProp.getProperty("verdate");
      String verdatesub = versionsProp.getProperty("verdatesub");

      System.out.println();
      System.out.println("********************************");
      System.out.println(">>>　　欢迎使用复旦天翼WEB开发平台");
      System.out.println(">>>　　version " + sysversion + "." + verdate + verdatesub);
      System.out.println("********************************");
      System.out.println();

      LOGGER.info("系统初始化中...v" + sysversion + "." + verdate + verdatesub);

      String configFile = DeploymentPath + 
        "WEB-INF" + 
        File.separator + "dbServerConfig.properties";

      in = new DataInputStream(new BufferedInputStream(new FileInputStream(configFile)));
      properties.load(in);

      charset = properties.getProperty("Charset");
      printLog = new String(properties.getProperty("PrintLog").getBytes("ISO-8859-1"), "UTF-8").toUpperCase();
      if (properties.containsKey("URMSG"))
        URMSG = properties.getProperty("URMSG");

      if (properties.containsKey("multiWin"))
        multiWin = new String(properties.getProperty("multiWin").getBytes("ISO-8859-1"), "UTF-8");
      else
        multiWin = "T";

      if (properties.containsKey("menuVertical"))
        menuVertical = new String(properties.getProperty("menuVertical").getBytes("ISO-8859-1"), "UTF-8");
      else
        menuVertical = "F";

      if (properties.containsKey("menuLeft"))
        menuLeft = new String(properties.getProperty("menuLeft").getBytes("ISO-8859-1"), "UTF-8");
      else
        menuLeft = "F";

      if ((menuLeft == "T") && (menuVertical == "T"))
        LOGGER.info("警告：dbServerConfig.properties中菜单设置冲突：menuLeft和menuVertical不能同为T。");

      if (properties.containsKey("allMenu")) {
        allMenu = new String(properties.getProperty("allMenu").getBytes("ISO-8859-1"), "UTF-8");
      }
      else
        allMenu = "F";

      if (properties.containsKey("SQLRule")) {
        useRuleSQL = new String(properties.getProperty("SQLRule").getBytes("ISO-8859-1"), "UTF-8");
      }
      else
        useRuleSQL = "N";

      if (properties.containsKey("RealRootPath")) {
        realRootPath = CommonOperation.nTrim(new String(properties.getProperty("RealRootPath").getBytes("ISO-8859-1"), "UTF-8"));
      }

      if (properties.containsKey("Debug")) {
        isDebugging = new String(properties.getProperty("Debug").getBytes("ISO-8859-1"), "UTF-8").toUpperCase();
      }

      startMode = new String(properties.getProperty("StartMode").getBytes("ISO-8859-1"), "UTF-8");

      if ("json".equals(startMode)) {
        if ("".equals(isDebugging))
          isDebugging = "N";
        busiName = new String(properties.getProperty("BusinessName").getBytes("ISO-8859-1"), "UTF-8");
        busiID = new String(properties.getProperty("BusinessID").getBytes("ISO-8859-1"), "UTF-8");
        prjId = "";
        LOGGER.info("业务ID:" + busiID + "---业务名称:" + busiName + "---启动模式:" + startMode);
        startServerByRemoteJSON(properties);

        String fName = DefinitionPath + "win.types";
        appWinTypes = CommonOperation.getFileContent(fName).toString();
        SQLHelper.LOGGERinfo("窗口类型(json)：" + appWinTypes);
      }
      else {
        if ("".equals(isDebugging))
          isDebugging = "Y";
        String prjName = new String(properties.getProperty("PrjName").getBytes("ISO-8859-1"), "UTF-8");
        prjId = new String(properties.getProperty("PrjId").getBytes("ISO-8859-1"), "UTF-8");
        busiName = "";
        busiID = "";
        LOGGER.info("项目ID:" + prjId + "---项目名称:" + prjName + "---启动模式:" + startMode);

        if ("remote".equals(startMode))
          startServerByRemote(properties);
        else
          startServerByLocal(properties);

        appWinTypes = new JqWintypes().getWinTypes();
        SQLHelper.LOGGERinfo("窗口类型(db)：" + appWinTypes);
      }

      LOGGER.info("系统启动成功！ ");
      LOGGER.info("系统启动模式："+startMode);
      LOGGER.info("wfServerName: "+wfServerName);
    }
    catch (Exception e)
    {
      LOGGER.error("系统启动异常");
      e.printStackTrace();

      if (in == null) return;
//      try {
//        in.close();
//      } catch (IOException e) {
//        LOGGER.error(e.getStackTrace());
//      }
    }
    finally
    {
      if (in != null)
        try {
          in.close();
        } catch (IOException e) {
          LOGGER.error(e.getStackTrace());
        }
    }
  }

  protected void startServerByLocal(Properties properties)
    throws Exception
  {
    prjLicense = new String(properties.getProperty("prjLicense").getBytes("ISO-8859-1"), "UTF-8");

    ConnectionPool cpool1 = new ConnectionPool(
      "wf_tianyi");
    try
    {
      cpool1.createPool();
    } catch (Exception e) {
      LOGGER.error(cpool1.getDbUsername() + "连接池创建失败!");
      throw e;
    }

    LOGGER.info("配置库  " + cpool1.getDbUsername() + "链接池创建成功!");

    wfServerName = new String(properties.getProperty("WFServer.name").getBytes("ISO8859-1"), "UTF-8");
    ConnectionPool cpool2 = configurePool("CMServer", properties);
    cmServerName = new String(properties.getProperty("CMServer.name").getBytes("ISO8859-1"), "UTF-8");
    LOGGER.info("业务库" + cpool2.getDbUsername() + "链接池创建成功!");

    ConnectionPoolManager.addPool("WFServer", cpool1);
    ConnectionPoolManager.addPool("CMServer", cpool2);

    String FileSystemEnable = new String(properties.getProperty("FileSystemEnable").getBytes("ISO8859-1"), "UTF-8");
    if ((FileSystemEnable.toUpperCase().equals("Y")) || (FileSystemEnable.toUpperCase().equals("TRUE")))
    {
      configureFileSystem(properties);

      createMapTable();
    }

    loadInitDataFunction();
  }

  protected void startServerByRemote(Properties pro)
    throws Exception
  {
    ConnectionPool pool1 = new ConnectionPool(
      "wf_tianyi");
    try
    {
      pool1.createPool();
    } catch (Exception e) {
      LOGGER.error(pool1.getDbUsername() + "连接池创建失败!");
      throw e;
    }
    LOGGER.info("配置库  " + pool1.getDbUsername() + "连接池创建成功!");

    HashMap confInfo = getConfInfoFromRemote(pro, pool1);

    wfServerName = CommonOperation.nTrim(confInfo.get("CONFIG_USER"));

    ConnectionPool pool2 = new ConnectionPool(
      CommonOperation.nTrim(confInfo.get("BUSINESS_USER")));
    try
    {
      pool2.createPool();
    } catch (Exception e) {
      LOGGER.error(pool2.getDbUsername() + "链接池创建失败!");
      throw e;
    }
    LOGGER.info("业务库  " + pool2.getDbUsername() + "链接池创建成功!");

    ConnectionPoolManager.addPool("WFServer", pool1);
    ConnectionPoolManager.addPool("CMServer", pool2);

    String filesys = CommonOperation.nTrim(confInfo.get("FILE_SYS"));
    if (filesys.toUpperCase().equals("Y")) {
      String systype = CommonOperation.nTrim(confInfo.get("FILE_SYS_TYPE"));
      createMapTable();
      fileSystemType = systype.toUpperCase();
      if ("FTP".equals(fileSystemType)) {
        wingsoft.core.action.FTPServer.ftpIP = CommonOperation.nTrim(confInfo.get("FILE_SYS_IP"));
        wingsoft.core.action.FTPServer.ftpUser = CommonOperation.nTrim(confInfo.get("FTP_USER"));
        wingsoft.core.action.FTPServer.ftpPwd = CommonOperation.nTrim(confInfo.get("FTP_PWD"));
        wingsoft.core.action.FTPServer.port = CommonOperation.nTrim(confInfo.get("FTP_PORT"));

        LOGGER.info("FTP服务器 配置完毕"); } else {
        systype.toUpperCase().equals("CLOUD");
      }

    }

    prjLicense = CommonOperation.nTrim(confInfo.get("LICENSE"));
    System.out.println("***prjLicense = " + prjLicense);

    loadInitDataFunction();
  }

  protected void startServerByRemoteJSON(Properties pro)
    throws Exception
  {
    ConnectionPool tyPool = new ConnectionPool("wf_tianyi");
    try {
      tyPool.createPool();
    } catch (Exception e) {
      LOGGER.error("基础系统连接池wf_tianyi创建失败!");
      throw e;
    }
    ConnectionPoolManager.addPool("wf_tianyi", tyPool);
    LOGGER.info("基础系统连接池wf_tianyi创建成功!");
    Connection conn = tyPool.getConnection();

    String sql = "select Project_ID,DBMapName from wf_businessprojs t where t.business_id = '" + busiID + "'";
    SQLHelper sqlHelper = new SQLHelper();
    List busiProjs = sqlHelper.executeSqlHashMapArray(sql, conn);

    if (busiProjs.size() == 0) {
      LOGGER.error("未在数据库中找到关于业务" + busiID + "的定义");
      throw new Exception();
    }
    LOGGER.info("连接" + busiProjs.size() + "个业务系统 ...");
    for (int i = 0; i < busiProjs.size(); ++i) {
      HashMap hmOneProj = (HashMap)busiProjs.get(i);
      String dbname = CommonOperation.nTrim(hmOneProj.get("DBMAPNAME"));
      ConnectionPool connPool = new ConnectionPool(dbname);
      try {
        connPool.createPool();
      } catch (Exception e) {
        LOGGER.error("业务系统连接池" + connPool.getDbUsername() + "创建失败!");
        throw e;
      }
      LOGGER.info("业务系统连接池" + connPool.getDbUsername() + "创建成功!");
      ConnectionPoolManager.addPool(CommonOperation.nTrim(hmOneProj.get("PROJECT_ID")), connPool);
      projDBConnMap.put(CommonOperation.nTrim(hmOneProj.get("PROJECT_ID")), dbname);
    }

    sql = "select * from wf_business t where t.business_id = '" + busiID + "'";
    HashMap confInfo = (HashMap)sqlHelper.executeSqlHashMapArray(sql, conn).get(0);
    String filesys = CommonOperation.nTrim(confInfo.get("FILE_SYS"));
    if (filesys.toUpperCase().equals("Y")) {
      String systype = CommonOperation.nTrim(confInfo.get("FILE_SYS_TYPE"));
      createMapTable();
      fileSystemType = systype.toUpperCase();
      if ("FTP".equals(fileSystemType)) {
        wingsoft.core.action.FTPServer.ftpIP = CommonOperation.nTrim(confInfo.get("FILE_SYS_IP"));
        wingsoft.core.action.FTPServer.ftpUser = CommonOperation.nTrim(confInfo.get("FTP_USER"));
        wingsoft.core.action.FTPServer.ftpPwd = CommonOperation.nTrim(confInfo.get("FTP_PWD"));
        wingsoft.core.action.FTPServer.port = CommonOperation.nTrim(confInfo.get("FTP_PORT"));

        LOGGER.info("FTP服务器 配置完毕"); } else {
        systype.toUpperCase().equals("CLOUD");
      }
    }

    busiLicense = CommonOperation.nTrim(confInfo.get("LICENSE"));
    conn.close();
    conn = null;

    loadInitDataFunction();

    pagesDef.loadDefs(DefinitionPath);

    sqlDef.loadDefs(DefinitionPath);

    wfwin.loadDefs(DefinitionPath);
  }

  protected ConnectionPool configurePool(String serverName, Properties conf) throws Exception {
    ConnectionPool pool = null;
    try {
      pool = new ConnectionPool(
        new String(conf.getProperty(serverName + ".name").getBytes("ISO8859-1"), "UTF-8"));
    }
    catch (UnsupportedEncodingException e) {
      LOGGER.error(e.getStackTrace());
    }
    try {
      pool.createPool();
    } catch (Exception e) {
      LOGGER.error(serverName + "链接池创建失败!");
      throw e;
    }

    return pool;
  }

  protected void configureFileSystem(Properties conf) throws UnsupportedEncodingException {
    fileSystemType = new String(conf.getProperty("FileSystemType").getBytes("ISO-8859-1"), "UTF-8").toUpperCase();
    if ("FTP".equals(fileSystemType))
      configureFTPServer(conf);
    else if ("CLOUD".equals(fileSystemType))
      configureCloudServer(conf);
  }

  protected void configureCloudServer(Properties conf)
  {
  }

  protected void configureFTPServer(Properties conf) throws UnsupportedEncodingException
  {
    String ftpName = conf.getProperty("FTPServer.name");
    if ((ftpName == null) || (ftpName.equals("")))
      return;
    wingsoft.core.action.FTPServer.ftpIP = CommonOperation.nTrim(new String(conf.getProperty("FTPServer.ip").getBytes("ISO8859-1"), "UTF-8"));
    wingsoft.core.action.FTPServer.ftpUser = CommonOperation.nTrim(new String(conf.getProperty("FTPServer.user").getBytes("ISO8859-1"), "UTF-8"));
    wingsoft.core.action.FTPServer.ftpPwd = CommonOperation.nTrim(new String(conf.getProperty("FTPServer.pwd").getBytes("ISO8859-1"), "UTF-8"));
    wingsoft.core.action.FTPServer.port = CommonOperation.nTrim(new String(conf.getProperty("FTPServer.port").getBytes("ISO8859-1"), "UTF-8"));
    LOGGER.info(ftpName + " FTP服务器 配置完毕."); } 
  // ERROR //
  protected void createMapTable() throws java.sql.SQLException { // Byte code:
	  } 
  protected void loadInitDataFunction() { if ("json".equals(startMode)) {
      String fLocalVar = DefinitionPath + "localVar" + ".DEF";

      StringBuffer functions = CommonOperation.getFileContent(fLocalVar);
    //  wingsoft.core.action.FunctionDefinitionAction.initFunctions = functions.toString();
      LOGGER.info("初始化环境变量定义 获取完毕.");
    }
    else {
      CustomDefDAO cdd = new CustomDefDAO();

      HashMap customDef = cdd.findCustomDef("0");
      List customDetail = cdd.findCustomDetail("0");
      if ((customDef != null) && (customDetail != null)) {
        JqCustomWin jqCw = new JqCustomWin();
        String functions = jqCw.generateCustomWinJson(customDef, customDetail);
       // wingsoft.core.action.FunctionDefinitionAction.initFunctions = functions;
        LOGGER.info("初始化环境变量定义 获取完毕.");
      } else {
       // wingsoft.core.action.FunctionDefinitionAction.initFunctions = "undefined";
        LOGGER.info("本系统未定义初始化环境变量.");
      }
    }
  }

  protected HashMap<String, Object> getConfInfoFromRemote(Properties p, ConnectionPool pool)
    throws Exception
  {
    Connection conn = pool.getConnection();
    List confInfo = null;
    try {
      String sql = "select * from wf_project t where t.project_id = '" + prjId + "'";

      SQLHelper sqlHelper = new SQLHelper();

      confInfo = sqlHelper.executeSqlHashMapArray(sql, conn);
    }
    finally
    {
      pool.returnConnection(conn);
    }

    if (confInfo.size() == 0) {
      LOGGER.error("未在远程数据库中找到,关于" + prjId + "的定义");
      throw new Exception();
    }
//wingsoft.core.start.WebInitServlet
    return ((HashMap)confInfo.get(0));
  }

  public void destroy() {
    super.destroy();
  }
}