package wingsoft.shopping.service.yb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
 




import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder; 

public class Ftp {

	private static FTPClient ftpClient = null;
	
	/*
	 * 链接ftp
	 * @path：ftp子目录
	 */
	public static void conn(String ip,int port,String user,String pwd,String path){
		try {
			ftpClient = new FTPClient();
			ftpClient.setControlEncoding("UTF-8"); // 中文支持  
			ftpClient.connect(ip,port);   
			ftpClient.login(user, pwd);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                System.out.println("未连接到FTP，用户名或密码错误。");  
                ftpClient.disconnect();  
            } else {  
    			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                ftpClient.enterLocalPassiveMode();  
    			System.out.println("FTP login success!");
            } 
//			if(path.length()!=0){
//				ftpClient.changeDirectory(path);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 关闭链接
	 */
	public static void closeFTP(){
		try {
			ftpClient.disconnect();  
			System.out.println("关闭ftp链接");
		} catch (IOException e) {
			System.out.println("ftp链接，关闭失败");
			e.printStackTrace();
		}
	}
	
	/*
	 * 从ftp上下载文件到本地（非base64加密）
	 * @param ftpFile  读取的文件名如：1.test
	 * @param localFile 下载到本地的文件
	 */
	public static long download(String ftpFile,String localFile) {
		InputStream is = null; 
		FileOutputStream os = null;
		long result = 0;
		String ftpPath = "";
		String ftpName = "";
		
		//读取文件
		try {   

			ftpPath = getFileNamePath(ftpFile);
            ftpName = getFileNameWithSuffix(ftpFile);
            ftpClient.changeWorkingDirectory(ftpPath); 
			//ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
			//ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			is = ftpClient.retrieveFileStream(ftpName);
			File outfile = new File(localFile);
			os = new FileOutputStream(outfile);
		}   catch (Exception e) {
			e.printStackTrace();
		}

		//byte[]
		byte[] buffer = new byte[1024];
		int c;
		try {
			while((c = is.read(buffer)) != -1){
				result = result +c;
				os.write(buffer, 0, c);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//关闭
		if(is !=null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
	}

	/*
	 * 读取文件，并返回base64字符串
	 */
	public static String getBase64(String ftpFile) {
		InputStream is = null; 
		String base64 = null;
		String ftpPath = "";
		String ftpName = "";
		//读取文件
		try {
			
            ftpPath = getFileNamePath(ftpFile);
            ftpName = getFileNameWithSuffix(ftpFile);
            ftpClient.changeWorkingDirectory(ftpPath);  
			is = ftpClient.retrieveFileStream(ftpName);
		}   catch (Exception e) {
			e.printStackTrace();
		}

		//byte[]
		/*byte[] buffer = null;
		try {
			buffer = new byte[is.available()];
			is.read(buffer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		BASE64Encoder encoder = new BASE64Encoder();
		String str = "";
		String result = "";
		byte[] buffer = null;
		buffer = new byte[1024];
		int c;
		try {
			while((c = is.read(buffer)) != -1){
				str =encoder.encode(buffer);
//				System.out.println("------");
//				System.out.println(str);
				result += str;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(is !=null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/*
	 * 读取文件，并返回base64字符串
	 */
	public static String getBase64(File file) {
		InputStream is = null; 
		String base64 = null;
		//读取文件
		try {
			if(!file.exists()){
				return "文件不存在";
			}
			is =new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//byte[]
		byte[] buffer = null;
		try {
			buffer = new byte[is.available()];
			is.read(buffer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(is !=null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(buffer);
	}
	
	/*
	 * 解码base64，下载文件
	 */
	public static boolean decode(String encode,String localFile){
		if(encode==null){
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(encode);
			for (int i = 0; i < b.length; i++) {
				if(b[i]<0){
					//调整异常数据
					b[i]+=256;
				}
			}
			OutputStream os = new FileOutputStream(localFile); 
			os.write(b);
			os.flush();
			os.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
	
		String filename = "SHOP/JPG/000/000/000/1230.JPG";
		Ftp.conn("192.168.102.5", 21, "ftpguest", "ac89c2a32392d39e", "");
		//Ftp.download(filename, "D:\\a116.jpg");
		//String encoder = Ftp.getBase64(filename);
		//boolean flag = Ftp.decode(encoder,"D:\\d5.jpg");
		//Ftp.closeFTP();
		//System.out.println(encoder);
	/*	String filename = "/11/22/33/1001.jpg";
		Ftp.conn("127.0.0.1", 21, "admin", "123","");
		String encoder = Ftp.getBase64(filename);
		boolean flag = Ftp.decode(encoder,"D:\\downtest\\2.jpg");
		Ftp.closeFTP();
		System.out.println(encoder);*/
		System.out.println(getFileNamePath(filename));
		download(filename,"D:\\test\\a23.jpg");
		Ftp.closeFTP();
	}

	public static String getEncoder(String filename){
		Ftp.conn("192.168.102.5", 21, "ftpguest", "ac89c2a32392d39e", "");
		//Ftp.conn("192.168.20.17", 21, "test", "test", "");
		String encoder = Ftp.getBase64(filename);
		Ftp.closeFTP();
//		System.out.println(encoder);
		return encoder;
	}
	
	
	 /** 
     * 保留文件名及后缀 
     */  
    public static String getFileNameWithSuffix(String pathandname) {         
        int start = pathandname.lastIndexOf("/");  
        if (start != -1 ) {  
            return pathandname.substring(start + 1);  
        } else {  
            return null;  
        }         
    }         
	
    /** 
     * 保留文件名及后缀 
     */  
    public static String getFileNamePath(String pathandname) {         
        int start = pathandname.lastIndexOf("/");   
        if (start != -1 ) {  
            return pathandname.substring(0,start + 1);  
        } else {  
            return null;  
        }         
    }    
	
	
	
	
	
	
	
	
	
	
	
	
}
