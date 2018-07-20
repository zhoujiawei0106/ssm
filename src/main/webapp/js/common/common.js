var common = {
	/**
	 * @author zhoujiawei
	 * 将null或"null"字符串置为""
	 * @param obj
	 * @returns
	 */
	null2empty : function(obj) {
	    if (!obj || "null" == obj)
	        return "";
	    for (var p in obj) {
//	        if (obj[p] instanceof Object) {
//	            obj[p] = null2empty(obj[p]);
//	        } else if (!obj[p] || "null" == obj[p]) {
//	            obj[p] = "";
//	        }
	        if (!obj[p] || "null" == obj[p]) {
	            obj[p] = "";
	        }
	    }
	    return obj;
	},
	/**
	 * 将null或"null"字符串置为"0"
	 * @author zhoujiawei
	 * @param obj
	 * @returns
	 */
	null2zero : function(obj) {
	    return common.isEmpty(obj) ? "0" : obj;
	},
	/**
	 * 判断是否为空
	 * @author zhoujiawei
	 * @param obj
	 * @returns {Boolean}
	 */
	isEmpty : function(obj) {
		if(obj == undefined || obj.length == 0 || obj == null) {
	        return true;
	    }
	    return false;
	},
	/**
	 * 判断是否不为空
	 * @author zhoujiawei
	 * @param obj
	 * @returns
	 */
	isNotEmpty : function(obj) {
	    return !common.isEmpty(obj);
	},
	/**
	 * 生成uuid，在grid中添加数据(addRowData方法)时没有id时使用
	 * @author zhoujiawei
	 * @returns
	 */
	uuid : function() {
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    // 设置uuid长度32位
	    for (var i = 0; i < 32; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
//	    s[8] = s[13] = s[18] = s[23] = "-"; // "-"分隔，和guid相似
	 
	    var uuid = s.join("");
	    return uuid;
	},
	/**
	 * 生成日期(年月日)
	 * @author zhoujiawei
	 * @param regex 分隔符
	 * @param count 减去的时间
	 */
	systemDate : function(regex, count) {
		var date = new Date();
		// 年
		var year = date.getFullYear() - count;
		// 月
		var month = date.getMonth()+1;
		// 日
		var day = date.getDate();
		// 如果月份是个位数，在月份前拼接0
		if (month.toString().length == 1) {
			month = "0" + month;
		}
		// 如果日期是个位数，在日期前拼接0
		if (day.toString().length == 1) {
			day = "0" + day;
		}
		return year + regex + month + regex + day;
	},
    /**
	 * ajax封装方法
	 * @author zhoujiawei
     * @param type 请求类型
     * @param url 请求url
     * @param data 数据
     * @param successFunction 成功执行方法
     * @param errorFunction 失败执行方法
     */
	ajaxRequest : function (type, url, data, async, successFunction, errorFunction) {
        $.ajax({
            type: type,
            url: url,
            data: data,
            dataType: "json",
			async: async,
            success: function(data){
                successFunction(data);
            },
            error : function (XMLHttpRequest, textStatus, errorThrown) {
                errorFunction(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    },
    /**
	 * ajax请求失败执行方法
     * @param XMLHttpRequest
     * @param textStatus
     * @param errorThrown
     */
	errorFunction : function (XMLHttpRequest, textStatus, errorThrown) {
        console.log(XMLHttpRequest);
        console.log(textStatus);
        console.log(errorThrown);
        alert("系统异常,请联系管理员!");
    }
}
