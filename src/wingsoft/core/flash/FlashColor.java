package wingsoft.core.flash;

public class FlashColor {
	private static final String[][] color = {
			{	
				"fd5d71", 
				"fa3205", 
				"f6930e", 
				"c09d11", 
				
				"e6ef1f",
				"78e60f",
				"0aa91a",
				"06551d",
				
				"09a6dc",
				"165ba2",
				"111d97",
				"450c7d"
			},
			
			{
				"fd5d71", 
				"f6930e", 
				"c09d11", 
				"e6ef1f",
				"78e60f",
				
				"fa3205", 	
				"0aa91a",
				"09a6dc",
				"111d97",
				"450c7d"
				
			},
			//best	
			{
				"fa3205", 
				"f6930e", 
				"0aa91a",
				"09a6dc",
				"111d97",
				
				
				"c09d11", 
				"e6ef1f",
				"78e60f",
				"450c7d",
				"fd5d71", 
				
			},
			//okay
			{
				
				"f6930e", 
				"c09d11", 
				"e6ef1f",
				"78e60f",
				"09a6dc",
				
				"fd5d71", 
				"e6ef1f",
				"78e60f",
				"fa3205", 
				"fd5d71", 
			}
		
			
	};
	
	public static String[] getColor(int index)
	{
		//to ensure that index is no larger than color array
		if(index >= color.length)
		{
			index = 0;
		}
		return color[index];
	}

}
