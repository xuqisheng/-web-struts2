package wingsoft.tool.common;

import com.sun.crypto.provider.SunJCE;
import java.io.PrintStream;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyDes
{
  private static String strDefaultKey = "wingsoft";//"wingsoft"
  private Cipher encryptCipher;
  private Cipher decryptCipher;

  public static String byteArr2HexStr(byte[] arrB)
    throws Exception
  {
    int iLen = arrB.length;

    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; ++i) {
      int intTmp = arrB[i];

      while (intTmp < 0) {
        intTmp += 256;
      }

      if (intTmp < 16)
        sb.append("0");

      sb.append(Integer.toString(intTmp, 16));
    }
    return sb.toString();
  }

  public static byte[] hexStr2ByteArr(String strIn)
    throws Exception
  {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;

    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i += 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[(i / 2)] = (byte)Integer.parseInt(strTmp, 16);
    }
    return arrOut;
  }

  public MyDes() throws Exception
  {
    this(strDefaultKey);
  }

  public MyDes(String strKey) throws Exception
  {
    this.encryptCipher = null;
    this.decryptCipher = null;

    Security.addProvider(new SunJCE());
    Key key = getKey(strKey.getBytes());

    this.encryptCipher = Cipher.getInstance("DES");
    this.encryptCipher.init(1, key);

    this.decryptCipher = Cipher.getInstance("DES");
    this.decryptCipher.init(2, key);
  }

  public byte[] encrypt(byte[] arrB)
    throws Exception
  {
    return this.encryptCipher.doFinal(arrB);
  }

  public String encrypt(String strIn)
    throws Exception
  {
    return byteArr2HexStr(encrypt(strIn.getBytes()));
  }

  public byte[] decrypt(byte[] arrB)
    throws Exception
  {
    return this.decryptCipher.doFinal(arrB);
  }

  public String decrypt(String strIn)
    throws Exception
  {
    return new String(decrypt(hexStr2ByteArr(strIn)));
  }

  private Key getKey(byte[] arrBTmp)
    throws Exception
  {
    byte[] arrB = new byte[8];

    for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
      arrB[i] = arrBTmp[i];
    }

    Key key = new SecretKeySpec(arrB, "DES");

    return key; }

//  public static void main(String[] argv) {
//    MyDes des;
//    try {//sKRfEOOs1rqvZKWD2DqB+l00nBTJ9OHtA2bpr4rMw1fIekFdLHjOSO35Z+pvE7S8
//      des = new MyDes("12345678");
//      System.out.println("de="+des.encrypt("A"));
//      MyDes des2 = new MyDes("96T4LJ");
//      System.out.println(des2.decrypt("409f7660aa6120866b0b0af6dc62c879"));
//      MyDes des3 = new MyDes("capturer");
//      System.out.println(des3.encrypt("96T5LJ"));
//    }
//    catch (Exception ex)
//    {
//      ex.printStackTrace();
//    }
//  }

  public static String md5Encrypt(String str) throws Exception{
    String strs = new MyDes().encrypt(str);
    String mdStr = Md5Tools.getMD5(strs.getBytes());
    return mdStr;
  }

}