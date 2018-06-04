package wingsoft.tool.printer;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;



public class PdfPrinter implements Printer {
	
	Rectangle pSize;
	Font font;
	Font fontT;//标题字体样式
	
	//页面大小设置
	private void setPageSize(Setting s){
		Rectangle pageSize=null;
		if(s.getPagesize().equals("A9")){
			pageSize=PageSize.A9;
		}else if(s.getPagesize().equals("A0")){
			pageSize=PageSize.A0;
		}else{
			pageSize=PageSize.A4;
		}
		pSize=new Rectangle(pageSize);
	}
	
	//设置支持中文的字体
	private void setFont(){
		try {
			font = new Font(BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
					                      BaseFont.IDENTITY_H,    
					                      BaseFont.EMBEDDED));
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//设置标题样式
	private void setFontT(Setting s){
		if(s.getTitle_bold().equals("bold")){
        	if(s.getTitle_italic().equals("italic")){
        		try {
        			fontT = new Font(BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
        					                      BaseFont.IDENTITY_H,    
        					                      BaseFont.EMBEDDED),Integer.valueOf(s.getFontsize()),Font.BOLDITALIC);
        		} catch (DocumentException e1) {
        			e1.printStackTrace();
        		} catch (IOException e1) {
        			e1.printStackTrace();
        		}
        	}else{
        		try {
        			fontT = new Font(BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
        					                      BaseFont.IDENTITY_H,    
        					                      BaseFont.EMBEDDED),Integer.valueOf(s.getFontsize()),Font.BOLD);
        		} catch (DocumentException e1) {
        			e1.printStackTrace();
        		} catch (IOException e1) {
        			e1.printStackTrace();
        		}
        	} 		
        }else if(s.getTitle_italic().equals("italic")){
        	try {
    			fontT = new Font(BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
    					                      BaseFont.IDENTITY_H,    
    					                      BaseFont.EMBEDDED),Integer.valueOf(s.getFontsize()),Font.ITALIC);
    		} catch (DocumentException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }else{
        	try {
    			fontT = new Font(BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
    					                      BaseFont.IDENTITY_H,    
    					                      BaseFont.EMBEDDED),Integer.valueOf(s.getFontsize()),Font.NORMAL);
    		} catch (DocumentException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
	}
	@SuppressWarnings("unchecked")
	public void print(List<HashMap> data,Setting s,OutputStream os) throws DocumentException, IOException {
		setPageSize(s);
        Document doc=new Document(pSize,5,5,5,5);
        setFont();
    	
        PdfWriter.getInstance(doc, os);
        doc.open();
        
        //写入标题
        this.setFontT(s);
        Paragraph p=new Paragraph(s.getTitle(),fontT);
        p.setAlignment(Element.ALIGN_CENTER);
        
        PdfPTable t = new PdfPTable(s.getCols_en().size());
        if(s.getT_border().equals("无")){
        	t.getDefaultCell().setBorder(0);//设置无边框表格
        }
       
        for(int i=0;i<s.getCols_cn().size();i++){    //添加列名
			t.addCell(new Paragraph(s.getCols_cn().get(i),font));	
		}

        for(int i=0;i<data.size();i++){			//添加表的内容
			HashMap oneData=(HashMap)data.get(i);
			for(int j=0;j<s.getCols_en().size();j++){
				t.addCell(new Paragraph(CommonOperation.nTrim(oneData.get(s.getCols_en().get(j))), font));
			}	
		}         
        p.add(t);
        doc.add(p);
        doc.close();
        os.flush();
        os.close();
       /* if(!s.waterMarkFilePath.equals(null)){
            waterMark(s.filePath,s.waterMarkFilePath,s.imageFilePath,s.waterMarkName);            	
        }
    */
	
	}
	@SuppressWarnings("unused")
	private void waterMark(String filePath_temp,String filePath,String imageFilePath,String waterMarkName){
		//---------pdf水印------------- 
		try {   //水印路径
			PdfReader reader = new PdfReader(filePath_temp); 
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(filePath)); 
			BaseFont font=null;  
			try {
				font = BaseFont.createFont("wingsoft/tool/printer/simsun.ttc,1",   
								                      BaseFont.IDENTITY_H,    
								                      BaseFont.EMBEDDED);
				} catch (DocumentException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			int total = reader.getNumberOfPages() + 1; 
			   //图片路径、位置与大小
			Image image = Image.getInstance(imageFilePath); 
			image.setAbsolutePosition(50, 300); 
			image.scalePercent(100); 
			PdfContentByte under; 
			
			int j = waterMarkName.length(); 
			char c = 0; 
			int rise = 0; 
			for (int i = 1; i < total; i++) { 
				rise = 500; 
				under = stamper.getUnderContent(i); 
				under.addImage(image); 
				under.beginText(); 
				under.setColorFill(BaseColor.CYAN); 
				under.setFontAndSize(font, 30); 
				if (j >= 15) {
					under.setTextMatrix(200, 120); 
					for (int k = 0; k < j; k++) {
						under.setTextRise(rise); 
						c = waterMarkName.charAt(k); 
						under.showText(c + ""); 
						rise -= 20; 
					} 
				} else { 
					under.setTextMatrix(180, 100); 
					for (int k = 0; k < j; k++) { 
						under.setTextRise(rise); 
						c = waterMarkName.charAt(k); 
						under.showText(c + ""); 
						rise -= 18; 
					} 
				} 
				under.endText(); 
				} 
				stamper.close(); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	}
}


