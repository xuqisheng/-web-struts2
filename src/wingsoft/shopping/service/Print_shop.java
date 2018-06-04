package wingsoft.shopping.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import wingsoft.shopping.service.QRCode.QRCodeUtils;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;
import wingsoft.shopping.util.Comm;

public class Print_shop {
	private InputStream responseText; // AJAX 请求响应文本流

	public void setResponseText(InputStream responseText) {
		this.responseText = responseText;
	}

	public InputStream getResponseText() {
		return responseText;
	}
	public static String NTrim(String src){
		if (src==null) {
			return "";
		}else {
			return src.trim();
		}
	}
	
	public String YB_INFO() {   //预约打印WZ_YB
		System.out.println("打印预约信息");
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer  SEQ_ID = Integer.parseInt(CommonOperation.nTrim(request.getParameter("SEQ_ID"))); 
		System.out.println("SEQ_ID="+SEQ_ID);	
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ResultSetMetaData rsmd = null;
		int c = 0;
		int i = 0;
		int s = 0;

		try {
			conn = pool.getConnection();
			sql = "select y.req_sno,y.req_name,y.bu_operator,y.amount,y.serion_no,to_char(y.seq_date,'yyyy-mm-dd') seq_date,y.arrange_date," +
					"y.arrange_time,y.arrange_place,y.remark,y.addition,y.tele ," +
					"y.pay_type,y.account,y.accountname,y.bankname" +
					" from YB_TRANCE_APP y " +
					" where y.seq_id=?";	
			System.out.println("print-yb----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SEQ_ID);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			c = rsmd.getColumnCount();
			StringBuffer dataMap = new StringBuffer();
			dataMap.append("[");
			while (rs.next()) {
				dataMap.append("{");

				for (i = 1; i <= c; i++) {
					dataMap.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\",");
					 
				}
			}
			
			
			sql = "select t.prj_code,t.b_name,t.charger,sum(t.amt) tamt from yb_trance_app_dtl t where t.seq_id=? group by t.prj_code,t.b_name,t.charger";	
			System.out.println("print-ybdtl----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SEQ_ID);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			c = rsmd.getColumnCount();
			StringBuffer dataMapDtl = new StringBuffer();

			
			i = 0;
			dataMapDtl.append("[");
			while (rs.next()) {
				dataMapDtl.append("{");

				for (i = 1; i <= c; i++) {
					dataMapDtl.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
					if (i < c) {
						dataMapDtl.append(",");
					}
				}
				dataMapDtl.append("},");
			}
			s = dataMapDtl.length();
			if (i > 0) {
				dataMapDtl.deleteCharAt(s - 1);
			}
			dataMapDtl.append("]");
			dataMap.append("DTL:"+dataMapDtl.toString());
			dataMap.append("}]");
			System.out.println(dataMapDtl.toString());
			System.out.println(dataMap.toString());
			this.setResponseText(new ByteArrayInputStream(dataMap.toString()
					.getBytes

					("utf-8")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return "textPlain";
	}
	
	
	
	public String YB_INFO_SELF() {   //预约打印WZ_YB
		System.out.println("打印预约信息自购");
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer  SEQ_ID = Integer.parseInt(CommonOperation.nTrim(request.getParameter("SEQ_ID"))); 
		System.out.println("SEQ_ID="+SEQ_ID);	
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ResultSetMetaData rsmd = null;
		int c = 0;
		int i = 0;
		int s = 0;

		try {
			conn = pool.getConnection();
			sql = "select y.req_sno,y.req_name,y.bu_operator,y.amount,y.serion_no,to_char(y.seq_date,'yyyy-mm-dd') seq_date,y.arrange_date," +
					"y.arrange_time,y.arrange_place,y.remark,y.addition,y.tele ," +
					"y.pay_type,y.account,y.accountname,y.bankname" +
					" from YB_TRANCE_APP y " +
					" where y.seq_id=?";	
			System.out.println("print-yb----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SEQ_ID);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			c = rsmd.getColumnCount();
			StringBuffer dataMap = new StringBuffer();
			dataMap.append("[");
			while (rs.next()) {
				dataMap.append("{");

				for (i = 1; i <= c; i++) {
					dataMap.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\",");
					 
				}
			}
			
			
			sql = "select t.prj_code,t.b_name,t.charger,sum(t.amt) tamt from yb_trance_app_dtl t where t.seq_id=? group by t.prj_code,t.b_name,t.charger";	
			System.out.println("print-ybdtl----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SEQ_ID);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			c = rsmd.getColumnCount();
			StringBuffer dataMapDtl = new StringBuffer();

			
			i = 0;
			dataMapDtl.append("[");
			while (rs.next()) {
				dataMapDtl.append("{");

				for (i = 1; i <= c; i++) {
					dataMapDtl.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
					if (i < c) {
						dataMapDtl.append(",");
					}
				}
				dataMapDtl.append("},");
			}
			s = dataMapDtl.length();
			if (i > 0) {
				dataMapDtl.deleteCharAt(s - 1);
			}
			dataMapDtl.append("]");
			
			
			
			sql = "select  s.account,s.accname,s.accbank,t.yb_type,sum(s.payamt) tamt from yb_trance_app_dtl t,supplier_bank s" +
					" where t.ordercode=s.ordercode and  t.seq_id=? group by s.account,s.accname,s.accbank,t.yb_type";	
			System.out.println("print-ybdtl----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, SEQ_ID);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();
			c = rsmd.getColumnCount();
			StringBuffer dataMapDtl_DF = new StringBuffer();

			
			i = 0;
			dataMapDtl_DF.append("[");
			while (rs.next()) {
				dataMapDtl_DF.append("{");

				for (i = 1; i <= c; i++) {
					dataMapDtl_DF.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
					if (i < c) {
						dataMapDtl_DF.append(",");
					}
				}
				dataMapDtl_DF.append("},");
			}
			s = dataMapDtl_DF.length();
			if (i > 0) {
				dataMapDtl_DF.deleteCharAt(s - 1);
			}
			dataMapDtl_DF.append("]");
			
			
			dataMap.append("DTL:"+dataMapDtl.toString());
			dataMap.append(",Dtl_DF:"+dataMapDtl_DF.toString());
			dataMap.append("}]");
			System.out.println(dataMapDtl.toString());
			System.out.println(dataMap.toString());
			this.setResponseText(new ByteArrayInputStream(dataMap.toString()
					.getBytes

					("utf-8")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return "textPlain";
	}
	
	public String YB_DTL() {   //预约打印附件明细
		System.out.println("预约打印明细");
		HttpServletRequest request = ServletActionContext.getRequest();
		String  SEQ_ID = request.getParameter("SEQ_ID").trim(); 
		System.out.println("SEQ_ID="+SEQ_ID);	
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = pool.getConnection();
			sql = "select o.yb_no serion_no,p.ordercode,t.prj_code,o.app_name,t.charger,to_char(t.deal_date,'yyyy-mm-dd') deal_date,o.b_name," +
					"p.common_name name,p.by_number,p.price,(p.by_number*p.price) tamt " +
					" from YB_TRANCE_APP_DTL t,t_order_product p,t_order o " +
					" where t.ordercode=p.ordercode and t.ordercode=o.ordercode and t.seq_id=? order by t.prj_code";	
			System.out.println("print-appdtl----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, SEQ_ID);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int c = rsmd.getColumnCount();

			StringBuffer dataMap = new StringBuffer();

			int i = 0;
			dataMap.append("[");
			while (rs.next()) {
				dataMap.append("{");

				for (i = 1; i <= c; i++) {
					dataMap.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
					if (i < c) {
						dataMap.append(",");
					}
				}
				dataMap.append("},");
			}
			int s = dataMap.length();
			if (i > 0) {
				dataMap.deleteCharAt(s - 1);
			}
			dataMap.append("]");

			this.setResponseText(new ByteArrayInputStream(dataMap.toString()
					.getBytes

					("utf-8")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return "textPlain";
	}
	
	public String YB_DTL_SELF() {   //预约打印附件明细_自购
		System.out.println("自购转账对照");
		HttpServletRequest request = ServletActionContext.getRequest();
		String  SEQ_ID = request.getParameter("SEQ_ID").trim(); 
		System.out.println("SEQ_ID="+SEQ_ID);	
		ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
		System.out.println(pool);
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";

		try {
			conn = pool.getConnection();
			sql = "select o.yb_no serion_no,o.ordercode,t.prj_code,o.app_name,b.account,b.accname,b.accbank," +
					"t.yb_type,t.charger,to_char(t.deal_date,'yyyy-mm-dd') deal_date,o.b_name," +
					"b.payamt " +
					" from YB_TRANCE_APP_DTL t,t_order o,supplier_bank b " +
					" where  t.ordercode=b.ordercode and t.ordercode=o.ordercode and t.seq_id=? order by t.prj_code";	
			System.out.println("print-appdtl_zg----sql:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, SEQ_ID);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int c = rsmd.getColumnCount();

			StringBuffer dataMap = new StringBuffer();

			int i = 0;
			dataMap.append("[");
			while (rs.next()) {
				dataMap.append("{");

				for (i = 1; i <= c; i++) {
					dataMap.append(rsmd.getColumnName(i) + ":\""
							+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
					if (i < c) {
						dataMap.append(",");
					}
				}
				dataMap.append("},");
			}
			int s = dataMap.length();
			if (i > 0) {
				dataMap.deleteCharAt(s - 1);
			}
			dataMap.append("]");

			this.setResponseText(new ByteArrayInputStream(dataMap.toString()
					.getBytes

					("utf-8")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return "textPlain";
	}
	

	  public void GetQRCode(){
		  FileInputStream fis = null; 
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setContentType("image/png");
	        try {
	            OutputStream out = response.getOutputStream();
	            /*先生成图片到指定位置，然后加载出来*/
	            String encodeddata = request.getParameter("encodeddata");
	            QRCodeUtils QRC = new QRCodeUtils();
	            String Ppath = ServletActionContext.getServletContext().getRealPath("/");//项目路径
	            String PathString  = QRC.CreateQRCode(Ppath,encodeddata);
	            File file = new File(PathString);
	            fis = new FileInputStream(file);
	            byte[] b = new byte[fis.available()];
	            fis.read(b);
	            out.write(b);
	            out.flush();
	        } catch (Exception e) {
	             e.printStackTrace();
	        } finally {
	            if (fis != null) {
	                try {
	                   fis.close();
	                } catch (IOException e) {
	                e.printStackTrace();
	            }   
	            }
	            }         		  
	  }  
	  
	  public String WZ_Send() {
			System.out.println("订单明细-打印送货单"); 
			HttpServletRequest request = ServletActionContext.getRequest(); 
			String ordercode  = Comm.nTrim(request.getParameter("v_ordercode"));
			
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			ResultSetMetaData rsmd = null;
			int c = 0;
			int i = 0;
			int s = 0;
			try {
				conn = pool.getConnection(); 
				sql = "select o.ordercode,o.pro_code,o.pro_name,o.app_name,o.sendcode,o.getname,o.getphone,s.name sname," +
						" to_char(o.date1,'yyyy-mm-dd') indate,to_char(o.date3,'yyyy-mm-dd') senddate,to_char(o.date6,'yyyy-mm-dd') appsenddate,o.totalprice,o.postage," +
						" o.app_msg,o.address,o.delivery_man,o.delivery_man_phone from t_order o,supplier s where " +
						" o.supplier_id=s.id and  o.ordercode=rpad(?,20,' ')";
				System.out.println("print-app----sql:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, ordercode);
				rs = ps.executeQuery();
				rsmd = rs.getMetaData();
				c = rsmd.getColumnCount();
				StringBuffer dataMap = new StringBuffer();
				dataMap.append("[");
				while (rs.next()) {
					dataMap.append("{");

					for (i = 1; i <= c; i++) {
						dataMap.append(rsmd.getColumnName(i) + ":\""
								+ CommonOperation.nTrim(rs.getObject(i)) + "\",");
						 
					}
				}
				
				
				sql = "SELECT p.scode,p.wcode,tor.common_name, tor.chemical_name, "+
						"tor.english_name, tor.cas_name, tor.specifications, "+
						"tor.measurement_unit, tor.by_number, tor.price, tor.id "+
						"FROM t_order_product tor,product p "+
						"WHERE  trim(tor.ordercode) = trim('"+ordercode+"') "+
						"and TRIM(p.id)=TRIM(tor.product_id)";	
				System.out.println("print-app----sql:" + sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				rsmd = rs.getMetaData();
				c = rsmd.getColumnCount();
				StringBuffer dataMapDtl = new StringBuffer();

				
				i = 0;
				dataMapDtl.append("[");
				while (rs.next()) {
					dataMapDtl.append("{");

					for (i = 1; i <= c; i++) {
						dataMapDtl.append(rsmd.getColumnName(i) + ":\""
								+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
						if (i < c) {
							dataMapDtl.append(",");
						}
					}
					dataMapDtl.append("},");
				}
				s = dataMapDtl.length();
				if (i > 0) {
					dataMapDtl.deleteCharAt(s - 1);
				}
				dataMapDtl.append("]");
				dataMap.append("DTL:"+dataMapDtl.toString());
				dataMap.append("}]");
				System.out.println(dataMapDtl.toString());
				System.out.println(dataMap.toString());
				this.setResponseText(new ByteArrayInputStream(dataMap.toString()
						.getBytes

						("utf-8")));

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
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
			return "textPlain";
		}
	  
	  public String SelfBuy() {
			System.out.println("订单明细-自购订单"); 
			HttpServletRequest request = ServletActionContext.getRequest(); 
			String ordercode  = Comm.nTrim(request.getParameter("ordercode"));
			
			ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
			System.out.println(pool);
			Connection conn = null;

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			ResultSetMetaData rsmd = null;
			int c = 0;
			int i = 0;
			int s = 0;

			String userid = "";
			
			try {
				if (request.getSession().getAttribute("userId")!=null) {
					userid = (String) request.getSession().getAttribute("userId");
					
					
				conn = pool.getConnection(); 
				sql = "select t.ordercode,t.pro_code,t.app_name,t.pro_name,t.receipt,t.self_msg,t.b_name,b.account,b.accname," +
						"b.accbank,s.name sname,(t.totalprice+t.postage) totalprice,t.postage,decode(t.yb_type,'DG','对公转账','DS','对私转账') yb_type," +
						"to_char(t.DATE7,'yyyy-mm-dd') buydate,to_char(t.DATE1,'yyyy-mm-dd') indate " +
						" from T_ORDER t,supplier_s s ,supplier_bank b " +
						" where t.supplier_id=s.id and s.id=b.sid and b.ordercode=t.ordercode " +
						" and t.ordercode=rpad(?,20,' ') and t.app_acc=rpad(?,128,' ')" ;
				System.out.println("print-app----sql:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, ordercode); 
				ps.setString(2, userid);
				rs = ps.executeQuery();
				rsmd = rs.getMetaData();
				c = rsmd.getColumnCount();
				StringBuffer dataMap = new StringBuffer();
				dataMap.append("[");
				while (rs.next()) {
					dataMap.append("{");

					for (i = 1; i <= c; i++) {
						dataMap.append(rsmd.getColumnName(i) + ":\""
								+ CommonOperation.nTrim(rs.getObject(i)) + "\",");
						 
					}
				}
				
				
				sql = "SELECT p.scode,tor.common_name, tor.chemical_name, "+
						"tor.english_name, tor.cas_name, tor.specifications, "+
						"tor.measurement_unit, tor.by_number, tor.price, tor.id "+
						"FROM t_order_product tor,product_s p ,T_ORDER o "+
						"WHERE tor.ordercode = o.ordercode and o.ordercode=rpad(?,20,' ') and o.app_acc=rpad(?,128,' ') "+
						"and TRIM(p.id)=TRIM(tor.product_id)";	
				System.out.println("print-app----sql:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, ordercode); 
				ps.setString(2, userid);
				rs = ps.executeQuery();
				rsmd = rs.getMetaData();
				c = rsmd.getColumnCount();
				StringBuffer dataMapDtl = new StringBuffer();

				
				i = 0;
				dataMapDtl.append("[");
				while (rs.next()) {
					dataMapDtl.append("{");

					for (i = 1; i <= c; i++) {
						dataMapDtl.append(rsmd.getColumnName(i) + ":\""
								+ CommonOperation.nTrim(rs.getObject(i)) + "\"");
						if (i < c) {
							dataMapDtl.append(",");
						}
					}
					dataMapDtl.append("},");
				}
				s = dataMapDtl.length();
				if (i > 0) {
					dataMapDtl.deleteCharAt(s - 1);
				}
				dataMapDtl.append("]");
				dataMap.append("DTL:"+dataMapDtl.toString());
				dataMap.append("}]");
				System.out.println(dataMapDtl.toString());
				System.out.println(dataMap.toString());
				this.setResponseText(new ByteArrayInputStream(dataMap.toString()
						.getBytes

						("utf-8")));
				} 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
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
			return "textPlain";
		}
	  
}
