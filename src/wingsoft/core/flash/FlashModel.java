package wingsoft.core.flash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import wingsoft.core.command.QueryDAO;


//classname changed
//import wingsoft.core.command.CommonBusinessQueryDAO;

public class FlashModel {

    //create different styles of Angular Gauge


    //function createAngularFlash360Json returns chart xml string that shows an angular meter that is 360 degreed
    public String createAngularAFlashJson(String funcno, String[] valueList, String[][] minMax, String[] colorList, String minStr, String maxStr, String postfix, List resultList, HashMap option)
    {
        String row_json = "{\"funcno\": " + "\"" + funcno + "\"" + "," + "categoryColName:" + "\"" + option.get("categoryColName") + "\"" + ",";

        HashMap hm = (HashMap)resultList.get(0);
        for(Object entryObject : hm.entrySet())
        {
            Map.Entry entry = (Map.Entry)entryObject;
            row_json += "\"" + entry.getKey() + "\"" + ":" + "\"" + Escape.escapeThenEncode("" + entry.getValue()) + "\"" + ",";
        }


        row_json += "}";





        String js =
                //flash_click_handler is defined in jquery.flashchart.js
                "flash_click_handler(" + row_json +"," + 0 + ");" +
                        //"alert(\"gut\");"+
                        "void(0);" +
                        "";
        //String strXml = "<chart bgColor='000000' gaugeStartAngle='225' gaugeEndAngle='-45' bgAlpha='100' lowerLimit='0' upperLimit='180' majorTMNumber='8' majorTMThickness='3' majorTMColor='FFFFFF' majorTMHeight='7' minorTMNumber='0' placeValuesInside='1' sgaugeOriginY='160' gaugeOuterRadius='110' gaugeInnerRadius='100' showShadow='0' pivotRadius='20' pivotFillColor='000000,383836'   pivotFillType='linear' pivotFillRatio='50,50' pivotFillAngle='240' annRenderDelay='0' gaugeFillMix='' showPivotBorder='1' pivotBorderColor='999999' pivotBorderThickness='2'>";

        //String strXml = "<chart bgColor='FFFFFF' bgAlpha='100' gaugestartAngle='235' gaugeendAngle='-55' lowerLimit='0' upperLimit='10' majorTMNumber='11' majorTMThickness='5' majorTMColor='F48900' majorTMHeight='15' minorTMNumber='4' minorTMThickness='2' minorTMColor='FFFFFF' minorTMHeight='13' placeValuesInside='1' gaugeOuterRadius='140' gaugeInnerRadius='85%25' baseFontColor='F48900' baseFont='Impact' baseFontSize='30' showShadow='0' pivotRadius='20' pivotFillColor='000000,383836'   pivotFillType='linear' pivotFillRatio='50,50' pivotFillAngle='240' annRenderDelay='0'>";

        //classic color
        //String strXml = "<chart palette='2' gaugestartAngle='245' gaugeendAngle='-65' lowerLimitDisplay='"+ minStr + "' upperLimitDisplay='" + maxStr + "' showTickMarks='1' showTickValues='1' autoScale='1' bgAlpha='0' bgColor='FFFFFF' lowerLimit='" + minMax[0][0]+ "' upperLimit='" + minMax[minMax.length - 1][1] + "' numberSuffix='" + postfix + "' showBorder='0' basefontColor='025B6A' chartTopMargin='15' chartBottomMargin='15' chartLeftMargin='0' chartRightMargin='0' gaugeFillMix='{dark-10},FFFFFF,{dark-10}' gaugeFillRatio='3' >";


        String strXml = "<chart bgColor='FFFFFF' upperLimit='100' lowerLimit='0' numberSuffix='/s' baseFontColor='646F8F'  majorTMNumber='11' majorTMColor='646F8F'  majorTMHeight='9' minorTMNumber='5' minorTMColor='646F8F' minorTMHeight='3' showGaugeBorder='0' gaugeOuterRadius='150' gaugeInnerRadius='135' gaugeOriginX='210' gaugeOriginY='210' gaugeScaleAngle='280' gaugeAlpha='50' placeValuesInside='1' displayValueDistance='30' toolTipBgColor='F2F2FF' toolTipBorderColor='6A6FA6' gaugeFillMix='' showShadow='0' annRenderDelay='0' pivotRadius='14' pivotFillMix='{A1A0FF},{6A6FA6}' pivotBorderColor='bebcb0' pivotFillRatio='70,30'>";





        //add color range
        strXml += "<colorRange>";
        for(int i = 0; i < minMax.length; i++)
        {
            strXml += "<color minValue='" + minMax[i][0] + "' maxValue='" + minMax[i][1] + "' code='" + colorList[i] + "'  />";
        }


        strXml += "</colorRange>";

        //add dials (pointers)
        strXml += "<dials>";

        for(int i = 0; i < valueList.length; i++)
        {
            strXml += "<dial borderAlpha='0' baseWidth='5' topWidth='4' value='" + valueList[i] + "' link='" + "JavaScript: " + js + "'/>";
        }

        strXml += "</dials>";

		 /*  strXml +=
		   "<annotations autoScale='0'>" +
			"<annotationGroup id='Grp1' showBelow='1'>" +
				"<annotation type='circle' x='150' y='150' color='1C1C1C,AAAAAA,1C1C1C' radius='127' fillPattern='linear' />" +
				"<annotation type='circle' x='150' y='150' color='9E9E9E,ECECEC' radius='117' fillPattern='linear' fillAngle='270'/>" +
				"<annotation type='circle' x='150' y='150' color='000000,6C6C6C' radius='115' fillPattern='linear' fillAngle='270'/>" +

			"</annotationGroup>" +

		"</annotations>";*/

        //full blue meter
        //autoscale is possible
        //we need to know the center of the pic
        strXml += "<annotations origW='400' origH='200' autoScale='0'>" +
                "<annotationGroup xPos='210' yPos='210' showBelow='1'>" +
                "<annotation type='circle' xPos='0' yPos='0' radius='200' fillColor='000000,2C6BB2, 135FAB'  fillRatio='80,15, 5' borderColor='2C6BB2' />" +
                "<annotation type='circle' xPos='0' yPos='0' radius='180' fillColor='FFFFFF, D4D4D4' fillRatio='20,80' borderColor='2C6BB2' />" +
                "<annotation type='arc' xPos='0' yPos='0' radius='180' innerRadius='170' startAngle='-60' endAngle='240' fillColor='51884F' fillAlpha='50' borderColor='51884F' />" +
                "</annotationGroup>" +
                "</annotations>";


        strXml += "</chart>";

        return strXml;
    }




    //function createAngularFlash360Json returns chart xml string that shows an angular meter that is 360 degreed
    public String createAngular360FlashJson(String funcno, String[] valueList, String[][] minMax, String[] colorList, String minStr, String maxStr, String postfix, List resultList, HashMap option)
    {
        String row_json = "{\"funcno\": " + "\"" + funcno + "\"" + "," + "categoryColName:" + "\"" + option.get("categoryColName") + "\"" + ",";

        HashMap hm = (HashMap)resultList.get(0);
        for(Object entryObject : hm.entrySet())
        {
            Map.Entry entry = (Map.Entry)entryObject;
            row_json += "\"" + entry.getKey() + "\"" + ":" + "\"" + entry.getValue() + "\"" + ",";
        }


        row_json += "}";





        String js =
                //flash_click_handler is defined in jquery.flashchart.js
                "flash_click_handler(" + row_json +"," + 0 + ");" +
                        //"alert(\"gut\");"+
                        "void(0);" +
                        "";

        //classic color
        String strXml = "<chart palette='2' gaugestartAngle='245' gaugeendAngle='-65' lowerLimitDisplay='"+ minStr + "' upperLimitDisplay='" + maxStr + "' showTickMarks='1' showTickValues='1' autoScale='1' bgAlpha='0' bgColor='FFFFFF' lowerLimit='" + minMax[0][0]+ "' upperLimit='" + minMax[minMax.length - 1][1] + "' numberSuffix='" + postfix + "' showBorder='0' basefontColor='025B6A' chartTopMargin='15' chartBottomMargin='15' chartLeftMargin='0' chartRightMargin='0' gaugeFillMix='{dark-10},FFFFFF,{dark-10}' gaugeFillRatio='3' >";


        //add color range
        strXml += "<colorRange>";
        for(int i = 0; i < minMax.length; i++)
        {
            strXml += "<color minValue='" + minMax[i][0] + "' maxValue='" + minMax[i][1] + "' code='" + colorList[i] + "'  />";
        }


        strXml += "</colorRange>";

        //add dials (pointers)
        strXml += "<dials>";

        for(int i = 0; i < valueList.length; i++)
        {
            strXml += "<dial borderAlpha='0' baseWidth='5' topWidth='4' value='" + valueList[i] + "' link='" + "JavaScript: " + js + "'/>";
        }

        strXml += "</dials>";

        strXml += "</chart>";

        return strXml;
    }






    //function createAngularFlashJson returns chart xml string that shows an angular meter
    public String createAngularFlashJson(String funcno, String[] valueList, String[][] minMax, String[] colorList, String minStr, String maxStr, String postfix, List resultList, HashMap option)
    {
        String row_json = "{\"funcno\": " + "\"" + funcno + "\"" + "," + "categoryColName:" + "\"" + option.get("categoryColName") + "\"" + ",";

        HashMap hm = (HashMap)resultList.get(0);
        for(Object entryObject : hm.entrySet())
        {
            Map.Entry entry = (Map.Entry)entryObject;
            row_json += "\"" + entry.getKey() + "\"" + ":" + "\"" + entry.getValue() + "\"" + ",";
        }


        row_json += "}";





        String js =
                //flash_click_handler is defined in jquery.flashchart.js
                "flash_click_handler(" + row_json +"," + 0 + ");" +
                        //"alert(\"gut\");"+
                        "void(0);" +
                        "";

        //classic color
        String strXml = "<chart palette='2' lowerLimitDisplay='"+ minStr + "' upperLimitDisplay='" + maxStr + "' showTickMarks='1' showTickValues='1' autoScale='1' bgAlpha='0' bgColor='FFFFFF' lowerLimit='" + minMax[0][0]+ "' upperLimit='" + minMax[minMax.length - 1][1] + "' numberSuffix='" + postfix + "' showBorder='0' basefontColor='025B6A' chartTopMargin='15' chartBottomMargin='15' chartLeftMargin='0' chartRightMargin='0' gaugeFillMix='{dark-10},FFFFFF,{dark-10}' gaugeFillRatio='3' >";


        //add color range
        strXml += "<colorRange>";
        for(int i = 0; i < minMax.length; i++)
        {
            strXml += "<color minValue='" + minMax[i][0] + "' maxValue='" + minMax[i][1] + "' code='" + colorList[i] + "'  />";
        }


        strXml += "</colorRange>";

        //add dials (pointers)
        strXml += "<dials>";

        for(int i = 0; i < valueList.length; i++)
        {
            strXml += "<dial borderAlpha='0' baseWidth='5' topWidth='4' value='" + valueList[i] + "' link='" + "JavaScript: " + js + "'/>";
        }

        strXml += "</dials>";


        strXml += "</chart>";

        return strXml;
    }

    //createFlashJsonWrapper is a wrapper of createMSFlashJson and createFlashJson
    //createFlashJsonWrapper takes a json string as parameter
    //and returns flash chart xml
    public String createFlashJsonWrapper(String json_param, int paging_to_show)
    {
        try
        {
            //System.out.println("paging_to_show is " + paging_to_show);
            //System.out.println(json_param);

            String decode = json_param;

            //decode = Escape.unescape(json_param);
            //decode = URLEncoder.encode(json_param, "UTF-8");

            //decode = URLDecoder.decode(json_param, "UTF-8");
            //decode = json_param;
            //System.out.println("FlashModel createFlashJsonWrapper: decode is " + decode);




		/*
		try {
			System.out.println(new String(json_param.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/


            //parse json_param to JSONObject
            JSONObject jo = null;

            String type = "";
            String funcno = "";
            String sql = "";



            String categoryPerPage = "";



            JSONArray colNamesJson = null;

            String categoryColName = "";
            ArrayList categoryList = new ArrayList();


            JSONArray seriesColNamesJson = null;
            ArrayList seriesList = new ArrayList();





            String[][] multiSeriesValueList = null;

            //String[] seriesList = null;

            List resultList = null;

            HashMap option = null;
            try {



                jo = JSONObject.fromObject(decode);


                type = "" + jo.get("type");

                funcno = "" + jo.get("funcno");



                option = new HashMap();
                //option for display
                //newly added categoryColName in option
                option.put("categoryColName", "" + jo.get("categoryColName"));


                option.put("color", jo.getJSONArray("meterRangeColor").toArray());

                option.put("caption", "" + jo.get("caption"));

                option.put("xAxisName", "" + jo.get("xAxisName"));
                option.put("yAxisName", "" + jo.get("yAxisName"));
                option.put("xUnit", "" + jo.get("xUnit"));
                option.put("yUnit", "" + jo.get("yUnit"));
                option.put("yPrefix", "" + jo.get("yPrefix"));

                option.put("captionFont", "" + jo.get("captionFont"));
                option.put("captionFontSize", "" + jo.get("captionFontSize"));
                option.put("captionFontColor", "" + jo.get("captionFontColor"));
                option.put("captionBold", "" + jo.get("captionBold"));
                option.put("captionUnderline", "" + jo.get("captionUnderline"));
                option.put("captionItalic", "" + jo.get("captionItalic"));


                option.put("axisFont", "" + jo.get("axisFont"));
                option.put("axisFontSize", "" + jo.get("axisFontSize"));
                option.put("axisFontColor", "" + jo.get("axisFontColor"));
                option.put("axisBold", "" + jo.get("axisBold"));
                option.put("axisUnderline", "" + jo.get("axisUnderline"));
                option.put("axisItalic", "" + jo.get("axisItalic"));

                option.put("bgColor", "" + jo.get("bgColor"));
                option.put("bgAlpha", "" + jo.get("bgAlpha"));



                //wrap the original sql read from database to enable paging

                //get category per page for paging
                categoryPerPage = "" + jo.get("categoryPerPage");
                int categoryPerPageInt = Integer.parseInt(categoryPerPage);


                //sqlPaging will wrap the sql and generate a paging sql

                //String sqlPaging = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ENAME, sum(DEPCODE) S FROM EMPLOYEE group by ENAME order by ENAME asc) A WHERE ROWNUM <= " +  paging_to_show * categoryPerPageInt + ") B WHERE RN >=" + ((paging_to_show-1) * categoryPerPageInt + 1);
                sql = "" + jo.get("sql");





                //filter sql by adding where
                String filter = "" + jo.get("filter");
                if(jo.get("filter") != null)
                {
                    sql = SQLParserFull.addWhere(sql, SQLParserFull.AND, filter);
                    //sql = sql + " where " + filter;

                    //System.out.println(filter);
                }



                String sqlPaging = "SELECT * FROM (SELECT A__.*, ROWNUM RN__ FROM (" +
                        sql +
                        ") A__ WHERE ROWNUM <= " +  paging_to_show * categoryPerPageInt + ") B__ WHERE RN__ >=" + ((paging_to_show-1) * categoryPerPageInt + 1);





                //query database to get data
                QueryDAO cb = new QueryDAO();
                resultList = cb.queryHashDataForm(sqlPaging);


                //trim resultList
                for(Object o : resultList)
                {
                    HashMap hm = (HashMap)o;

                    for(Object entryObj : hm.entrySet())
                    {
                        Map.Entry entry = (Map.Entry)entryObj;
                        entry.setValue(("" + entry.getValue()).trim());


                    }

                }



		/*
			//query database to get number of data rows
			String sqlPagingTotal = "SELECT COUNT(*) C FROM (" +
															sql +
															")";

			long sqlPagingTotalLong = cb.queryCountData(sqlPagingTotal);
			//System.out.println("FlashModel createFlashJsonWrapper: sqlPagingTotal is " + sqlPagingTotalLong);
			*/



                categoryColName = "" + jo.get("categoryColName");


                seriesColNamesJson = jo.getJSONArray("seriesColNames");
                //System.out.println(categoryColName);



                //resultListTrans uses column as key
                HashMap resultListTrans = new HashMap();
                colNamesJson = jo.getJSONArray("colNames");

                //add __RN__ to represent row number for paging
                colNamesJson.add("RN__");


                for(int i = 0; i < colNamesJson.size(); i++)
                {
                    ArrayList al = new ArrayList();
                    resultListTrans.put("" + colNamesJson.get(i), al);
                }


                for(int i = 0; i < resultList.size(); i++)
                {
                    HashMap row = (HashMap)resultList.get(i);
                    Iterator iter = row.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();

                        //System.out.println(entry.getKey());

                        ArrayList al = (ArrayList)resultListTrans.get("" + entry.getKey());

                        if(al == null)
                        {
                            System.out.println("resultListTrans al is null. 请检查 COLNAMES, CATEGORY_COLNAME and SERIES_COLNAMES in SYS_CHART_DEF in oracle 是否包含" + entry.getKey());
                            return "";
                        }


                        //delete blanks

                        al.add(("" + entry.getValue()).trim());

                        //System.out.println(al.get(i));

                    }

                }


                //find the category and series column to show
                categoryList = (ArrayList)resultListTrans.get(categoryColName);

                for(int i = 0; i < seriesColNamesJson.size(); i++)
                {
                    seriesList.add("" + seriesColNamesJson.get(i));
                }


                multiSeriesValueList = new String[categoryList.size()][seriesList.size()];
                for(int j = 0; j < seriesList.size(); j++)
                {
                    //System.out.println(seriesList.get(j));

				/*System.out.println(resultListTrans.get("ENAME"));
				System.out.println(resultListTrans.get("BS"));
				System.out.println(resultListTrans.get("RN"));*/
                    ArrayList al = (ArrayList)resultListTrans.get(seriesList.get(j));

                    for(int i = 0; i < categoryList.size(); i++)
                    {
                        multiSeriesValueList[i][j] = "" + al.get(i);
                    }
                }




            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                //System.out.println("oh no");
                e1.printStackTrace();
            }catch(Exception e)
            {
                e.printStackTrace();
            }


            //get chartXml from FlashModel
            String chartXml = "";

            //System.out.println("FlashModel createFalshJsonWrapper: before branching, type is " + type);



            String[] categoryArray = new String[categoryList.size()];
            for(int i = 0; i < categoryList.size(); i++)
            {
                categoryArray[i] = "" + categoryList.get(i);
            }

            String[] seriesArray = new String[seriesList.size()];
            for(int i = 0; i < seriesList.size(); i++)
            {
                seriesArray[i] = "" + seriesList.get(i);
            }


            //handle multi series chart
            if(type.startsWith("MS"))
            {
                //System.out.println("Entering MS ");
                chartXml = createMSFlashJson(funcno, categoryArray, seriesArray, multiSeriesValueList, resultList, option);


            }
            //handle stacked chart, stacked chart is similar to multi series chart
            else if(type.startsWith("Stacked"))
            {
                chartXml = createMSFlashJson(funcno, categoryArray, seriesArray, multiSeriesValueList, resultList, option);

            }
            else if(type.startsWith("Radar"))
            {
                //System.out.println("Radar Radar");
                chartXml = createMSFlashJson(funcno, categoryArray, seriesArray, multiSeriesValueList, resultList, option);

            }
            //handle angular meter chart
            else if (type.startsWith("AngularGauge"))
            {

                //String[] valueList = {"70", "10"};

                //use categoryArray as value
                String[] valueList = categoryArray;

                //System.out.println(categoryArray[0]);

                //meterRangeColor stores the corresponding color of the meter range
                JSONArray meterRangeColor = jo.getJSONArray("meterRangeColor");
                String[] colorList = new String[meterRangeColor.size()];
                for(int i = 0; i < colorList.length; i++)
                {
                    colorList[i] = "" + meterRangeColor.get(i);
                }

                //minMax is a 2-dimensional array while meterRange is a 1-dimensional array
                //we convert meterRange like 0,10,10,30,30,100
                //to minMax 0, 10
                //			10, 30
                //			30, 100
                JSONArray meterRange = jo.getJSONArray("meterRange");

                //System.out.println(meterRange);
                String[][] minMax = new String[meterRange.size() / 2][2];
                for(int i = 0; i < minMax.length; i++)
                {
                    minMax[i][0] = "" + meterRange.get(i * 2);
                    minMax[i][1] = "" + meterRange.get(i * 2 + 1);
                }

                String minStr = "" + jo.get("xAxisName");
                String maxStr = "" + jo.get("yAxisName");

                String postfix = "" + jo.get("xUnit");


                if(type.startsWith("AngularGauge_360"))
                {
                    chartXml = createAngular360FlashJson(funcno, valueList, minMax, colorList, minStr, maxStr, postfix, resultList, option);
                }
                else if(type.startsWith("AngularGauge_A"))
                {
                    chartXml = createAngularAFlashJson(funcno, valueList, minMax, colorList, minStr, maxStr, postfix, resultList, option);
                }
                else
                {
                    chartXml = createAngularFlashJson(funcno, valueList, minMax, colorList, minStr, maxStr, postfix, resultList, option);
                }

            }
            //handle single series chart
            else
            {
                String[] valueList = new String[categoryList.size()];
                for(int i = 0; i < categoryList.size(); i++)
                {
                    //System.out.println("category is " + categoryList[i]);
                    //System.out.println("value is " + valueList[i]);
                    valueList[i] = multiSeriesValueList[i][0];


                }
                chartXml = createFlashJson(funcno, categoryArray, valueList, resultList, option);
            }

            return chartXml;
        }catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }




    //create multi series flash json
    //创建多系列图表 包括多系列柱状图，多系列折线图
    //需要自行修改此方法
    //该方法返回String strXML
    //注意该strXML组成
    public String createMSFlashJson(String funcno, String[] categoryList, String[] seriesList, String[][] multiSeriesValueList, List resultList, HashMap option)
    {
        try{




            //unescapeLink = '0' ensures that chinese characters will display normally in link
            String strXML = "<chart numberPrefix='" + option.get("yPrefix") +
                    "' bgColor='" + option.get("bgColor") +
                    "' bgAlpha='" + option.get("bgAlpha") +
                    "' numberSuffix='" + option.get("yUnit") +
                    "' yAxisName='" + option.get("yAxisName") +
                    "' xAxisName='" + option.get("xAxisName") +
                    "' labelDisplay='WRAP' unescapeLinks='0' palette='3' caption='" + option.get("caption") + "' useRoundEdges='1' formatNumberScale='0' rotateValues='0' placeValuesInside='1' decimals='0' showBorder='0' baseFont='幼圆'  baseFontSize='12'>";

            String strStyle = "";
            strStyle = " <styles>" +
                    "<definition>" +
                    "<style name='myCaptionFont' type='font' font='"+ option.get("captionFont") + "' size='" + option.get("captionFontSize") + "' color='" + option.get("captionFontColor") + "' bold='"+ option.get("captionBold") + "' underline='" + option.get("captionUnderline") + "' italic='" + option.get("captionItalic") + "'/>" +
                    "<style name='myAxisTitlesFont' type='font' font='"+ option.get("axisFont") + "' size='" + option.get("axisFontSize") + "' color='" + option.get("axisFontColor") + "' bold='"+ option.get("axisBold") + "' underline='" + option.get("axisUnderline") + "' italic='" + option.get("axisItalic") + "'/>" +
                    "</definition>" +
                    "<application>" +
                    "<apply toObject='Caption' styles='myCaptionFont' />" +
                    "<apply toObject='XAxisName' styles='myAxisTitlesFont' />" +
                    "<apply toObject='YAxisName' styles='myAxisTitlesFont' />" +
                    "</application>" +
                    "</styles>";
            strXML += strStyle;


            //Initialize <categories> element - necessary to generate a multi-series chart
            String strCategories = "<categories>";


            for(int i = 0; i < categoryList.length; i++)
            {
                strCategories += "<category label='" + categoryList[i].toString().trim() + "' />";
            }

            //Close <categories> element
            strCategories += "</categories>";


            //Initiate <dataset> elements
            String strValue = "";

            //System.out.println(seriesList.length);
            //Iterate through the data
            for(int j = 0; j < seriesList.length; j++)
            {

                Object[] colorList = (Object[])option.get("color");

                String color = "";
                if(j < colorList.length)
                {
                    color = "" + colorList[j];
                }

                strValue += "<dataset color='"+ color +"' seriesName='" + seriesList[j] + "'>";
                for(int i=0;i < categoryList.length;i++)
                {
                    String row_json = "{\"funcno\": " + "\"" + funcno + "\"" + "," + "categoryColName:" + "\"" + option.get("categoryColName") + "\"" + ",";

                    HashMap hm = (HashMap)resultList.get(i);
                    for(Object entryObject : hm.entrySet())
                    {
                        Map.Entry entry = (Map.Entry)entryObject;
                        row_json += "\"" + entry.getKey() + "\"" + ":" + "\"" + entry.getValue() + "\"" + ",";
                    }


                    row_json += "}";

	                	        	/* System.out.println("FlashModel createMSFlashJson: row_json is " + row_json);
	                				 System.out.println(resultList);*/



                    //be careful with the js string here
                    //test it first in explorer and then put it into the java
                    String js =
                            //flash_click_handler is defined in jquery.flashchart.js
                            "flash_click_handler(" + row_json +"," + i + ");" +
                                    "void(0);" +
                                    "";




                    strValue += "<set value='" + multiSeriesValueList[i][j] +  "' link='" + "JavaScript: " + js + "'/>";
                }


                //Close <dataset> elements
                strValue += "</dataset>";

            }

            //Assemble the entire XML now
            strXML += strCategories + strValue + "</chart>";



            System.out.println(strXML);


            return strXML;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    //create single series flash json
    //创建单系列图表 包括饼图,柱状图等
    //需要自行修改此方法
    //该方法返回String strXML
    //注意该strXML组成
    public String createFlashJson(String funcno, String[] categoryList, String[] valueList, List resultList, HashMap option)
    {

        try
        {
            //System.out.println("single series");

	/*	for (int i = 0; i < resultList.size(); i++)
		{
			HashMap hm = (HashMap)resultList.get(i);
			//System.out.println(hm.entrySet());
			for(Object entryObject : hm.entrySet())
			{
				Map.Entry entry = (Map.Entry)entryObject;
				System.out.println(entry);
			}

		}
		*/



            //unescapeLink = '0' ensures that chinese characters will display normally in link
            String strXML = "<chart numberPrefix='" + option.get("yPrefix") +
                    "' bgColor='" + option.get("bgColor") +
                    "' bgAlpha='" + option.get("bgAlpha") +
                    "' numberSuffix='" + option.get("yUnit") +
                    "' yAxisName='" + option.get("yAxisName") +
                    "' xAxisName='" + option.get("xAxisName") +
                    "' labelDisplay='WRAP' unescapeLinks='0' palette='3' caption='" + option.get("caption") +"' useRoundEdges='1' formatNumberScale='0' rotateValues='0' showPercentValues='1' showLabels='1' showValues='0' placeValuesInside='1' decimals='0' showBorder='0' baseFont='STKaiti'  baseFontSize='12'>";

            String strStyle = "";
            strStyle = " <styles>" +
                    "<definition>" +
                    "<style name='myCaptionFont' type='font' font='"+ option.get("captionFont") + "' size='" + option.get("captionFontSize") + "' color='" + option.get("captionFontColor") + "' bold='"+ option.get("captionBold") + "' underline='" + option.get("captionUnderline") + "' italic='" + option.get("captionItalic") + "'/>" +
                    "<style name='myAxisTitlesFont' type='font' font='"+ option.get("axisFont") + "' size='" + option.get("axisFontSize") + "' color='" + option.get("axisFontColor") + "' bold='"+ option.get("axisBold") + "' underline='" + option.get("axisUnderline") + "' italic='" + option.get("axisItalic") + "'/>" +
                    "</definition>" +
                    "<application>" +
                    "<apply toObject='Caption' styles='myCaptionFont' />" +
                    "<apply toObject='XAxisName' styles='myAxisTitlesFont' />" +
                    "<apply toObject='YAxisName' styles='myAxisTitlesFont' />" +
                    "</application>" +
                    "</styles>";
            strXML += strStyle;

            //Initiate <dataset> elements
            String strValue = "";


            //Iterate through the data
            for(int i=0; i < categoryList.length;i++)
            {
                String row_json = "{\"funcno\": " + "\"" + funcno + "\"" + "," + "categoryColName:" + "\"" + option.get("categoryColName") + "\"" + ",";

                HashMap hm = (HashMap)resultList.get(i);
                for(Object entryObject : hm.entrySet())
                {
                    Map.Entry entry = (Map.Entry)entryObject;
                    row_json += "\"" + entry.getKey() + "\"" + ":" + "\"" + entry.getValue() + "\"" + ",";
                }


                row_json += "}";

                //System.out.println("FlashModel createFlashJson: row_json is " + row_json);

                //be careful with the js string here
                //test it first in explorer and then put it into the java
                String js =
                        //flash_click_handler is defined in jquery.flashchart.js
                        "flash_click_handler(" + row_json +"," + i + ");" +
                                "void(0);" +
                                "";


                Object[] colorList = (Object[])option.get("color");

                String color = "";
                if(i < colorList.length)
                {
                    color = "" + colorList[i];
                }
                strValue += "<set color='" + color + "' label='" + categoryList[i] + option.get("xUnit") +"' value='" + valueList[i] + "' link='" + "JavaScript: " + js + "'/>";
            }




            //Assemble the entire XML now
            strXML +=  strValue + "</chart>";



            System.out.println("createFlashJson: strXML is " + strXML);


            return strXML;
        }catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }

    }
}
