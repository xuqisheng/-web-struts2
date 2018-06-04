package wingsoft.tool.printer;

import java.awt.Color;
import java.util.List;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.lowagie.text.Font;  
import jxl.write.WritableFont;
public class Setting {
	public String getP_type() {
		return p_type;
	}
	public void setP_type(String pType) {
		p_type = pType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getTitle_bold() {
		return title_bold;
	}
	public void setTitle_bold(String titleBold) {
		title_bold = titleBold;
	}
	public String getTitle_italic() {
		return title_italic;
	}
	public void setTitle_italic(String titleItalic) {
		title_italic = titleItalic;
	}
	public String getT_border() {
		return t_border;
	}
	public void setT_border(String tBorder) {
		t_border = tBorder;
	}
	public String getFontsize() {
		return fontsize;
	}
	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getFuncno() {
		return funcno;
	}
	public void setFuncno(String funcno) {
		this.funcno = funcno;
	}
	public List<String> getCols_cn() {
		return cols_cn;
	}
	public void setCols_cn(List<String> colsCn) {
		cols_cn = colsCn;
	}
	public List<String> getCols_en() {
		return cols_en;
	}
	public void setCols_en(List<String> colsEn) {
		cols_en = colsEn;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	String p_type;  //打印类型：pdf/word/excel
	String title;   //打印出的文档标题，对应前台的name
	String pagesize;//页面大小：A0,A4,A9
	String title_bold;
	String title_italic;
	String t_border;
	String fontsize;
	List<String> cols_cn;
	List<String> cols_en;
	List<String> types;
	String funcno;
	String scope;

	/*String filePath;
	String waterMarkFilePath;
	String imageFilePath;
	String waterMarkName;	
	BaseColor bkColor=BaseColor.PINK;*/
}
