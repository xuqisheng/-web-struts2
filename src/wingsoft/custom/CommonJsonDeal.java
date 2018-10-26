package wingsoft.custom;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CommonJsonDeal {


    private CommonJsonDeal() {
    }

//    public static CommonJsonDeal getInstance() {
//        if (instance == null) {
//            synchronized (CommonJsonDeal.class) {
//                if (instance == null) {
//                    instance = new CommonJsonDeal();
//                }
//            }
//        }
//        return instance;
//    }
    // 将json属性中的值提出来封装

    /**
     * @param "jsonStr"
     * @param "keys需要提出来的KEYS"
     * @return jsonObject
     */
    private static JSONObject upJsonDeal(String jsonStr, String keys) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String firstValue = "";
        JSONObject newObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            firstValue = jsonObject.getString(keys);
            newObj.put(keys, firstValue);
            jsonObject.remove(keys);
            jsonArray.add(jsonObject);
            newObj.put("list", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newObj;
    }

    /**
     * 扩展相同的value
     *
     * @return
     */
    private static JSONArray updateList(JSONArray jsonStr, String keys) {
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        JSONArray temp = new JSONArray();
        try {
            HashSet<String> pici = new HashSet<String>();
            for (Object o : jsonArray) {
                JSONObject ob = JSONObject.fromObject(o);
                pici.add(ob.getString(keys));
            }
            for (String s : pici) {
                JSONArray piciArray = new JSONArray();
                JSONObject job = new JSONObject();
                for (Object o : jsonArray) {
                    JSONObject ob = JSONObject.fromObject(o);
                    if (ob.containsValue(s)) {
                        piciArray.addAll(ob.getJSONArray("list"));
                        job.put(keys, ob.getString(keys));
                        job.put("list", piciArray);
                    }
                }
                temp.add(job);
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * 根据key提出key
     * 提出keys值，但是并不合并 ————》下一步是updateList，用这个方法合并list
     *
     * @param "jsonStr(Array)"
     * @param "keys"
     * @return jsonArray
     */
    private static JSONArray dealArray(JSONArray jsonStr, String keys) {
        JSONArray paramArray = JSONArray.fromObject(jsonStr);
        JSONArray jsonArray = new JSONArray();
        for (Object o : paramArray) {
            JSONObject jo = JSONObject.fromObject(o);
            jo = upJsonDeal(jo.toString(), keys);
            jsonArray.add(jo);
        }
        return jsonArray;
    }

    /**
     * 对外接口 直接根据关键字更新json状态
     *
     * @param "jsonArrayStr"
     * @param "keys"
     * @return
     */
    public static JSONArray updateJsonType(JSONArray jsonArrayStr, String keys) {
        JSONArray first = dealArray(jsonArrayStr, keys);
        JSONArray array = updateList(first, keys);
        return array;
    }

    /**
     * //两层包装结构的意思
     * 根据已有的list再封装,是根据updateJsonType再写的,
     *
     * @param jsonArray
     * @param keys
     * @return
     */
    public static JSONArray updateJsonTypeByList(JSONArray jsonArray, String keys) {
        JSONArray rsArray = new JSONArray();
        try {
            for (Object jo : jsonArray) {
                JSONObject job = JSONObject.fromObject(jo);
                if (!job.containsKey("list")) {
                    return jsonArray;
                } else {
                    job.put("list", updateJsonType(JSONArray.fromObject(job.get("list")), keys));
                    rsArray.add(job);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsArray;
    }

    /**
     * 类型树的合并
     * 生成父类-》子类 树
     * 这个类好像没有什么通用性、待用
     *
     * @param @JSONArrayStr
     * @param @parentsStr父类id
     * @return
     */
    public static JSONArray childTreeCount(String test, String parentsStr) {
        JSONArray result = new JSONArray();
        JSONArray jo = JSONArray.fromObject(test);
        JSONArray fList = new JSONArray();
        try {
            for (Object j : jo) {
                JSONObject job = JSONObject.fromObject(j);
                if (job.getString(parentsStr).equals("")) {//不通用性在此 parentsStr字段
                    fList.add(job);
                }
            }
            for (Object j : fList) {
                JSONObject fJob = JSONObject.fromObject(j);// 父类
                JSONArray childList = new JSONArray();
                for (Object chi : jo) {
                    JSONObject childObj = JSONObject.fromObject(chi);// 子
                    if (childObj.getString(parentsStr).equals(fJob.getString("id"))) {
                        childList.add(childObj);
                    }
                }
                fJob.put("list", childList);
                result.add(fJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 递归算法 生成树结构
     * 根据关键字生成树结构
     *
     * @param jsonArray
     * @param keys
     * @return
     */
    public static JSONArray createTree(JSONArray jsonArray, String... keys) {
        if (jsonArray == null || keys.length == 0 || keys == null) return null;
        JSONArray jsonArr = updateJsonType(jsonArray, keys[0]);//list化
        JSONArray rsArray = new JSONArray();
        if (keys.length > 1) {
            for (Object obj : jsonArr) {
                JSONObject jsObj = JSONObject.fromObject(obj);
                JSONArray tempArray = createTree(jsObj.getJSONArray("list"), Arrays.copyOfRange(keys, 1, keys.length));
                jsObj.put("list",tempArray);
                rsArray.add(jsObj);
            }
        }else if(keys.length == 1){
            rsArray = jsonArr;
        }
        return rsArray;
    }

    public static String getParameters(List<String> stringList){
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        for(String str:stringList){
            sb.append("\'");
            sb.append(str);
            sb.append("\',");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

}
