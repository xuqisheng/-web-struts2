/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
        required: "必填",
		remote: "请修正该字段",
		email: "邮件格式错误",
		url: "网址错误",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "必须是数字",
		digits: "必须是整数",
		creditcard: "信用卡号非法",
		equalTo: "值不相同",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("长度不应超过 {0}"),
		minlength: jQuery.validator.format("长度不应小于{0}"),
		rangelength: jQuery.validator.format("长度应介于 {0}到 {1}之间"),
		range: jQuery.validator.format("请输入一个介于 {0}到 {1}之间的值"),
		max: jQuery.validator.format("最大值不能超过 {0}"),
		min: jQuery.validator.format("最小值不能小于 {0}")
});