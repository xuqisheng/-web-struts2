package wingsoft.tool.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Md5Tools {
	  public static String getMD5(byte[] source)
	    throws NoSuchAlgorithmException
	  {
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	      'a', 'b', 'c', 'd', 'e', 'f' };
	    MessageDigest digest = MessageDigest.getInstance("MD5");
	    digest.update(source);
	    byte[] tmp = digest.digest();
	    char[] dest = new char[32];
	    int k = 0;
	    for (int i = 0; i < 16; ++i) {
	      byte byte0 = tmp[i];
	      dest[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
	      dest[(k++)] = hexDigits[(byte0 & 0xF)];
	    }
	    return new String(dest);
	  }


}
