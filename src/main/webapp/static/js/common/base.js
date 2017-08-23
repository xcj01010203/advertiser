// 公共ajax方法
function doPost(url, params, successCall, errorCall, async, completeCall, type, dataType, contentType) {
    $.ajax({
        url: url || "",  // url路径
        data: params || {},  // 要穿的参数
        success: function (result) {
            if (successCall && (typeof(successCall) == "function")) {
                successCall(result);
            }
        },
        error: function (result) {
            if (errorCall && (typeof(errorCall) == "function")) {
                errorCall(result);
            }
        },
        async: async == false ? false : true,  //不传为异步
        complete: function (result) {
            if (completeCall && (typeof(completeCall) == "function")) {
                completeCall(result);
            }
        },
        type: type || "post",  // 不传为post
        dataType: dataType || 'json',  // 不传为json
        contentType: contentType || 'application/x-www-form-urlencoded;charset=utf-8'
    });
}

function doPostAjax(url,param,successCall){
	$.ajax({
        url:  url,
        method: 'post',
        contentType: 'application/json', // 
        data: JSON.stringify(param), // 以json字符串方式传递
        success: function(data) {
        	successCall(data.data);
        },
		async:true
    });
}

/**
 * 时间转换
 *   用法      dateTime.format(text)         text：自定义格式  ('yyyy-MM-dd HH:mm:ss EE')
 * @param
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "\u65e5",
        "1": "\u4e00",
        "2": "\u4e8c",
        "3": "\u4e09",
        "4": "\u56db",
        "5": "\u4e94",
        "6": "\u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
Date.prototype.addDays = function (d) {
    this.setDate(this.getDate() + d);
};


String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
};

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
};

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};