package wingsoft.tool.printer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelPrinter implements Printer {

	private WritableCellFormat getFormat(Setting s) throws WriteException{
		//Excel中的字体设置	
		WritableFont efont=null;
		if(s.getTitle_italic().equals("italic")){
			efont = new  WritableFont(WritableFont.TIMES, Integer.valueOf(s.getFontsize()) ,WritableFont.BOLD,true);
		}else{
			efont = new  WritableFont(WritableFont.TIMES, Integer.valueOf(s.getFontsize()) ,WritableFont.BOLD);
		}
		WritableCellFormat format = new  WritableCellFormat(efont);
		format.setAlignment(jxl.format.Alignment.CENTRE); 
		return format;
	}
	@SuppressWarnings("unchecked")
	public void print(List<HashMap> data,Setting s,OutputStream os) throws IOException, WriteException {
		
			WritableWorkbook book = Workbook.createWorkbook(os);//新建工作簿
			WritableSheet sheet = book.createSheet(s.getTitle(), 0);//新建工作表
			WritableCellFormat format=getFormat(s);
			int currR=0;//记录行序
			
			Label title=new Label(0,currR,s.getTitle(),format);//标题
			sheet.addCell(title);
			sheet.mergeCells(0,currR,s.getCols_en().size()-1, currR);
			currR++;
			
			for(int i=0;i<s.getCols_cn().size();i++){    //添加列名
				Label l=new Label(i,currR,s.getCols_cn().get(i));	
				sheet.addCell(l);
			}
			currR++;
			
			for(int i=0;i<data.size();i++){			//添加表的内容
    			HashMap oneData=(HashMap)data.get(i);
    			for(int j=0;j<s.getCols_en().size();j++){
    				if(s.getTypes().get(j).equals("NUMBER")){
    					//BigDecimal num=(BigDecimal)oneData.get(col_en.get(j));
    					String num = CommonOperation.nTrim(oneData.get(s.getCols_en().get(j)));
    					Double dNum = Double.parseDouble(num);
    					jxl.write.Number number = new jxl.write.Number(j,currR,dNum);
    					sheet.addCell(number);
    				}else{
    					Label l = new Label(j,currR,CommonOperation.nTrim(oneData.get(s.getCols_en().get(j))));
    					sheet.addCell(l);
    				}
    			}
    			currR++;
    		}
			
			book.write();
			book.close();
			os.close();
	}

}
