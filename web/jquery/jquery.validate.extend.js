
  //字母数字
 jQuery.validator.addMethod("alnum", function(value, element) {
   return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
 }, "只能包括英文字母和数字");
 //浮点数
 jQuery.validator.addMethod("float", function(value, element) {
	   return this.optional(element) || /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|[0-9]$/.test(value);
}, "必须是浮点数");
 //全字母
 jQuery.validator.addMethod("letter", function(value, element) {
	   return this.optional(element) || /^[A-Za-z]+$/.test(value);
}, "必须是纯英文字母");
 
  // 手机号码验证   
 jQuery.validator.addMethod("cellphone", function(value, element) {
   var length = value.length;
   return this.optional(element) || (length == 11 && /^(1\d{10})$/.test(value));
 }, "请正确填写手机号码"); 
 
  // 电话号码验证   
 jQuery.validator.addMethod("telephone", function(value, element) {
   var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
   return this.optional(element) || (tel.test(value));
 }, "请正确填写电话号码");
 
 // 邮政编码验证
 jQuery.validator.addMethod("zipcode", function(value, element) {
   var tel = /^[0-9]{6}$/;
   return this.optional(element) || (tel.test(value));
 }, "请正确填写邮政编码");
 
//金额
 jQuery.validator.addMethod("money", function(value, element) {
	
	if(/([0-9]+\.[0-9]{2})$/.test(value)){
		return true;
	}
	
	if(/[0-9]$/.test(value))
		return true;
	
	if(/([1-9][0-9]+)$/.test(value))
		return true;
	
   return this.optional(element) ;
 }, "金额必须是保留两位的小数或者整数");
 
  // 汉字
 jQuery.validator.addMethod("chcharacter", function(value, element) {
   var tel = /^[\u4e00-\u9fa5]+$/;
   return this.optional(element) || (tel.test(value));
 }, "请输入汉字");
 
 //身份证
 jQuery.validator.addMethod("ssn", function(value, element) {
	   return this.optional(element) || isIdCardNo(value);   
	 }, "请正确输入身份证号码");
	 
 /**
 * 身份证号码验证
 *
 */
function isIdCardNo(num) {

 var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
 var parityBit=new Array("1","0","X","9","8","7","6","5","4","3","2");
 var varArray = new Array();
 var intValue;
 var lngProduct = 0;
 var intCheckDigit;
 var intStrLen = num.length;
 var idNumber = num;
   // initialize
     if ((intStrLen != 15) && (intStrLen != 18)) {
         return false;
     }
     // check and set value
     for(i=0;i<intStrLen;i++) {
         varArray[i] = idNumber.charAt(i);
         if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
             return false;
         } else if (i < 17) {
             varArray[i] = varArray[i] * factorArr[i];
         }
     }
     
     if (intStrLen == 18) {
         //check date
         var date8 = idNumber.substring(6,14);
         if (isDate8(date8) == false) {
            return false;
         }
         // calculate the sum of the products
         for(i=0;i<17;i++) {
             lngProduct = lngProduct + varArray[i];
         }
         // calculate the check digit
         intCheckDigit = parityBit[lngProduct % 11];
         // check last digit
         if (varArray[17] != intCheckDigit) {
             return false;
         }
     }
     else{        //length is 15
         //check date
         var date6 = idNumber.substring(6,12);
         if (isDate6(date6) == false) {

             return false;
         }
     }
     return true;
     
 };
 /**
  * 判断是否为“YYYYMM”式的时期
  *
  */
 function isDate6(sDate) {
    if(!/^[0-9]{6}$/.test(sDate)) {
       return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false
    if (month < 1 || month > 12) return false
    return true
 };
 /**
  * 判断是否为“YYYYMMDD”式的时期
  *
  */
 function isDate8(sDate) {
    if(!/^[0-9]{8}$/.test(sDate)) {
       return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1]=29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
 }; 
/*
 * ^[1-9]\d*$　 　 //匹配正整数
^-[1-9]\d*$ 　 //匹配负整数
^-?[1-9]\d*$　　 //匹配整数
^[1-9]\d*|0$　 //匹配非负整数（正整数 + 0）
^-[1-9]\d*|0$　　 //匹配非正整数（负整数 + 0）
^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$　　 //匹配正浮点数
^-([1-9]\d*\.\d*|0\.\d*[1-9]\d*)$　 //匹配负浮点数
^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$　 //匹配浮点数
^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
评注：处理大量数据时有用，具体应用时注意修正

匹配特定字符串：
^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
^\w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串


 * */
 function preview(url,width,height,id){
		
		var preview = document.getElementById(id);   
		preview.innerHTML="";
		  
		var fileext=url.substring(url.lastIndexOf("."),url.length);      
		fileext=fileext.toLowerCase();   
		if((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp')){   
		    alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传！");       
		}else{   
		        preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
		        preview.style.width = width+"px";   
		        preview.style.height = height+"px";    
		 }   
	};
