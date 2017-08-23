$(function() {
	loadPlayList();
	checkAnalyseStatus();
});

//加载剧本列表
function loadPlayList() {
	$("#playPage").createPage({
		url: "/play/queryPlayList",
		pageSize: 10,
		successFn: function(response) {
			if (response.status == 1) {
				alert(response.message);
				return;
			}
			
			var playList = response.data.playList;
			
			var playTrArray = [];
			$.each(playList, function(index, item) {
				playTrArray.push("<tr>");
				playTrArray.push("	<td>"+ (index + 1) +"</td>");
				playTrArray.push("	<td class='over-hide'>"+ item.name +"</td>");
				playTrArray.push("	<td>"+ new Date(item.uploadTime).format("yyyy-MM-dd") +"</td>");
				playTrArray.push("</tr>");
			});
			
			$("#playListTbody").html(playTrArray.join(""));
		}
	});
}

//自动分析剧本中的广告
function autoAnalyse() {
	$("#analyseStatus").removeClass("hidden");
	$("#analyseStatus").removeClass("alert-success");
	$("#analyseStatus").removeClass("alert-danger");
	$("#analyseStatus").addClass("alert-warning");
	
	$("#ananlyseBtn").attr("disabled", true);
	
	$("#analyseStatus").find("strong").text("提示：");
	$("#analyseStatus").find("span").text("正在解析，请稍候...");
	
	var url = "/analyseResult/saveAnalysisResult";
	var successFn = function(response) {
		if (response.status == 1) {
			return;
		}
		checkAnalyseStatus();
	};
	doPost(url, {pageSize:10000}, successFn);
}

//检查当前分析状态
function checkAnalyseStatus() {
	var checkInterval = null;
	
	var url = "/analyseResult/queryAnalysisJob";
	var successFn = function(response) {
		if (response.status == 1) {
			alert(response.message);
			return;
		}
		var result = response.data;
		if (result == null) {
			clearInterval(checkInterval);
			$("#ananlyseBtn").attr("disabled", false);
			return;
		}
		if (result.status == 0 || result.status == 1) {
			clearInterval(checkInterval);
		}
		
		if (result.status == 0) {	//执行成功
			$("#analyseStatus").removeClass("hidden");
			$("#analyseStatus").removeClass("alert-warning");
			$("#analyseStatus").removeClass("alert-danger");
			$("#analyseStatus").addClass("alert-success");
			
			$("#ananlyseBtn").attr("disabled", false);
			
			$("#analyseStatus").find("strong").text("解析成功！");
			
			var startTime = new Date(result.startTime).getTime();
			var endTime = new Date(result.endTime).getTime();
			var timeInterval = calculateTime(endTime - startTime);
			
			$("#analyseStatus").find("span").text("解析时间：" + new Date(result.startTime).format("yyyy-MM-dd HH:mm:ss") + "，用时" + timeInterval);
		}
		if (result.status == 1) {	//执行失败
			$("#analyseStatus").removeClass("hidden");
			$("#analyseStatus").removeClass("alert-warning");
			$("#analyseStatus").removeClass("alert-success");
			$("#analyseStatus").addClass("alert-danger");

			$("#ananlyseBtn").attr("disabled", false);
			
			$("#analyseStatus").find("strong").text("解析失败！");
			$("#analyseStatus").find("span").text("解析失败，解析时间：" + new Date(result.startTime).format("yyyy-MM-dd HH:mm:ss"));
		}
		if (result.status == 2) {	//执行中
			$("#analyseStatus").removeClass("hidden");
			$("#analyseStatus").removeClass("alert-success");
			$("#analyseStatus").removeClass("alert-danger");
			$("#analyseStatus").addClass("alert-warning");
			
			$("#ananlyseBtn").attr("disabled", true);
			
			$("#analyseStatus").find("strong").text("提示：");
			$("#analyseStatus").find("span").text("正在解析，请稍候...");
		}
		
	};
	
	checkInterval = setInterval(function() {
		doPost(url, {}, successFn);
	}, 1000);
}

//把给定的毫秒值转化为时分秒的格式
function calculateTime(timeNumber) {
	var hours = parseInt(divide(timeNumber, 1000 * 60 * 60));
	var minit = parseInt(divide(subtract(timeNumber, multiply(hours, 1000 * 60 * 60)), 1000 * 60));
	var second = parseInt(divide(subtract(timeNumber, multiply(minit, 1000 * 60)), 1000));
	
	var result = [];
	if (hours) {
		result.push(hours + "小时");
	}
	if (minit) {
		result.push(minit + "分钟");
	}
	if (second) {
		result.push(second + "秒");
	}
	
	return result.join("")
}