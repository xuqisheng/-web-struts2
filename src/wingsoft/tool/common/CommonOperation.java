package wingsoft.tool.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import wingsoft.core.command.Field;

public class CommonOperation
{
  public static String formatDate(Date date, String fmtStr)
  {
    return new SimpleDateFormat(fmtStr).format(date);
  }

  public static String TransactSQLInjection(String param)
  {
    Pattern p = Pattern.compile(".*((';)+|(--)+|(chr\\()+|(char\\()).*", 2);
    Matcher m = p.matcher(param);
    if (m.find())
      return m.replaceAll(" ");

    p = Pattern.compile("\\s(exec|master|truncate|declare|dbms\\.|waitfor\\sdelay|-|\\+|,)\\s", 2);
    m = p.matcher(param);
    return m.replaceAll(" ");
  }

  public static String formatDate(Timestamp timestamp, String fmtStr)
  {
    return new SimpleDateFormat(fmtStr).format(timestamp);
  }

  public static Date parseFullDate(String dateStr, String fmtStr)
    throws ParseException
  {
    if ((dateStr == null) || (dateStr.equals("")))
      return null;

    if (dateStr.length() <= 10)
      dateStr = dateStr + " 00:00:00";

    return new SimpleDateFormat(fmtStr).parse(dateStr);
  }

  public static String nTrim(String str)
  {
    if (str == null)
      return "";
    return str.trim();
  }

  public static String nTrim(Object str)
  {
    if (str == null)
      return "";

    return str.toString().trim();
  }

  public static String nTrimToDefault(Object str, String defaultStr) {
    if (str == null)
      return defaultStr;

    return str.toString().trim();
  }

  public static String nTrimToShow(Object str) {
    if (str == null)
      return "&nbsp;";

    return str.toString().trim();
  }

  public static String nTrimToShowEditTable(Object str) {
    if (str == null)
      return "";

    return str.toString().trim();
  }

  public static String nTrim2(String str) {
    if (str == null)
      return "";

    return str;
  }

  public static String nTrimObj(Object obj) {
    if (obj == null)
      return "";

    return obj.toString().trim();
  }

  public static String nTrimObj2(Object obj)
  {
    if (obj == null)
      return "";

    return obj.toString();
  }

  public static String transfer(String s)
  {
    StringBuffer content = new StringBuffer();

    for (int k = 0; k < s.length(); ++k) {
      char c = s.charAt(k);
      if (c == '\n') {
        content.append(" ");
      } else if (c == '\r') {
        content.append(" ");
      }
      else if (c == '"') {
        content.append("\\\"");
      } else {
        if (c == '\t')
          continue;

        content.append(c);
      }
    }

    return content.toString();
  }

  public static String quoteJsonKey(String s) {
    return s;
  }

  public static String transferLineBreak(String s)
  {
    StringBuffer content = new StringBuffer();

    for (int k = 0; k < s.length(); ++k)
    {
      char c = s.charAt(k);
      if ((c == '\n') || (c == '\r') || (c == '\t'))
        content.append(" ");
      else
        content.append(c);

    }

    return content.toString();
  }

  public static String transferlineBreak2(String s)
  {
    StringBuffer content = new StringBuffer();

    for (int k = 0; k < s.length(); ++k) {
      char c = s.charAt(k);
      if ((c == '\n') || (c == '\r'))
        content.append("<br/>");
      else if (c == '\t')
        content.append("\\\t");
      else if (c == '"')
        content.append("\\\"");
      else
        content.append(c);

    }

    return content.toString();
  }

  public static String formatNumber(double d) {
    DecimalFormat df = new DecimalFormat("###,###.##");
    return df.format(d);
  }

  public static String formatMoneyToShow(Object m)
  {
    double money;
    if (m == null)
      return "&nbsp;";
    try
    {
      money = Double.parseDouble(m.toString());
    } catch (Exception localException) {
      return m.toString();
    }

    String mo = formatNumber(money);

    int index = mo.indexOf(".");
    if (index < 0) {
      return mo + ".00";
    }

    String xs = mo.substring(index);
    if (xs.length() < 3) {
      return mo + "0";
    }

    return mo;
  }

  /**
   * 获取随机字符串
   * @param num
   * @return
   */
  public static String getRandString(int num) {
    StringBuffer sb = new StringBuffer();
    Random random = new Random();

    for (int i = 0; i < num; ++i)
      if (random.nextInt(2) == 1)
        sb.append((char)(random.nextInt(10) + 48));
      else
        sb.append((char)(random.nextInt(26) + 65));


    return sb.toString();
  }

  


  public static String encrypt(String cs, int shift)
  {
    StringBuffer s = new StringBuffer();
    for (int i = cs.length() - 1; i >= 0; --i) {
      char c = cs.charAt(i);
      int n = (c + shift) % 127;
      if (n > 63)
        n = n % 10 + 48;
      else
        n = n % 26 + 65;

      s.append((char)n);
    }

    return s.toString();
  }

  public static String encrypt2(String cs, int shift) {
    StringBuffer s = new StringBuffer();
    shift = shift % 8 + 1;
    for (int i = cs.length() - 1; i >= 0; i -= shift) {
      int j = 0;
      int n = 0;
      while (j < shift) {
        int p = i - j;
        if (p < 0)
          break;
        n += cs.charAt(p);
        ++j;
      }

      n %= 127;
      if (n > 63)
        n = n % 10 + 48;
      else
        n = n % 26 + 65;

      s.append((char)n);
    }

    return s.toString(); }

  public static String parseSQL(String sql, List<Field> params) {
    for (int i = 0; i < params.size(); ++i) {
      Field f = (Field)params.get(i);
      sql = sql.replace(f.getName(), f.getValue());
    }
    return sql; }

  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
      ip = request.getHeader("Proxy-Client-IP");

    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
      ip = request.getHeader("WL-Proxy-Client-IP");

    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip)))
      ip = request.getRemoteAddr();

    return ip;
  }

  public static String getStrBetween(String srcStr, String str1, String str2) {
    int idx1 = srcStr.indexOf(str1);
    int idx2 = srcStr.indexOf(str2, idx1 + 1);
    if (idx1 == idx2)
      return "";

    return srcStr.substring(idx1 + 1, idx2);
  }

  public static String getHorMapCond(String sectionName, String mapCode, String cond)
  {
    String mapCond = "";
    sectionName = sectionName.toUpperCase();
    cond = cond.toUpperCase();

    if (cond.indexOf(sectionName) >= 0) {
      String[] allConds = cond.split(";");
      for (int i = 0; i < allConds.length; ++i) {
        String[] oneCond = allConds[i].split(" OR ");
        for (int j = 0; j < oneCond.length; ++j)
          if (oneCond[j].indexOf(sectionName) >= 0)
            mapCond = mapCond + " AND " + oneCond[j];
      }
    }

    return mapCond.replace(sectionName, mapCode);
  }

  public static StringBuffer getFileContent(String fName) {
    StringBuffer sbFileContent = new StringBuffer();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fName), "UTF-8"));
      try {
        String line = br.readLine();
        while (line != null) {
          sbFileContent.append(line);
          line = br.readLine();
        }
      }
      finally {
        br.close();
      }
      return sbFileContent;
    } catch (IOException e) {
      e.printStackTrace(); }
    return null;
  }

  public static String HTMLDecode(String s) {
    return s.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&nbsp;", " ").replace("&#39;", "'").replace("&quot;", "\"");
  }

  public static boolean checkSqlInjection(String s) {
    Pattern p = Pattern.compile("\\s('|and|exec|insert|select|delete|update|count|\\*|\\%|chr|mid|master|truncate|char|declare|dbms\\.|union|waitfor\\sdelay|;|or|-|\\+|,)\\s", 2);
    Matcher m = p.matcher(s);

    return (!(m.find()));
  }

  public static String captureChallengeDecode(String encryptedChallangeCode)
  {
    String originalChallangeCode = "";
    String encrypted = encryptedChallangeCode.substring(0, 10); String key = encryptedChallangeCode.substring(11, 15);
    try {
      //originalChallangeCode = new MyDes(key).decrypt(encrypted);
    }
    catch (Exception localException) {
    }
    return originalChallangeCode;
  }
}