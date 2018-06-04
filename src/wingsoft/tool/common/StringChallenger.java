package wingsoft.tool.common;

import java.util.Date;
import java.util.HashMap;

public class StringChallenger extends HashMap<String, Date>
{
  public String newString()
  {
    String s = CommonOperation.getRandString(16);
    String src = s.substring(0, 10); String key = s.substring(10, 16);
    String encrypted = "";
    try {
     // encrypted = new MyDes(key).encrypt(src).toUpperCase();
      while (get(src) != null) {
        s = CommonOperation.getRandString(16);
        src = s.substring(0, 10); key = s.substring(10, 16);
      //  encrypted = new MyDes(key).encrypt(src).toUpperCase();
      }
      put(src, new Date());
     // key = new MyDes("capturer").encrypt(key).toUpperCase();
    }
    catch (Exception localException)
    {
    }
    return encrypted + key;
  }

  public boolean validate(String scc) {
    boolean valid = false;
    if ((scc != null) && (!("".equals(scc)))) {
      Date then = (Date)get(scc);
      if ((then != null) && (new Date().getTime() - then.getTime() < 60000L))
        valid = true;
      remove(scc);
    }
    return valid;
  }
}