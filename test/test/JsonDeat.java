package test;


import net.sf.json.JSONArray;
import wingsoft.custom.CommonJsonDeal;

public class JsonDeat {
    public static void main(String[] args) {
        StringBuffer str = new StringBuffer();
        str=new Test().str;
        System.out.println(str);
        JSONArray jo = JSONArray.fromObject(str.toString());
        System.out.println(jo);
        JSONArray jo2 = CommonJsonDeal.updateJsonType(jo,"class_name");
        System.out.println(jo2);
    }
}
