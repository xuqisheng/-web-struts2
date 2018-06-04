package wingsoft.core.flash;

import java.util.Stack;


public class SQLParserFull {

	
	
	public static final String AND = "and";
	public static final String OR = "or";
	
	

	
	
	
	public static String[] getOrderExp(String sql)
	{
		
		
		
		
		//use space \s as splitter
		String []splitStr = sql.split("\\s+");
		
		int splitStrLast = splitStr.length - 1;
		
		int orderIndex = -1;
		
		
		Stack stack = new Stack();
		
		for(int i = 0; i < splitStr.length; i++)
		{
			//System.out.println(splitStr[i]);		
			
			for(int j = 0; j < splitStr[i].length(); j++)
			{
				if(splitStr[i].charAt(j) == '(')
				{
					stack.push("(");
				}
				else if(splitStr[i].charAt(j) == ')')
				{
					stack.pop();
				}
			}
			/*if(splitStr[i].startsWith("("))
			{
				//System.out.println("start with (");
				stack.push("(");
			}
			
			if(splitStr[i].endsWith(")"))
			{
				//System.out.println("end with )");
				stack.pop();
			}*/
			
			if(splitStr[i].compareTo("order") == 0 && splitStr[i + 1].compareTo("by") == 0)
			{
				
				if(stack.empty())
				{
					orderIndex = i;
				}
				
			}
		}
		
		
		//if there is no order by
		if(orderIndex == -1)
		{
			//emptyResult[0] means beforeOrderExp, which is sql itself
			//emptyResult[1] means OrderExp, which is ""
			//emptyResult[2] means afterOrderExp, which is ""
			String[] emptyResult = {sql, "", ""};
			return emptyResult;
		}
		
		//otherwise, there exists order by
		
		
		
		int orderEnd = splitStrLast;
		
		String orderExp = "";
		for(int i = orderIndex; i <= orderEnd; i++)
		{
			
			orderExp += splitStr[i] + " ";
		}
		
		String beforeOrderExp = "";
		for(int i = 0; i < orderIndex; i++)
		{
			beforeOrderExp += splitStr[i] + " ";
		}
		
		String afterOrderExp = "";
		
		String[] result = {beforeOrderExp, orderExp, afterOrderExp};
		
		return result;
	}
	
	
	
	
	public static String[] getGroupExp(String sql)
	{
		
		//call getOrderExp
		//then we will handle part of sql that is before order by
		String[] order = getOrderExp(sql);
		String beforeOrder = order[0];
		
		//use space \s as splitter
		String []splitStr = beforeOrder.split("\\s+");
		
		int splitStrLast = splitStr.length - 1;
		int groupIndex = -1;
		
		
		Stack stack = new Stack();
		
		for(int i = 0; i < splitStr.length; i++)
		{
			//System.out.println(splitStr[i]);		
			
			
			
			for(int j = 0; j < splitStr[i].length(); j++)
			{
				if(splitStr[i].charAt(j) == '(')
				{
					stack.push("(");
				}
				else if(splitStr[i].charAt(j) == ')')
				{
					stack.pop();
				}
			}
			
			
			
			/*if(splitStr[i].startsWith("("))
			{
				//System.out.println("start with (");
				stack.push("(");
			}
			
			if(splitStr[i].endsWith(")"))
			{
				//System.out.println("end with )");
				stack.pop();
			}*/
			
			if(splitStr[i].compareTo("group") == 0 && splitStr[i + 1].compareTo("by") == 0)
			{
				
				if(stack.empty())
				{
					groupIndex = i;
				}
				
			}
		}
		
		//if there is no group by
		if(groupIndex == -1)
		{
			String[] emptyResult = {beforeOrder, "", order[1] + order[2]};
			return emptyResult;
		}
		//otherwise there exists group by
			
		int groupEnd = splitStrLast;
		
		
		String groupExp =  "";	
		for(int i = groupIndex; i <= groupEnd; i++)
		{
			
			groupExp += splitStr[i] + " ";
		}
		
		String beforeGroupExp = "";
		for(int i = 0; i < groupIndex; i++)
		{
			beforeGroupExp += splitStr[i] + " ";
		}
		
		//afterGroupExp may be followed by order by
		String afterGroupExp = order[1] + order[2];
		
		
		
		String[] result = {beforeGroupExp, groupExp, afterGroupExp};
		
		
		
		return result;
		
		
		
		
	
	}
	
	public static String[] getWhereExp(String sql)
	{
		String[] group = getGroupExp(sql);
		
		String beforeGroup = group[0];
		
		//use space \s as splitter
		String []splitStr = beforeGroup.split("\\s+");
		
		int splitStrLast = splitStr.length - 1;
		int whereIndex = -1;
		
		
		
		Stack stack = new Stack();
		
		for(int i = 0; i < splitStr.length; i++)
		{
			//System.out.println(splitStr[i]);		
			
			
			for(int j = 0; j < splitStr[i].length(); j++)
			{
				if(splitStr[i].charAt(j) == '(')
				{
					stack.push("(");
				}
				else if(splitStr[i].charAt(j) == ')')
				{
					stack.pop();
				}
			}
			
			/*if(splitStr[i].startsWith("("))
			{
				//System.out.println("start with (");
				stack.push("(");
			}
			
			if(splitStr[i].endsWith(")"))
			{
				//System.out.println("end with )");
				stack.pop();
			}*/
			
			if(splitStr[i].compareTo("where") == 0)
			{
				
				if(stack.empty())
				{
					System.out.println("where is set");
					whereIndex = i;
				}
				
			}
		}
		
		//if there is no where
		if(whereIndex == -1)
		{
			String[] emptyString = {beforeGroup, "", group[1] + group[2]};
			return emptyString;
		}
			
		//otherwise there exists where
		
		int whereEnd = splitStrLast;
		
		
			
		String whereExp =  "";	
		for(int i = whereIndex; i <= whereEnd; i++)
		{
			
			whereExp += splitStr[i] + " ";
		}
		
		String beforeWhereExp = "";
		for(int i = 0; i < whereIndex; i++)
		{
			beforeWhereExp += splitStr[i] + " ";
		}
		
		String afterWhereExp = group[1] + group[2];
	
		String[] result = {beforeWhereExp, whereExp, afterWhereExp};
		
		return result;
	
		
		
		
	}
	
	
	
	public static String addWhere(String sql, String relation, String condition)
	{
		
		//call getWhere
		String[] where = getWhereExp(sql);
		String beforeWhereExp = where[0];
		String whereExp = where[1];
		String afterWhereExp = where[2];
		
		
		
		
		//if there is where
		if(whereExp != "")
		{
			whereExp = whereExp.trim();
			
			
			
			//if it is where(
			if(whereExp.startsWith("where ("))
			{
				//remove the last )
				whereExp = whereExp.substring(0, whereExp.length() - 1);
				whereExp += " " + relation + " " + condition + ") ";
			}
			else
			{
				whereExp += " " + relation + " " + condition + " ";
			}
					
			
		}
		else
		{
			whereExp = " where " + condition + " ";
		}
		
		
		return beforeWhereExp + whereExp + afterWhereExp;
		
		
		
		
		
		
		
		
		
		
	
	}
	
	
	
	
	public static void main(String[] args)
	{
		//simple sql with select from where
		//String testSql = "select A.C1, A.C2 from    A where eid='hi'";
		
		
		//sql without where()
		//String testSql = "select * from AB ���,C T2 group by name order by eid";
		
		//sql with where() 
		//String testSql = "select * from A where eid=1 order by bbc";
		
		//complex sql with sub query, not handled
		//String testSql = "select A.C1, A.C2 from (select * from B where (ee = 5) group by hello) A where (eid='hi') group by amount";
		
		//String testSql = "SELECT sum(employee.eid) , department.depcode, employee.ename, employee.address, employee.sex, employee.nation, employee.education, employee.operater, employee.birthday FROM department, employee WHERE (employee.depcode = department.depcode) ORDER BY department.depcode ASC ";
		
		String testSql = "select a.item_code,a.item_name,b.thisyear/10000 c from  REPORT_ITEMS a,     REPORT_DATA2010 b" +
" where a.item_code=b.item_code and a.report_name=b.report_name" +
" and a.report_name='something'" +
" and length(a.item_code)-length(replace(a.item_code,'.',''))=1" +
" and a.item_code like '1%'" +
" order by a.item_code";

		
		//super sql with exist
		/*String testSql = "select sum(lastalct_val)" +
        " from ALLOCATE_DATA2010 b" +
        " where b.version_no = 'EXEC06'" +
          " and b.allocate_name = a.allocate_name" +
          " and b.unit_code = a.unit_code" +
          " and b.item_code like trim(a.item_code) || '.%'" +
          " and not exists" +
        " (select 1" +
                 " from ALLOCATE_DATA2010 c" +
                " where c.version_no = 'EXEC06'" +
                 " and c.allocate_name = b.allocate_name" +
                  " and c.unit_code = b.unit_code" +
                  "and c.item_code like trim(b.item_code) || '.%')";*/
		
		
		testSql = testSql.toLowerCase();
		
		/*System.out.println("getOrder is " + getOrderExp(testSql)[0]);
		System.out.println("getGroup is " + getGroupExp(testSql));*/
		
		System.out.println("testSql is " + testSql);
		String testAdd = addWhere(testSql, OR, "abc = 5");
		System.out.println("testAdd is " + testAdd);
		
		//String result = addWhere(testSql, SQLParser.AND, "ename='nice'");
		
		
		//String result = getFromExp(testSql);
		//String result = getWhereExp(testSql)[1];
		//System.out.println("result is " + result);
		
	
		
		
		
	}
}
