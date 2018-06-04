package wingsoft.shopping.service.yb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import wingsoft.tool.common.Md5Tools;
import wingsoft.shopping.util.Comm;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;




import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import sun.text.resources.FormatData;

import javax.xml.namespace.QName;

public class ConnectWWS {
	private static String version = "";
	private static String USER_ID = "";
    private static RPCServiceClient serviceClient;
	private static String endPoint = "";
	private static String namespaceURI = ""; 
	
	static {
		version = "201704";
		USER_ID = "WB";
		endPoint = "http://192.168.20.16:8080/wws/services/WingsoftWebService";
		namespaceURI = "http://webservice.wws.wingsoft.com";
	}
	/**SEQ_ID 推送序列号
	 * FreezeInfo：[{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'}{}]
	 * amt 金额
	 * uni_prj_code 冻结项目
	 * b_code  费用项代码
	 * frozen_seq_id 原冻结号，解冻时需要**/
	public String Frozen(String SEQ_ID,String FreezeInfo,String Ftype ){
		String func_id = "3001";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);  
		String Fdtl = "";

		String RetMsg = "";
		try {
			JSONArray arry = JSONArray.fromObject(FreezeInfo);
		    for (int i = 0; i < arry.size(); i++)
	        {	
		    	JSONObject jsonObject = arry.getJSONObject(i);
		    	//F : 冻结 ，C:解冻
		    	if ("F".endsWith(Ftype)) {
		    		Fdtl = Fdtl + "<frozen_record>" +
									"<uni_prj_code>"+jsonObject.get("uni_prj_code").toString()+"</uni_prj_code>" +
									"<bu_code></bu_code>" +
									"<b_code>"+jsonObject.get("b_code").toString()+"</b_code>" +
									"<frozen_amt>"+jsonObject.get("amt").toString()+"</frozen_amt>" +
									"<frozen_seq_id></frozen_seq_id>" +
									"<frozen_remark></frozen_remark>" +
									"</frozen_record>";
				}else {
		    		Fdtl = Fdtl + "<frozen_record>" +
									"<uni_prj_code>"+jsonObject.get("uni_prj_code").toString()+"</uni_prj_code>" +
									"<bu_code></bu_code>" +
									"<b_code>"+jsonObject.get("b_code").toString()+"</b_code>" +
									"<frozen_amt>"+ -Double.parseDouble(jsonObject.get("amt").toString())+"</frozen_amt>" +
									"<frozen_seq_id>"+jsonObject.get("frozen_seq_id").toString()+"</frozen_seq_id>" +
									"<frozen_remark></frozen_remark>" +
									"</frozen_record>";
					
				}
		    	
	        }	
			String xmlData = "<root>" +
								"<head>" +
									"<version>"+version+"</version>" +
									"<user_id>"+USER_ID+"</user_id>" +
									"<func_id> "+func_id+"</func_id>" +
									"<seq_id>"+ SEQ_ID +"</seq_id>" +
									"<seq_datetime>"+dateString+"</seq_datetime>" +
								"</head>" +
								"<body>" +
									"<frozen_records>"+
									Fdtl	+
									"</frozen_records>" +
								"</body>" +
							"</root>";
				
				
					RetMsg = this.doPost(SEQ_ID,xmlData, "1");
					

					
					
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			RetMsg = "冻结异常="+e.getMessage();
		} 
			
		

		return RetMsg;
	}
	
	
	public void F_C_Order() throws Exception {   
		System.out.println("财务冻结解冻");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");//传值编码 
		String SEQ_ID = Comm.nTrim(request.getParameter("SEQ_ID"));//冻结流水
		String FreezeInfo  = Comm.nTrim(request.getParameter("FreezeInfo"));//冻结解冻明细
		String Ftype  = Comm.nTrim(request.getParameter("Ftype"));//物资年份 
		String RetMsg  = "";
		//FreezeInfo：[{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'}{}]

		try{
			RetMsg = this.Frozen( SEQ_ID, FreezeInfo, Ftype );
		    System.out.println("F/C_bak="+RetMsg);
		     
		    response.setContentType("text/plain;charset=UTF-8");
		        response.getWriter().write(RetMsg); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			RetMsg = "冻结/解冻异常="+e.getMessage();
		}
			
	}
	
	

	public void YB_CWBS() throws Exception {   //Print_YB_CWBS
		//http://localhost:8080/FDWZN/Print_YB_CWBS.action?v_SEQ_ID=1115&v_req_sno=23884&v_MultiRows=150902C000059&v_wz_year=2015&v_tele=8889887&v_remark=a&v_email=111@qq.com&v_bu_operator=&v_arrange_time=09:30-10:00&v_arrange_date=2015-09-08&v_amount=146.00&v_addition=0&v_other_uni_prj_code=asda
		System.out.println("财务预约");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");//传值编码
		String SEQ_ID  = Comm.nTrim(request.getParameter("seq_id"));//预约请求流水号
		System.out.println(SEQ_ID);
		String v_req_sno  = Comm.nTrim(request.getParameter("req_sno"));//预约请求工号
		String v_wz_year  = Comm.nTrim(request.getParameter("wz_year"));//物资年份
		String v_tele  = Comm.nTrim(request.getParameter("tele"));//预约请求联系方式
		String v_req_name  = Comm.nTrim(request.getParameter("req_name"));//预约请求人名字
				v_req_name=new String(v_req_name.getBytes("ISO-8859-1"),"utf-8");
		
		System.out.println(v_req_name);
		String v_remark  = Comm.nTrim(request.getParameter("remark"));//备注
		v_remark=new String(v_remark.getBytes("ISO-8859-1"),"utf-8");
		String v_email  = Comm.nTrim(request.getParameter("email"));//邮箱
		String v_bu_operator = Comm.nTrim(request.getParameter("bu_operator"));
		v_bu_operator=new String(v_bu_operator.getBytes("ISO-8859-1"),"utf-8");
			System.out.println("v_bu_operator="+v_bu_operator);//经办人
		String v_arrange_time  = Comm.nTrim(request.getParameter("arrange_time"));//
		String v_arrange_place  = Comm.nTrim(request.getParameter("arrange_place"));//
		v_arrange_place=new String(v_arrange_place.getBytes("ISO-8859-1"),"utf-8");
		System.out.println(v_arrange_place);
		String v_arrange_date  = Comm.nTrim(request.getParameter("arrange_date"));//
		String v_amount  = Comm.nTrim(request.getParameter("amount"));//总金额
		String v_addition  = Comm.nTrim(request.getParameter("addition"));//附件
		String v_pay_type  = Comm.nTrim(request.getParameter("pay_type"));//付费方式
		v_pay_type=new String(v_pay_type.getBytes("ISO-8859-1"),"utf-8");
		String v_other_uni_prj_code  = Comm.nTrim(request.getParameter("other_uni_prj_code"));//转入项目
		String v_accountname = Comm.nTrim(request.getParameter("accountname"));//银行账户名
		v_accountname=new String(v_accountname.getBytes("ISO-8859-1"),"utf-8");
		String v_account = Comm.nTrim(request.getParameter("account"));//银行账户
		String v_bankname = Comm.nTrim(request.getParameter("bankname"));//开户行
		v_bankname=new String(v_bankname.getBytes("ISO-8859-1"),"utf-8");
		/**[{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'},{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'}]**/
		String yb_dtl = Comm.nTrim(request.getParameter("yb_dtl"));//预约解冻明细
		System.out.println(yb_dtl);
		

		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String USER_ID = "";
		String version = "";
		String key = "";
		String seq_datetime = "";
		String RetMsg = "";
		
		
      
			try {
			conn = pool.getConnection();

			
		    sql = "select trim(PARAMVALUE) version from sysparam t WHERE upper(trim(PARAMKEY)) = upper(trim('XML_VERSION'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				version = Comm.nTrim(rs.getString("version"));
			}
			sql = "select trim(PARAMVALUE) USER_ID from sysparam t WHERE UPPER(TRIM(PARAMKEY)) = UPPER(TRIM('USER_ID'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				USER_ID = Comm.nTrim(rs.getString("USER_ID"));
			}
			sql = "select trim(PARAMVALUE) key from sysparam t WHERE UPPER(TRIM(PARAMKEY)) = UPPER(TRIM('KEY'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				key = Comm.nTrim(rs.getString("key"));
			}

		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			seq_datetime = sdf.format(currentTime);
			String  xmlData = "<root><head><version>" + version +
			        "</version><user_id>" + USER_ID + "</user_id>" +
			        "<func_id>1001</func_id>" + "<seq_id>" +
			        SEQ_ID + "</seq_id>" + "<seq_datetime>" +
			        seq_datetime + "</seq_datetime></head><body>"+
	               "<baseinfo>" +
	                "<uni_prj_code>"+v_other_uni_prj_code+"</uni_prj_code>" +
	                "<req_sno>"+v_req_sno+"</req_sno>" +
	                "<req_name>"+v_req_name+"</req_name>" +
	                "<bu_operator>"+v_bu_operator+"</bu_operator>" +
	                "<mobilephone>"+v_tele+"</mobilephone>" +
	                "<tele>"+v_tele+"</tele>" +
	                "<email>"+v_email+"</email>" +
	                "<addition>"+v_addition+"</addition>" +
	                "<arrange_place>"+v_arrange_place+"</arrange_place>" +
	                "<arrange_date>"+v_arrange_date+"</arrange_date>" +
	                "<arrange_time>"+v_arrange_time+"</arrange_time>" +
	                "<remark>"+v_remark+"</remark>" +
	                "<amount>"+v_amount+"</amount>" +
	            "</baseinfo>" +
	            "<fee_records>";
			JSONArray arry = JSONArray.fromObject(yb_dtl);
		 	
		    String v_uni_prj_code = "";
		    String v_b_code = "";
		    String v_amt = "";
		    String frozen_seq_id = "";
		    float C_PAYAMOUNT = 0;
		    for (int i = 0; i < arry.size(); i++)
	        {	
		    	JSONObject jsonObject = arry.getJSONObject(i);
		 
				v_uni_prj_code = Comm.nTrim(jsonObject.get("uni_prj_code").toString());
				v_b_code = Comm.nTrim(jsonObject.get("b_code").toString());
				v_amt = Comm.nTrim(jsonObject.get("amt").toString());
				//frozen_seq_id = Comm.nTrim(jsonObject.get("frozen_seq_id").toString());
				if (Float.parseFloat(v_amt)<0) {
					xmlData = xmlData + "<fee_record>" +
				            "<uni_prj_code>"+v_uni_prj_code+"</uni_prj_code>" +
				            "<b_code>BBBB</b_code>" +
				            "<b_code_nega>"+v_b_code+"</b_code_nega>" +
				            "<fee_amount>"+(-Float.parseFloat(v_amt))+"</fee_amount>" +
				            "<frozen_seq_id></frozen_seq_id>"+
				            "</fee_record>";	
				}else{
					xmlData = xmlData + "<fee_record>" +
			                "<uni_prj_code>"+v_uni_prj_code+"</uni_prj_code>" +
			                "<b_code>"+v_b_code+"</b_code>" +
			                "<b_code_nega></b_code_nega>" +
			                "<fee_amount>"+v_amt+"</fee_amount>" + "</fee_record>";
					
				}
				C_PAYAMOUNT = C_PAYAMOUNT + Float.parseFloat(v_amt);
			}
			xmlData = xmlData + "</fee_records>" +
								"<pay_records>" +
					            "<pay_record>" +
					            "<pay_type>"+v_pay_type+"</pay_type>" +
					            "<pay_amount>"+C_PAYAMOUNT+"</pay_amount>" +
					            "<sno></sno>" +
					            "<acnt>"+v_account+"</acnt>" +
					            "<other_unit>"+v_accountname+"</other_unit>" +
					            "<bankno>"+v_bankname+"</bankno>" +
					            "<address1></address1>" +
					            "<address2></address2>" +
					            "<branchcode></branchcode>" +
					            "<remark></remark>" +
					            "<clr_order></clr_order>" +
					            "<other_uni_prj_code>"+v_other_uni_prj_code+"</other_uni_prj_code>"+
					            "</pay_record></pay_records>" +
					            "</body>" +
					            "</root>";
			
			//保存XML日志
			System.out.println("记录报文");
			

			   String sign =Md5Tools.getMD5((xmlData+key).getBytes("UTF-8"));
	        	RetMsg = this.doPost(SEQ_ID,xmlData, sign);
	        	System.out.println("ybbak="+RetMsg);
	     

				System.out.println(RetMsg);
	            response.setContentType("text/plain;charset=UTF-8");
            	response.getWriter().write(RetMsg); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				RetMsg = "冻结异常="+e.getMessage();
			}finally{
				
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
	
	public void YB_CWBS_SELF() throws Exception {   //Print_YB_CWBS
		//http://localhost:8080/FDWZN/Print_YB_CWBS.action?v_SEQ_ID=1115&v_req_sno=23884&v_MultiRows=150902C000059&v_wz_year=2015&v_tele=8889887&v_remark=a&v_email=111@qq.com&v_bu_operator=&v_arrange_time=09:30-10:00&v_arrange_date=2015-09-08&v_amount=146.00&v_addition=0&v_other_uni_prj_code=asda
		System.out.println("财务预约");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");//传值编码
		String SEQ_ID  = Comm.nTrim(request.getParameter("seq_id"));//预约请求流水号
		System.out.println(SEQ_ID);
		String v_req_sno  = Comm.nTrim(request.getParameter("req_sno"));//预约请求工号
		String v_wz_year  = Comm.nTrim(request.getParameter("wz_year"));//物资年份
		String v_tele  = Comm.nTrim(request.getParameter("tele"));//预约请求联系方式
		String v_req_name  = Comm.nTrim(request.getParameter("req_name"));//预约请求人名字
				v_req_name=new String(v_req_name.getBytes("ISO-8859-1"),"utf-8");
		
		System.out.println(v_req_name);
		String v_remark  = Comm.nTrim(request.getParameter("remark"));//备注
		v_remark=new String(v_remark.getBytes("ISO-8859-1"),"utf-8");
		String v_email  = Comm.nTrim(request.getParameter("email"));//邮箱
		String v_bu_operator = Comm.nTrim(request.getParameter("bu_operator"));
		v_bu_operator=new String(v_bu_operator.getBytes("ISO-8859-1"),"utf-8");
			System.out.println("v_bu_operator="+v_bu_operator);//经办人
		String v_arrange_time  = Comm.nTrim(request.getParameter("arrange_time"));//
		String v_arrange_place  = Comm.nTrim(request.getParameter("arrange_place"));//
		v_arrange_place=new String(v_arrange_place.getBytes("ISO-8859-1"),"utf-8");
		System.out.println(v_arrange_place);
		String v_arrange_date  = Comm.nTrim(request.getParameter("arrange_date"));//
		String v_amount  = Comm.nTrim(request.getParameter("amount"));//总金额
		String v_addition  = Comm.nTrim(request.getParameter("addition"));//附件
		String v_pay_type  = Comm.nTrim(request.getParameter("pay_type"));//付费方式
		v_pay_type=new String(v_pay_type.getBytes("ISO-8859-1"),"utf-8");
		String v_other_uni_prj_code  = Comm.nTrim(request.getParameter("other_uni_prj_code"));//转入项目
		String v_accountname = Comm.nTrim(request.getParameter("accountname"));//银行账户名
		v_accountname=new String(v_accountname.getBytes("ISO-8859-1"),"utf-8");
		String v_account = Comm.nTrim(request.getParameter("account"));//银行账户
		String v_bankname = Comm.nTrim(request.getParameter("bankname"));//开户行
		v_bankname=new String(v_bankname.getBytes("ISO-8859-1"),"utf-8");
		/**[{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'},{uni_prj_code:'aaaa',amt:80,b_code:'b0001',frozen_seq_id:'0002'}]**/
		String yb_dtl = Comm.nTrim(request.getParameter("yb_dtl"));//预约解冻明细
		yb_dtl=new String(yb_dtl.getBytes("ISO-8859-1"),"utf-8");
		System.out.println(yb_dtl);
		String yb_dtl_df = Comm.nTrim(request.getParameter("yb_dtl_df"));//贷方
		yb_dtl_df=new String(yb_dtl_df.getBytes("ISO-8859-1"),"utf-8");
		System.out.println("yb_dtl_df="+yb_dtl_df);
		

		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		String USER_ID = "";
		String version = "";
		String key = "";
		String seq_datetime = "";
		String RetMsg = "";
		
		
      
			try {
			conn = pool.getConnection();

			
		    sql = "select trim(PARAMVALUE) version from sysparam t WHERE upper(trim(PARAMKEY)) = upper(trim('XML_VERSION'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				version = Comm.nTrim(rs.getString("version"));
			}
			sql = "select trim(PARAMVALUE) USER_ID from sysparam t WHERE UPPER(TRIM(PARAMKEY)) = UPPER(TRIM('USER_ID'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				USER_ID = Comm.nTrim(rs.getString("USER_ID"));
			}
			sql = "select trim(PARAMVALUE) key from sysparam t WHERE UPPER(TRIM(PARAMKEY)) = UPPER(TRIM('KEY'))";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
			if (rs.next()) {
				key = Comm.nTrim(rs.getString("key"));
			}

		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentTime = new Date();
			seq_datetime = sdf.format(currentTime);
			String  pay_records = "";
			String fee_records = "";
			String  xmlData = "<root><head><version>" + version +
			        "</version><user_id>" + USER_ID + "</user_id>" +
			        "<func_id>1001</func_id>" + "<seq_id>" +
			        SEQ_ID + "</seq_id>" + "<seq_datetime>" +
			        seq_datetime + "</seq_datetime></head><body>"+
	               "<baseinfo>" +
	                "<uni_prj_code>"+v_other_uni_prj_code+"</uni_prj_code>" +
	                "<req_sno>"+v_req_sno+"</req_sno>" +
	                "<req_name>"+v_req_name+"</req_name>" +
	                "<bu_operator>"+v_bu_operator+"</bu_operator>" +
	                "<mobilephone>"+v_tele+"</mobilephone>" +
	                "<tele>"+v_tele+"</tele>" +
	                "<email>"+v_email+"</email>" +
	                "<addition>"+v_addition+"</addition>" +
	                "<arrange_place>"+v_arrange_place+"</arrange_place>" +
	                "<arrange_date>"+v_arrange_date+"</arrange_date>" +
	                "<arrange_time>"+v_arrange_time+"</arrange_time>" +
	                "<remark>"+v_remark+"</remark>" +
	                "<amount>"+v_amount+"</amount>" +
	            "</baseinfo>" +
	            "<fee_records>";
			JSONArray arry = JSONArray.fromObject(yb_dtl);
		 	
		    String v_uni_prj_code = "";
		    String v_b_code = "";
		    String v_amt = "";
		    String frozen_seq_id = "";
		    String yb_type = "";
		    float C_PAYAMOUNT = 0;
		    float C_DF_AMOUNT = 0;
		    for (int i = 0; i < arry.size(); i++)
	        {	
		    	JSONObject jsonObject = arry.getJSONObject(i);
		 
				v_uni_prj_code = Comm.nTrim(jsonObject.get("uni_prj_code").toString());
				v_b_code = Comm.nTrim(jsonObject.get("b_code").toString());
				v_amt = Comm.nTrim(jsonObject.get("amt").toString());
				//frozen_seq_id = Comm.nTrim(jsonObject.get("frozen_seq_id").toString());
				if (Float.parseFloat(v_amt)<0) {
					fee_records =fee_records + "<fee_record>" +
				            "<uni_prj_code>"+v_uni_prj_code+"</uni_prj_code>" +
				            "<b_code>BBBB</b_code>" +
				            "<b_code_nega>"+v_b_code+"</b_code_nega>" +
				            "<fee_amount>"+(-Float.parseFloat(v_amt))+"</fee_amount>" +
				            "<frozen_seq_id></frozen_seq_id>"+
				            "</fee_record>";	
				}else{
					fee_records =fee_records + "<fee_record>" +
			                "<uni_prj_code>"+v_uni_prj_code+"</uni_prj_code>" +
			                "<b_code>"+v_b_code+"</b_code>" +
			                "<b_code_nega></b_code_nega>" +
			                "<fee_amount>"+v_amt+"</fee_amount>" + "</fee_record>";
					
				}
				
				
				
				C_PAYAMOUNT = C_PAYAMOUNT + Float.parseFloat(v_amt);
			}
		    JSONArray arry_df = JSONArray.fromObject(yb_dtl_df);
		    for (int i = 0; i < arry_df.size(); i++)
	        {	
		    	JSONObject jsonObject = arry_df.getJSONObject(i);
		 
				
				yb_type = Comm.nTrim(jsonObject.get("yb_type").toString());
				System.out.println("yb_type="+yb_type);
				if ("DG".equals(yb_type)) {
					yb_type = "对公转账";
				}else if ("DS".equals(yb_type)) {
					yb_type = "对私转账";
					
				}else{
					RetMsg = "参数异常yb_type="+yb_type;
					return;
				}
				pay_records = pay_records + "<pay_record>" +
					            "<pay_type>"+yb_type+"</pay_type>" +
					            "<pay_amount>"+Comm.nTrim(jsonObject.get("payamt").toString())+"</pay_amount>" +
					            "<sno></sno>" +
					            "<acnt>"+Comm.nTrim(jsonObject.get("account").toString())+"</acnt>" +
					            "<other_unit>"+Comm.nTrim(jsonObject.get("accname").toString())+"</other_unit>" +
					            "<bankno>"+Comm.nTrim(jsonObject.get("accbank").toString())+"</bankno>" +
					            "<address1></address1>" +
					            "<address2></address2>" +
					            "<branchcode></branchcode>" +
					            "<remark></remark>" +
					            "<clr_order></clr_order>" +
					            "<other_uni_prj_code></other_uni_prj_code>"+
					            "</pay_record>" ;
				
				
				C_DF_AMOUNT = C_DF_AMOUNT + Float.parseFloat(v_amt);
			}
		    if (C_DF_AMOUNT!=C_PAYAMOUNT) {

				RetMsg = "借方贷方金额不一致";
				return;
			}
		    
		    xmlData = xmlData +fee_records+ "</fee_records>" +
								"<pay_records>" +
								pay_records+
					            "</pay_records>" +
					            "</body>" +
					            "</root>";
			
			//保存XML日志
			System.out.println("记录报文");
			

			   String sign =Md5Tools.getMD5((xmlData+key).getBytes("UTF-8"));
	        	RetMsg = this.doPost(SEQ_ID,xmlData, sign);
	        	System.out.println("ybbak="+RetMsg);
	     

				System.out.println(RetMsg);
	            response.setContentType("text/plain;charset=UTF-8");
            	response.getWriter().write(RetMsg); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				RetMsg = "冻结异常="+e.getMessage();
			}finally{
				
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
	
	
//	
//	public String Frozentest(String SEQ_ID,String FreezeInfo ){
//		String func_id = "3001";
//		Date currentTime = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String dateString = formatter.format(currentTime);  
//		String Fdtl = "";
//
//		String sql = "";
//		try {
//			JSONArray arry = JSONArray.fromObject(FreezeInfo);
//		    for (int i = 0; i < arry.size(); i++)
//	        {	
//		    	JSONObject jsonObject = arry.getJSONObject(i);
//		    	Fdtl = Fdtl + "<frozen_record>" +
//								"<uni_prj_code>"+jsonObject.get("uni_prj_code").toString()+"</uni_prj_code>" +
//								"<bu_code></bu_code>" +
//								"<b_code>"+jsonObject.get("b_code").toString()+"</b_code>" +
//								"<frozen_amt>"+jsonObject.get("amt").toString()+"</frozen_amt>" +
//								"<frozen_seq_id>"+jsonObject.get("frozen_seq_id").toString()+"</frozen_seq_id>" +
//								"<frozen_remark></frozen_remark>" +
//								"</frozen_record>";
//	        }	
//			String xmlData = "<root>" +
//								"<head>" +
//									"<version>"+version+"</version>" +
//									"<user_id>"+USER_ID+"</user_id>" +
//									"<func_id> "+func_id+"</func_id>" +
//									"<seq_id>"+ SEQ_ID +"</seq_id>" +
//									"<seq_datetime>"+dateString+"</seq_datetime>" +
//								"</head>" +
//								"<body>" +
//									"<frozen_records>"+
//									Fdtl	+
//									"</frozen_records>" +
//								"</body>" +
//							"</root>";
//				
//				//this.ConnectUrl(xmlData, "http://192.168.20.16:8080/wws/services/WingsoftWebService/submit", "1");
//				this.doPost(xmlData, "a");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
//		
//
//		return "";
//	}
	

	  
	protected String doPost(String SEQ_ID,String xmlData,String sign)
    throws ServletException, IOException
    {
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
    try {
			conn = pool.getConnection();
		
		conn.setAutoCommit(false);
      serviceClient = new RPCServiceClient();
      Options options = serviceClient.getOptions();
      EndpointReference targetEPR = new EndpointReference(endPoint);
      options.setTo(targetEPR);
      QName opQName = new QName(namespaceURI,  "submit");
      Object[] opArgs = { xmlData, sign };
      Class[] opReturnType = { String.class };
      ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
      returnValue = (String)ret[0]; 
      if ("".equals(returnValue)||""==returnValue) {
			returnValue = "error:冻结未返回数据";
		
	  }else{
        System.out.println(returnValue);
        	try{
			  sql = "insert into  XML_LOG(log_id,LOG_DATE,XML_TEXT,RESP_XML_TEXT)" +
					" values('"+SEQ_ID+"',sysdate,substrb('"+xmlData+"',0,3999),substrb('"+returnValue+"',0,3999))";
					System.out.println("推送报文="+sql);
				    ps = conn.prepareStatement(sql);
				    ps.execute();
				} catch (Exception e) { 
					System.out.println("日志过长,保存失败，忽略"+SEQ_ID);
				}
				conn.commit();
        
		doc = DocumentHelper.parseText(returnValue);
		Element rootElt = doc.getRootElement(); // 获取根节点
		/**serial_no 表示冻结成功**/
		if ("serial_no".equals(rootElt.getName())) {
			serial_no = rootElt.getText();
			System.out.println("serial_no="+serial_no);
			returnValue = "success:"+serial_no;
		}else{
			//errmsg = rootElt.getn
			errmsg = rootElt.elementText("msg");
			System.out.println("serial_no="+serial_no);
			returnValue = "error:"+errmsg;
		}
	 }
    } catch (AxisFault e) {
    	returnValue = e.getMessage();
		returnValue = "error:"+returnValue;
		try {
			conn.setAutoCommit(true);
			conn.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	returnValue = e.getMessage();
		returnValue = "error:"+returnValue;
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NamingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}finally {
		try {
			serviceClient.cleanupTransport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	

	
	public static void main(String[] args) throws Exception {
		//ConnectWWS c = new ConnectWWS();
		String returnValue = "<?xml version=\"1.0\" encoding=\"utf-8\"?><error><type>322</type><msg>第1条&lt;frozen_record&gt;记录，，" +
				"项目号[10602-412111-15099]报销项B09017不存在</msg></error>";
		//<?xml version="1.0" encoding="utf-8"?><serial_no>223225</serial_no>
	    //c.Frozentest("12341", "[{uni_prj_code:'10602-412111-15099',amt:80,b_code:'B0907',frozen_seq_id:''}]");
		String a = "1234中文5";
		a = a.substring(0,a.length()-1);
		System.out.println(a);

	    Document doc = null;
		doc = DocumentHelper.parseText(returnValue);
		Element rootElt = doc.getRootElement(); // 获取根节点
		System.out.println(rootElt.elementText("msg"));
	}
	
}
