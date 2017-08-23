var logCommites=""; //日志详细内容
$(document).ready(function(){
	ajaxQueryLogs();

    // 时间插件
    createTime()
})

//加载日志信息列表
function ajaxQueryLogs(){
$("#tcdPageCode").createPage({
		url: "/logManage/queryLog",
		data: {},
		pageSize: 10,
		successFn: function(data) {
			var dataHtml = "";
        	for	(var i = 0; i < data.data.logManageList.length; i++) {
        	var status =data.data.logManageList[i].status=='0'?"成功":"失败";
        	dataHtml += '<li class="userManage-data">'
        	dataHtml += '<ul class="userManage-data-in">'
			dataHtml += '<li>'+data.data.logManageList[i].userModel.userName+'</li>'
			dataHtml += '<li>'+data.data.logManageList[i].menuModel.menuName+'</li>'
			dataHtml += '<li>'+data.data.logManageList[i].ip+'</li>'
			dataHtml += '<li>'+formatDate(data.data.logManageList[i].createTime)+'</li>'
			dataHtml += '<li>'+status+'</li>'
			dataHtml += '<li> <a class="logCommiteClick" href="javascript:seeDetails();">'+data.data.logManageList[i].commite+'</a></li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	//查看日志详细内容
        	$(".logCommiteClick").click(function () {
        		logCommites=$(this).text();
        	})
		}
	});
}

//根据账号搜索日志记录
function search(){
	var userName = $("#daily-input-search").val();
	var	startTime =$("#start-date").val()=="开始日期"?null:$("#start-date").val();
	var endTime =$("#end-date").val()=="结束日期"?null:$("#end-date").val();
	var status =$("#status option:selected").val()=="状态"?null:$("#status option:selected").val();
	$("#tcdPageCode").createPage({
		url: "/logManage/queryLog",
		data: {userName:userName,startTime:startTime,endTime:endTime,status:status},
		pageSize: 10,
		successFn: function(data) {
			var dataHtml = "";
        	for	(var i = 0; i < data.data.logManageList.length; i++) {
        	var status =data.data.logManageList[i].status=='0'?"成功":"失败";
        	
        	dataHtml += '<li class="userManage-data">'
        	dataHtml += '<ul class="userManage-data-in">'
    		dataHtml += '<li>'+data.data.logManageList[i].userModel.userName+' </li>'
    		dataHtml += '<li>'+data.data.logManageList[i].menuModel.menuName+' </li>'
			dataHtml += '<li>'+data.data.logManageList[i].ip+' </li>'
			dataHtml += '<li>'+formatDate(data.data.logManageList[i].createTime)+' </li>'
			dataHtml += '<li>'+status+' </li>'
			dataHtml += '<li> <a class="logCommiteClick" href="javascript:seeDetails();">'+data.data.logManageList[i].commite+'</a></li>'
			dataHtml += '</ul>'
			dataHtml += '</li>'
        	}
        	$(".usrManage-table-td").html(dataHtml)
        	
        	//查看日志详细内容
        	$(".logCommiteClick").click(function () {
        		logCommites=$(this).text();
        	})
		}
	});
	
}

//查看日志详情
function seeDetails(type){
	if(type == "iconfont"){
		$("#logCommiteShade").attr("style", "display:none;");
		$("#logCommitePop").attr("style", "display:none;");
	}else{
		$("#logCommiteShade").attr("style", "display:block;");
		$("#logCommitePop").attr("style", "display:block;");
		$(".feed-text").html(logCommites);
	}
}


// 时间插件
function createTime() {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    var checkin = $('#start-date').fdatepicker({
        format: 'yyyy-mm-dd',
        onRender: function (date) {
        }
    }).on('changeDate', function (ev) {
        if (ev.date.valueOf() > checkout.date.valueOf()) {
        }
        checkin.hide();
        if($("#end-date").val() == "开始日期") {
        }
    }).data('datepicker');

    var checkout = $('#end-date').fdatepicker({
        format: 'yyyy-mm-dd',
        onRender: function (date) {
            return date.valueOf() <= checkin.date.valueOf()-1 ? 'disabled' : '';
        }
    }).on('changeDate', function (ev) {
        checkout.hide();
        if($("#start-date").val() == "结束日期") {
        }
    }).data('datepicker');
}