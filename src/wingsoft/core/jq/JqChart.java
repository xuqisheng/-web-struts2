package wingsoft.core.jq;

import java.util.HashMap;

import wingsoft.tool.common.CommonOperation;



public class JqChart {
public String generateChartJson(HashMap<String,Object> chartDef){
		
		StringBuffer jsonStr=new StringBuffer();
		jsonStr.append("[{");
		
		jsonStr.append("funcno:\"" + CommonOperation.nTrim(chartDef.get("FUNCNO"))+"\",");
		jsonStr.append("type:\""+CommonOperation.nTrim(chartDef.get("TYPE"))+"\",");
		jsonStr.append("caption:\""+CommonOperation.nTrim(chartDef.get("CAPTION"))+"\",");
		jsonStr.append("xAxisName:\""+CommonOperation.nTrim(chartDef.get("XAXISNAME"))+"\",");
		jsonStr.append("yAxisName:\""+CommonOperation.nTrim(chartDef.get("YAXISNAME"))+"\",");
		jsonStr.append("xUnit:\""+CommonOperation.nTrim(chartDef.get("XUINT"))+"\",");
		jsonStr.append("yUnit:\""+CommonOperation.nTrim(chartDef.get("YUINT"))+"\",");
		jsonStr.append("yPrefix:\""+CommonOperation.nTrim(chartDef.get("YPREFIX"))+"\",");
		
		//caption font style
		jsonStr.append("captionFont:\""+CommonOperation.nTrim(chartDef.get("CAPTION_FONT"))+"\",");
		jsonStr.append("captionFontSize:\""+CommonOperation.nTrim(chartDef.get("CAPTION_FONT_SIZE"))+"\",");
		jsonStr.append("captionFontColor:\""+CommonOperation.nTrim(chartDef.get("CAPTION_FONT_COLOR"))+"\",");
		jsonStr.append("captionBold:\""+CommonOperation.nTrim(chartDef.get("CAPTION_BOLD"))+"\",");
		jsonStr.append("captionUnderline:\""+CommonOperation.nTrim(chartDef.get("CAPTION_UNDERLINE"))+"\",");
		jsonStr.append("captionItalic:\""+CommonOperation.nTrim(chartDef.get("CAPTION_ITALIC"))+"\",");
	
		//axis font style
		jsonStr.append("axisFont:\""+CommonOperation.nTrim(chartDef.get("AXIS_FONT"))+"\",");
		jsonStr.append("axisFontSize:\""+CommonOperation.nTrim(chartDef.get("AXIS_FONT_SIZE"))+"\",");
		jsonStr.append("axisFontColor:\""+CommonOperation.nTrim(chartDef.get("AXIS_FONT_COLOR"))+"\",");
		jsonStr.append("axisBold:\""+CommonOperation.nTrim(chartDef.get("AXIS_BOLD"))+"\",");
		jsonStr.append("axisUnderline:\""+CommonOperation.nTrim(chartDef.get("AXIS_UNDERLINE"))+"\",");
		jsonStr.append("axisItalic:\""+CommonOperation.nTrim(chartDef.get("AXIS_ITALIC"))+"\",");
		
		//background color and alpha
		jsonStr.append("bgColor:\""+CommonOperation.nTrim(chartDef.get("BG_COLOR"))+"\",");
		jsonStr.append("bgAlpha:\""+CommonOperation.nTrim(chartDef.get("BG_ALPHA"))+"\",");
		
		//columns we need from database
		jsonStr.append("colNames:"+CommonOperation.nTrim(chartDef.get("COLNAMES"))+",");
		jsonStr.append("categoryColName:\""+CommonOperation.nTrim(chartDef.get("CATEGORY_COLNAME"))+"\",");
		jsonStr.append("seriesColNames:"+CommonOperation.nTrim(chartDef.get("SERIES_COLNAMES"))+",");
		jsonStr.append("meterRange:"+CommonOperation.nTrim(chartDef.get("METER_RANGE"))+",");
		jsonStr.append("meterRangeColor:"+CommonOperation.nTrim(chartDef.get("METER_RANGE_COLOR"))+",");
		
		//number of categories per page
		jsonStr.append("categoryPerPage:\""+CommonOperation.nTrim(chartDef.get("CATEGORY_PER_PAGE"))+"\",");
		
		jsonStr.append("sql:\""+CommonOperation.transferLineBreak((CommonOperation.nTrim(chartDef.get("SQL"))))+"\"");
		jsonStr.append("}]");
		
		
		//System.out.println("JqChart jsonStr " + jsonStr);
		return jsonStr.toString();
		
	}
}
