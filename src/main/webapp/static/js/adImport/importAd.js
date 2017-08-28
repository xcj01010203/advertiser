$(function() {
	loadRecordList();
});

//加载广告列表
function loadRecordList() {
	$("#tcdPageCode").createPage({
		url: "/implantRecord/queryRecordList",
		data: {},
		pageSize: 20,
		successFn: function(response) {
			if (response.status == 1) {
                modelWindow(response.msssage)
				return;
			};
			
			var recordList = response.data.recordList;
			
			var recordTrArray = [];
			$.each(recordList, function(index, item) {
				recordTrArray.push("<tr>");
				recordTrArray.push("	<td width='15%'><a class='text-primary'  roundId=\""+ item.playRound.id +"\" href='javascript:void(0)'  onclick='showViewDetail(this)'>"+ item.playRound.seriesNo + "-" + item.playRound.roundNo +"</a></td>");
				recordTrArray.push("	<td width='15%' class='over-hide' title='"+ item.name +"'>"+ item.name +"</td>");
				recordTrArray.push("	<td width='25%' class='over-hide' title='"+ item.roleNameList.join("|") +"'>"+ item.roleNameList.join("|") +"</td>");
				recordTrArray.push("	<td width='10%' class='over-hide' title='"+ item.implantMode.name +"'>"+ item.implantMode.name +"</td>");
				recordTrArray.push("	<td width='10%' class='over-hide' title='"+ item.goods.goods +"'>"+ item.goods.goods +"</td>");
				recordTrArray.push("	<td width='25%' class='over-hide' title='"+ filterNull(item.desc) +"'>"+ filterNull(item.desc) +"</td>");
				recordTrArray.push("</tr>");
			});

			$("#implantRecordList").html(recordTrArray.join(""));
		}
	});
}

//显示场次详情弹窗
function showViewDetail(own) {
    var roundId = $(own).attr("roundId");

    xiaotuObj.roundId = roundId;
    // 获取剧本内容信息
    getPlayContentInfo(roundId)

    //给对应的tr添加active标识
    $("#implantRecordList").find("tr").removeClass("active");
    $(own).parent("td").parent("tr").addClass("active");

    //打开弹窗
    $("#viewDetailDiv").modal("show");
    $("#seriesRoundNo").text($(own).text());

	// 重新刷一下剧本分析的内容
	refreshAnalyContent()

}