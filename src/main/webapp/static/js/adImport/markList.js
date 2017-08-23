$(function() {
	loadRecordLists();
});

//加载剧本标记列表
function loadRecordLists() {
	$("#tcdPageCode").createPage({
		url: "/playMark/queryScriptMarkList",
		data: {},
		pageSize: 20,
		successFn: function(response) {
			if (response.status == 1) {
				alert(response.message);
				return;
			};
			
			var recordList = response.data.markList;
			var kGoodsList = '';
			var kGoodsListId = '';
			var lRoleNameList ='';
			var recordTrArray = [];
			$.each(recordList, function(index, item) {
				kGoodsList ='';
				kGoodsListId ='';
				for (var g = 0; g < recordList[index].goodsList.length; g++) {
					if (g == 0) {
						kGoodsList += recordList[index].goodsList[g].goods
						kGoodsListId += recordList[index].goodsList[g].id
					} else {
						kGoodsList += '|' + recordList[index].goodsList[g].goods
						kGoodsListId += '|' + recordList[index].goodsList[g].id
					}
				}
				recordTrArray.push("<tr>");
				recordTrArray.push("	<td width='15%'><a class='text-primary'  roundId=\""+ item.playRound.id +"\" href='javascript:void(0)'  onclick='showViewDetail(this)'>"+ item.playRound.seriesNo + "-" + item.playRound.roundNo +"</a></td>");
				recordTrArray.push("	<td width='15%' class='over-hide' title='"+ item.word +"'>"+ item.word +"</td>");
				recordTrArray.push("	<td width='25%' class='over-hide' title='"+ kGoodsList +"'>"+ kGoodsList+"</td>");
				if(item.roleNameList !=null){
					recordTrArray.push("	<td width='10%' class='over-hide' title='"+ item.roleNameList.join("|") +"'>"+ item.roleNameList.join("|") +"</td>");
				}else{
					recordTrArray.push("	<td width='10%' class='over-hide' title=''></td>");
				}
				recordTrArray.push("	<td width='10%' class='over-hide' title='"+ item.description +"'>"+ item.description +"</td>");
				recordTrArray.push("</tr>");
			});

			$("#implantRecordList").html(recordTrArray.join(""));
		}
	});
}

//显示场次详情弹窗
function showViewDetail(own) {
    var roundId = $(own).attr("roundId");

    console.log(roundId);
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