package wingsoft.shopping.service.yb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap; 
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.http.HttpRequest;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import wingsoft.tool.common.Md5Tools;
import wingsoft.shopping.model.Supplier;
import wingsoft.shopping.model.T_order;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.common.MyDes;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

/** 新版集中采购接口2.0 **/
public class New_WWS {
	private static String version = "";
	private static String USER_ID = "";
	private static String endPoint = "";
	private static String md5check = "";
	private static String namespaceURI = "";

	static {
		/*version = "201704";
		USER_ID = "JCJSWB";
		endPoint = "http://192.168.20.16:8080/wws/services/WingsoftWebService";
		namespaceURI = "http://webservice.wws.wingsoft.com";
		md5check = "JSPT";
		*/
		HashMap<String,String> params = getParam();
		version = params.get("VERSION");
		USER_ID = params.get("USER_ID");
		endPoint = params.get("SENDPOINT_2");
		namespaceURI = params.get("NAMESPACEURL");
		md5check = params.get("MD5CHECK");
		System.out.println("获取初始化参数成功");
	}

	/**
	 * 获取静态参数
	 */
	public static HashMap<String,String> getParam(){
		System.out.println("获取初始化参数");
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		HashMap<String,String> params = new HashMap<String,String>();
		try {
			conn = pool.getConnection();
			sql = "select t.*, t.rowid from SYSPARAM t where t.parammsg like '%结算%'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String paramKey = Comm.nTrim(rs.getString("paramkey"));
				String paramValue = Comm.nTrim(rs.getString("paramvalue"));
				params.put(paramKey, paramValue);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return params;
		
	}
	
	protected String doPost(String func_id, String SEQ_ID, String xmlData)
			throws ServletException, IOException {
		System.out.println("endPoint:"+endPoint);
		RPCServiceClient serviceClient = null;
		Object[] ret = null;
		String returnValue = "";
		String serial_no = "";
		String errmsg = "";
		Document doc = null;
		String sql = "";
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		xmlData = "<root>" + "<head>" + "<version>" + version + "</version>"
				+ "<user_id>" + USER_ID + "</user_id>" + "<func_id> " + func_id
				+ "</func_id>" + "<seq_id>" + SEQ_ID + "</seq_id>"
				+ "<seq_datetime>" + dateString + "</seq_datetime>" + "</head>"
				+ "<body>" + xmlData + "</body>" + "</root>";
		System.out.println("xmlData="+xmlData);
		String sign = "";
		try {
			conn = pool.getConnection();

			conn.setAutoCommit(false);
			sign = Md5Tools.getMD5(xmlData.getBytes("UTF-8"));

			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(endPoint);
			options.setTo(targetEPR);
			QName opQName = new QName(namespaceURI, "submit");
			Object[] opArgs = { xmlData, sign };
			Class[] opReturnType = { String.class };
			ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
			returnValue = (String) ret[0];
			System.out.println("returnValue="+returnValue);
			if ("".equals(returnValue) || "" == returnValue) {
				returnValue = "error:未返回数据";

			} else {
				System.out.println(returnValue);
				try {
					/*sql = "insert into  XML_LOG(log_id,LOG_DATE,XML_TEXT,RESP_XML_TEXT)"
							+ " values('"
							+ SEQ_ID
							+ "',sysdate,substrb('"
							+ xmlData
							+ "',0,3999),substrb('"
							+ returnValue
							+ "',0,3999))";*/
					sql = "insert into  XML_LOG(log_id,LOG_DATE,XML_TEXT,RESP_XML_TEXT) values(?,sysdate,?,?)";
					ps = conn.prepareStatement(sql);
					StringReader reader_xmlData = new StringReader(xmlData);
					StringReader reader_returnValue = new StringReader(returnValue);
					ps.setString(1, SEQ_ID);
					ps.setCharacterStream(2, reader_xmlData, xmlData.length());
					ps.setCharacterStream(3, reader_returnValue, returnValue.length());
					ps.executeUpdate();
					conn.commit();
					
					System.out.println("推送报文=" + sql);
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("日志过长,记录失败" + SEQ_ID);
				}
				conn.commit();

			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (AxisFault e) {
			returnValue = e.getMessage();
			returnValue = "error:" + returnValue;
			try {
				conn.setAutoCommit(true);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (NamingException e1) {
			e1.printStackTrace();
		} finally {
			try {
				serviceClient.cleanupTransport();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			pool.returnConnection(conn);
		}
		return returnValue;
	}

	public boolean Md5Check(String md5str, String str) throws Exception {
		str = str + md5check;
		String MD5JAVA = Md5Tools.getMD5(str.getBytes("UTF-8"));
		if (MD5JAVA.equals(md5str)) {
			return true;
		} else {
			return false;
		}

	}

	/**4.1平台上传附件   FUNC_ID = 1399 **/
	public void uploadFile(){
		System.out.println("平台上传附件");
		System.out.println("获取附件，base64转码");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String seqs = Comm.nTrim(request.getParameter("seqs"));
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		
		String RetMsg = "";
		String uploadXml = "";
		String uploadXml_record = "";
		String func_id = "1399";
		Document doc = null;
		
		System.out.println("seqs:"+seqs);
		String seqss[] = seqs.split("-");
		String seq = "";
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = pool.getConnection();
			if(Md5Check(md5str, SEQ_ID)){
				for (int i = 0; i < seqss.length; i++) {
					seq = new MyDes("seq2017needenc!!!~").decrypt(seqss[i]);
					System.out.println("seq:"+seq);
					sql = "select fm.path,fm.filename from filesystemmap fm where fm.seq = rpad(?,50,' ') ";
				
					ps = conn.prepareStatement(sql);
					ps.setString(1, seq);
					rs = ps.executeQuery();
					if(rs.next()){
						String file_name = rs.getString("filename");
						String path = rs.getString("path");
						String file_str = Ftp.getEncoder(Comm.nTrim(path));
						System.out.println("第"+(i+1)+"个附件:"+file_name+"&&&"+file_str);
						
						uploadXml_record+=
								"<upload_file>"
										+ "<file_name>"+file_name+"</file_name>"
										+ "<file_str>"+file_str+"</file_str>"
								+ "</upload_file>";
					}
				}
				uploadXml = "<upload_files>"+uploadXml_record+"</upload_files>";
				RetMsg = this.doPost(func_id, SEQ_ID, uploadXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					RetMsg = "success:"+root.getText();
//					RetMsg = "success:"+root.elementText("file_code");
				}
			}else{
				RetMsg ="error:验证失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:结算确认异常："+e.getMessage();
		} 
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}
	}
	
	/**4.2文件下载  (FUNC_ID = 1398) 
	 * 下载 转码？CLOB-String
	 * */
	public void download(){
		System.out.println("平台下载附件");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String file_code = Comm.nTrim(request.getParameter("file_code"));//文件统一码 “-”分隔
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String filePath = Comm.nTrim(request.getParameter("filePath"));//获取下载的文件路径
		String RetMsg = "";
		String downloadXml = "";
		String func_id = "1398";
		Document doc = null;
		
		System.out.println("file_code:"+file_code);
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn = pool.getConnection();
			if(Md5Check(md5str, SEQ_ID)){
				downloadXml="<file_code>"+file_code+"</file_code>";
				RetMsg = this.doPost(func_id, SEQ_ID, file_code);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					Element file_downloads = root.element("file_downloads");
					Element file_download = file_downloads.element("file_download");
					Element file_name = file_download.element("file_name");
					Element file_str = file_download.element("file_str");
					String fname = file_name.getText();
					String fstr = file_str.getText();
					//解码base64
					boolean flag = Ftp.decode(fstr, filePath);
					if(flag){
						RetMsg = "success:file_name:"+fname+",filePath:"+filePath;//下载成功，返回文件名/地址
					}else{
						RetMsg = "error:下载失败";
					}
				}
			}else{
				RetMsg ="error:验证失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:下载异常："+e.getMessage();
		} 
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}
		
	}
	
	/**4.3注册普通供应商(FUNC_ID =1301)
	 * 返回值改变
	 * **/
	public void Register() {
		System.out.println("****供应商注册！！！***");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String sid = Comm.nTrim(request.getParameter("sid"));// 注册供应商
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
		String username = Comm.nTrim(request.getParameter("username"));//登记人
		try {
			username = new String(username.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String md5str = Comm.nTrim(request.getParameter("keystr"));

		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String RetMsg = "";
		String RegistXml = "";
		Document doc = null;

		try {
			request.setCharacterEncoding("UTF-8");// 传值编码
			if (Md5Check(md5str, sid)) {

				Supplier sup = new Supplier();
				conn = pool.getConnection();
				sql = "select s.*,(select wm_concat(trim(b.b_code)) from supplier_bcode b where b.sid=s.id) b_code from supplier s where s.id=rpad(?,20,' ')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, sid);
				rs = ps.executeQuery();
				if (rs.next()) {
					sup.setId(Comm.nTrim(rs.getString("id")));
					sup.setName(Comm.nTrim(rs.getString("NAME")));
					sup.setTaxNo(Comm.nTrim(rs.getString("TAX_NO")));
					sup.setAccount(Comm.nTrim(rs.getString("ACCOUNT")));
					sup.setAccname(Comm.nTrim(rs.getString("ACCNAME")));
					sup.setAccbank(Comm.nTrim(rs.getString("ACCBANK")));
					sup.setBranchcode(Comm.nTrim(rs.getString("BRANCHCODE")));
					sup.setContact(Comm.nTrim(rs.getString("CONTACT")));
					sup.setTelephone(Comm.nTrim(rs.getString("TELEPHONE")));
					sup.setPaymentDays(rs.getInt("PAYMENT_DAYS"));
					sup.setDeposit(rs.getDouble("DEPOSIT"));
					sup.setEInvoice(Comm.nTrim(rs.getString("E_INVOICE")));
					sup.setEInvoiceNoauth(Comm.nTrim(rs
							.getString("E_INVOICE_NOAUTH")));
					sup.setPfiles(Comm.nTrim(rs.getString("PFILES")));
					sup.setRemark(Comm.nTrim(rs.getString("REMARK")));
					sup.setB_CODE(Comm.nTrim(rs.getString("b_code")));
					String pwd =Comm.nTrim(rs.getString("WWS_PWD"));
					pwd = new MyDes("hiudiudiusaascbhsvgwpeodcbctyw").encrypt(pwd);
					RegistXml ="<js_provider>"
							+ "<prov_name>" + sup.getName()+ "</prov_name>"
							+ "<short_name>"+Comm.nTrim(rs.getString("short_name"))+"</short_name>"
							+ "<tax_no>" + sup.getTaxNo()+ "</tax_no>"
							+ "<tax_addr>"+Comm.nTrim(rs.getString("TAX_ADDR"))+"</tax_addr>"
							+ "<invoice_name>"+Comm.nTrim(rs.getString("INVOICE_NAME"))+"</invoice_name>"
							+ "<acnt>" + sup.getAccount()+ "</acnt>"
							+ "<accbank>" + sup.getAccbank() + "</accbank>"
							+ "<branchcode>"+ sup.getBranchcode() + "</branchcode>"
							+ "<contact_addr>"+Comm.nTrim(rs.getString("address"))+"</contact_addr>"
							+ "<contact>" + sup.getContact() + "</contact>"
							+ "<tel>" + sup.getTelephone() + "</tel>"
							+ "<mobilephone>"+Comm.nTrim(rs.getString("TELEPHONE"))+"</mobilephone>"
							+ "<email>"+Comm.nTrim(rs.getString("email"))+"</email>"
							+ "<payment_days>" + sup.getPaymentDays()+ "</payment_days>"
							+ "<deposit>"+ sup.getDeposit() + "</deposit>"
							+ "<e_invoice>"+ sup.getEInvoice() + "</e_invoice>"
							+ "<prov_doc><![CDATA["+ sup.getPfiles() + "]]></prov_doc>"//<![CDATA["---"]]>
							+ "<remark>"+ sup.getRemark() + "</remark>"
							+ "<input_user>"+username+"</input_user>"
//							+ "<b_code>"+sup.getB_CODE()+"</b_code>"
							+ "</js_provider>";
							
					RetMsg = this.doPost("1301", SEQ_ID, RegistXml);
					doc = DocumentHelper.parseText(RetMsg);
					Element rootElt = doc.getRootElement(); // 获取根节点
					if (RetMsg.indexOf("error") > 0) {
						RetMsg = rootElt.elementText("msg");
						RetMsg = "error:" + RetMsg;
					} else {
						
						RetMsg = "success:" + rootElt.element("prov_code").getTextTrim();//供应商编码
					}

				} else {
					RetMsg = "未找到注册供应商";
				}

				System.out.println("RetMsg=" + RetMsg);
			} else {
				RetMsg = "注册认证失败";
			}
			System.out.println("RetMsg=" + RetMsg);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(RetMsg);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg = "注册异常=" + e.getMessage();
			response.setContentType("text/plain;charset=UTF-8");
			try {
				response.getWriter().write(RetMsg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{ 
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}

	}

	/**
	 * 4.4	更新普通供应商(FUNC_ID=1302)    
	 * **/
	public void updateProvider(){
		System.out.println("供应商更新");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String sid = Comm.nTrim(request.getParameter("sid"));// 注册供应商
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
		String username = Comm.nTrim(request.getParameter("username"));//登记人
		try {
			username = new String(username.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String md5str = Comm.nTrim(request.getParameter("keystr"));

		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String RetMsg = "";
		String RegistXml = "";
		Document doc = null;

		try {
			request.setCharacterEncoding("UTF-8");// 传值编码
			if (Md5Check(md5str, SEQ_ID)) {

				Supplier sup = new Supplier();
				conn = pool.getConnection();
				sql = "select s.*,(select wm_concat(trim(b.b_code)) from supplier_bcode b where b.sid=s.id) b_code from supplier s where s.id=rpad(?,20,' ')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, sid);
				rs = ps.executeQuery();
				if (rs.next()) {
					sup.setId(Comm.nTrim(rs.getString("wws_userid")));//供应商编码
					sup.setName(Comm.nTrim(rs.getString("NAME")));
					sup.setTaxNo(Comm.nTrim(rs.getString("TAX_NO")));
					sup.setAccount(Comm.nTrim(rs.getString("ACCOUNT")));
					sup.setAccname(Comm.nTrim(rs.getString("ACCNAME")));
					sup.setAccbank(Comm.nTrim(rs.getString("ACCBANK")));
					sup.setBranchcode(Comm.nTrim(rs.getString("BRANCHCODE")));
					sup.setContact(Comm.nTrim(rs.getString("CONTACT")));
					sup.setTelephone(Comm.nTrim(rs.getString("TELEPHONE")));
					sup.setPaymentDays(rs.getInt("PAYMENT_DAYS"));
					sup.setDeposit(rs.getDouble("DEPOSIT"));
					sup.setEInvoice(Comm.nTrim(rs.getString("E_INVOICE")));
					sup.setEInvoiceNoauth(Comm.nTrim(rs
							.getString("E_INVOICE_NOAUTH")));
					String pfiles = Comm.nTrim(rs.getString("fileseq"));
					pfiles= pfiles.replaceAll("&", "&amp;");
					sup.setPfiles(pfiles);
					sup.setRemark(Comm.nTrim(rs.getString("REMARK")));
					sup.setB_CODE(Comm.nTrim(rs.getString("b_code")));
					String pwd =Comm.nTrim(rs.getString("WWS_PWD"));
					pwd = new MyDes("hiudiudiusaascbhsvgwpeodcbctyw").encrypt(pwd);
					RegistXml ="<js_provider>"
							+ "<prov_code>" + sup.getId()+ "</prov_code>"
							+ "<prov_name>" + sup.getName()+ "</prov_name>"
							+ "<short_name>"+Comm.nTrim(rs.getString("short_name"))+"</short_name>"
							+ "<tax_no>" + sup.getTaxNo()+ "</tax_no>"
							+ "<tax_addr>"+Comm.nTrim(rs.getString("TAX_ADDR"))+"</tax_addr>"
							+ "<invoice_name>"+Comm.nTrim(rs.getString("INVOICE_NAME"))+"</invoice_name>"
							+ "<acnt>" + sup.getAccount()+ "</acnt>"
							+ "<accbank>" + sup.getAccbank() + "</accbank>"
							+ "<branchcode>"+ sup.getBranchcode() + "</branchcode>"
							+ "<contact_addr>"+Comm.nTrim(rs.getString("address"))+"</contact_addr>"
							+ "<contact>" + sup.getContact() + "</contact>"
							+ "<tel>" + sup.getTelephone() + "</tel>"
							+ "<mobilephone>"+Comm.nTrim(rs.getString("TELEPHONE"))+"</mobilephone>"
							+ "<email>"+Comm.nTrim(rs.getString("email"))+"</email>"
							+ "<payment_days>" + sup.getPaymentDays()+ "</payment_days>"
							+ "<deposit>"+ sup.getDeposit() + "</deposit>"
							+ "<e_invoice>"+ sup.getEInvoice() + "</e_invoice>"
							+ "<prov_doc>"+ sup.getPfiles() + "</prov_doc>"
							+ "<remark>"+ sup.getRemark() + "</remark>"
							+ "<input_user>"+username+"</input_user>"
							+ "</js_provider>";
							
					RetMsg = this.doPost("1302", SEQ_ID, RegistXml);
					doc = DocumentHelper.parseText(RetMsg);
					Element rootElt = doc.getRootElement(); // 获取根节点
					if (RetMsg.indexOf("error") > 0) {
						RetMsg = rootElt.elementText("msg");
						RetMsg = "error:" + RetMsg;
					} else {
						RetMsg = "success:" + rootElt.getTextTrim();
					}

				} else {
					RetMsg = "未找到注册供应商";
				}

				System.out.println("RetMsg=" + RetMsg);
			} else {
				RetMsg = "认证失败";
			}
			System.out.println("RetMsg=" + RetMsg);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(RetMsg);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg = "修改异常=" + e.getMessage();
			response.setContentType("text/plain;charset=UTF-8");
			try {
				response.getWriter().write(RetMsg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{ 
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}
	}
	
	/**
	 * 4.5	根据工号获取项目列表(FUNC_ID=1311)
	 * **/
	public void getItemList(){
		System.out.println("根据工号获取项目列表");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String userId = Comm.nTrim(request.getParameter("userId"));// 老师工号
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//流水号
		String b_code = Comm.nTrim(request.getParameter("b_code"));//费用项代码
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String func_id = "1311";
		
		String RetMsg = "";
		String itemXml = "";
		String itemXml_record = "";
		Document doc = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			if(Md5Check(md5str, SEQ_ID)){
				
				itemXml_record=	"<sno>"+userId+"</sno>"
							+ "<b_code>"+b_code+"</b_code>";
				
				itemXml = "<other_info>"+itemXml_record+"</other_info>";
				RetMsg = this.doPost(func_id, SEQ_ID, itemXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					//成功
					Element prj_records = root.element("prj_records");
					List<Element> list = root.elements("prj_record");
					RetMsg = "success:[";
					for (int i = 0; i < list.size(); i++) {
						Element prj_record = list.get(i);
						String uni_prj_code = prj_record.element("uni_prj_code").getTextTrim();
						String uni_prj_name = prj_record.element("uni_prj_name").getTextTrim();
						String charge_sno = prj_record.element("charge_sno").getTextTrim();
						String charge_name = prj_record.element("charge_name").getTextTrim();
						String sa_depart = prj_record.element("sa_depart").getTextTrim();
						String gk_flag = prj_record.element("gk_flag").getTextTrim();
						String finish_date = prj_record.element("finish_date").getTextTrim();
						String max_amt = prj_record.element("max_amt").getTextTrim();
						RetMsg += "{uni_prj_code:"+uni_prj_code+",uni_prj_name:"+uni_prj_name+",charge_sno:"+charge_sno+","
								+ "charge_name:"+charge_name+",sa_depart:"+sa_depart+",gk_flag:"+gk_flag+","
								+ "finish_date:"+finish_date+",max_amt:"+max_amt+"}";
						if(i!=list.size()-1){
							RetMsg +=",";
						}
					}
					RetMsg = "]";
					//RetMsg=success:[{..},{..}]
					System.out.println("RetMsg="+RetMsg);
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:根据工号获取项目列表异常："+e.getMessage();
		}
		
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
				
		
	}
	
	/**
	 * 4.6	根据项目代码获取项目信息(FUNC_ID=1312)
	 * **/
	public void getItemDetail(){
		System.out.println("根据项目代码获取项目信息");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String uni_prj_code = Comm.nTrim(request.getParameter("uni_prj_code"));// 项目id
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//流水号
		String b_code = Comm.nTrim(request.getParameter("b_code"));//费用项代码
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String func_id = "1312";
		
		String RetMsg = "";
		String itemDetailXml = "";
		String itemDetailXml_record = "";
		Document doc = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			if(Md5Check(md5str, SEQ_ID)){
				
				itemDetailXml_record=	"<uni_prj_code>"+uni_prj_code+"</uni_prj_code>"
							+ "<b_code>"+b_code+"</b_code>";
				
				itemDetailXml = "<other_info>"+itemDetailXml_record+"</other_info>";
				RetMsg = this.doPost(func_id, SEQ_ID, itemDetailXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					//成功
					Element prj_record = root.element("prj_record");
					String uni_prj_code2 = prj_record.element("uni_prj_code").getTextTrim();
					String uni_prj_name = prj_record.element("uni_prj_name").getTextTrim();
					String charge_sno = prj_record.element("charge_sno").getTextTrim();
					String charge_name = prj_record.element("charge_name").getTextTrim();
					String sa_depart = prj_record.element("sa_depart").getTextTrim();
					String gk_flag = prj_record.element("gk_flag").getTextTrim();
					String finish_date = prj_record.element("finish_date").getTextTrim();
					String max_amt = prj_record.element("max_amt").getTextTrim();
					RetMsg = "success:{uni_prj_code:"+uni_prj_code2+",uni_prj_name:"+uni_prj_name+",charge_sno:"+charge_sno+","
							+ "charge_name:"+charge_name+",sa_depart:"+sa_depart+",gk_flag:"+gk_flag+","
							+ "finish_date:"+finish_date+",max_amt:"+max_amt+"}";
					System.out.println("RetMsg="+RetMsg);
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:根据工号获取项目列表异常："+e.getMessage();
		}
		
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public String OrderFrozee(String Fxml,String SEQ_ID){
		String RetMsg = ""; 
		try {
			System.out.println("冻结订单1321");
			RetMsg =this.doPost("1321", SEQ_ID, Fxml);
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RetMsg;
	}
	
	/** 4.7-4.8 冻结/解冻订单  **/
	public void frozee()  {
		System.out.println("冻结订单");
		System.out.println("endPoint:"+endPoint);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		
		// 订单号 json数组 {["ordercode":"xxx"],["ordercode":"xxx"]}
		String ordercodes = Comm.nTrim(request.getParameter("ordercode"));
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID")); // 流水号
		String userid = Comm.nTrim(request.getParameter("userid")); // 冻结人
		String isF = Comm.nTrim(request.getParameter("isF"));//冻结订单：F;解冻：C
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String RetMsg = "";
		String frozeXml = "";
		String frozeXml_record = "";
		String func_id = "";
		Document doc = null;

		JSONArray jsonArr = JSONArray.fromObject(ordercodes);
		
		try {
			request.setCharacterEncoding("UTF-8");
			//验证
			if(Md5Check(md5str, SEQ_ID)){
				
				boolean flag = true;
				
				// 处理每个订单
				for (int i = 0; i < jsonArr.size(); i++) {
					RetMsg = "";
					JSONObject ordercode_json = jsonArr.getJSONObject(i);
					String ordercode = (String) ordercode_json.get("ordercode");
					try {
						request.setCharacterEncoding("UTF-8");
						conn = pool.getConnection();
						sql = "select t.FREEZE_NO ,s.wws_userid,t.ORDERCODE,t.pro_code,t.b_code,t.totalprice,t.postage "
								+ "from t_order t,supplier s where t.supplier_id=s.id and t.ordercode=rpad(?,20,' ')";
						T_order order = new T_order();
						ps = conn.prepareStatement(sql);
						ps.setString(1, ordercode);
						rs = ps.executeQuery();

						if (rs.next()) {
							String FREEZE_NO = Comm.nTrim(rs.getString("FREEZE_NO"));
							order.setSupplier_id(Comm.nTrim(rs.getString("wws_userid")));
							order.setOrderCode(Comm.nTrim(rs.getString("ORDERCODE")));
							order.setPro_code(Comm.nTrim(rs.getString("pro_code")));
							order.setB_code(Comm.nTrim(rs.getString("b_code")));
							order.setTotalPrice(rs.getDouble("totalprice"));
							order.setPostage(rs.getDouble("postage"));
							double amt = order.getTotalPrice() + order.getPostage();
							if("F".equalsIgnoreCase(isF)){
								//冻结
								if (!("".equals(FREEZE_NO))) {
									RetMsg = "error:订单号：" + ordercode + "已经被被冻结,冻结订单失败";
									flag = false;
									break;
								} else {
									func_id = "1321";
									frozeXml_record += 
											"<order>" 
													+ "<prov_code>"+ order.getSupplier_id() + "</prov_code>"
													+ "<order_no>" + ordercode + "</order_no>"
													+ "<frozen_user>" + userid+ "</frozen_user>" 
													+ "<uni_prj_code>" + order.getPro_code()+ "</uni_prj_code>" 
													+ "<b_code>"+ order.getB_code() + "</b_code>"
													+ "<frozen_amt>" + amt+ "</frozen_amt>"
											+"</order >";
								}
							}else if("C".equalsIgnoreCase(isF)){
								// 解冻
								func_id = "1322";
								if (("".equals(FREEZE_NO))) {
									RetMsg = "error:订单号：" + ordercode + "未被冻结,取消订单失败";
									flag = false;
									break;
								} else {
									frozeXml_record += 
											"<order>" 
													+ "<prov_code>"+ order.getSupplier_id() + "</prov_code>"
													+ "<order_no>" + ordercode + "</order_no>"
											+"</order>";
								}
							}
						} else {
							RetMsg = "error:未找到订单号：" + ordercode ;
							flag = false;
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
						RetMsg = "error:冻结/解冻异常=" + e.getMessage();
						flag = false;
						break;
					}

				}
				// 循环结束
				if(flag == true){
					//订单无异常，拼报文
					frozeXml = "<orders>" + frozeXml_record
							+ "</orders>";
					
					try {
						RetMsg = this.doPost(func_id, SEQ_ID, frozeXml);
						doc = DocumentHelper.parseText(RetMsg);
						Element rootElt = doc.getRootElement(); // 获取根节点
						if (RetMsg.indexOf("error") > 0) {
							RetMsg = rootElt.elementText("msg");
							RetMsg = "error:" + RetMsg;
						} else {
							// RetMsg = "success:ok";
							RetMsg = "success:" + rootElt.getTextTrim();
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
						RetMsg = "error:冻结/解冻异常=" + e.getMessage();
					} 
				}
				
			}else{
				RetMsg = "error:认证失败";
			}
			
			// 返回信息
			System.out.println("RetMsg=" + RetMsg);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write(RetMsg);
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg = "冻结/解冻异常=" + e.getMessage();
			response.setContentType("text/plain;charset=UTF-8");
			try {
				response.getWriter().write(RetMsg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		} finally{ 
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}

	}

	
	/**
	 * 4.9	更换项目 (FUNC_ID=1323)
	 * ??返回值
	 * **/
	public void changeItem(){
		System.out.println("更换项目");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String prov_code = Comm.nTrim(request.getParameter("prov_code"));// 供应商编码
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//流水号
		String order_no = Comm.nTrim(request.getParameter("order_no"));//订单号
		String old_uni_prj_code = Comm.nTrim(request.getParameter("old_uni_prj_code"));//原项目代码
		String new_uni_prj_code = Comm.nTrim(request.getParameter("new_uni_prj_code"));//新项目代码
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String func_id = "1323";
		
		String RetMsg = "";
		String changeItemXml = "";
		String changeItemXml_record = "";
		Document doc = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			if(Md5Check(md5str, SEQ_ID)){
				
				changeItemXml_record=
						"<order>"
						+ "<prov_code>"+prov_code+"</prov_code>"
						+ "<order_no>"+order_no+"</order_no>"
						+ "<old_uni_prj_code>"+old_uni_prj_code+"</old_uni_prj_code>"
						+ "<new_uni_prj_code>"+new_uni_prj_code+"</new_uni_prj_code>"
						+ "</order>";
				
				changeItemXml = "<orders>"+changeItemXml_record+"</others>";
				RetMsg = this.doPost(func_id, SEQ_ID, changeItemXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					//成功
					RetMsg = "success:"+root.getText();
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:根据工号获取项目列表异常："+e.getMessage();
		}
		
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 4.10	收货确认 (FUNC_ID=1331)
	 * **/
	public void receive(){
		System.out.println("收货确认");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String ordercodes = "";//订单号 （多个）
		String SEQ_ID = "";// 流水号
		String md5str = "";
		String func_id = "1331";
		String confirm_user = "";//收货确认人
		
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String RetMsg = "";
		String receiveXml = "";
		String receiveXml_record = "";
		String order_xml = "";
		Document doc = null;
		
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			ordercodes = Comm.nTrim(request.getParameter("ordercodes"));
			SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
			md5str = Comm.nTrim(request.getParameter("keystr"));
			confirm_user = Comm.nTrim(request.getParameter("dev_user"));
			confirm_user=new String(confirm_user.getBytes("ISO-8859-1"),"utf-8");

			JSONArray ordercodes_json = JSONArray.fromObject(ordercodes);
			conn = pool.getConnection();
			
			if(Md5Check(md5str, SEQ_ID)){
				boolean flag = true;
				for (int i = 0; i < ordercodes_json.size(); i++) {
					JSONObject ordercode_json = ordercodes_json.getJSONObject(i);
					String ordercode = (String) ordercode_json.get("ordercode");
					
					//获取订单的信息
					sql = "select s.wws_userid,t.ORDERCODE,t.pro_code,t.totalprice+t.postage amt,t.acceptancefileseq "+
							"from t_order t,supplier s "+
							"where t.supplier_id=s.id  "+
							"and t.ordercode=rpad(?,20,' ')";
					ps = conn.prepareStatement(sql);
					ps.setString(1, ordercode);
					rs = ps.executeQuery();
					if(rs.next()){
						String wws_userid = Comm.nTrim(rs.getString("wws_userid"));
						String pro_code = rs.getString("pro_code");
						double amt = rs.getDouble("amt");
//						String files = rs.getString("files");
						String acceptancefileseq = Comm.nTrim(rs.getString("acceptancefileseq"));
						
						//获取订单明细
						sql = "select t.ORDERCODE,tp.product_id,tp.COMMON_NAME,tp.BY_NUMBER,tp.PRICE,tp.specifications,nvl(tp.brand,'*') brand,nvl(tp.scode,'*') scode "+
								"from t_order t,t_order_product tp "+
								"where t.ordercode = tp.ordercode "+
								"and t.ordercode=rpad(?,20,' ')";
						ps = conn.prepareStatement(sql);
						ps.setString(1, ordercode);
						rs = ps.executeQuery();
						while(rs.next()){
							String brand = rs.getString("brand");
							String scode = rs.getString("SCODE");
							String product_id = rs.getString("product_id");
							String common_name = rs.getString("COMMON_NAME");
							String by_number = rs.getString("BY_NUMBER");
							String mode = rs.getString("specifications");
							String price = rs.getString("PRICE");
							ordercode = rs.getString("ORDERCODE");
							order_xml +="<order_dtl>"+
											"<品牌>"+brand+"</品牌>"+
											"<货号>"+scode+"</货号>"+ 
											"<规格>"+mode+"</规格>"+
											"<数量>"+by_number+"</数量>"+
											"<单价>"+price+"</单价>"+
										"</order_dtl>";

						}
						/**获取order_project 多项目值   05-15**/
						sql = "select  order_id, ord, pro_code, amt from order_project op where op.order_id = rpad(?,20,' ')";
						ps = conn.prepareStatement(sql);
						ps.setString(1, ordercode);
						rs = ps.executeQuery();
						while(rs.next()){
							ordercode = rs.getString("order_id");
							int ord = Integer.parseInt(rs.getString("ord"));
							ordercode = ordercode.trim() + "_"+ord;
							amt = Double.parseDouble(rs.getString("amt"));
						
							receiveXml_record += 
									  "<order>"
										+ " <prov_code>"+wws_userid+"</prov_code>"
										+ " <order_no>"+ordercode+"</order_no>"
										+ " <confirm_user>"+confirm_user+"</confirm_user>"
										+ " <order_amt>"+amt+"</order_amt>"
//										+ " <order_doc>"+acceptancefileseq+"</order_doc>"
										+ " <order_doc><![CDATA["+acceptancefileseq+"]]></order_doc>"
										+ " <order_dtls>"+order_xml+"</order_dtls>"
									+ "</order>";
						}
						/**end**/
						
					}else{
						RetMsg = "error:未找到订单号：" + ordercode ;
						flag = false;
						break;
					}
				}
				
				if(flag == true){
					receiveXml = "<orders>"+receiveXml_record+"</orders>";
					RetMsg = this.doPost(func_id, SEQ_ID, receiveXml);
					doc = DocumentHelper.parseText(RetMsg);
					Element root = doc.getRootElement();
					if(RetMsg.indexOf("error")>0){
						RetMsg = root.elementText("msg");
						RetMsg = "error:"+RetMsg;
					}else{
						RetMsg = "success:"+root.getTextTrim();
					}
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:收货确认异常："+e.getMessage();
		} 
		
		//写出返回信息
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{ 
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}

	}

	/**
	 * 4.11	申请结算 (FUNC_ID=1341)
	 * 发票信息 app_settle_invoice 少字段
	 * */
	public void applyForSettlement(){
		System.out.println("供应商申请结算");
		HttpServletRequest request =ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		
		String func_id = "1341";
		String wws_userid = "";
		double apply_amt = 0;
		
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		
		String RetMsg = "";
		String applyXml = "";
		String applyXml_record = "";
		String invoiceXml = "";
		String invoiceXml_record = "";
		Document doc = null;
		try {
			request.setCharacterEncoding("UTF-8");// 传值编码
			response.setContentType("text/plain;charset=UTF-8");
			conn = pool.getConnection();
			String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));
			String apply_user = Comm.nTrim(request.getParameter("apply_user"));//结算申请人 
			String apply_no_t = Comm.nTrim(request.getParameter("apply_no"));//结算申请 临时单号 
			String ordercodes = Comm.nTrim(request.getParameter("ordercodes"));
			System.out.println(ordercodes);
			String md5str = Comm.nTrim(request.getParameter("keystr"));
			//发票信息
			/*String invoices = Comm.nTrim(request.getParameter("invoices"));//多张发票号信息 json
			invoices=new String(invoices.getBytes("ISO-8859-1"),"utf-8");
			System.out.println("发票json："+invoices);
			*/
			JSONArray ordercodes_json = JSONArray.fromObject(ordercodes);
			//JSONArray invoices_json = JSONArray.fromObject(invoices);
			
			if(Md5Check(md5str,SEQ_ID)){
				boolean flag = true;
				for (int i = 0; i < ordercodes_json.size(); i++) {
					JSONObject ordercode_json = ordercodes_json.getJSONObject(i);
					String ordercode = (String) ordercode_json.get("ordercode");
					
					sql = "select s.wws_userid,t.ORDERCODE,t.pro_code,t.b_code,t.totalprice+t.postage amt  "+
							"from t_order t,supplier s  "+
							"where t.supplier_id=s.id  "+
							"and t.ordercode=rpad(?,20,' ')";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, ordercode);
					rs = ps.executeQuery();
					if(rs.next()){
						wws_userid = rs.getString("wws_userid");
						String pro_code = rs.getString("pro_code");
						double amt = rs.getDouble("amt");
						apply_amt += amt;
						String b_code = rs.getString("b_code");
						
						/**获取order_project 多项目值   05-15**/
						sql = "select  order_id, ord, pro_code, amt from order_project op where op.order_id = rpad(?,20,' ')";
						ps = conn.prepareStatement(sql);
						ps.setString(1, ordercode);
						rs = ps.executeQuery();
						while(rs.next()){
							ordercode = rs.getString("order_id");
							int ord = Integer.parseInt(rs.getString("ord"));
							ordercode = ordercode.trim() + "_"+ord;
							amt = Double.parseDouble(rs.getString("amt"));
							applyXml_record += "<order>"
									+ "<order_no>"+ordercode+"</order_no>"
							+ "</order>";
						}
						/**end**/
						
						
						
						
						
					}else{
						RetMsg = "error:未找到订单号：" + ordercode ;
						flag = false;
						break;
					}
					
				}
				/*
				for (int i = 0; i < invoices_json.size(); i++) {
					JSONObject invoice_json = invoices_json.getJSONObject(i);
					String invoice_no = (String) invoice_json.get("invoice_no");//发票号
					String invoice_type = (String) invoice_json.get("invoice_type");//发票类型（增值税普票; 增值税专票）
					double invoice_amt = Double.parseDouble((String) invoice_json.get("invoice_amt"));//发票金额
					String e_invoice = (String) invoice_json.get("e_invoice");//是否电子发票
					String invoice_doc = (String) invoice_json.get("invoice_doc");//发票附件
					String invoice_code = (String) invoice_json.get("invoice_code");//发票代码，少字段
					String invoice_date = (String) invoice_json.get("invoice_date");//开票日期，数据库缺少字段app_settle_invoice
					
					invoice_doc = invoice_doc.replaceAll("&", "&amp;");
					
					invoiceXml_record+="<invoice>"
							+ "<invoice_type>"+invoice_type+"</invoice_type>"
							+ "<invoice_code>"+invoice_code+"</invoice_code>"
							+ "<invoice_no>"+invoice_no+"</invoice_no>"
							+ "<invoice_date>"+invoice_date+"</invoice_date>"
							+ "<invoice_amt>"+invoice_amt+"</invoice_amt>"
							+ "<invoice_doc>"+invoice_doc+"</invoice_doc>"
							+ "<e_invoice>"+e_invoice+"</e_invoice>"
					   + "</invoice>";
				}
				*/
				//获取发票信息
				sql ="select t.* from app_settle_invoice t where t.apply_no =rpad(?,20,' ') ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, apply_no_t);
				rs = ps.executeQuery();
				while(rs.next()){
					String invoice_type = rs.getString("invoice_type");
					String invoice_code = rs.getString("invoice_code");
					String invoice_no = rs.getString("invoice_no");
					String invoice_date = rs.getString("invoice_date");
					Double invoice_amt = rs.getDouble("invoice_amt");
					String invoice_doc = rs.getString("fileseq");
					String e_invoice = rs.getString("e_invoice");
					
					invoice_doc = invoice_doc.replaceAll("&", "&amp;");
					
					invoiceXml_record+="<invoice>"
							+ "<invoice_type>"+invoice_type+"</invoice_type>"
							+ "<invoice_code>"+invoice_code+"</invoice_code>"
							+ "<invoice_no>"+invoice_no+"</invoice_no>"
							+ "<invoice_date>"+invoice_date+"</invoice_date>"
							+ "<invoice_amt>"+invoice_amt+"</invoice_amt>"
							+ "<invoice_doc>"+invoice_doc+"</invoice_doc>"
							+ "<e_invoice>"+e_invoice+"</e_invoice>"
					   + "</invoice>";
					
				}
				
				
				if(flag == true){
					applyXml = 
							"<apply>"
								+ "<prov_code>"+wws_userid+"</prov_code>"
								+ "<apply_user>"+apply_user+"</apply_user>"
								+ "<apply_amt>"+apply_amt+"</apply_amt>"
							+ "<orders>"+applyXml_record+"</orders>"
							+ "<invoices>"+invoiceXml_record+"</invoices>"
							+"</apply>";
					RetMsg = this.doPost(func_id, SEQ_ID, applyXml);
					doc = DocumentHelper.parseText(RetMsg);
					Element root = doc.getRootElement();
					if(RetMsg.indexOf("error")>0){
						RetMsg = root.elementText("msg");
						RetMsg = "error:"+RetMsg;
					}else{
						String apply_no = root.elementText("apply_no");//申请号
						String req_no = root.elementText("serial_no");//预约单号
						RetMsg = "success:apply_no:"+apply_no+",req_no:"+req_no;
					}
				}
			}else{
				RetMsg = "error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:申请结算异常："+e.getMessage();
		}
		//写出返回信息
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{ 
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}
		
	}

	
	/**
	 * 4.12	取消结算申请 (FUNC_ID=1342)
	 * **/
	public void NoApplyForSettlement(){
		System.out.println("取消结算申请");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String prov_code = Comm.nTrim(request.getParameter("prov_code"));// 供应商编码
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//流水号
		String apply_no = Comm.nTrim(request.getParameter("apply_no"));//结算申请号
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String func_id = "1342";
		
		String RetMsg = "";
		String NoApplyXml = "";
		String NoApplyXml_record = "";
		Document doc = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			if(Md5Check(md5str, SEQ_ID)){
				
				NoApplyXml_record=
						 "<prov_code>"+prov_code+"</prov_code>"
						+ "<apply_no>"+apply_no+"</apply_no>";
				NoApplyXml = "<apply>"+NoApplyXml_record+"</apply>";
				RetMsg = this.doPost(func_id, SEQ_ID, NoApplyXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					//成功
					RetMsg = "success:"+root.getTextTrim();
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:根据工号获取项目列表异常："+e.getMessage();
		}
		
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 4.13	查询结算进度 (FUNC_ID=1343)
	 * **/
	public void querySchedule(){
		System.out.println("查询结算进度");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//参数
		String prov_code = Comm.nTrim(request.getParameter("prov_code"));// 供应商编码
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//流水号
		String apply_no = Comm.nTrim(request.getParameter("apply_no"));//结算申请号
		String md5str = Comm.nTrim(request.getParameter("keystr"));
		String func_id = "1343";
		
		String RetMsg = "";
		String queryXml = "";
		String queryXml_record = "";
		Document doc = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			if(Md5Check(md5str, SEQ_ID)){
				
				queryXml_record=
						 "<prov_code>"+prov_code+"</prov_code>"
						+ "<apply_no>"+apply_no+"</apply_no>";
				queryXml = "<apply>"+queryXml_record+"</apply>";
				RetMsg = this.doPost(func_id, SEQ_ID, queryXml);
				doc = DocumentHelper.parseText(RetMsg);
				Element root = doc.getRootElement();
				if(RetMsg.indexOf("error")>0){
					RetMsg = root.elementText("msg");
					RetMsg = "error:"+RetMsg;
				}else{
					//成功
					String prov_code_back = root.element("prov_code").getText();
					String apply_no_back = root.element("apply_no").getText();
					String prov_name = root.element("prov_name").getText();
					String acnt = root.element("acnt").getText();
					String accbank = root.element("accbank").getText();
					String branchcode = root.element("branchcode").getText();
					String apply_amt = root.element("apply_amt").getText();
					String apply_user = root.element("apply_user").getText();
					String apply_date = root.element("apply_date").getText();
					String req_no = root.element("req_no").getText();
					String pz_uni_no = root.element("pz_uni_no").getText();
					String pay_status = root.element("pay_status").getText();
					String pay_date = root.element("pay_date").getText();
					RetMsg = "success:{prov_code:"+prov_code_back+",apply_no:"+apply_no_back+",prov_name:"+prov_name+","
							+ "acnt:"+acnt+",accbank:"+accbank+",branchcode:"+branchcode+","
							+ "apply_amt:"+apply_amt+",apply_user:"+apply_user+","
							+ "apply_date:"+apply_date+",req_no:"+req_no+","
							+ "pz_uni_no:"+pz_uni_no+",pay_status:"+pay_status+",pay_date:"+pay_date+"}";
					//RetMsg ="success:{prov_code:xxx,apply_no:xxx,...}"
				}
			}else{
				RetMsg ="error:验证失败";
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg ="error:根据工号获取项目列表异常："+e.getMessage();
		}
		
		System.out.println("RetMsg=" + RetMsg);
		try {
			response.getWriter().write(RetMsg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
 	public static void main(String[] args) throws Exception {

	}

}
