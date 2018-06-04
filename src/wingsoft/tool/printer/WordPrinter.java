package wingsoft.tool.printer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

public class WordPrinter implements Printer {
	com.lowagie.text.Rectangle wpageSize;
	Font font;
	private void setFont(Setting s){
		if(s.getTitle_bold().equals("bold")){
 			if(s.getTitle_italic().equals("italic")){
				font = new Font(Font.NORMAL, Integer.valueOf(s.getFontsize()), Font.BOLDITALIC);
			}else{
				font = new Font(Font.NORMAL, Integer.valueOf(s.getFontsize()), Font.BOLD);
			}
 		}else if(s.getTitle_italic().equals("italic")){
 			System.out.println("heere");
 			font = new Font(Font.NORMAL, Integer.valueOf(s.getFontsize()), Font.ITALIC);
 		}else{
 			font = new Font(Font.NORMAL, Integer.valueOf(s.getFontsize()), Font.NORMAL);
 		}
	}
	private void setPageSize(Setting s){
		if(s.getPagesize().equals("A9")){
			wpageSize=com.lowagie.text.PageSize.A4;
		}else if(s.getPagesize().equals("A0")){
			wpageSize=com.lowagie.text.PageSize.A4;
		}else{
			wpageSize=com.lowagie.text.PageSize.A4;
		}
	}
	@SuppressWarnings({ "unchecked" })
	public void print(List<HashMap> data,Setting s,OutputStream os) throws DocumentException, IOException {
		
		setPageSize(s);
		Document document = new Document(wpageSize);
		
		
		RtfWriter2.getInstance(document,os);  
 		document.open();
 		setFont(s);
 		
 		//设置标题  
 		Paragraph p = new Paragraph(s.getTitle(),font); 
 		p.setAlignment(1); 
 		document.add(p);  
 		
 		
 		Table t = new Table(s.getCols_en().size());
 		t.setBorderWidth(1);  
 		t.setPadding(0); 
 		t.setSpacing(0); 
 	
 		for(int i=0;i<s.getCols_cn().size();i++){    		//添加列名
			t.addCell(new Paragraph(s.getCols_cn().get(i)));	
		}
 		
 		for(int i=0;i<data.size();i++){			//添加表的内容
			HashMap oneData=(HashMap)data.get(i);
			for(int j=0;j<s.getCols_en().size();j++){
				t.addCell(new Paragraph(CommonOperation.nTrim(oneData.get(s.getCols_en().get(j)))));
			}	
		} 
 		document.add(t);  
 		document.close(); 
 		os.close();
	 	
	}
}
