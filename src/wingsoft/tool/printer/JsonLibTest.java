package wingsoft.tool.printer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class JsonLibTest {

	/**
	 * @param args
	 */
	public String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int age;
	public boolean bool;

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*String json = "{bool:true,integer:1,string:\"json\"}";   
		JSONObject jsonObject = JSONObject.fromObject( json );   
		JsonLibTest bean = (JsonLibTest) JSONObject.toBean( jsonObject, JsonLibTest.class );   
		//System.out.println(Boolean.valueOf( bean.bool ));   
		System.out.println(bean.getInt());
		//System.out.print(bean.name);
		System.out.println("sldf");*/
		/*String myjson = "{name=\"json\",bool:true,int:1,double:2.2,function:function(a){return a;},array:[1,2]}";
		JSONObject json1 = JSONObject.fromObject(myjson);
		Object bean1 = JSONObject.toBean(json1);
		try {
			System.out.println(PropertyUtils.getProperty( bean1, "array" ));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	/*	boolean[] boolArray = new boolean[]{true,false,true};   
		JSONArray jsonArray = JSONArray.fromObject( boolArray );   
		System.out.println( jsonArray );   
		
		List list = new ArrayList();   
		list.add( "first" );   
		list.add( "second" );   
	    jsonArray = JSONArray.fromObject( list );   
		System.out.println( jsonArray );   
		
		Map map = new HashMap();   
		map.put( "name", "json" );   
		map.put( "bool", Boolean.TRUE );   
		map.put( "int", new Integer(1) );   
		map.put( "arr", new String[]{"a","b"} );   
		map.put( "func", "function(i){ return this.arr[i]; }" );   */
		  
		/*String json = "{name:\"json\",age:1,bool:true}";   
		JSONObject jsonObject = JSONObject.fromObject( json );   
		JsonLibTest bean = (JsonLibTest) JSONObject.toBean( jsonObject, JsonLibTest.class );   */
		//assertEquals( jsonObject.get( "bool" ), Boolean.valueOf( bean.isBool() ) );   
		//assertEquals( jsonObject.get( "integer" ), new Integer( bean.getInteger() ) );   
		//assertEquals( jsonObject.get( "string" ), bean.getString() );  
		/*
		
		JSONObject jsonObject = JSONObject.fromObject(vars); 

		List<String> list = new ArrayList<String>(); 
		for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) { 
		list.add((String)JSONObject.toBean(jsonObject.getJSONObject(iterator.next().toString()), String.class)); 
		} 
		System.out.print(list.size()); 
		*/

//		String vars = "{'col_names':['课程编号','课程名称','fdf',]}";
//		JSONObject jsonObject = JSONObject.fromObject( vars );
//		List expected = JSONArray.toList( jsonObject.getJSONArray( "col_names" ) );
//		System.out.println(expected.size());
		} 
		

}
