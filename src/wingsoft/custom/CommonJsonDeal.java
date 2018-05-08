package wingsoft.custom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import wingsoft.tool.common.CommonOperation;

public class CommonJsonDeal {

	private static volatile CommonJsonDeal instance = null;
	private CommonJsonDeal(){}
	public static CommonJsonDeal getInstance(){
		if(instance==null){
			synchronized (CommonJsonDeal.class){
				if(instance==null){
					instance = new CommonJsonDeal();
				}
			}
		}
		return instance;
	}

	// 将json属性中的值提出来封装
	/**
	 * @param "jsonObject"
	 * @param "keys需要提出来的KEYS"
	 * @return jsonObject
	 */
	public JSONObject upJsonDeal(String jsonStr, String keys) {
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
	public JSONArray updateList(String jsonStr, String keys) {
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
	 * 
	 * @param @jsonStr(Array)
	 * @param @keys
	 * @return jsonArray
	 */
	public JSONArray dealArray(String jsonStr, String keys) {
		JSONArray paramArray = JSONArray.fromObject(jsonStr);
		JSONArray jsonArray = new JSONArray();
		for (Object o : paramArray) {
			JSONObject jo = JSONObject.fromObject(o);
			jo = instance.upJsonDeal(jo.toString(), keys);
			jsonArray.add(jo);
		}
		return jsonArray;
	}

	/**
	 * 对外接口 直接根据关键字更新json状态
	 * 
	 * @param jsonArrayStr
	 * @param keys
	 * @return
	 */
	public JSONArray updateJsonType(String jsonArrayStr, String keys) {
		String first = instance.dealArray(jsonArrayStr, keys).toString();
		JSONArray array = instance.updateList(first, keys);
		return array;
	}
	
	/**
	 * 根据已有的list再封装
	 * @param "jsonArray"
	 * @param "keys"
	 * @return
	 */
	public JSONArray updateJsonTypeByList(JSONArray jsonArray, String keys) {
		JSONArray rsArray = new JSONArray();
		try {
			for(Object jo :jsonArray) {
				JSONObject job = JSONObject.fromObject(jo);
				if(!job.containsKey("list")) {
					return jsonArray;
				}else {
					job.put("list", instance.updateJsonType(job.getString("list"), keys));
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
	 * 这个类好像没有什么通用性、待用
	 * @param "JSONArrayStr"
	 * @param "parentsStr父类id"
	 * @return
	 */
	public JSONArray childTreeCount(String test,String parentsStr) {
		JSONArray result = new JSONArray();
		JSONArray jo = JSONArray.fromObject(test);
		JSONArray fList = new JSONArray();
		try {
			for (Object j : jo) {
				JSONObject job = JSONObject.fromObject(j);
				if (job.getString(parentsStr).equals("")) {//不通用性在此 parents字段
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
	 * 待做 可尝试 递归\\\\\\\\
	 * @param array
	 * @param strs
	 * @return
	 */
//	public static JSONArray dealWithDiffer(JSONArray array,String ...strs) {
//		for(String str:strs) {
//			for(Object l:array) {
//				JSONObject obj = JSONObject.fromObject(l);
//			}
//		}
//		return null;
//	} 
	
	//public static void main(String[] args) {
//		String tests= "[{\"supplier_name\":\"北京山农生态农业有限公司\",\"list\":[{\"id\":\"I20180416D12T1\",\"product_name\":\"白芸豆\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"袋\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\"3\",\"total\":\"3\",\"product_id\":\"20306\",\"pici\":\"I20180416D12\",\"apply_ord\":\"122\"}]},{\"supplier_name\":\"北京元茂商贸有限公司\",\"list\":[{\"id\":\"I20180416D8T3\",\"product_name\":\"宾格瑞棒支\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"5\",\"total\":\"555\",\"product_id\":\"20641\",\"pici\":\"I20180416D8\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D8T2\",\"product_name\":\"八喜\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"4.5\",\"total\":\"499.5\",\"product_id\":\"19722\",\"pici\":\"I20180416D8\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D8T1\",\"product_name\":\"阿波罗鲷鱼烧\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"3.5\",\"total\":\"388.5\",\"product_id\":\"19579\",\"pici\":\"I20180416D8\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T6\",\"product_name\":\"俄式三明治\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"3\",\"total\":\"333\",\"product_id\":\"20470\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T5\",\"product_name\":\"大红果\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"2.5\",\"total\":\"277.5\",\"product_id\":\"19723\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T4\",\"product_name\":\"冰工厂\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"1.5\",\"total\":\"166.5\",\"product_id\":\"19716\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T3\",\"product_name\":\"宾格瑞棒支\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"2.5\",\"total\":\"277.5\",\"product_id\":\"20641\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T2\",\"product_name\":\"八喜\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"3.5\",\"total\":\"388.5\",\"product_id\":\"19722\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"},{\"id\":\"I20180416D3T1\",\"product_name\":\"阿波罗鲷鱼烧\",\"specifications\":\"未设置\",\"package_unit\":\"箱\",\"in_num\":\"111\",\"return_num\":\"0\",\"in_price\":\"4.2\",\"total\":\"466.2\",\"product_id\":\"19579\",\"pici\":\"I20180416D3\",\"apply_ord\":\"102\"}]},{\"supplier_name\":\"北京宁悦发商贸有限公司\",\"list\":[{\"id\":\"I20180416D16T2\",\"product_name\":\"卞萝卜\",\"specifications\":\"500g/斤\",\"package_unit\":\"斤\",\"in_num\":\"501\",\"return_num\":\"0\",\"in_price\":\".7\",\"total\":\"350.7\",\"product_id\":\"19980\",\"pici\":\"I20180416D16\",\"apply_ord\":\"125\"},{\"id\":\"I20180416D16T1\",\"product_name\":\"白萝卜\",\"specifications\":\"500g/斤\",\"package_unit\":\"斤\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\".5\",\"total\":\".5\",\"product_id\":\"19979\",\"pici\":\"I20180416D16\",\"apply_ord\":\"125\"}]},{\"supplier_name\":\"北京乾元亨泰商贸有限公司\",\"list\":[{\"id\":\"I20180416D1T2\",\"product_name\":\"白寇\",\"specifications\":\"未设置\",\"package_unit\":\"斤\",\"in_num\":\"11\",\"return_num\":\"0\",\"in_price\":\"9.2\",\"total\":\"101.2\",\"product_id\":\"20391\",\"pici\":\"I20180416D1\",\"apply_ord\":\"126\"},{\"id\":\"I20180416D1T1\",\"product_name\":\"白果\",\"specifications\":\"未设置\",\"package_unit\":\"斤\",\"in_num\":\"11\",\"return_num\":\"0\",\"in_price\":\"12\",\"total\":\"132\",\"product_id\":\"19802\",\"pici\":\"I20180416D1\",\"apply_ord\":\"126\"}]},{\"supplier_name\":\"北京本乡良实面业有限公司\",\"list\":[{\"id\":\"I20180417D1T2\",\"product_name\":\"本乡特精粉\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"袋\",\"in_num\":\"100\",\"return_num\":\"0\",\"in_price\":\"15\",\"total\":\"1500\",\"product_id\":\"20289\",\"pici\":\"I20180417D1\",\"apply_ord\":\"121\"},{\"id\":\"I20180417D1T1\",\"product_name\":\"本乡富强粉\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"袋\",\"in_num\":\"100\",\"return_num\":\"0\",\"in_price\":\"11\",\"total\":\"1100\",\"product_id\":\"20288\",\"pici\":\"I20180417D1\",\"apply_ord\":\"121\"}]},{\"supplier_name\":\"北京吉鸿运商贸有限公司\",\"list\":[{\"id\":\"I20180416D13T3\",\"product_name\":\"苍杰紫菜\",\"specifications\":\"50g/袋\",\"package_unit\":\"包\",\"in_num\":\"12\",\"return_num\":\"0\",\"in_price\":\"5\",\"total\":\"60\",\"product_id\":\"20326\",\"pici\":\"I20180416D13\",\"apply_ord\":\"101\"},{\"id\":\"I20180416D13T2\",\"product_name\":\"粉条\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"捆\",\"in_num\":\"11\",\"return_num\":\"0\",\"in_price\":\"2\",\"total\":\"22\",\"product_id\":\"20323\",\"pici\":\"I20180416D13\",\"apply_ord\":\"101\"},{\"id\":\"I20180416D13T1\",\"product_name\":\"安琪酵母\",\"specifications\":\"500g/袋\",\"package_unit\":\"箱\",\"in_num\":\"11\",\"return_num\":\"0\",\"in_price\":\"4\",\"total\":\"44\",\"product_id\":\"20432\",\"pici\":\"I20180416D13\",\"apply_ord\":\"101\"},{\"id\":\"I20180416D5T1\",\"product_name\":\"茶香干\",\"specifications\":\"未设置\",\"package_unit\":\"斤\",\"in_num\":\"20000\",\"return_num\":\"0\",\"in_price\":\"1\",\"total\":\"20000\",\"product_id\":\"20279\",\"pici\":\"I20180416D5\",\"apply_ord\":\"101\"},{\"id\":\"I20180416D19T6\",\"product_name\":\"安琪酵母\",\"specifications\":\"500g/袋\",\"package_unit\":\"箱\",\"in_num\":\"11\",\"return_num\":\"0\",\"in_price\":\"4\",\"total\":\"44\",\"product_id\":\"20432\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"},{\"id\":\"I20180416D19T5\",\"product_name\":\"粉条\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"捆\",\"in_num\":\"12\",\"return_num\":\"0\",\"in_price\":\"2\",\"total\":\"24\",\"product_id\":\"20323\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"},{\"id\":\"I20180416D19T4\",\"product_name\":\"苍杰紫菜\",\"specifications\":\"50g/袋\",\"package_unit\":\"包\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\"5\",\"total\":\"5\",\"product_id\":\"20326\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"},{\"id\":\"I20180416D19T3\",\"product_name\":\"百利黑椒酱\",\"specifications\":\"1kg/袋\",\"package_unit\":\"箱\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\"3\",\"total\":\"3\",\"product_id\":\"20375\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"},{\"id\":\"I20180416D19T2\",\"product_name\":\"百利番茄沙司\",\"specifications\":\"1kg/公斤\",\"package_unit\":\"箱\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\"6\",\"total\":\"6\",\"product_id\":\"20377\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"},{\"id\":\"I20180416D19T1\",\"product_name\":\"阿香婆香辣酱\",\"specifications\":\"200g/瓶\",\"package_unit\":\"箱\",\"in_num\":\"1\",\"return_num\":\"0\",\"in_price\":\"4\",\"total\":\"4\",\"product_id\":\"20383\",\"pici\":\"I20180416D19\",\"apply_ord\":\"123\"}]}]\r\n" +
//				"";
//		String fs = CommonJsonDeal.updateJsonTypeByList(JSONArray.fromObject(tests), "apply_ord").toString();
//		System.out.println(fs);
	//		}

	public String getParameters(List<String> listString){
		String rsStr = "(";
		for(String s:listString){
			rsStr = rsStr+"\'" + s + "\'," ;
		}
		if(rsStr != "")
			rsStr = rsStr.substring(0, rsStr.length() - 1);
		rsStr += ")";
		return rsStr;
	}
}
